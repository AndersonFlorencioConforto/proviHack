package br.com.hackprovi.proviHack.dtos;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import br.com.hackprovi.proviHack.models.User;

public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@Size(min = 2, max = 50, message = "Deve conter entre 2 e 50 caracteres.")
	@NotBlank(message = "Campo obrigatório.")
	private String name;
	@Email(message = "Favor inserir um email válido.")
	private String email;
	private String nivel;
	private String photo;
	@Size(min = 10, max = 200, message = "Deve conter entre 10 e 200 caracteres.")
	@NotBlank(message = "Campo obrigatório.")
	private String description;
	private String url;
	private String state;
	private String city;
	private String function;

	public UserDto() {

	}

	public UserDto(Long id, String name, String email, String nivel, String photo, String description, String url,
			String state,String city,String function) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.nivel = nivel;
		this.photo = photo;
		this.description = description;
		this.url = url;
		this.state = state;
		this.city = city;
		this.function = function;
	}

	public UserDto(User obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.email = obj.getEmail();
		this.nivel = obj.getNivel();
		this.photo = obj.getPhoto();
		this.description = obj.getDescription();
		this.url = obj.getUrl();
		this.state = obj.getState();
		this.city = obj.getCity();
		this.function = obj.getFunction();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
	
	
	
	

}
