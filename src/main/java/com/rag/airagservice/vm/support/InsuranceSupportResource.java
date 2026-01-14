package com.rag.airagservice.vm.support;

import java.util.concurrent.CompletionStage;

public interface InsuranceSupportResource {
    CompletionStage<SupportResponse> answer(SupportRequest request);
}
