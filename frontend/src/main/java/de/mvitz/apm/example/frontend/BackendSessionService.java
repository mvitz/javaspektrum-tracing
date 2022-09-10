package de.mvitz.apm.example.frontend;

import de.mvitz.apm.example.frontend.util.DelayGenerator;
import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.delete;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.http.RequestEntity.put;

@Service
public class BackendSessionService {

    private final RestTemplate restTemplate;
    private final DelayGenerator delayGenerator;

    public BackendSessionService(RestTemplate backendRestTemplate, DelayGenerator delayGenerator) {
        this.restTemplate = backendRestTemplate;
        this.delayGenerator = delayGenerator;
    }

    @WithSpan
    public String create() {
        delayGenerator.delay(500);
        final var session = restTemplate.exchange(
                post("/sessions").build(), String.class);

        return session.getBody();
    }

    @WithSpan
    public void update(String sessionId, String key, String value) {
        delayGenerator.delay(500);
        restTemplate.exchange(
                put("/sessions/{sessionId}", sessionId)
                        .contentType(APPLICATION_JSON)
                        .body("{ \"%s\": \"%s\" }".formatted(key, value)), Void.class);
    }

    @WithSpan
    public Map<?, ?> transmit(String sessionId) {
        delayGenerator.delay(500);
        final var response = restTemplate.exchange(
                delete("/sessions/{sessionId}", sessionId).build(), Map.class);
        return response.getBody();
    }
}
