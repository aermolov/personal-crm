# Step 12: Run documentation

Parent: plan section 6, step 12. Status: DRAFT. Depends on: step 11.

## Objective

A README section that lets a fresh clone reach a working Inspector session.

## Content

1. Prerequisites with versions (from `DECISIONS.md`): Java 25, Docker in WSL, Node 22.7.5+.
2. Setup: `cp .env.example .env` and which placeholders to fill in (never commit `.env`).
3. Start order, with exact commands:
   1. `docker compose up -d` and how to check health.
   2. Run the app with the Gradle wrapper (`./gradlew bootRun` from `mcp/`).
   3. Inspector command and the URL to enter (transport Streamable HTTP, `http://localhost:<port>/<path>`).
4. Tool list with one example call each.
5. Security notes: localhost only, unauthenticated until Phase 1b, keep the Inspector session token, no exposed ports.
6. Troubleshooting for WSL localhost access: NAT vs mirrored mode, run Inspector inside WSL fallback, port conflicts, Postgres not healthy, wrong endpoint path.
7. Running tests: `./gradlew test` and `./gradlew build`.

## Verification

Follow the README literally from a fresh clone (new directory, no `.env`, no volume); fix gaps found.

## Done when

- A fresh clone works using only the README.
- No secrets in the docs; commands match the files in the repo.
