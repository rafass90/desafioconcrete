package com.concrete.desafio;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.desafio.repository.UserRepository;
import com.concrete.desafio.vo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;

	public static User user = new User();

	@Test
	public void testSave(){
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setName("Rafael");
		user.setEmail("joao@mail.com.br");
		user.setCreated(Instant.now());
		user.setPassword("111111");
		user.setToken("111111");

		userRepository.save(user).getId();
		assertThat(user != null);
		this.user = user;
	}

	@Test
	public void testFindByEmail(){
		userRepository.findByEmail("joao@mail.com.br");
	}

	@Test
	public void testFindByToken(){
		userRepository.findByToken("token");
	}	

	@Test
	public void testFindById(){
		userRepository.findById(user.getId());
	}	

	@Test
	public void testFindByIdAndPassword(){
		assertThat(userRepository.findByIdAndPassword(user.getId(), "222222"));
	}

	@Test
	public void testExistsUserByEmail(){
		assertThat(userRepository.existsUserByEmail(user.getEmail()));
	}

	@Test
	public void testExistsUserByToken(){
		assertThat(userRepository.existsUserByToken("111111"));
	}


}
