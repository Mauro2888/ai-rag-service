package com.rag.airagservice.vm.document;

import java.util.concurrent.CompletionStage;

public interface DocumentResource {
    CompletionStage<DocumentIngestResponse> ingest(DocumentIngestRequest request);
}
