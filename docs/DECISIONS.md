# DECISIONS

Running log of settled choices and verified facts. This file outranks `PROJECT_PLAN.md`. Update it at the end of each phase.

## Status: Phase 0 (plan refinement), Phase 1a spec drafted

Last updated: 2026-11-23

Phase 0 stays open for use case, tool list, data model, hosting and definition of done. A "Phase 0-lite" settled only what the local skeleton (Phase 1a) needs.

## Settled

| #   | Decision                                                                                                  | Date       | Notes                                                                             |
| --- | --------------------------------------------------------------------------------------------------------- | ---------- | --------------------------------------------------------------------------------- |
| D1  | Claude project is the architect and spec writer. It writes no code                                        | 2026-11-21 | Allowed: prose, tables, Mermaid diagrams, inline identifiers, acceptance criteria |
| D2  | GitHub Copilot implements all code from specs                                                             | 2026-11-21 | Specs are attached to Copilot tasks as context                                    |
| D3  | Specs live in the repo under `docs/specs/`                                                                | 2026-11-21 | One spec per phase                                                                |
| D4  | `PROJECT_PLAN.md` is a draft to be critiqued in Phase 0                                                   | 2026-11-21 | Not a spec                                                                        |
| D5  | Source-of-truth order: `DECISIONS.md`, then current chat and specs, then `PROJECT_PLAN.md`, then the wiki | 2026-11-21 | Wiki added by D10                                                                 |
| D6  | Phase 1 is split: 1a = local, no auth, Postgres (Compose), Inspector. 1b = Keycloak, JWT, Caddy           | 2026-11-22 | Amended: Postgres in Compose moved into 1a. 1a uses the configurable dev-only `personal-crm.dev.user-sub` property (D16), replaced by the JWT `sub` in 1b |
| D7  | Spring AI 2.1.0 on Spring Boot 4.2.0, Java 25, Gradle (wrapper 9.1.0 or newer), single module in `mcp/` | 2026-11-22 | Updated after verifying the latest stable compatible releases (step 2)             |
| D8  | `AGENTS.md` is the only Copilot instruction file. No `.github/copilot-instructions.md`                    | 2026-11-21 | Copilot for JetBrains reads AGENTS.md at the repo root                            |
| D9  | `AGENTS.md` stays short (rules and pointers). Wiki rules live in `docs/wiki/SCHEMA.md`                    | 2026-11-21 | Always-loaded context should be small                                             |
| D10 | The LLM wiki is derived: it cites its sources and never records decisions                                 | 2026-11-21 | Lowest rank in the source-of-truth order                                          |
| D11 | `docs/raw/` holds URL, retrieval date and own summary, not copies of third-party docs                     | 2026-11-21 | Public repo, avoids copyright issues                                              |
| D12 | graphify is installed only after code exists. `graphify-out/` is gitignored. Git hooks are opt-in         | 2026-11-21 | Official repo and package to be verified first (task T9)                          |
| D13 | Gradle Groovy DSL, no version catalog                                                                    | 2026-11-24 | Build scripts use `build.gradle` and `settings.gradle`                            |
| D14 | Everything runs in WSL2 with the repo on the Linux filesystem. IntelliJ Community with the Copilot plugin | 2026-11-21 |                                                                                   |
| D15 | Group id `io.github.aermolov`, base package `io.github.aermolov.personalcrm` (sub-packages `mcp`, `crm`), artifact `personal-crm` | 2026-11-22 | Resolves O2. App folder is `mcp/` |
| D16 | Use `personal-crm.dev.user-sub` as the configurable development-only subject property in Phase 1a | 2026-11-23 | Replaced by the authenticated JWT `sub` in Phase 1b; never use this property in production |
| D17 | Contacts and interactions use UUID primary keys | 2026-11-24 | Implemented by the Phase 1a V1 Flyway migration |

## Verified versions / facts

Record only what was checked by web search. Format: fact, date checked, source URL.

