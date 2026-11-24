# Step 7: `crm` layer

Parent: plan section 6, step 7. Status: IMPLEMENTED (2026-11-24). Depends on: step 6.

## Objective

Service interfaces, implementations and persistence, with `sub` an explicit parameter on every method.

## Design

- Package `<base>.crm` with sub-packages, for example `api` (service interfaces and DTO/records, the only types `mcp` may use), `internal`/`persistence` (entities, repositories, implementations). Exact names follow existing style and D15.
- No MCP or Spring AI imports anywhere in `crm`.
- Service interfaces:
  - `ContactService`: `create(sub, name, email, notes)`, `list(sub)`, `search(sub, text)`.
  - `InteractionService`: `log(sub, contactId, occurredAt, summary)`, `list(sub, contactId)`.
- Return immutable records, not entities.
- Errors: a `crm` exception type for not found and validation (for example `ContactNotFoundException`, `InvalidInputException`). A contact owned by another sub is reported as not found.

## Tasks

1. Entities/repositories (JPA or JDBC per step 2 choice). Every query includes `owner_sub = :sub`; load by id uses `id AND owner_sub`.
2. Validation: name not blank (trim), email format if present, summary not blank, length limits matching the schema.
3. `log` verifies the contact belongs to `sub` before inserting.
4. `search`: case-insensitive match on name or email; escape LIKE wildcards in user text.
5. Dev `sub` is NOT used in `crm`; it is supplied by the `mcp` layer (step 8).

## Tests (real Postgres, Testcontainers or Compose per step 2)

- Create, list, search happy paths.
- Validation errors write nothing.
- Security: contacts created as sub A are not returned to sub B in list or search; B cannot read or log interactions for A's contact id (not found); interactions list for A's contact as B is rejected.
- Wildcard characters in search are treated literally.

## Done when

- Service tests pass via `./gradlew test`, including the user-B-cannot-read-user-A test.
