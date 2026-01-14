package com.rag.airagservice.domain.service;

import java.util.concurrent.CompletionStage;

public interface DocumentIngestor {
    CompletionStage<Integer> ingest(String content, String documentName, String domain);
}