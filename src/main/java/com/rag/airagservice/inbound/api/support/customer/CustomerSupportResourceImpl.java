package com.rag.airagservice.inbound.api.support.customer;

import com.rag.airagservice.vm.support.CustomerSupportResource;
import com.rag.airagservice.vm.support.SupportRequest;
import com.rag.airagservice.vm.support.SupportResponse;
import common.be.common.rest.qualifier.InboundDelegate;
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
@Path("/api/support/customer")
@Tag(name = "Customer Support Resource", description = "Customer support AI assistant with RAG")
public class CustomerSupportResourceImpl implements CustomerSupportResource {

    private final CustomerSupportResource delegate;

    public CustomerSupportResourceImpl(@InboundDelegate CustomerSupportResource delegate) {
        this.delegate = delegate;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Ask customer support question",
            description = "Ask a question to the customer support AI assistant"
    )
    @APIResponse(responseCode = "200", description = "Question answered successfully")
    @APIResponse(responseCode = "500", description = "Error processing request")
    @Override
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
