package com.rag.airagservice.inbound.api.support.insurance;

import com.rag.airagservice.domain.service.support.insurance.InsuranceSupportService;
import com.rag.airagservice.vm.support.InsuranceSupportResource;
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
public class InsuranceSupportResourceDelegate implements InsuranceSupportResource {
    private final Logger log = LoggerFactory.getLogger(InsuranceSupportResourceDelegate.class);
    private final InsuranceSupportService insuranceSupportService;

    public InsuranceSupportResourceDelegate(InsuranceSupportService insuranceSupportService) {
        this.insuranceSupportService = insuranceSupportService;
    }

    @Override
    public CompletionStage<SupportResponse> answer(SupportRequest request) {
        var sessionId = request.sessionId() != null ? request.sessionId() : UUID.randomUUID().toString();
        log.info("Processing insurance support question for session: {}", sessionId);

        var answer = insuranceSupportService.answer(request.question());
        return completedStage(new SupportResponse(answer, sessionId, true));
    }
}
