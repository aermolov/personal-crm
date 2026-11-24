# Project purpose

This repository contains a Spring AI MCP server and its supporting documentation. Build the project incrementally from the phase specifications, preserve the documented contracts, and keep generated graphify output ignored.

# Source of truth

Resolve conflicts in this order: docs/DECISIONS.md; the current chat and the applicable specifications in docs/specs/; docs/PROJECT_PLAN.md; then docs/wiki/. The wiki is derived documentation, must cite its sources, and never records decisions. Decisions and pinned versions belong in docs/DECISIONS.md.

# Environment rules

Everything runs inside WSL, including commands, Gradle, tests, and supporting tools. Use the Gradle wrapper rather than a system Gradle installation. Use Java 25 from `/home/ermol/.jdks/openjdk-25.0.2` and the Gradle Groovy DSL. If `java` is not already on `PATH`, initialize the documented JDK before running Gradle:

```sh
export JAVA_HOME=/home/ermol/.jdks/openjdk-25.0.2
export PATH="$JAVA_HOME/bin:$PATH"
```

Verify both `java -version` and `docker version` in the same WSL session before reporting verification as blocked. Keep the repository on the WSL Linux filesystem when possible. Consult docs/DECISIONS.md for all pinned versions instead of repeating or guessing them here.

# Package layout

The Spring Boot application belongs under mcp/. Keep phase specifications in docs/specs/, project decisions in docs/DECISIONS.md, the draft plan in docs/PROJECT_PLAN.md, and maintained wiki content in docs/wiki/. Keep raw wiki sources under docs/raw/. Keep generated graphify artifacts under graphify-out/ and ignored by git. Use the base Java package and group ID recorded in the current project decisions; do not invent them.

# Coding conventions

Follow the current phase specification and existing project style before introducing a new pattern. Keep components small and focused, use clear names, preserve type safety, and prefer existing helpers and framework conventions. Keep configuration explicit, documented, and environment-driven where the specification requires it. Do not add application code outside the requested phase scope.

# Testing rules

Use the Gradle wrapper for every build and test. Add focused tests for each behavior introduced or fixed, including integration coverage where the specification requires client-to-server behavior. Tests must cover validation and error behavior, not only successful paths. Run the smallest relevant test task, then the full build when the change affects shared configuration or integration wiring.

# Security rules

Every data access must be scoped by the authenticated JWT sub; never allow one user to read another user's data. Maintain a test proving that user B cannot read user A's data. Validate the JWT audience as well as signature and other required claims. Never commit secrets, real credentials, private tokens, or .env files; commit only safe examples and placeholders.

# Verification and documentation

Never invent configuration keys, class names, artifact names, API details, or versions. Look them up in the official documentation for the pinned version in docs/DECISIONS.md, and report the source and any discrepancy before using them. Follow docs/wiki/SCHEMA.md for wiki ingestion, querying, citations, and linting. Consult docs/specs/ for the active phase contract and acceptance criteria.
