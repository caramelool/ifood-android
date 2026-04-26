# Research: Meal Recommendation Backend

## Language & Runtime

**Decision**: Node.js 20 LTS with TypeScript 5
**Rationale**: Established in root CLAUDE.md; LTS guarantees stability for internal use.
**Alternatives considered**: None — stack fixed by project context.

## HTTP Framework

**Decision**: Express.js (latest v4)
**Rationale**: Simplest production-grade HTTP framework for Node.js; minimal boilerplate
for a single-endpoint service; no need for a more opinionated framework (Fastify, NestJS)
given the one-endpoint scope.
**Alternatives considered**: Fastify (faster, but more setup); plain `http` module
(too low-level for JSON routing); NestJS (excessive for one endpoint).

## Mocking Strategy

**Decision**: In-memory static array of `Meal` objects in `src/mock/meals.ts`.
**Rationale**: No database needed per spec; static fixtures are the simplest approach
that satisfies the recommendation logic and allows deterministic testing.
**Alternatives considered**: JSON file on disk (unnecessary I/O); SQLite in-memory
(overkill with no persistence requirement).

## Recommendation Algorithm

**Decision**: Filter mocked meals by `mealType` + `mealTime`, then score by number of
matched `preferences` strings; return the top match.
**Rationale**: Simplest attribute-intersection scoring satisfying Simplicity-First
principle. No ML or external ranking service needed for internal validation.
**Alternatives considered**: Random selection (non-deterministic, hard to test);
external ranking API (out of scope, violates Internal Scope Only principle).

## Testing

**Decision**: Jest + Supertest
**Rationale**: De-facto standard for Node.js integration testing; Supertest allows
HTTP-level endpoint testing without spinning up a real server port.
**Alternatives considered**: Mocha + Chai (more setup); Vitest (good but less common
in existing Node.js codebases).

## Query Parameter Handling

**Decision**: `preferences` passed as repeated query param: `?preferences=vegan&preferences=spicy`.
**Rationale**: Standard REST convention for array query params; straightforward to parse
with Express; maps directly to `string[]` in TypeScript.
**Alternatives considered**: Comma-separated single param (requires manual splitting,
error-prone); JSON body on GET (non-standard, violates REST conventions).
