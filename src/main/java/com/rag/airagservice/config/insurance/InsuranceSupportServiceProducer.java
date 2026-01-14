package com.rag.airagservice.config.insurance;

import com.rag.airagservice.domain.service.support.insurance.InsuranceSupportService;
import com.rag.airagservice.inbound.api.ChatMemoryStore;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.service.AiServices;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class InsuranceSupportServiceProducer {

    private final RetrievalAugmentor retrievalAugmentor;
    private final ChatLanguageModel chatLanguageModel;
    private final ChatMemoryStore chatMemoryProvider;

    @Inject
    public InsuranceSupportServiceProducer(
            @InsuranceRag RetrievalAugmentor retrievalAugmentor,
            ChatLanguageModel chatLanguageModel,
            ChatMemoryStore chatMemoryProvider
    ) {
        this.retrievalAugmentor = retrievalAugmentor;
        this.chatLanguageModel = chatLanguageModel;
        this.chatMemoryProvider = chatMemoryProvider;
    }

    @Produces
    @ApplicationScoped
    InsuranceSupportService insuranceSupportService() {
        return AiServices.builder(InsuranceSupportService.class)
                .chatLanguageModel(chatLanguageModel)
                .retrievalAugmentor(retrievalAugmentor)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }
}