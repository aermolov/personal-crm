# Step 2 Version Verification

Retrieved: 2026-11-22

## Spring AI and Spring Boot

- https://github.com/spring-projects/spring-ai/releases/tag/v2.1.0
  - Official Spring AI v2.1.0 release, dated 2026-11-20.
- https://repo1.maven.org/maven2/org/springframework/ai/spring-ai-bom/maven-metadata.xml
  - Maven Central metadata lists 2.1.0 as the latest stable Spring AI BOM.
- https://raw.githubusercontent.com/spring-projects/spring-ai/v2.1.0/README.md
  - The v2.1.0 README states Spring AI 2.x supports Spring Boot 4.x.
- https://spring.io/blog/2026/11/19/spring-boot-4-2-0-available-now
  - Official announcement of Spring Boot 4.2.0 as released and available from Maven Central.
- https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-dependencies/maven-metadata.xml
  - Maven Central metadata lists 4.2.0 as the latest Spring Boot release.
- https://docs.spring.io/spring-boot/4.2/system-requirements.html
  - Spring Boot 4.2.0 supports Java 17 through Java 27 and Gradle 8.14+ or 9.x.
- https://repo1.maven.org/maven2/org/springframework/ai/spring-ai-starter-mcp-server-webmvc/2.1.0/spring-ai-starter-mcp-server-webmvc-2.1.0.pom
  - Confirms the WebMVC MCP starter artifact exists at 2.1.0 and has a Boot 4.2.0 dependency.
- https://docs.spring.io/spring-ai/reference/api/mcp/mcp-streamable-http-server-boot-starter-docs.html
  - Artifact: `spring-ai-starter-mcp-server-webmvc`.
  - Protocol key: `spring.ai.mcp.server.protocol=STREAMABLE`.
  - Name: `spring.ai.mcp.server.name`.
  - Version: `spring.ai.mcp.server.version`.
  - Endpoint key: `spring.ai.mcp.server.streamable-http.mcp-endpoint`.
  - Default endpoint path: `/mcp`.
- Documentation discrepancy: the attempted versioned URL `https://docs.spring.io/spring-ai/reference/2.1/getting-started.html` redirects to the unversioned reference, whose compatibility paragraph still describes Spring AI 2.0.x and Boot 4.0/4.1. The tagged v2.1.0 README says Spring AI 2.x supports Boot 4.x; the v2.1.0 starter POM also confirms Boot 4.2.0 as its dependency.

## Gradle and Java

- https://docs.gradle.org/current/userguide/compatibility.html
  - Java 25 is supported by Gradle 9.1.0 and later.
- `mcp/gradle/wrapper/gradle-wrapper.properties`
  - The existing WSL wrapper is pinned to Gradle 9.9.2.

## Database and testing

- https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-starter-flyway/4.2.0/spring-boot-starter-flyway-4.2.0.pom
  - `org.springframework.boot:spring-boot-starter-flyway:4.2.0` brings the Boot Flyway integration and JDBC support.
- https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-flyway/4.2.0/spring-boot-flyway-4.2.0.pom
  - Flyway core is managed at 12.11.0 for this Boot line.
- https://repo1.maven.org/maven2/org/flywaydb/flyway-database-postgresql/12.11.0/flyway-database-postgresql-12.11.0.pom
  - Postgres support is supplied by `org.flywaydb:flyway-database-postgresql:12.11.0`.
- https://hub.docker.com/_/postgres
  - Docker Official Image lists `17.12-trixie` and `arm64v8` support.
- https://repo1.maven.org/maven2/org/springframework/boot/spring-boot-dependencies/4.2.0/spring-boot-dependencies-4.2.0.pom
  - Boot 4.2.0 manages Testcontainers 2.0.5, but Phase 1a will use the Compose Postgres service for integration tests.

## Architecture test

- https://www.archunit.org/userguide/html/000_Index.html
  - ArchUnit 1.5.1 documents JUnit 5 and JUnit 6 convenience artifacts.
- https://repo1.maven.org/maven2/com/tngtech/archunit/archunit-junit5/maven-metadata.xml
  - Maven Central metadata lists ArchUnit 1.5.1 as the latest release.
