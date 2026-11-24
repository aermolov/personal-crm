# Step 5: Postgres via Compose

Parent: plan section 6, step 5. Status: DRAFT. Depends on: step 4.

## Objective

A local Postgres container the app connects to.

## Tasks

1. Create the Compose file (location per project layout, for example repo root `compose.yaml`) with one `postgres` service only, using the image tag verified in step 2.
2. Named volume for data.
3. Publish the port bound to loopback only: `127.0.0.1:5432:5432`.
4. Credentials via `${VARS}` from an uncommitted `.env`; commit `.env.example` with placeholders only (DB name, user, password placeholder). Confirm `.env` is git-ignored.
5. Healthcheck with `pg_isready`, with interval/retries.
6. Add datasource config to the app (`application.yaml`): URL, user, password read from environment variables, no defaults containing real credentials. Add the JDBC starter and Postgres driver (artifact names verified in step 2).
7. Document that the app reads the same variables as Compose.

## Out of scope

Keycloak, Caddy, other services, schema (step 6).

## Done when

- `docker compose up -d` shows the container healthy.
- The app starts and connects (context-load against the DB, or a startup connection check).
- `git status` shows no `.env` committed.
