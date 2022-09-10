package de.mvitz.apm.example.backend.session;

import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class RedisSessionRepository {

    private final ValueOperations<String, Map<String, String>> redis;

    public RedisSessionRepository(RedisOperations<String, Map<String, String>> sessionRedisTemplate) {
        this.redis = sessionRedisTemplate.opsForValue();
    }

    @WithSpan
    public void save(Session session) {
        redis.set(session.getId(), session.asMap());
    }

    @WithSpan
    public Optional<Session> findById(String sessionId) {
        final var result = redis.get(sessionId);
        return Optional.ofNullable(result)
                .map(attributes -> {
                    final var session = new Session(sessionId);
                    attributes.forEach(session::addAttribute);
                    return session;
                });
    }

    @WithSpan
    public void delete(String sessionId) {
        redis.getAndDelete(sessionId);
    }
}
