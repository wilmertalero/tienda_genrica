package com.grupo3.tiendagenerica.service;

import javax.validation.Valid;

import com.grupo3.tiendagenerica.entity.User;

public interface UserService {
	
	public Iterable getAllUsers();

	public User createUser(@Valid User user) throws Exception;
	
	public User getUserByCedula(long cedula_usuario) throws Exception;
	
	public User updateUser(User user) throws Exception;
	
	public void deleteUser(Long cedula_usuario) throws Exception;

}
