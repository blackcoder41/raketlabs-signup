package com.raketlabs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.raketlabs.model.UserInfo;

@Repository
@Component
public interface UserRepository extends JpaRepository<UserInfo, Long> {
	Optional<UserInfo> findByUserName(String userName);
	
	boolean existsByUserName(String userName);
}
