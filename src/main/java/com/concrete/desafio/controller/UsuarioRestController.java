package com.concrete.desafio.controller;

import java.time.Instant;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.concrete.desafio.service.UserService;
import com.concrete.desafio.vo.Mensagem;
import com.concrete.desafio.vo.User;

@RestController
public class UsuarioRestController {

	@Inject
	UserService userService;

	@RequestMapping({"/", "/home"})
	public String home() {
        return "<h1>DESAFIO CONCRETE</h1>";
    }
	
	@RequestMapping(value= "/register", method=RequestMethod.POST, headers="Accept=application/json", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody User user, BindingResult result) {
		
		if(result.hasFieldErrors())
			return new ResponseEntity<Object>(new Mensagem(""), HttpStatus.NOT_ACCEPTABLE);
		
		try{
			user = userService.register(user);
			user.setPassword("******");//hide pass
		}catch (Exception b) {
			return new ResponseEntity<Object>(new Mensagem(b.getMessage()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(user, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST, headers="Accept=application/json", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Object> login(@RequestBody User user) {
		String tPassword = user.getPassword();
		try {
			user = userService.getByMail(user);
		} catch (Throwable e1) {
			return new ResponseEntity<Object>(new Mensagem("Usuário e/ou senha inválidos"), HttpStatus.BAD_REQUEST); 
		}
		
		try {
			userService.validatePassword(user, tPassword);
		} catch (Throwable e) {
			return new ResponseEntity<Object>(new Mensagem("Usuário e/ou senha inválidos"), HttpStatus.UNAUTHORIZED);
		}
		
		try {
			user.setLastLogin(Instant.now());
			userService.register(user);
		} catch (Throwable e) {
		}
		
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/profiles", method=RequestMethod.POST, headers="Accept=application/json", produces = "application/json; charset=UTF-8")
	public ResponseEntity<Object> profile(@RequestParam(value="id", required = true) String id, @RequestHeader(required=true, value="token") String token, @RequestBody User user) {
		if(!userService.existsToken(token))
			return new ResponseEntity<Object>(new Mensagem("Não autorizado"), HttpStatus.UNAUTHORIZED);
		User dbUser = null;
		try {
			 dbUser = userService.getById(UUID.fromString(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(new Mensagem("Não autorizado"), HttpStatus.UNAUTHORIZED);
		}
		if(!dbUser.getToken().equals(token))
			return new ResponseEntity<Object>(new Mensagem("Não autorizado"), HttpStatus.UNAUTHORIZED);
		try {
			user.setId(UUID.fromString(id));
			userService.verifyToken(token, user);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new Mensagem("Sessão inválida"), HttpStatus.UNAUTHORIZED);
		}
		dbUser.setPassword("******");//hide pass
		return new ResponseEntity<Object>(dbUser, HttpStatus.OK);	
		
	}
	
}
