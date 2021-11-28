package br.com.hackprovi.proviHack.controllers;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.hackprovi.proviHack.dtos.UserDto;
import br.com.hackprovi.proviHack.dtos.UserInsertDto;
import br.com.hackprovi.proviHack.dtos.UserUpdateDto;
import br.com.hackprovi.proviHack.services.UserService;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	@Autowired
    private UserService service;
	
	
	@GetMapping
    public ResponseEntity<Page<UserDto>> findAllNivel(Pageable pageable,@RequestParam(required = false) String nivel) {
		Page<UserDto> list;
		if(nivel != null) {
			list = service.findAllNivel(nivel, pageable);
		}else {
			list = service.findAllPaged(pageable);
		}
        return ResponseEntity.ok().body(list);
    }
	
	
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) throws Exception {
        UserDto dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserInsertDto dto) {
        UserDto newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,@Valid @RequestBody UserUpdateDto dto) {
        UserDto newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
