# Step 13: Full build

Parent: plan section 6, step 13. Status: DRAFT. Depends on: steps 1-12.

## Objective

Final verification and bookkeeping for Phase 1a.

## Tasks

1. From `mcp/`, run `./gradlew clean build` with Postgres available as the tests require; all tests including the boundary test pass.
2. Walk the plan section 7 acceptance criteria and tick each one:
   - README flow works; Inspector connects from Windows.
   - All five tools listed with input schemas.
   - Sub B cannot read sub A's data (automated test).
   - Invalid input gives a clear error and writes nothing.
   - Boundary test fails on a violation.
   - No secrets or `.env` committed; `graphify-out/` ignored (`git status`, `git check-ignore graphify-out`).
3. Update `DECISIONS.md` status for Phase 1a and fill any open items.
4. Update the spec index and the status line of the 1a plan (DRAFT -> done) and, if needed, `docs/PROJECT_PLAN.md`/wiki per `docs/wiki/SCHEMA.md` (wiki cites sources, records no decisions).
5. Note the handoff items for Phase 1b (replace dev `sub` seam, audience validation, Keycloak and Caddy in Compose).

## Done when

- Build green, all acceptance criteria checked, documentation updated.
