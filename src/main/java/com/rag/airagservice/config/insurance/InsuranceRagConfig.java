package com.rag.airagservice.config.insurance;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

@ApplicationScoped
public class InsuranceRagConfig {

    @Produces
    @ApplicationScoped
    @InsuranceRag
    ContentRetriever insuranceContentRetriever(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel
    ) {
        Filter domainFilter = metadataKey("domain").isEqualTo("insurance");

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.7)
                .filter(domainFilter)
                .build();
    }

    @Produces
    @ApplicationScoped
    @InsuranceRag
    RetrievalAugmentor insuranceRetrievalAugmentor(
            @InsuranceRag ContentRetriever contentRetriever
    ) {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(contentRetriever)
                .build();
    }
}