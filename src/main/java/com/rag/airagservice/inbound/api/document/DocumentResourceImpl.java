package com.rag.airagservice.inbound.api.document;

import com.rag.airagservice.vm.document.DocumentIngestRequest;
import com.rag.airagservice.vm.document.DocumentIngestResponse;
import com.rag.airagservice.vm.document.DocumentResource;
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
@Path("/api/documents")
@Tag(name = "Document Resource", description = "Document ingestion for RAG system")
public class DocumentResourceImpl implements DocumentResource {

    private final DocumentResource delegate;

    public DocumentResourceImpl(@InboundDelegate DocumentResource delegate) {
        this.delegate = delegate;
    }

    @POST
    @Path("/ingest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Ingest document",
            description = "Ingest a document into the RAG system"
    )
    @APIResponse(responseCode = "200", description = "Document ingested successfully")
    @APIResponse(responseCode = "500", description = "Error ingesting document")
    @Override
    @Blocking
    public CompletionStage<DocumentIngestResponse> ingest(@Valid @RequestBody(
            description = "Document to ingest",
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = DocumentIngestRequest.class)))
                                                            DocumentIngestRequest request) {
        return delegate.ingest(request);
    }
}
