# Step 8: `mcp` layer

Parent: plan section 6, step 8. Status: DRAFT. Depends on: step 7.

## Objective

Register the five tools, validate inputs, map results and errors.

## Design

- Package `<base>.mcp`. Depends only on `crm` service interfaces and DTOs.
- Tool registration uses the Spring AI annotation/API verified in step 2 for the pinned version (do not guess annotation names).
- Tools: `create_contact`, `list_contacts`, `search_contacts`, `log_interaction`, `list_interactions`, each with a clear description and input schema.
- A small `CurrentUser`-style provider returns the configured dev `sub` (property key recorded in step 4). It is the single seam replaced by the JWT `sub` in Phase 1b; tools never read the property directly.
- Error mapping: `crm` exceptions become MCP tool error results (`isError`) with a clear message; no stack traces or internal details. Unknown or foreign contact: "contact not found".

## Tasks

1. Add the MCP server starter (`spring-ai-starter-mcp-server-webmvc`, verified in step 2).
2. Implement the dev-sub provider with a validated, non-blank property.
3. Implement the tools, validating inputs before calling `crm` (required fields, ISO-8601 timestamps, id format).
4. Implement result/error mapping in one place.

## Tests

- Each tool success path (using `crm` mocks or real services).
- Blank name, unknown contact id, malformed id/date, missing required argument: error result and nothing written.
- Tools pass the dev sub to `crm` (verify with a mock).

## Done when

- `./gradlew test` passes for the tool tests.
