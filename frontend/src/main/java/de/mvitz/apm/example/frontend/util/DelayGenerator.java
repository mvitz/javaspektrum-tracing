package de.mvitz.apm.example.frontend.util;

import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class DelayGenerator {

    private final Random random = new Random();

    @WithSpan
    public void delay(int bound) {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(bound));
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
