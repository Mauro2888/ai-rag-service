package com.rag.airagservice.outbound.ingestion;

import com.rag.airagservice.domain.service.DocumentIngestor;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@ApplicationScoped
public class DocumentIngestionService implements DocumentIngestor {

    private final Logger log = LoggerFactory.getLogger(DocumentIngestionService.class);

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;
    private final ManagedExecutor managedExecutor;

    @Inject
    public DocumentIngestionService(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel,
            ManagedExecutor managedExecutor
    ) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
        this.managedExecutor = managedExecutor;
    }

    @Override
    public CompletionStage<Integer> ingest(
            String content,
            String documentName,
            String domain
    ) {
        var promise = supplyAsync(() -> ingestSync(content, documentName, domain),
                managedExecutor
        );
        promise.thenAccept(chunks ->
                log.info("Ingestion completed: {} ({} chunks)", documentName, chunks)
        );

        promise.exceptionally(ex -> {
            log.error("Errore ingestion document {}", documentName, ex);
            return null;
        });

        return promise;
    }

    private int ingestSync(String content, String documentName, String domain) {

        log.info("start ingestion: {}", documentName);

        var metadata = new Metadata();
        metadata.put("source", documentName);
        if (domain != null) {
            metadata.put("domain", domain);
        }

        var document = Document.from(content, metadata);

        var splitter =
                DocumentSplitters.recursive(500, 100);

        var ingestor = EmbeddingStoreIngestor.builder()
                        .documentSplitter(splitter)
                        .embeddingStore(embeddingStore)
                        .embeddingModel(embeddingModel)
                        .build();

        ingestor.ingest(document);

        return (int) Math.ceil((double) content.length() / 500);
    }
}