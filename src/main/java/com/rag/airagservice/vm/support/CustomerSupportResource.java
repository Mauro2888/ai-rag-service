package com.rag.airagservice.vm.support;

import java.util.concurrent.CompletionStage;

public interface CustomerSupportResource {
    CompletionStage<SupportResponse> answer(SupportRequest request);
}
