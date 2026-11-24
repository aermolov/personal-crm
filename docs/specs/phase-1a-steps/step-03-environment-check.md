# Step 3: Environment check in WSL

Parent: plan section 6, step 3. Status: DRAFT. Depends on: step 2 (pinned versions).

## Objective

Confirm the WSL2 toolchain can build and run the project.

## Tasks (all inside WSL)

1. `java -version` reports Java 25 (the pinned distribution from `DECISIONS.md`).
2. `docker --version` and `docker compose version` succeed; `docker ps` works without sudo (or note the required setup).
3. `node --version` is 22.7.5 or newer (needed for MCP Inspector); `npx --version` works.
4. `git --version`; repo lives on the WSL Linux filesystem (`/home/ermol/...`), not `/mnt/c`.
5. Check localhost forwarding assumption: WSL networking mode (NAT or mirrored) via `wslconfig`/`wsl --status`; note the result for step 11 and the README.
6. Check ports 5432 (Postgres), 8080 (app, or the configured port), 6274 and 6277 (Inspector) are free.

## Output

A short "Environment verified" note (versions and WSL networking mode) in the step 12 README troubleshooting draft or in `DECISIONS.md` if a version differs from the pin.

## Done when

- All version commands succeed and meet minimums.
- Any missing tool is installed or reported as a blocker.
