package de.mvitz.apm.example.backend.session;

import de.mvitz.apm.example.backend.util.DelayGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("sessions")
public class SessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    private final SessionService sessionService;
    private final DelayGenerator delayGenerator;

    public SessionController(SessionService sessionService, DelayGenerator delayGenerator) {
        this.sessionService = sessionService;
        this.delayGenerator = delayGenerator;
    }

    @PostMapping
    public ResponseEntity<String> createSession() {
        LOGGER.info("Creating new session");
        delayGenerator.delay(50);

        final var session = sessionService.createSession();

        delayGenerator.delay(250);
        return ResponseEntity.ok(session.getId());
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<Void> updateSession(@PathVariable String sessionId,
                                              @RequestBody Map<String, String> body) {
        LOGGER.info("Updating session {}", sessionId);
        delayGenerator.delay(50);

        try {
            body.forEach((attributeName, attributeValue) ->
                    sessionService.updateSession(sessionId, attributeName, attributeValue));
        } catch (IllegalStateException e) {
            delayGenerator.delay(250);
            return ResponseEntity.notFound().build();
        }

        delayGenerator.delay(250);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Map<String, String>> transmitSession(@PathVariable String sessionId) {
        LOGGER.info("Transmitting session {}", sessionId);
        delayGenerator.delay(50);

        try {
            final var session = sessionService.transmitSession(sessionId);

            delayGenerator.delay(250);
            return ResponseEntity.ok(session.asMap());
        } catch (IllegalStateException e) {
            delayGenerator.delay(250);
            return ResponseEntity.notFound().build();
        }
    }
}
