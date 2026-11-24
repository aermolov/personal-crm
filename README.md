# Personal CRM

Repository for the local MCP server. The initial local database setup is documented below.

## Local PostgreSQL

Copy `.env.example` to `.env` and set a local password for `POSTGRES_PASSWORD`. From the repository root, start the database with `docker compose up -d --wait`. Compose reads the database name and credentials from `.env`.

The Spring application reads those same `POSTGRES_DB`, `POSTGRES_USER`, and `POSTGRES_PASSWORD` variables from its process environment. From `mcp/`, export them before starting or testing the app:

```sh
set -a
. ../.env
set +a
./gradlew bootRun
```

The database test also requires the Compose service to be running.
