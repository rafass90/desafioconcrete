package com.concrete.desafio.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

	private static String algorithm = "MD5";
	
	public static String getHash(String password) throws Exception{
		BigInteger bi = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update((password + "1y124Dd").getBytes("UTF-8"));//Salt 1y124Dd
			
			bi = new BigInteger(1, md.digest());
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new Exception("Erro ao criptografar senha");
		}
		return String.format("%1$032X", bi);
	}
	
	
}
