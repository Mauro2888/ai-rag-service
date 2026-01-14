package com.rag.airagservice.inbound.api.document;

import com.rag.airagservice.domain.service.DocumentIngestionService;
import com.rag.airagservice.inbound.api.document.mapper.DocumentIngestResponseMapper;
import com.rag.airagservice.vm.document.DocumentIngestRequest;
import com.rag.airagservice.vm.document.DocumentIngestResponse;
import com.rag.airagservice.vm.document.DocumentResource;
import common.be.common.rest.qualifier.InboundDelegate;
import jakarta.enterprise.context.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;


@RequestScoped
@InboundDelegate
public class DocumentResourceDelegate implements DocumentResource {

    private final Logger log = LoggerFactory.getLogger(DocumentResourceDelegate.class);

    private final DocumentIngestionService documentIngestionService;
    private final DocumentIngestResponseMapper documentIngestResponseMapper;

    public DocumentResourceDelegate(DocumentIngestionService documentIngestionService, DocumentIngestResponseMapper documentIngestResponseMapper) {
        this.documentIngestionService = documentIngestionService;
        this.documentIngestResponseMapper = documentIngestResponseMapper;
    }


    @Override
    public CompletionStage<DocumentIngestResponse> ingest(DocumentIngestRequest request) {

        var documentName = request.name() != null ? request.name() : "unknown";

        log.info("Ingesting document: {} with domain: {}",
                documentName,
                request.domain()
        );

        var promise = documentIngestionService.ingestDocument(
                request.content(),
                documentName,
                request.domain()
        ).thenApply(chunks -> documentIngestResponseMapper.apply(documentName, chunks));

        promise.exceptionally(exception -> {
            log.error("Error ingesting document {}", documentName, exception);
            return null;
        });

        return promise;
    }
}
