package com.collegeproject.service;

import com.collegeproject.model.User;
import com.collegeproject.model.UserAuthenticationToken;

public interface UserAuthenticationTokenService {

	public UserAuthenticationToken createToken(User user);

	public void deleteToken(UserAuthenticationToken authenticationToken);

	public boolean isValidToken(String token);

	public void clearUnusedTokens();

	public UserAuthenticationToken findByToken(String token);

}
