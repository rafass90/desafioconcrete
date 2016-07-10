package com.concrete.desafio.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.concrete.desafio.vo.User;

public class JWTUtil {

    public static String sign(User user) {

        final String issuer = "";
        final String clientId = user.getId().toString(); // this is the audience
        final String clientSecret = user.getPassword();
        final String name = user.getName();
        final String email = user.getEmail();
        final String subject = "auth0|usuario";

         final long iat = System.currentTimeMillis() / 1000l;
         final long exp = iat + 30000L;
        // Base64 Decode Auth0 Client Secret before signing !!!
        final JWTSigner signer = new JWTSigner(new Base64(true).decodeBase64(clientSecret));
        final HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("name", name);
        claims.put("email", email);
        claims.put("email_verified", "true");
        claims.put("iss", issuer);
        claims.put("roles", new String[] {"ROLE_ADMIN"});
        claims.put("sub", subject);
        claims.put("aud", clientId);
        claims.put("exp", exp);
        claims.put("iat", iat);

        final String token = signer.sign(claims);
        return token;

    }
    
    public static void verifier(User user){

        final JWTVerifier jwtVerifier = new JWTVerifier(new Base64(true).decodeBase64(user.getPassword()), user.getId().toString(), "");
        Map<String, Object> decodedPayload = null;
		try {
			decodedPayload = jwtVerifier.verify(user.getToken());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JWTVerifyException e) {
			e.printStackTrace();
		}
    }

}