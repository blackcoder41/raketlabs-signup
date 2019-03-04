package com.raketlabs.dao;

import java.util.List;
import java.util.Optional;

import com.raketlabs.model.UserInfo;
import com.raketlabs.model.UserRole;


public interface LoginDao {

	Optional<UserInfo> findUserInfo(String username);

	List<UserRole> getUserRoles(String username);
}