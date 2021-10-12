package com.grupo3.tiendagenerica.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.grupo3.tiendagenerica.repository.UserRepository;
import com.grupo3.tiendagenerica.entity.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	/*@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;*/
	
	@Override
	public Iterable getAllUsers() {
		return userRepository.findAll();
	}
	
	private boolean checkUsernameExist(User user) throws Exception {
		Optional<User> userfound = userRepository.findByUsername(user.getUsername());
		if (userfound.isPresent()) {
			throw new Exception("Nombre de usuario ya existe!");
		}
		return true;
	}
	
	private boolean checkPasswordValid(User user) throws Exception {
		if ( !user.getPassword().equals(user.getConfirmaPassword())) {
			throw new Exception("Password y Confirmacion Password no son iguales");
		}
		return true;
	}

	@Override
	public User createUser(@Valid User user) throws Exception {
		if (checkUsernameExist(user) && checkPasswordValid(user)) {
			//instanciamos un nuevo objeto BCrypPasswordEncoder
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
			//Pasamos el password que contiene el objeto user, lo encriptamos y los asignamos
			//a una nueva variable
			String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
			//Se asigna el password encriptado al objeto user
			user.setPassword(encodedPassword);
			user = userRepository.save(user);
		}
		return user;
	}
	
	@Override
	public User getUserByCedula(long cedula_usuario)throws Exception {
		return userRepository.findBycedula_usuario(cedula_usuario).orElseThrow(() ->  new Exception("El usuario no existe!"));
	}
	
	@Override
	public User updateUser(User fromUser) throws Exception{
		User toUser = getUserByCedula(fromUser.getCedula_usuario());
		mapUser(fromUser, toUser);
		return userRepository.save(toUser);
	}
	
	protected void mapUser(User from,User to) {
		to.setEmail_usuario(from.getEmail_usuario());
		to.setEnabled(from.isEnabled());
		to.setAuthority(from.getAuthority());
		to.setUsername(from.getUsername());
	}
	
	@Override
	public void deleteUser(Long cedula_usuario) throws Exception {
		User user = getUserByCedula(cedula_usuario);
		
		userRepository.delete(user);
	}

}
