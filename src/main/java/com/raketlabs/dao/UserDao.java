package com.raketlabs.dao;

import java.util.List;

import com.raketlabs.model.UserInfo;


public interface UserDao {

	public List<UserInfo> list();

	public UserInfo findUserByUsername(String username);

	public void update(String username, String password);

	public void add(String username, String password);

	public boolean userExists(String username);

}