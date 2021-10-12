package com.grupo3.tiendagenerica.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.grupo3.tiendagenerica.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>  {
   
	/*
	 * por convencion spring boot tiene ciertas formas de realizar las consultas, cundo se cambian
	 * los nombres de las variables es necesario enseñarle a spring boot como se debe de hacer la 
	 * consulta para eso utilizamos la anotacion @Query!
	 */
	@Query("SELECT u FROM User u where u.cedula_usuario = ?1")
	public Optional<User> findBycedula_usuario(Long cedula_usuario);
	
	public Optional<User> findByUsername(String username);

}