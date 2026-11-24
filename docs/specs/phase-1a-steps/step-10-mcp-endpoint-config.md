# Step 10: MCP endpoint config

Parent: plan section 6, step 10. Status: DRAFT. Depends on: step 8.

## Objective

Streamable HTTP MCP endpoint on localhost, verified with an initialize request.

## Tasks

1. In `application.yaml`, set the property keys verified in step 2 for: streamable HTTP protocol, endpoint path (default expected `/mcp`, confirm), server name and version. Use no unverified keys.
2. Bind the server to loopback (`server.address=127.0.0.1`) because the endpoint is unauthenticated until Phase 1b. Note: with WSL NAT forwarding this should still be reachable from Windows via `localhost`; if not, see the step 11 fallback.
3. Fix the server port and record it for the README.
4. Add an integration test (random port) that sends a JSON-RPC `initialize` request with the headers required by Streamable HTTP and asserts a valid result (protocol version, server info, tools capability). Follow with `tools/list` asserting all five tools.
5. Manual check with `curl` against the running app.

## Done when

- The initialize request returns a valid response in both the test and `curl`.
- Config contains only documented keys.
