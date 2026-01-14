package com.rag.airagservice.vm.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupportRequest(
        @NotBlank(message = "Question cannot be blank")
        @Size(min = 3, max = 2000, message = "Question must be between 3 and 2000 characters")
        String question,
        String sessionId
) {
}
