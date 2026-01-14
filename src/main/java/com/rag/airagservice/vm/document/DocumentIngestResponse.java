package com.rag.airagservice.vm.document;

public record DocumentIngestResponse(
        String documentName,
        Integer chunksCreated,
        String message,
        boolean success
) {
}
