package com.pada.safe_sphere.service;


import com.pada.safe_sphere.repository.UserSession;
import com.pada.safe_sphere.repository.UserSessionRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SessionManager {

    private final UserSessionRedisRepository userSessionRepository;

    public UserSession getSession(String userId) {
        return userSessionRepository.getSession(userId);
    }

    public void saveSession(String userId, UserSession session) {
        userSessionRepository.saveSession(userId, session);
    }

    public void clearSession(String userId) {
        userSessionRepository.removeSession(userId);
    }
}
