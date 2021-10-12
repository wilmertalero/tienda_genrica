package com.grupo3.tiendagenerica.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.*;

import com.grupo3.tiendagenerica.entity.Authority;

@Entity
public class User {

	@Id
	@NotBlank
	private Long cedula_usuario;
	
	@Column(unique = true) 
	@Email 
	@NotBlank
	private String email_usuario;

	@Column
	@NotBlank
	private String username;

	@Column
	@NotBlank
	private String password;

	@Column
	@NotNull
	private boolean enabled;
	
	@Transient
	@NotBlank
	private String confirmaPassword;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "authorities_users", joinColumns = @JoinColumn(name = "usuario_cedula"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private Set<Authority> authority;
	
	public User() {	}
	
	public User(Long cedula_usuario) {
		this.cedula_usuario = cedula_usuario;
	}       
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cedula_usuario == null) ? 0 : cedula_usuario.hashCode());
		return result;
	}

	//Getters y Setters
	public Long getCedula_usuario() {
		return cedula_usuario;
	}


	public void setCedula_usuario(Long cedula_usuario) {
		this.cedula_usuario = cedula_usuario;
	}


	public String getEmail_usuario() {
		return email_usuario;
	}


	public void setEmail_usuario(String email_usuario) {
		this.email_usuario = email_usuario;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getConfirmaPassword() {
		return confirmaPassword;
	}

	public void setConfirmaPassword(String confirmaPassword) {
		this.confirmaPassword = confirmaPassword;
	}


	public Set<Authority> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<Authority> authority) {
		this.authority = authority;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (cedula_usuario == null) {
			if (other.cedula_usuario != null)
				return false;
		} else if (!cedula_usuario.equals(other.cedula_usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [cedula_usuario=" + cedula_usuario + ", email_usuario=" + email_usuario + ", username=" + username
				+ ", password=" + password + ", enabled=" + enabled + ", confirmaPassword=" + confirmaPassword
				+ ", authority=" + authority + "]";
	}


}