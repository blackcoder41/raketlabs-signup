package com.raketlabs.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raketlabs.model.UserInfo;
import com.raketlabs.repository.UserRepository;

@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	UserRepository loginRepository;
	
	@Override
	public List<UserInfo> list() {
		return loginRepository.findAll();
	}

	@Override
	public UserInfo findUserByUsername(String userName) {
		return loginRepository.findByUserName(userName).get();
	}

	@Override
	public void update(String userName, String password) {
		UserInfo user = new UserInfo();
		user.setUserName(userName);
		user.setPassword(password);
		loginRepository.save(user);
	}

	@Override
	public void add(String userName, String password) {
		UserInfo user = new UserInfo();
		user.setUserName(userName);
		user.setPassword(password);
		loginRepository.save(user);
	}

	@Override
	public boolean userExists(String userName) {
		return loginRepository.existsByUserName(userName);
	}

}
