package com.raketlabs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.raketlabs.model.PersistentLogins;

@Repository
@Component
public interface TokenRepository extends JpaRepository<PersistentLogins, Long> {
	Optional<PersistentLogins> findBySeries(String series);
	
	void deleteByUserName(String userName);
}
