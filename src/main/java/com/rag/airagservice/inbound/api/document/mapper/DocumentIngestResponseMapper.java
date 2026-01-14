package com.rag.airagservice.inbound.api.document.mapper;

import com.rag.airagservice.vm.document.DocumentIngestResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.BiFunction;

@ApplicationScoped
public class DocumentIngestResponseMapper implements BiFunction<String, Integer, DocumentIngestResponse> {
    @Override
    public DocumentIngestResponse apply(String documentName, Integer chunks) {
        return new DocumentIngestResponse(
                documentName,
                chunks,
                "Document ingestion completed",
                true
        );
    }
}
