# Step 9: Boundary test

Parent: plan section 6, step 9. Status: DRAFT. Depends on: steps 7, 8.

## Objective

An automated test that fails if the layer boundary is broken.

## Tasks

1. Add the architecture test dependency chosen and verified in step 2 (for example ArchUnit, version supporting Java 25).
2. Write rules, scoped to the application packages:
   - `mcp` must not depend on `crm` persistence/internal packages (entities, repositories, implementations).
   - `mcp` must not depend on data-access types: `javax/jakarta.persistence`, `org.springframework.data..`, `org.springframework.jdbc..`, `java.sql..`, Flyway.
   - `crm` must not depend on `mcp` or `org.springframework.ai..`.
   - `mcp` may use `crm` api package only.
3. Make sure the rules are not vacuous: fail if no classes are matched in the packages.
4. Run the deliberate-violation check: temporarily import a repository or `JdbcTemplate` in an `mcp` class, confirm the test fails, then remove the violation (do not commit it).

## Done when

- Test passes on clean code; the deliberate violation made it fail and was removed.
- Runs as part of `./gradlew build`.
