package de.mvitz.apm.example.frontend;

import io.opentelemetry.api.trace.Span;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UiController {

    private final BackendSessionService sessionService;

    public UiController(BackendSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @PostMapping
    public String create() {
        final var sessionId = sessionService.create();
        return "redirect:/" + sessionId;
    }

    @GetMapping("/{sessionId}")
    public String show(@PathVariable String sessionId, Model model) {
        model.addAttribute("sessionId", sessionId);
        return "show";
    }

    @PutMapping("/{sessionId}")
    public String update(@PathVariable String sessionId,
                         @ModelAttribute("key") String key,
                         @ModelAttribute("value") String value) {
        sessionService.update(sessionId, key, value);
        return "redirect:/" + sessionId;
    }

    @DeleteMapping("/{sessionId}")
    public String transmit(@PathVariable String sessionId, Model model) {
        final var session = sessionService.transmit(sessionId);

        model.addAttribute("data", session);
        return "transmit";
    }
}
