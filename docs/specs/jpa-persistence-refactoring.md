# JPA Persistence Refactoring Specification

Status: DRAFT  
Scope: `mcp` module, CRM persistence only  
Source of truth: `docs/DECISIONS.md`, this specification, then existing phase specifications

## 1. Goal

Replace the CRM application's hand-written JDBC persistence implementations with
Spring Data JPA repositories and JPA entities. Repository behavior, owner
isolation, Flyway-managed schema, service APIs, and externally visible CRM
behavior must remain unchanged.

The target is to remove application SQL strings from
`crm.persistence`. JPA repository method derivation and the JPA Criteria/
Specification API are preferred. Native SQL and string-based JPQL are not part
of this refactoring.

## 2. Current-state analysis

| Area | Current implementation | Refactoring impact |
|---|---|---|
| Schema | Flyway `V1__create_contacts_and_interactions.sql` owns `contacts` and `interactions` | Preserve the migration and database shape |
| Contact repository | `JdbcContactRepository` uses `JdbcTemplate`, SQL, `ResultSet` mapping, and manual LIKE escaping | Replace with a Spring Data repository plus entity/DTO mapping and a Criteria/Specification search |
| Interaction repository | `JdbcInteractionRepository` uses `JdbcTemplate` and SQL | Replace with a Spring Data repository and entity/DTO mapping |
| Repository contracts | `ContactRepository` and `InteractionRepository` expose CRM records and explicit `sub` arguments | Preserve the service-facing contract unless a JPA adapter boundary requires a package-private change |
| Services | `JdbcContactService` and `JdbcInteractionService` validate input and enforce owner checks before persistence | Preserve behavior; rename implementations to remove the obsolete `Jdbc` prefix |
| API records | `Contact` and `Interaction` are immutable records with no persistence annotations | Keep them as the application/API model; do not expose entities outside persistence |
| Tests | CRM integration tests use JDBC to clear rows; application tests use JDBC to verify Flyway and constraints | Replace test cleanup with repository operations; retain focused SQL in schema tests because those tests verify the physical migration |
| Build | `spring-boot-starter-jdbc` supplies the current persistence API | Replace it with the verified Spring Data JPA starter for the pinned Boot version |

Important existing invariants:

