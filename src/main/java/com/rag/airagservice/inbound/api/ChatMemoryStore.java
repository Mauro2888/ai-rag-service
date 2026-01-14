package com.rag.airagservice.inbound.api;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ChatMemoryStore implements ChatMemoryProvider {

    private static final Map<Object, ChatMemory> MEMORIES = new ConcurrentHashMap<>();

    @Override
    public ChatMemory get(Object memoryId) {
        return MEMORIES.computeIfAbsent(memoryId,
            id -> MessageWindowChatMemory.withMaxMessages(10));
    }

    public void clear(Object memoryId) {
        MEMORIES.remove(memoryId);
    }

    public void clearAll() {
        MEMORIES.clear();
    }
}
