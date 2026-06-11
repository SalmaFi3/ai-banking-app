package org.example.bdccaiagent.agents;


import org.example.bdccaiagent.tools.AgentTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AdvisorUtils;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;

@Service
public class AIAgent {
    private ChatClient chatClient;

    public AIAgent(ChatClient.Builder chatClient, ChatMemory chatMemory, AgentTools agentTools, VectorStore vectorStore) {
        this.chatClient = chatClient
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultTools(agentTools)
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

    public Flux<String> onQuery(String query) {
        // Réponse personnalisée pour un bonjour initial
        String lower = query == null ? "" : query.toLowerCase();
        if (lower.contains("bonjour")) {
            String greeting = "Bonjour et bienvenue sur notre application **CodeQueens** !\n\n" +
                    "Je suis votre assistant virtuel. Vous pouvez par exemple me demander :\n" +
                    "- de vous expliquer les fonctionnalités de l’application,\n" +
                    "- comment gérer vos comptes ou vos clients,\n" +
                    "- ou toute autre question liée à CodeQueens.";
            return Flux.just(greeting);
        }

        return chatClient
                .prompt()
                .system("Tu es un assistant virtuel pour l'application bancaire **CodeQueens**. " +
                        "Ton rôle est d'aider les utilisateurs avec les fonctionnalités de l'application bancaire CodeQueens uniquement. " +
                        "Réponds en français, de façon claire et polie. " +
                        "\n\n" +
                        "IMPORTANT : " +
                        "- Ne mentionne JAMAIS de CV, de personnes (comme Mohamed YOUSSFI), ou d'informations personnelles. " +
                        "- Concentre-toi UNIQUEMENT sur l'application CodeQueens : gestion de comptes bancaires, clients, transactions, etc. " +
                        "- Si l'utilisateur pose une question générale, réponds sur les fonctionnalités de CodeQueens. " +
                        "- Quand c'est pertinent, propose 2 ou 3 idées de questions que l'utilisateur peut poser sur CodeQueens.")
                .user(query)
                .stream()
                .content();
    }
}
