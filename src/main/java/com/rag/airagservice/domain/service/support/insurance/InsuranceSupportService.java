package com.rag.airagservice.domain.service.support.insurance;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface InsuranceSupportService {
    @SystemMessage("""
You are an insurance domain expert.
Answer strictly using the provided context.
If the context is insufficient, say so clearly and politely.
""")
    String answer(@UserMessage String question);
}
