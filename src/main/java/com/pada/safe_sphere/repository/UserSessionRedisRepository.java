package com.pada.safe_sphere.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class UserSessionRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final long SESSION_TIMEOUT = 5; // Timeout in minutes

    public UserSession getSession(String userId) {
        String sessionData = redisTemplate.opsForValue().get(userId);
        if (sessionData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(sessionData, UserSession.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize session data", e);
        }
    }

    public void saveSession(String userId, UserSession session) {
        try {
            String sessionData = objectMapper.writeValueAsString(session);
            redisTemplate.opsForValue().set(userId, sessionData, SESSION_TIMEOUT, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize session data", e);
        }
    }

    public void removeSession(String userId) {
        redisTemplate.delete(userId);
    }
}