1. Every read and write is scoped by `owner_sub`.
2. A contact owned by another subject is reported as not found.
3. Contacts are ordered by `created_at, id`.
4. Interactions are ordered by `occurred_at, id`.
5. Search is case-insensitive across name and nullable email.
6. `%`, `_`, and `\` in search input are literal characters, not LIKE
   wildcards.
7. Flyway owns schema creation; Hibernate must not create or alter tables.
8. The CRM API returns records, never persistence entities.

## 3. Target design

### 3.1 Dependencies and configuration

1. Remove `org.springframework.boot:spring-boot-starter-jdbc` from
   `mcp/build.gradle`.
2. Add `org.springframework.boot:spring-boot-starter-data-jpa`, using the
   version managed by the already pinned Spring Boot `4.2.0` BOM. Verify the
   exact starter and Hibernate compatibility against the official Boot
   documentation before implementation.
3. Keep PostgreSQL and Flyway dependencies unchanged.
4. Set Hibernate schema management to validation-only:
   `spring.jpa.hibernate.ddl-auto: validate`.
5. Do not add `schema.sql`, `data.sql`, Hibernate-generated migrations, or
   automatic schema creation.
6. Keep datasource configuration environment-driven and unchanged.

### 3.2 Persistence entities

Add persistence-only entities under `crm.persistence` (proposed names:
`ContactEntity` and `InteractionEntity`).

`ContactEntity` maps:

| Java property | Column | Mapping |
|---|---|---|
| `id` | `id` | UUID identifier, application-generated |
| `ownerSub` | `owner_sub` | required string |
| `name` | `name` | required string |
| `email` | `email` | nullable string |
| `notes` | `notes` | nullable string |
| `createdAt` | `created_at` | required `OffsetDateTime` |

`InteractionEntity` maps:

| Java property | Column | Mapping |
|---|---|---|
| `id` | `id` | UUID identifier, application-generated |
| `ownerSub` | `owner_sub` | required string |
| `contactId` | `contact_id` | required UUID scalar |
| `occurredAt` | `occurred_at` | required `OffsetDateTime` |
| `summary` | `summary` | required string |

Keep `contactId` as a UUID scalar in the first refactoring. Do not introduce a
JPA relationship that could load a contact without applying the owner
predicate. The existing foreign key remains enforced by PostgreSQL.

Entities must not implement or replace the CRM API records. Use explicit
mapping methods or small package-private mappers to convert entities to
`Contact` and `Interaction`.

### 3.3 Spring Data repositories

Add package-private or persistence-scoped Spring Data interfaces:

- `ContactJpaRepository extends JpaRepository<ContactEntity, UUID>`.
- `InteractionJpaRepository extends JpaRepository<InteractionEntity, UUID>`.

Required repository capabilities:

| Operation | Required owner predicate and ordering |
|---|---|
| Insert contact | Persist an entity whose `ownerSub` is set from the service argument |
| List contacts | `ownerSub`, ordered by `createdAt ASC, id ASC` |
| Search contacts | `ownerSub` plus case-insensitive name/email matching, ordered by `createdAt ASC, id ASC` |
| Check contact ownership | `id AND ownerSub` |
| Insert interaction | Persist an entity whose `ownerSub` and `contactId` are set explicitly |
| List interactions | `ownerSub AND contactId`, ordered by `occurredAt ASC, id ASC` |

Use derived repository methods for simple predicates and
`JpaSpecificationExecutor`/Criteria API for contact search so that LIKE
escaping remains explicit without embedding SQL or JPQL strings. The search
specification must:

1. escape backslash first, then `%` and `_`;
2. use a fixed escape character;
3. lower-case both the database expressions and the input using a stable
   locale;
4. group the name/email OR expression inside the owner predicate.

Do not use `findById` for owner-scoped access unless the owner check is applied
in the same repository operation.

### 3.4 CRM repository boundary

The existing `ContactRepository` and `InteractionRepository` interfaces may
remain as domain-facing adapters so services do not depend on Spring Data
types. Replace `JdbcContactRepository` and `JdbcInteractionRepository` with
JPA-backed implementations that delegate to the Spring Data interfaces and map
entities to API records.

Rename the service implementations to `ContactServiceImpl` and
`InteractionServiceImpl` (or retain the current names if minimizing the diff
is preferable), but remove the `Jdbc` prefix once no JDBC implementation
remains. No `JdbcTemplate`, `ResultSet`, `SQLException`, or SQL literals may
remain in production CRM persistence code.

### 3.5 Transactions

1. Mark service write operations transactional.
2. Mark list/search operations read-only transactional where required by JPA
   session behavior.
3. Keep contact ownership validation and interaction insertion in the same
   transaction boundary where practical.
4. Do not rely on lazy entity relationships for authorization.

## 4. Refactoring sequence

### Phase A: Establish the JPA baseline

1. Verify the pinned Boot 4.2.0 JPA starter and Hibernate/JDK 25 compatibility
   from official documentation.
2. Change Gradle dependencies and add `spring.jpa.hibernate.ddl-auto=validate`.
3. Start the application against the existing Postgres schema.
4. Confirm Flyway remains the only schema migration mechanism.

Check: application context starts, Flyway reports no pending migration, and
Hibernate validation accepts the existing V1 schema.

### Phase B: Introduce entities and Spring Data repositories

1. Add the two entities with explicit table/column mappings.
2. Add the two Spring Data repository interfaces.
3. Add entity-to-record mapping.
4. Implement owner-scoped derived methods.
5. Implement escaped search using Criteria/Specification API.

Check: repository integration tests pass against the existing Postgres
database, including ordering, nullable email, and literal wildcard behavior.

### Phase C: Replace the CRM adapters

1. Replace both JDBC repository implementations with JPA-backed adapters.
2. Preserve the existing `ContactRepository` and `InteractionRepository`
   behavior and explicit `sub` parameters.
3. Remove JDBC imports and SQL literals from production CRM code.
4. Keep service validation and error semantics unchanged.

Check: all existing CRM service tests pass without changing their behavioral
assertions.

### Phase D: Migrate tests and enforce the boundary

1. Replace `JdbcTemplate` test cleanup with repository-level deletion or a
   test fixture using the JPA repositories.
2. Keep direct SQL only in the application/schema test that verifies Flyway,
   constraints, indexes, and the physical database contract.
3. Update the architecture test to allow JPA types only inside CRM
   persistence and to reject JPA/JDBC types from `mcp`.
4. Add a check that production CRM persistence contains no JDBC implementation
   or SQL query strings.

Check: targeted tests and the full Gradle build pass; a deliberate JPA or
JDBC import in `mcp` fails the boundary test.

### Phase E: Remove obsolete code

1. Delete the JDBC repository implementations and any now-unused JDBC-facing
   production interfaces or helpers.
2. Remove the JDBC starter if no application code requires it.
3. Update the phase documentation and this specification with the final names
   and verified dependency details.

Check: clean build succeeds from a clean database and no obsolete JDBC
production classes remain.

## 5. Required test coverage

Preserve and/or add focused tests for:

- create, list, and search contacts;
- contact and interaction ordering;
- nullable email and notes;
- blank name, invalid email, null date, and blank summary validation;
- validation failures do not write rows;
- subject A cannot list or search subject B's contacts;
- subject B cannot log or list interactions for subject A's contact;
- literal `%`, `_`, and `\` search input;
- Flyway migration, indexes, constraints, and foreign key;
- Hibernate validation against the Flyway schema;
- no application SQL/JDBC implementation remains;
- `mcp` depends only on `crm.api`, not JPA, Spring Data, JDBC, or Flyway.

The owner-isolation test is mandatory and must remain a real Postgres
integration test.

## 6. Files expected to change

Likely changes:

- `mcp/build.gradle`
- `mcp/src/main/resources/application.yaml`
- `mcp/src/main/java/io/github/aermolov/personalcrm/crm/persistence/*`
- `mcp/src/main/java/io/github/aermolov/personalcrm/crm/internal/*`
- `mcp/src/test/java/io/github/aermolov/personalcrm/crm/CrmServiceTests.java`
- `mcp/src/test/java/io/github/aermolov/personalcrm/PersonalCrmApplicationTests.java`
- the CRM boundary test and its specification

Likely new files:

- `ContactEntity`
- `InteractionEntity`
- `ContactJpaRepository`
- `InteractionJpaRepository`
- a search `Specification`/Criteria helper, if kept separate

Do not change `V1__create_contacts_and_interactions.sql` unless Hibernate
validation exposes a real mapping/schema mismatch. Any schema change must be a
new Flyway migration, never an edit to V1.

## 7. Acceptance criteria

1. The application starts against the current V1 schema with Hibernate in
   validation-only mode.
2. CRM production persistence contains no hand-written SQL, `JdbcTemplate`,
   `ResultSet`, or JDBC repository implementation.
3. All repository operations apply the authenticated/development subject
   predicate in the same database operation.
4. Existing CRM API records, service signatures, validation behavior, ordering,
   search semantics, and owner-isolation behavior are preserved.
5. Search wildcard characters remain literal.
6. Flyway remains responsible for schema evolution.
7. No `mcp` class imports JPA, Spring Data, Spring JDBC, `java.sql`, or Flyway.
8. The owner-isolation integration test and full Gradle build pass.

## 8. Risks and decisions to resolve during implementation

- Confirm the exact Boot 4.2.0 starter and Hibernate behavior on Java 25 before
  changing the build.
- Verify PostgreSQL `TIMESTAMP WITH TIME ZONE` maps consistently to
  `OffsetDateTime` with the selected Hibernate version.
- Verify Criteria API escaping produces the same literal-search behavior as the
  current JDBC implementation.
- Decide whether the domain-facing repository adapter interfaces are retained
  permanently or removed after services depend directly on a persistence
  facade. Retaining them is the lower-risk first step.
- Do not add JPA relationships merely to model the foreign key; owner scoping
  is an authorization invariant, not only an object graph concern.

