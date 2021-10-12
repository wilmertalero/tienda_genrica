package com.grupo3.tiendagenerica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grupo3.tiendagenerica.entity.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long>{
	
	

}
