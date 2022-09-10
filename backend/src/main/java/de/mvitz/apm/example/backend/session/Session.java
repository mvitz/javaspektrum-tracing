package de.mvitz.apm.example.backend.session;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

public final class Session {

    private final String id;
    private final Map<String, String> attributes = new HashMap<>();

    public Session(String id) {
        this.id = requireNonNull(id);
    }

    public String getId() {
        return id;
    }

    public void addAttribute(String name, String value) {
        this.attributes.put(
                requireNonNull(name, "name must not be null"),
                requireNonNull(value, "value must be not null"));
    }

    public Map<String, String> asMap() {
        return unmodifiableMap(attributes);
    }
}
