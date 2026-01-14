package com.rag.airagservice.config;

import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;


@ApplicationScoped
public class EmbeddingStoreConfig {

    private final PgVectorEmbeddingStore store;

    @Inject
    public EmbeddingStoreConfig(PgVectorEmbeddingStore store) {
        this.store = store;
    }
    @Produces
    @ApplicationScoped
    public EmbeddingStoreIngestor embeddingStoreIngestor() {
        return EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .build();
    }
}