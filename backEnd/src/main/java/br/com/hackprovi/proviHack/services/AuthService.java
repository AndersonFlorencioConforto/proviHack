package br.com.hackprovi.proviHack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hackprovi.proviHack.models.User;
import br.com.hackprovi.proviHack.repositories.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository repository;
	
	
	@Transactional(readOnly = true)
	public User authenticated() throws Exception {
		try {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return repository.findByEmail(username);
		}catch (Exception e) {
			throw new Exception("error");
		}
		
	}

}
