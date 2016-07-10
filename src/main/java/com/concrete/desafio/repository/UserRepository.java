package com.concrete.desafio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.concrete.desafio.vo.User;

public interface UserRepository extends Repository<User, UUID>{

	Optional<User> findByEmail(String email);
	
	Optional<User> findByToken(String token);
	
	Optional<User> findById(UUID id);
	
	@Query("select u from User u where u.id = :id and u.password = :passw")
	User findByIdAndPassword(@Param("id")UUID id, @Param("passw")String password);
	
	User save(User user);
	
	@Query("select count(u) > 0 from User u where email = :email")
	boolean existsUserByEmail(@Param("email")String email);

	@Query("select count(u) > 0 from User u where token = :token")
	boolean existsUserByToken(@Param("token")String token);
}
