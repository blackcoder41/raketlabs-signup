package com.raketlabs.service;

import java.util.List;
import java.util.Optional;

import com.raketlabs.model.UserInfo;
import com.raketlabs.model.UserRole;

public interface UserService {

	public List<UserInfo> list();

	public Optional<UserInfo> findUserByUsername(String userName);

	public void update(String username, String password);

	public void add(String username, String password);

	public boolean userExists(String username);
	
	public List<UserRole> getUserRoles(String userName);
}