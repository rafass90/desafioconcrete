package com.concrete.desafio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.desafio.repository.UserRepository;
import com.concrete.desafio.service.UserService;
import com.concrete.desafio.vo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	User user;
	
	@Before
    public void setupMock() {
       MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testMock(){
		assertNotNull(user);
		assertNotNull(userRepository);
	}
	
	@Test
	public void testSave(){
		when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(true);
		assertThat(userRepository.existsUserByEmail(user.getEmail()));
		
		when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(true);
		assertThat(userRepository.existsUserByEmail(user.getEmail()));
				
		when(userRepository.save(user)).thenReturn(user);
		assertThat(userRepository.save(user) != null);
	}
	

}
