# Implementation Plan: Meal Recommendation

**Branch**: `001-meal-recommendation` | **Date**: 2026-04-26 | **Spec**: [spec.md](spec.md)
**Input**: Feature specification from `specs/001-meal-recommendation/spec.md`

## Summary

Single GET `/recommendation` endpoint that receives `userName`, `mealType`, and a
required non-empty list of preference strings, then returns a random meal for the given
type from a static in-memory mock dataset — no database, no external services.

## Technical Context

**Language/Version**: TypeScript 5 / Node.js 20 LTS
**Primary Dependencies**: Express.js v4, Jest, Supertest, ts-node, tsx
**Storage**: N/A — in-memory static mock data only
**Testing**: Jest + Supertest (integration tests, no external dependencies)
**Target Platform**: Local HTTP server (internal validation only)
**Project Type**: Web service (single-endpoint REST API)
**Performance Goals**: No specific targets — internal validation use only
**Constraints**: No database, no auth, no persistence, mocked responses
**Scale/Scope**: Single endpoint, ~10 mock meals, 1 consumer (ifood Android app)

## Constitution Check

### Pre-Design Gate ✅

| Principle              | Status | Notes                                          |
|------------------------|--------|------------------------------------------------|
| I. Simplicity-First    | ✅ PASS | Single endpoint, no DB, static mock, no layers |
| II. Internal Scope Only| ✅ PASS | No auth, no rate-limiting, no prod hardening   |
| III. Minimal Test Coverage | ✅ PASS | Integration test required for happy path   |

### Post-Design Re-Check ✅

Design adds no violations. Static mock + simple filter/score algorithm satisfies all
three principles without any unjustified complexity.

## Project Structure

### Documentation (this feature)

```text
specs/001-meal-recommendation/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── contracts/
│   └── recommendation-api.md   # Phase 1 output
└── tasks.md             # Phase 2 output (/speckit-tasks)
```

### Source Code (repository root)

```text
ifood-backend/
├── src/
│   ├── app.ts                      # Express app factory (no server.listen here)
│   ├── server.ts                   # Entry point — calls app.listen
│   ├── routes/
│   │   └── recommendation.ts       # GET /recommendation — validate, score, respond
│   └── mock/
│       └── meals.ts                # Static MockMeal[] seed data (~10 entries)
├── tests/
│   └── recommendation.test.ts      # Supertest integration tests
├── package.json
├── tsconfig.json
└── .specify/                       # Spec Kit tooling (not part of app build)
```

**Structure Decision**: Single Node.js project at repo root. No monorepo, no separate
packages — one source tree satisfies the single-endpoint scope per Simplicity-First.

## Complexity Tracking

> No constitution violations — section not applicable.
