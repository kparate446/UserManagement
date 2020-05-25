package com.bridgelabz.usermanagement.utility;

import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * @Created By :- krunal Parate
 * Purpose :- Created the JWT Token
 */
@Component
public class JwtToken {
// Algorithm
	SignatureAlgorithm alorithm = SignatureAlgorithm.HS256;
// secret Key used by Algorithm
	static String secretKey = "iamsecretkey";
	//  Generate the Token
	public String generateToken(String email) {
		return Jwts.builder().setId(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(alorithm, secretKey).compact();
		
	}
	// To decode Token
	public String getToken(String token) {
		Claims claim = null;
		try {
			claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (Exception e) {
		System.out.println("INVALID TOKEN");
		}
		return claim.getId();
	}
	
}