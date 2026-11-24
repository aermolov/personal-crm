# Step 1: Record decisions

Parent: `../phase-1a-implementation-plan.md` section 6, step 1. Status: DRAFT.

## Objective

Make sure O2, C1, C4 and C5 are recorded in `docs/DECISIONS.md` so no later step invents them.

## Inputs

- `docs/DECISIONS.md` (D6, D7, D13, D14, D15)
- Plan section 3 (all items already marked settled)

## Tasks

1. Open `DECISIONS.md` and check each item:
   - O2 base Java package and group id: D15 exists and states both values.
   - C1 app directory `mcp/`.
   - C4 dev identity: fixed configurable dev `sub` property, replaced by JWT `sub` in Phase 1b. Name the property key only after step 2 and step 4 settle it; record it as "to be named in step 4".
   - C5 D6 amended: Postgres in Compose is part of 1a.
2. Add any missing entry using the existing numbering and format. Do not change pinned versions here.
3. Confirm `AGENTS.md` and the spec index point to `mcp/` and to the 1a plan.

## Out of scope

Version verification (step 2), any code.

## Done when

- Each of O2, C1, C4, C5 has a findable entry in `DECISIONS.md`.
- Check: file updated, no conflict with the plan (DECISIONS wins on conflict).
