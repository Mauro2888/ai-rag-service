package com.rag.airagservice.inbound.api.support.insurance;

import com.rag.airagservice.vm.support.InsuranceSupportResource;
import com.rag.airagservice.vm.support.SupportRequest;
import com.rag.airagservice.vm.support.SupportResponse;
import common.be.common.rest.qualifier.InboundDelegate;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.concurrent.CompletionStage;

@RequestScoped
@Path("/api/support/insurance")
@Tag(name = "Insurance Support Resource", description = "Insurance support AI assistant with RAG")
public class InsuranceSupportResourceImpl implements InsuranceSupportResource {

    private final InsuranceSupportResource delegate;

    public InsuranceSupportResourceImpl(@InboundDelegate InsuranceSupportResource delegate) {
        this.delegate = delegate;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Ask insurance support question",
            description = "Ask a question to the insurance support AI assistant"
    )
    @APIResponse(responseCode = "200", description = "Question answered successfully")
    @APIResponse(responseCode = "500", description = "Error processing request")
    @Override
    @Blocking
    public CompletionStage<SupportResponse> answer(@Valid @RequestBody(
            description = "Support request",
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = SupportRequest.class)))
                                                     SupportRequest request) {
        return delegate.answer(request);
    }
}
