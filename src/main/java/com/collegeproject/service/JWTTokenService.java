package com.collegeproject.service;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.collegeproject.codetype.NotificationInfo;
import com.collegeproject.core.CollegeProjectConstants;
import com.collegeproject.core.TransactionInfo;
import com.collegeproject.model.User;
import com.collegeproject.model.UserAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component
public class JWTTokenService {

	@Autowired
	TransactionInfo transactionInfo;

	@Autowired
	UserAuthenticationTokenService userAuthenticationTokenService;

	public static final String SECRET = "cd+Pr1js+w2qfT2BoCD+tPcYp9LbjpmhSMEJqUob1mcxZ7+Wmik4AYdjX+DlDjmE4yporzQ9tm7v3z/j+QbdYg==";

	public String getJWTToken(User user) {
		String jwtToken = Jwts.builder().setId(UUID.randomUUID().toString())
				.setNotBefore(new Date(System.currentTimeMillis())).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + CollegeProjectConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return jwtToken;
	}

	public String getJWTTokenPasswordReset() {
		String jwtToken = Jwts.builder().setId(UUID.randomUUID().toString())
				.setNotBefore(new Date(System.currentTimeMillis())).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + CollegeProjectConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(SECRET)).compact();
		return jwtToken;
	}

	public Boolean isValidJWTToken(String token) {

		if (null != token) {
			try {
				Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
						.parseClaimsJws(token).getBody();
				Date now = new Date();
				Date notBeforeTime = claims.getNotBefore();
				Date expirationTime = claims.getExpiration();

				if (notBeforeTime.compareTo(now) > 0) {
					transactionInfo.generateException("unathenticated_request", NotificationInfo.ERROR,
							Status.UNAUTHORIZED.getStatusCode());
				}
				if (expirationTime.compareTo(now) <= 0) {
					transactionInfo.generateException("unathenticated_request", NotificationInfo.ERROR,
							Status.UNAUTHORIZED.getStatusCode());
				}
			} catch (ExpiredJwtException eje) {
				UserAuthenticationToken expiredToken = userAuthenticationTokenService.findByToken(token);
				userAuthenticationTokenService.deleteToken(expiredToken);
				return false;
			}

			return true;
		}
		return false;
	}

}