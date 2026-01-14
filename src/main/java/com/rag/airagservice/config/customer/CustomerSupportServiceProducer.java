package com.rag.airagservice.config.customer;

import com.rag.airagservice.domain.service.support.customer.CustomerSupportService;
import com.rag.airagservice.inbound.api.ChatMemoryStore;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.service.AiServices;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomerSupportServiceProducer {

    private final RetrievalAugmentor retrievalAugmentor;
    private final ChatLanguageModel chatLanguageModel;
    private final ChatMemoryStore chatMemoryProvider;

    @Inject
    public CustomerSupportServiceProducer(
            @CustomerSupportRag RetrievalAugmentor retrievalAugmentor,
            ChatLanguageModel chatLanguageModel,
            ChatMemoryStore chatMemoryProvider
    ) {
        this.retrievalAugmentor = retrievalAugmentor;
        this.chatLanguageModel = chatLanguageModel;
        this.chatMemoryProvider = chatMemoryProvider;
    }

    @Produces
    @ApplicationScoped
    CustomerSupportService customerSupportService() {
        return AiServices.builder(CustomerSupportService.class)
                .chatLanguageModel(chatLanguageModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }
}