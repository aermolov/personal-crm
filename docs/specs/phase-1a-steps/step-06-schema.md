# Step 6: Schema

Parent: plan section 6, step 6. Status: IMPLEMENTED (2026-11-24). Depends on: step 5.

## Objective

Flyway-managed schema for contacts and interactions.

## Tasks

1. Add Flyway (artifact names verified in step 2, including the Postgres module if required).
2. Add `V1__create_contacts_and_interactions.sql` in the verified default location.
3. `contacts`: UUID `id`, `owner_sub` NOT NULL, `name` NOT NULL, `email` nullable, `notes`, `created_at` NOT NULL default now.
4. `interactions`: UUID `id`, `owner_sub` NOT NULL, `contact_id` NOT NULL FK to contacts, `occurred_at` NOT NULL, `summary` NOT NULL.
5. Indexes: `contacts(owner_sub)`, `interactions(owner_sub)`, `interactions(owner_sub, contact_id)`; consider an index to support name/email search.
6. Consider a check constraint rejecting blank `name`.
7. Hibernate/JPA schema generation must be off or `validate` only; Flyway owns the schema.

## Out of scope

Entities and repositories (step 7).

## Done when

- On a clean database (`docker compose down -v`, up), the app starts and Flyway applies V1.
- A second start is a no-op.
- Inspect tables/indexes with `psql` to confirm.
