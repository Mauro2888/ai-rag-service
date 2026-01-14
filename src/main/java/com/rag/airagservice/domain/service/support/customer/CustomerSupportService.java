package com.rag.airagservice.domain.service.support.customer;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface CustomerSupportService {

    @SystemMessage("""
        You are a stakeholder representative for a software development team.
        Answer the team's question using only the information provided.
        If the available information is insufficient, state this clearly and politely.
        """)
    @UserMessage("Developer question: {question}")
    String answerQuestion(String question);
}