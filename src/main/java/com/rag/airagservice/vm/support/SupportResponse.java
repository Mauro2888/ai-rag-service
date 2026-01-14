package com.rag.airagservice.vm.support;

public record SupportResponse(
        String answer,
        String sessionId,
        boolean success
) {
}
