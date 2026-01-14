package com.rag.airagservice.vm.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DocumentIngestRequest(
        @NotBlank(message = "Document name cannot be blank")
        @Size(min = 1, max = 255, message = "Document name must be between 1 and 255 characters")
        String name,

        @NotBlank(message = "Content cannot be blank")
        @Size(min = 10, message = "Content must be at least 10 characters")
        String content,

        @Pattern(regexp = "customer|insurance", message = "Domain must be either 'customer' or 'insurance'")
        String domain
) {
}
