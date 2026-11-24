# Step 4: Project skeleton

Parent: plan section 6, step 4. Status: DONE (skeleton implemented; step 3 environment check remains incomplete). Depends on: steps 2, 3.

## Objective

An empty Spring Boot app under `mcp/` that builds with the Gradle wrapper.

## Tasks

1. Keep `mcp/` as a single Gradle module using Groovy build scripts (`build.gradle`, `settings.gradle`), as required by D13. No version catalog.
2. Generate the Gradle wrapper at the version verified in step 2; commit wrapper files.
3. Configure the Java 25 toolchain, Spring Boot plugin and dependency management at the verified versions. Use the group id from D15.
4. Dependencies for now: Spring Boot starter and test starter only. Later steps add the rest (webmvc MCP starter, JDBC/JPA, Flyway, Postgres driver, test tools).
5. Create the main application class in the D15 base package, with sub-packages `mcp` and `crm` (placeholder `package-info.java` or a first real class later; avoid empty dead code).
6. Add a context-load test.
7. Add/verify `.gitignore` covers `graphify-out/`, `.env`, `build/`, `.gradle/`.
8. Decide the dev `sub` property key (for example under a project prefix) and record it in `DECISIONS.md` (completes C4); it is used from step 7.

## Out of scope

Tools, entities, Docker, MCP config.

## Done when

- `./gradlew build` in `mcp/` passes using the wrapper, and the context-load test passes.
