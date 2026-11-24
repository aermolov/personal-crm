# Step 11: Inspector run

Parent: plan section 6, step 11. Status: DRAFT. Depends on: steps 5, 10.

## Objective

Exercise the tools end to end with MCP Inspector from Windows.

## Tasks

1. Start Postgres (`docker compose up -d`) and the app in WSL.
2. Start Inspector from Windows with Node 22.7.5 or newer (`npx @modelcontextprotocol/inspector`; pin the version used and record it). Default ports: UI 6274, proxy 6277.
3. Security: keep the proxy session token enabled (never disable auth, never set `DANGEROUSLY_OMIT_AUTH`); keep both ports on localhost only; do not set host binding to `0.0.0.0` (CVE-2025-49596, see `DECISIONS.md`).
4. In the UI choose transport "Streamable HTTP" and URL `http://localhost:<port>/<path>` (from step 10); connect using the token-bearing URL.
5. Verify: tools list shows all five tools with input schemas.
6. Run the flow: `create_contact` -> `list_contacts` -> `search_contacts` -> `log_interaction` -> `list_interactions`.
7. Run the error cases: blank name, unknown contact id; confirm clear error and no row written (check with `psql`).
8. Fallback if Windows cannot reach WSL `localhost`: run Inspector inside WSL and open the UI from Windows. Record which mode worked.

## Done when

- All acceptance items for Inspector in plan section 7 are observed; results (and any fallback used) are noted for step 12.