| Fact                                                                                                                                          | Date       | Source                                                                                                                                                                                                                              |
| --------------------------------------------------------------------------------------------------------------------------------------------- | ---------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Spring AI 2.0.0 initial release on 2026-06-12; shipped with MCP Java SDK 2.0.0                                                              | 2026-11-21 | https://spring.io/blog/2026/06/12/spring-ai-2-0-0-GA-available-now/                                                                                                                                                                 |
| Spring AI 2.1.0 is a stable release; the v2.1.0 project README states Spring AI 2.x supports Spring Boot 4.x                               | 2026-11-22 | https://github.com/spring-projects/spring-ai/releases/tag/v2.1.0, https://raw.githubusercontent.com/spring-projects/spring-ai/v2.1.0/README.md, and https://repo1.maven.org/maven2/org/springframework/ai/spring-ai-bom/maven-metadata.xml |
| Matching pinned stack: Spring Boot 4.2.0, Spring Framework 7.1.0, Spring AI BOM 2.1.0                                                     | 2026-11-22 | https://spring.io/blog/2026/11/19/spring-boot-4-2-0-available-now and https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-dependencies/4.2.0/spring-boot-dependencies-4.2.0.pom, https://repo1.maven.org/maven2/org/springframework/ai/spring-ai-bom/2.1.0/spring-ai-bom-2.1.0.pom |
| Spring AI 2.1.0 starter `spring-ai-starter-mcp-server-webmvc` uses `spring.ai.mcp.server.protocol=STREAMABLE`; HTTP transports are unauthenticated by default | 2026-11-21 | https://docs.spring.io/spring-ai/reference/api/mcp/mcp-streamable-http-server-boot-starter-docs.html                                                                                                                    |
| Spring AI 2.1.0 WebMVC starter: `spring-ai-starter-mcp-server-webmvc`; MCP keys `spring.ai.mcp.server.protocol=STREAMABLE`, `spring.ai.mcp.server.name`, `spring.ai.mcp.server.version`, `spring.ai.mcp.server.streamable-http.mcp-endpoint`; default `/mcp` | 2026-11-22 | https://repo1.maven.org/maven2/org/springframework/ai/spring-ai-starter-mcp-server-webmvc/2.1.0/spring-ai-starter-mcp-server-webmvc-2.1.0.pom and https://docs.spring.io/spring-ai/reference/api/mcp/mcp-streamable-http-server-boot-starter-docs.html |
| Spring Boot JDBC starter: `spring-boot-starter-jdbc:4.2.0`; PostgreSQL driver: `org.postgresql:postgresql`, managed as 42.7.14 by the Boot 4.2.0 BOM | 2026-11-24 | https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-starter-jdbc/4.2.0/spring-boot-starter-jdbc-4.2.0.pom, https://docs.spring.io/spring-boot/4.2/reference/data/sql.html, and https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-dependencies/4.2.0/spring-boot-dependencies-4.2.0.pom |
| Spring Boot JPA starter: `spring-boot-starter-data-jpa:4.2.0`; it provides Spring Data JPA with Hibernate and transitively includes the JDBC starter needed for the configured DataSource | 2026-11-25 | https://docs.spring.io/spring-boot/reference/data/sql.html and https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-starter-data-jpa/4.2.0/spring-boot-starter-data-jpa-4.2.0.pom |
| Spring Boot 4.0 is Java 25 ready                                                                                                              | 2026-11-21 | https://github.com/spring-projects/spring-boot/issues/47245                                                                                                                                                                         |
| Java 25 needs Gradle 9.1.0 or newer. Boot 4 supports Gradle 9 and 8.14+                                                                       | 2026-11-21 | https://docs.gradle.org/current/userguide/compatibility.html and https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Release-Notes                                                                                  |
| The pinned Spring AI 2.1.0 MCP starter targets Boot 4.2.0; Boot supports Java through 27 and Gradle 9.1.0+ supports Java 25. No separate Java runtime requirement was stated in the Spring AI README | 2026-11-22 | https://repo1.maven.org/maven2/org/springframework/ai/spring-ai-starter-mcp-server-webmvc/2.1.0/spring-ai-starter-mcp-server-webmvc-2.1.0.pom, https://docs.spring.io/spring-boot/4.2/system-requirements.html, https://docs.gradle.org/current/userguide/compatibility.html, and https://raw.githubusercontent.com/spring-projects/spring-ai/v2.1.0/README.md |
| Gradle wrapper pin: 9.9.2                                                                                                                     | 2026-11-22 | https://docs.gradle.org/current/userguide/compatibility.html and `mcp/gradle/wrapper/gradle-wrapper.properties`                                                                                                                    |
| Flyway in Boot 4.2.0: `spring-boot-starter-flyway`, managed Flyway core 12.11.0, plus `flyway-database-postgresql:12.11.0`                    | 2026-11-22 | https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-starter-flyway/4.2.0/spring-boot-starter-flyway-4.2.0.pom, https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-flyway/4.2.0/spring-boot-flyway-4.2.0.pom, and https://repo1.maven.org/maven2/org/flywaydb/flyway-database-postgresql/12.11.0/flyway-database-postgresql-12.11.0.pom |
| Step 7 integration database: Compose Postgres, not Testcontainers; Testcontainers 2.0.5 is managed by Boot 4.2.0 as an optional alternative   | 2026-11-22 | https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-dependencies/4.2.0/spring-boot-dependencies-4.2.0.pom                                                                                                       |
| Architecture test tool: ArchUnit 1.5.1 with JUnit 6 artifact `com.tngtech.archunit:archunit-junit6:1.5.1`                                  | 2026-11-22 | https://www.archunit.org/userguide/html/000_Index.html and https://repo1.maven.org/maven2/com/tngtech/archunit/archunit-junit5/maven-metadata.xml                                                                                  |
| Postgres image pin: `postgres:17.12-trixie`                                                                                                  | 2026-11-22 | https://hub.docker.com/_/postgres                                                                                                                                                                                                  |
| Copilot for JetBrains reads AGENTS.md and CLAUDE.md. Nested files need a setting. Copilot coding agent also supports AGENTS.md                | 2026-11-21 | https://github.blog/changelog/2026-03-11-major-agentic-capabilities-improvements-in-github-copilot-for-jetbrains-ides and https://github.blog/changelog/2026-06-23-copilot-coding-agent-now-supports-agents-md-custom-instructions/ |
| WSL2 NAT mode: Windows reaches Linux apps via localhost. Not reachable from the LAN by default. Mirrored mode needs Windows 11 22H2 or newer  | 2026-11-21 | https://learn.microsoft.com/en-us/windows/wsl/networking                                                                                                                                                                            |
| MCP Inspector needs Node 22.7.5 or newer. UI on port 6274, proxy on 6277. Proxy session token required. Past critical RCE (CVE-2025-49596)    | 2026-11-21 | https://github.com/modelcontextprotocol/inspector                                                                                                                                                                                   |
| LLM Wiki pattern (Karpathy, April 2026): raw sources, wiki, schema file                                                                       | 2026-11-21 | https://gist.github.com/karpathy/442a6bf555914893e9891c11519de94f                                                                                                                                                                   |

