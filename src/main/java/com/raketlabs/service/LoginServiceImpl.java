package com.raketlabs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raketlabs.dao.LoginDao;
import com.raketlabs.model.UserInfo;
import com.raketlabs.model.UserRole;

@Service
public class LoginServiceImpl implements UserDetailsService {


	LoginDao loginDao;

	@Autowired
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = loginDao.findUserInfo(userName);

		if (userInfo.isEmpty()) {
			throw new UsernameNotFoundException("username was not found in the database");
		}

		List<UserRole> roles = loginDao.getUserRoles(userName);

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		if (roles != null) {
			for (UserRole role : roles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
				grantList.add(authority);
			}
		}
		
		UserDetails userDetails = new User(userInfo.get().getUserName(), userInfo.get().getPassword(), grantList);

		return userDetails;
	}

}