package com.collegeproject.service;



import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.collegeproject.codetype.NotificationInfo;
import com.collegeproject.codetype.RecordStatus;
import com.collegeproject.core.PasswordMasker;
import com.collegeproject.core.TransactionInfo;
import com.collegeproject.model.User;
import com.collegeproject.model.UserAuthenticationToken;
import com.collegeproject.repository.UserAuthenticationTokenRepository;

@Service
@Transactional
public class UserAuthenticationTokenServiceImpl implements UserAuthenticationTokenService {

	@Autowired
	TransactionInfo transactionInfo;
	@Autowired
	UserService userService;

	@Autowired
	UserAuthenticationTokenRepository userAuthenticationRepository;

	@Autowired
	PasswordMasker passwordMasker;

	@Autowired
	JWTTokenService jwtTokenService;

	@Override
	public UserAuthenticationToken createToken(User user) {
		String hashedPassword = null;
		try {
			hashedPassword = passwordMasker.getHashedPassword(user.getPassword());
		} catch (NoSuchAlgorithmException e) {
			transactionInfo.generateException("generic_error_message", NotificationInfo.ERROR,
					Status.UNAUTHORIZED.getStatusCode());
		}
		UserAuthenticationToken authenticationToken = null;
		User fetchedUser = userService.findByEmailAndPassword(user.getEmail(), hashedPassword);
		if (null != fetchedUser) {
			authenticationToken = userAuthenticationRepository.findByUserAndStatus(fetchedUser, RecordStatus.Active);
			if (null != authenticationToken) {
				return authenticationToken;
			} else {
				authenticationToken = new UserAuthenticationToken();
				authenticationToken.setUser(fetchedUser);
				authenticationToken.setToken(jwtTokenService.getJWTToken(fetchedUser));
				return userAuthenticationRepository.save(authenticationToken);
			}
		} else {
			transactionInfo.generateException("invalid.user", NotificationInfo.ERROR,
					Status.UNAUTHORIZED.getStatusCode());
		}
		return authenticationToken;

	}

	@Override
	public void deleteToken(UserAuthenticationToken authenticationToken) {
		if (null == authenticationToken.getToken())
			transactionInfo.generateException("unathenticated_request", NotificationInfo.ERROR,
					Status.UNAUTHORIZED.getStatusCode());
		authenticationToken = findByToken(authenticationToken.getToken());
		userAuthenticationRepository.delete(authenticationToken);
	}

	public String getUniqueToken() {
		String token = UUID.randomUUID().toString();
		while (true) {
			if (null == userAuthenticationRepository.findByToken(token)) {
				return token;
			}
		}

	}

	@Override
	public boolean isValidToken(String token) {

		UserAuthenticationToken authenticationToken = userAuthenticationRepository.findByToken(token);
		if (null != authenticationToken) {
			jwtTokenService.isValidJWTToken(authenticationToken.getToken());
			userAuthenticationRepository.save(authenticationToken);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Scheduled(cron = "0 0 0 * * *")
	public void clearUnusedTokens() {

		List<UserAuthenticationToken> allTokens = userAuthenticationRepository.findAll();
		allTokens.stream().forEach(token -> {
			if (!token.getExpiryDate().isAfter(LocalDateTime.now())) {
				deleteToken(token);
			}
		});

	}

	@Override
	public UserAuthenticationToken findByToken(String token) {
		return userAuthenticationRepository.findByToken(token);
	}
}
