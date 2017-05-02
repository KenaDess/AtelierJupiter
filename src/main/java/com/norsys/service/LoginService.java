package com.norsys.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.norsys.service.exception.AuthenticationException;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    private static final Logger LOGGER = Logger.getLogger(LoginService.class);

    private static final String GOOD_PASSWORD = "Jupiter";
    private static final int MAX_ATTEMPT = 5;

    private LoadingCache<String, Integer> attemptsCache;

    public LoginService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String user) {
                return 0;
            }
        });
    }

    public LoginService(LoadingCache<String, Integer> attemptsCache) {
        this.attemptsCache = attemptsCache;
    }

    public void authenticate(String username, String password) throws AuthenticationException {
        if (GOOD_PASSWORD.equals(password)) {
            loginSucceeded(username);
        } else if (isBlocked(username)) {
            throw new AuthenticationException();
        } else {
            loginFailed(username);
            throw new BadCredentialsException("Mauvaise identification");
        }
    }

    private void loginSucceeded(String username) {
        attemptsCache.invalidate(username);
    }

    private void loginFailed(String username) {
        int attempts;
        try {
            attempts = attemptsCache.get(username);
        } catch (ExecutionException e) {
            attempts = 0;
            LOGGER.debug(e.getMessage(), e);
        }
        attempts++;
        attemptsCache.put(username, attempts);
    }

    private boolean isBlocked(String username) {
        try {
            return attemptsCache.get(username) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            LOGGER.debug(e.getMessage(), e);
            return false;
        }
    }

    public void setAttemptsCache(LoadingCache<String, Integer> attemptsCache) {
        this.attemptsCache = attemptsCache;
    }
}