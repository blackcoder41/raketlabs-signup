package com.raketlabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.raketlabs.model.UserRole;

@Repository
@Component
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	List<UserRole> findByUserName(String userName);
}
