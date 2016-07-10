package com.concrete.desafio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.desafio.security.HashUtil;
import com.concrete.desafio.vo.User;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTests {

	public static User user = new User();

	@Test
	public void testHash() throws Exception{
		assertThat("65E8484C8512163980C8B8C15F2A3BDE".equals(HashUtil.getHash("123456")));
	}
}