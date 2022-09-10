package de.mvitz.apm.example.backend.session;

import de.mvitz.apm.example.backend.util.DelayGenerator;
import io.opentelemetry.extension.annotations.WithSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);

    private final SessionIdGenerator idGenerator;
    private final RedisSessionRepository repository;
    private final DelayGenerator delayGenerator;

    public SessionService(SessionIdGenerator idGenerator,
                          RedisSessionRepository redisSessionRepository,
                          DelayGenerator delayGenerator) {
        this.idGenerator = idGenerator;
        this.repository = redisSessionRepository;
        this.delayGenerator = delayGenerator;
    }

    @WithSpan
    public Session createSession() {
        LOGGER.info("Creating session");
        delayGenerator.delay(100);

        final var sessionId = idGenerator.next();
        final var session = new Session(sessionId);

        repository.save(session);
        delayGenerator.delay(500);

        return session;
    }

    @WithSpan
    public void updateSession(String sessionId, String attributeName, String attributeValue) {
        LOGGER.info("Updating session {}", sessionId);
        delayGenerator.delay(100);

        final var session = getSession(sessionId);
        delayGenerator.delay(500);

        session.addAttribute(attributeName, attributeValue);

        repository.save(session);
        delayGenerator.delay(500);
    }

    @WithSpan
    public Session transmitSession(String sessionId) {
        LOGGER.info("Transmitting session {}", sessionId);
        delayGenerator.delay(100);

        final var session = getSession(sessionId);
        delayGenerator.delay(500);

        repository.delete(sessionId);
        delayGenerator.delay(500);

        return session;
    }

    private Session getSession(String sessionId) {
        return repository.findById(sessionId)
                .orElseThrow(() -> new IllegalStateException("No session found for id: " + sessionId));
    }
}
