package org.example.bdccaiagent.controllers;

import org.example.bdccaiagent.agents.AIAgent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
public class AgentController {
    private AIAgent agent;

    public AgentController(AIAgent agent) {
        this.agent = agent;
    }

    @GetMapping(value = "/askAgent", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> chat(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Mono.just("Veuillez poser une question.");
        }
        
        // Collecte tous les chunks du Flux en une seule chaîne
        return agent.onQuery(query)
                .collectList()
                .map(chunks -> String.join("", chunks))
                .onErrorResume(error -> {
                    return Mono.just("Erreur lors de la génération de la réponse: " + 
                            (error.getMessage() != null ? error.getMessage() : "Erreur inconnue"));
                });
    }
}

