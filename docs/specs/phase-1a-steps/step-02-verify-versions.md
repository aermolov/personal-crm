# Step 2: Verify versions

Parent: plan section 6, step 2. Status: DONE. Depends on: step 1.

## Objective

Confirm from official documentation every version, artifact name, property key and path that later steps use. Record them under "Verified versions" in `docs/DECISIONS.md`.

## Items to verify

| Item | Official source | Result to record |
|---|---|---|
| Latest stable Spring AI 2.x | Spring AI release and Maven Central | exact version |
| Matching Spring Boot 4.x | Spring AI compatibility statement and Boot requirements | exact version |
| Gradle wrapper (9.1.0 or newer) | gradle.org releases, Java 25 compatibility matrix | exact version |
| Java 25 support in Spring AI and Boot | Spring AI and Boot docs | yes/no, with source |
| Starter `spring-ai-starter-mcp-server-webmvc` | Spring AI MCP server docs | exact artifact name, BOM usage |
| MCP property keys (protocol/Streamable HTTP, endpoint path, name, version) | Spring AI MCP server reference for the pinned version | exact keys |
| Default endpoint path (`/mcp`?) | same | exact path |
| Flyway starter/modules for Boot 4 and Postgres | Boot 4 docs, Flyway docs | artifact names |
| Testcontainers or Compose DB for tests | Boot 4 docs | choice plus reason (needed by step 7) |
| Architecture test tool (for example ArchUnit) | its docs/releases | version that supports Java 25 (needed by step 9) |
| Postgres image tag | Docker Hub official image | tag |

## Tasks

1. Look up each item in official docs only; note URL and date.
2. Record results under "Verified versions" in `DECISIONS.md` with source URLs.
3. If any item contradicts the plan (for example, Spring AI does not support Java 25 or the chosen Boot version), stop and report the discrepancy before choosing alternatives (plan section 8).
4. Record the step 7 decision: Testcontainers vs Compose DB.

## Done when

- Every item above has a recorded value and source, or a reported discrepancy.
- Check: no unverified keys remain in use in any later step.
