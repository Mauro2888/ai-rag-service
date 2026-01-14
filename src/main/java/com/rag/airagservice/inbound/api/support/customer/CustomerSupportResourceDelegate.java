package com.rag.airagservice.inbound.api.support.customer;

import com.rag.airagservice.domain.service.support.customer.CustomerSupportService;
import com.rag.airagservice.vm.support.CustomerSupportResource;
import com.rag.airagservice.vm.support.SupportRequest;
import com.rag.airagservice.vm.support.SupportResponse;
import common.be.common.rest.qualifier.InboundDelegate;
import jakarta.enterprise.context.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

@RequestScoped
@InboundDelegate
public class CustomerSupportResourceDelegate implements CustomerSupportResource {
    private final Logger log = LoggerFactory.getLogger(CustomerSupportResourceDelegate.class);
    private final CustomerSupportService customerSupportService;

    public CustomerSupportResourceDelegate(CustomerSupportService customerSupportService) {
        this.customerSupportService = customerSupportService;
    }

    @Override
    public CompletionStage<SupportResponse> answer(SupportRequest request) {
        var sessionId = request.sessionId() != null ? request.sessionId() : UUID.randomUUID().toString();
        log.info("Processing customer support question for session: {}", sessionId);

        var answer = customerSupportService.answerQuestion(request.question());
        return completedStage(new SupportResponse(answer, sessionId, true));
    }
}