## Unverified claims

To be checked when the relevant phase starts. Move to the table above once confirmed.

Carried over from the draft plan:

- Oracle Always Free ARM allowance is 2 OCPU and 12 GB RAM after a mid-2026 reduction
- AWS free plan for new accounts is time-limited (about 6 months or until credits run out)
- `mcp-server-security` 0.1.x requires Spring AI 2.0.x. Spring AI 1.1.x needs 0.0.6
- Claude's custom-connector redirect URI is `https://claude.ai/api/mcp/auth_callback`
- Claude.ai OAuth may fail with some self-hosted servers (2026 GitHub issues)
- Keycloak anonymous dynamic registration needs policy tuning and an audience mapper
- Keycloak, Spring Boot, Postgres and Caddy fit in about 4 GB with JVM limits
- All required images have arm64 builds

New in this round:

- AGENTS.md support in IntelliJ Community specifically (verified for JetBrains IDEs in general). Test: AC8
- Repo on the WSL Linux filesystem is recommended for speed (common practice)
- Official graphify repository and package (many forks exist). Check in T9
- Temurin 25 arm64 images for the Oracle VM. Check in Phase 3

## Open questions

- [ ] Use case: CRM (contacts and interactions) or a better demo domain
- [ ] Tool list (names, inputs, outputs, read or write)
- [ ] Data model
- [ ] Stack confirmation beyond Phase 1a (library fallback for MCP security)
- [ ] Hosting (Oracle primary, fallback choice)
- [ ] Final definition of done
- [ ] O1: how IntelliJ attaches to WSL (Windows IntelliJ on a WSL path, or Linux IntelliJ via WSLg)
- [x] O2: base Java package and group id (settled, D15)

## Spec index

| Phase | Repo path                               | Status                        |
| ----- | --------------------------------------- | ----------------------------- |
| 0     | `docs/specs/phase-0-design.md`          | Not started                   |
| 1a    | `docs/specs/phase-1a-implementation-plan.md` | Draft, versions verified |
| 1b    | `docs/specs/phase-1b-auth-compose.md`   | Not started                   |
| 2     | `docs/specs/phase-2-tools-data.md`      | Not started                   |
| 3     | `docs/specs/phase-3-deploy.md`          | Not started                   |
| 4     | `docs/specs/phase-4-connect-claude.md`  | Not started                   |
| 5     | `docs/specs/phase-5-harden.md`          | Not started                   |

## Change log

- 2026-11-21: File created. Process decisions D1 to D5 recorded.
- 2026-11-21: Phase 0-lite. D6 to D14 recorded (split Phase 1, Java 25 and Gradle, AGENTS.md only, wiki and graphify rules, WSL2). Verified facts added. Phase 1a spec drafted.
- 2026-11-22: D6 amended (Postgres in Compose moves into 1a; configurable dev-only `sub` property in 1a, replaced by JWT `sub` in 1b; key to be named in step 4). App directory confirmed as `mcp/`. 1a spec replaced by `phase-1a-implementation-plan.md`.
- 2026-11-22: D15 recorded (group id, base package, artifact name). O2 closed.
- 2026-11-22: Step 2 verified the Spring AI 2.1.0/Boot 4.2.0/Gradle 9.9.2 stack, MCP properties and endpoint, Flyway/Postgres dependencies, Compose DB test choice, ArchUnit 1.5.1 and Postgres 17.12-trixie. D7 updated to the newer stable compatible Spring AI and Boot releases.
- 2026-11-23: D16 records the development-only subject property key. Step 4 uses the Groovy DSL and D15 coordinates; the Gradle wrapper build and context-load test pass with Java 25.0.2.
- 2026-11-24: D17 records UUID primary keys for contacts and interactions; step 6 adds the Flyway schema migration.
