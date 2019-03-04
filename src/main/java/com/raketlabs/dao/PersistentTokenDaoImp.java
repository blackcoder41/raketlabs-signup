package com.raketlabs.dao;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.raketlabs.model.PersistentLogins;
import com.raketlabs.repository.TokenRepository;

@Repository("persistentTokenRepository")
@Transactional
public class PersistentTokenDaoImp implements PersistentTokenRepository {

	@Autowired
	TokenRepository tokenRepository;
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		PersistentLogins logins = new PersistentLogins();
		logins.setUserName(token.getUsername());
		logins.setSeries(token.getSeries());
		logins.setToken(token.getTokenValue());
		logins.setLastUsed(token.getDate());
		tokenRepository.save(logins);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		Optional<PersistentLogins> logins = tokenRepository.findBySeries(seriesId);

		if (logins.isPresent()) {
			return new PersistentRememberMeToken(
					logins.get().getUserName(), 
					logins.get().getSeries(), 
					logins.get().getToken(),
					logins.get().getLastUsed());
		}

		return null;
	}

	@Override
	public void removeUserTokens(String userName) {
		tokenRepository.deleteByUserName(userName);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		Optional<PersistentLogins> logins = tokenRepository.findBySeries(series);
		
		if (logins.isPresent()) {
			logins.get().setSeries(series);
			logins.get().setToken(tokenValue);
			logins.get().setLastUsed(lastUsed);
			tokenRepository.save(logins.get());
		} else {
			throw new IllegalArgumentException("Can't update token.");
		}
	}

}