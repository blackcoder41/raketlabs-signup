package com.raketlabs.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raketlabs.model.UserInfo;
import com.raketlabs.model.UserRole;
import com.raketlabs.repository.UserRepository;
import com.raketlabs.repository.UserRoleRepository;

@Service
public class LoginDaoImpl implements LoginDao {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public Optional<UserInfo> findUserInfo(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public List<UserRole> getUserRoles(String userName) {
		return userRoleRepository.findByUserName(userName);
	}

}
