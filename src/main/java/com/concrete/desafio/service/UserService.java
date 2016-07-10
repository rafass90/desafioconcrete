package com.concrete.desafio.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.concrete.desafio.repository.UserRepository;
import com.concrete.desafio.security.HashUtil;
import com.concrete.desafio.security.JWTUtil;
import com.concrete.desafio.vo.User;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User register(User user) throws Exception{
		
		//verifica se existe e-mail
		if(userRepository.existsUserByEmail(user.getEmail()))
			throw new Exception("E-mail já existente"); 
		
		Instant now = Instant.now();
		if(user.getId() == null){
			user.setId(UUID.randomUUID());
			user.setCreated(now);
			user.setModified(now);
			user.setPassword(HashUtil.getHash(user.getPassword()));
			try {
				user.setToken(JWTUtil.sign(user));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		user.setLastLogin(now);
		
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao inserir usuário");
		}
	}
	
	public User getById(UUID id) throws Exception {
		try{
		return userRepository.findById(id).get();
	}catch(Exception t){
		throw new Exception("Registro não encontrado");
	}catch(Throwable t){
		throw new Exception("Erro desconhecido");
	}
}
	
	public User getByMail(User user) throws Exception {
		System.out.println("Email" + user.getEmail());
	
		try{
			return userRepository.findByEmail(user.getEmail()).get();
		}catch(Exception t){
			throw new Exception("Registro não encontrado");
		}catch(Throwable t){
			throw new Exception("Erro desconhecido");
		}
	}
	
	public void validatePassword(User user, String password) throws Exception, Throwable {
		if(!user.getPassword().equals(HashUtil.getHash(password)))
			throw new Exception("Senhas não correspondem");
	}

	public boolean existsToken(String token){
		try{
			return userRepository.existsUserByToken(token);
		}catch (Exception e) {
			return false;
		}
	}

	public void verifyToken(String token, User user) throws Exception {
		user.setToken(token);
		user.setPassword(HashUtil.getHash(user.getPassword()));
		try {
			JWTUtil.verifier(user);
		} catch (Exception e) {
			throw e;
		}
	}

	
}
