package com.rag.airagservice.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DocumentIngestionService {

    private final DocumentIngestor documentIngestor;

    @Inject
    public DocumentIngestionService(DocumentIngestor documentIngestor) {
        this.documentIngestor = documentIngestor;
    }

    public CompletionStage<Integer> ingestDocument(String content, String documentName, String domain) {
        return documentIngestor.ingest(content, documentName, domain);
    }
}