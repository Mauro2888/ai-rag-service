# AiRagService Service

Rag investion and service service

## Stack

- Java 22
- Quarkus 3.16.2
- PostgreSQL + Flyway
- Common-BE libraries

## Package Structure

```
com.rag.airagservice
├── inbound/
│   └── api/          # REST endpoints (inbound adapters)
├── domain/
│   ├── model/        # Domain entities
│   └── repository/   # Repository interfaces (ports)
├── outbound/
│   ├── jpa/          # JPA implementations
│   └── mapper/       # Entity mappers
└── vm/               # DTOs
```

## Quick Start

```bash
# Start in dev mode (DevServices handles PostgreSQL automatically)
mvn quarkus:dev
```

Endpoints:
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui
- Health: http://localhost:8080/q/health

## Development

### Add New Feature

1. Create domain model in `domain/model/`
2. Define repository interface in `domain/repository/`
3. Implement repository in `outbound/jpa/`
4. Create DTOs in `vm/`
5. Add REST endpoint in `inbound/api/`

### Database Migration

```bash
# Create migration file
cat > src/main/resources/db/migration/V1.0.1__add_users_table.sql <<EOF
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
EOF

# Restart dev mode - migrations run automatically
```

### Add AI Tools with RAG

AI Tools allow the assistant to execute specific actions or retrieve data from ingested documents. Tools are complementary to RAG:

- **RAG**: Automatic semantic search in documents (context augmentation)
- **Tools**: Explicit function calls for specific data retrieval

**Example: Tool that queries the embedding store**

```java
// inbound/api/support/insurance/InsuranceTool.java
@ApplicationScoped
public class InsuranceTool {

    private final ContentRetriever contentRetriever;

    @Inject
    public InsuranceTool(@InsuranceRag ContentRetriever contentRetriever) {
        this.contentRetriever = contentRetriever;
    }

    @Tool("Search for vehicle plate numbers in insurance documents")
    public String getPlate(String policyNumber) {
        Query query = Query.from("vehicle plate number for policy " + policyNumber);
        List<Content> contents = contentRetriever.retrieve(query);

        if (contents.isEmpty()) {
            return "No plate found for policy: " + policyNumber;
        }

        return contents.stream()
                .map(Content::textSegment)
                .map(TextSegment::text)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("No plate information found");
    }
}
```

**Register the tool in the AI service producer:**

```java
// config/insurance/InsuranceSupportServiceProducer.java
@Inject
public InsuranceSupportServiceProducer(
        @InsuranceRag RetrievalAugmentor retrievalAugmentor,
        ChatLanguageModel chatLanguageModel,
        ChatMemoryStore chatMemoryProvider,
        InsuranceTool insuranceTool  // Add the tool
) { ... }

@Produces
@ApplicationScoped
InsuranceSupportService insuranceSupportService() {
    return AiServices.builder(InsuranceSupportService.class)
            .chatLanguageModel(chatLanguageModel)
            .retrievalAugmentor(retrievalAugmentor)
            .chatMemoryProvider(chatMemoryProvider)
            .tools(insuranceTool)  // Register the tool
            .build();
}
```

**How it works:**
1. User asks: "What's the plate for policy 12345?"
2. AI calls `getPlate("12345")` tool
3. Tool searches in ingested documents using semantic search
4. Tool returns matching content
5. AI uses the result to formulate the answer

The AI autonomously decides **when to use RAG** (for general context) and **when to call tools** (for specific data).

## Configuration

Edit `META-INF/microprofile-config.properties`:

```properties
# Change database schema
quarkus.hibernate-orm.database.default-schema=my_schema
quarkus.flyway.schemas=my_schema

# Disable DevServices (use docker-compose instead)
quarkus.datasource.devservices.enabled=false
```

## Testing

```bash
mvn test        # Unit tests (H2 in-memory)
mvn verify      # Integration tests
```

## Build

```bash
mvn clean package              # Standard build
mvn clean package -Pnative     # Native build (GraalVM required)
```

## Notes

- DevServices auto-starts PostgreSQL in dev mode (requires Docker)
- `docker-compose.yml` is optional - for persistent data only
- No boilerplate - start adding your features immediately
- Health checks available at `/q/health`


## LLM model
- get `ollama pull nomic-embed-text` designed for rag
