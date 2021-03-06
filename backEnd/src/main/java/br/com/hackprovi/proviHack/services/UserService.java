package br.com.hackprovi.proviHack.services;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.hackprovi.proviHack.dtos.UriDTO;
import br.com.hackprovi.proviHack.dtos.UserDto;
import br.com.hackprovi.proviHack.dtos.UserInsertDto;
import br.com.hackprovi.proviHack.dtos.UserUpdateDto;
import br.com.hackprovi.proviHack.models.User;
import br.com.hackprovi.proviHack.repositories.UserRepository;
import br.com.hackprovi.proviHack.services.exceptions.DataBaseException;
import br.com.hackprovi.proviHack.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;
	
	
	@Autowired private S3Service s3Service;
	

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Page<UserDto> findAllPaged(Pageable pageable) {
		Page<User> list = repository.findAll(pageable);
		return list.map(x -> new UserDto(x));
	}
	
	@Transactional(readOnly = true)
	public Page<UserDto> findAllNivel(String nivel ,Pageable pageable) {
		Page<User> list = repository.findByNivelIgnoreCase(nivel, pageable);
		return list.map(x -> new UserDto(x));
	}

	@Transactional(readOnly = true)
	public UserDto findById(Long id) throws Exception {
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id n??o encontrado"));
		return new UserDto(entity);
	}

	@Transactional
	public UserDto insert(UserInsertDto dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDto(entity);
	}
	
	
	@Transactional
    public UserDto update(Long id, UserUpdateDto dto) {
        try {
            User entity = repository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDto(entity);
        } catch (EntityNotFoundException e) {
        	throw new ResourceNotFoundException("Id n??o encontrado " + id);
        }
    }
	
	
	public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
        	throw new ResourceNotFoundException("Id n??o encontrado " + id);
        } catch (DataIntegrityViolationException e) {
        	throw new DataBaseException("Viola????o de integridade");
        }

    }

	private void copyDtoToEntity(UserDto dto, User entity) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setNivel(dto.getNivel());
		entity.setPhoto(dto.getPhoto());
		entity.setDescription(dto.getDescription());
		entity.setUrl(dto.getUrl());
		entity.setState(dto.getState());
		entity.setCity(dto.getCity());
		entity.setFunction(dto.getFunction());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (user == null) {
			logger.error("Usu??rio n??o encontrado: " + username);
			throw new UsernameNotFoundException("Email n??o encontrado");
		}
		logger.info("Usu??rio encontrado " + username);
		return user;
	}

	public UriDTO uploadFile(MultipartFile file) throws IOException {
		URL url = s3Service.uploadFile(file);
		return new UriDTO(url.toString());
	}
	
	

}
