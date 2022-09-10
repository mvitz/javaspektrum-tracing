package de.mvitz.apm.example.backend.session;

import de.mvitz.apm.example.backend.util.DelayGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SessionIdGenerator {

    private final DelayGenerator delayGenerator;

    public SessionIdGenerator(DelayGenerator delayGenerator) {
        this.delayGenerator = delayGenerator;
    }

    public String next() {
        delayGenerator.delay(150);
        return UUID.randomUUID().toString();
    }
}
