---
description: "Task list for Meal Recommendation backend"
---

# Tasks: Meal Recommendation

**Input**: Design documents from `specs/001-meal-recommendation/`
**Prerequisites**: plan.md ✅, spec.md ✅, research.md ✅, data-model.md ✅, contracts/ ✅

**Tests**: Integration tests included (required by Constitution — Principle III).

**Organization**: Tasks are grouped by user story to enable independent implementation
and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (US1, US2)
- Include exact file paths in descriptions

---

## Phase 1: Setup

**Purpose**: Project initialization and TypeScript/Node.js scaffolding

- [x] T001 Initialize npm project and create `package.json` at repo root (`ifood-backend/`)
- [x] T002 [P] Create `tsconfig.json` at repo root with `strict: true`, `outDir: dist`, `rootDir: src`
- [x] T003 [P] Install runtime dependencies: `express` in `package.json`
- [x] T004 [P] Install dev dependencies: `typescript`, `ts-node`, `tsx`, `@types/express`, `@types/node`, `jest`, `supertest`, `@types/jest`, `@types/supertest`, `ts-jest` in `package.json`
- [x] T005 [P] Add npm scripts to `package.json`: `dev` (tsx watch src/server.ts), `build` (tsc), `start` (node dist/server.js), `test` (jest)
- [x] T006 [P] Create `jest.config.ts` at repo root configuring `ts-jest` preset and `testMatch: ['**/tests/**/*.test.ts']`
- [x] T007 Create directory structure: `src/routes/`, `src/mock/`, `tests/`

---

## Phase 2: Foundational

**Purpose**: Core app wiring shared by all user stories

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [x] T008 Create `src/app.ts` — Express app factory that registers routes and exports the app (no `listen` call here)
- [x] T009 Create `src/server.ts` — entry point that imports app from `src/app.ts` and calls `app.listen(3000)`

**Checkpoint**: Foundation ready — user story implementation can now begin

---

## Phase 3: User Story 1 — Submit Preferences and Get Recommendation (Priority: P1) 🎯 MVP

**Goal**: A user sends a non-empty preferences list and a mealType via GET and receives a random matching meal.

**Independent Test**: `GET /recommendation?userName=Lucas&mealType=lunch&preferences=vegan`
returns a 200 JSON body with all required fields populated.

### Integration Tests for User Story 1 ⚠️ Write first — confirm they FAIL before implementing

- [x] T010 [P] [US1] Write integration test: happy path with preferences returns 200 and all response fields in `tests/recommendation.test.ts`
- [x] T011 [P] [US1] Write integration test: known mealType echoed back in `tests/recommendation.test.ts`
- [x] T012 [P] [US1] Write integration test: missing `userName` returns 400 in `tests/recommendation.test.ts`
- [x] T013 [P] [US1] Write integration test: missing `mealType` returns 400 in `tests/recommendation.test.ts`
- [x] T014 [P] [US1] Write integration test: unknown mealType returns 404 in `tests/recommendation.test.ts`
- [x] T015 [P] [US1] Write integration test: afternoon_snack returns 200 in `tests/recommendation.test.ts`
- [x] T016 [P] [US1] Write integration test: unknown mealType (supper) returns 404 in `tests/recommendation.test.ts`
- [x] T017 [P] [US1] Write integration test: missing preferences returns 400 in `tests/recommendation.test.ts`
- [x] T018 [P] [US1] Write integration test: empty preferences array returns 400 in `tests/recommendation.test.ts`

### Implementation for User Story 1

- [x] T019 [US1] Create `src/mock/meals.ts` — define `MockMeal` type (no `tags`, no time fields) and export a static array of ≥8 diverse meal entries covering breakfast, lunch, dinner, and afternoon_snack
- [x] T020 [US1] Create `src/routes/recommendation.ts` — validate `mealType` (required) and `preferences` (required non-empty array); return 400 with descriptive message on failure
- [x] T021 [US1] Add selection logic to `src/routes/recommendation.ts` — filter mock meals by `mealType`, pick one at random, return 404 if no match
- [x] T022 [US1] Register `GET /recommendation` route in `src/app.ts` using the handler from `src/routes/recommendation.ts`

**Checkpoint**: Run `npm test` — all tests in T010–T018 must pass

---

## Phase 4: Polish & Cross-Cutting Concerns

**Purpose**: Final validation and documentation check

- [x] T023 [P] Verify `npm run dev` starts without errors and all curl examples in `specs/001-meal-recommendation/quickstart.md` return expected responses
- [x] T024 [P] Ensure `npm run build` compiles without TypeScript errors
- [x] T025 Run `npm test` final pass — all tests green, no console errors

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies — start immediately; T002–T007 can all run in parallel after T001
- **Foundational (Phase 2)**: Depends on Setup completion — BLOCKS user story work
- **User Story 1 (Phase 3)**: Depends on Foundational (Phase 2)
  - T010–T018 (tests): Write and run together after T008–T009; they must FAIL at this point
  - T019: No dependency within US1 — can start immediately after Foundational
  - T020: Depends on T019 (needs MockMeal type for type imports)
  - T021: Depends on T020 (extends validation handler with selection)
  - T022: Depends on T020–T021 (registers complete handler)
- **Polish (Phase 4)**: Depends on Phase 3 completion; T023–T024 run in parallel, T025 runs last

### Parallel Opportunities

- Setup: T002, T003, T004, T005, T006, T007 all run in parallel after T001
- US1 tests: T010–T018 all run in parallel (same file, no inter-dependency)
- US1 models: T019 runs in parallel with test writing
- Polish: T023, T024 run in parallel

---

## Implementation Strategy

### MVP (User Story 1 Only)

1. Complete Phase 1: Setup
2. Complete Phase 2: Foundational
3. Write tests T010–T018 → confirm they FAIL
4. Complete Phase 3: User Story 1 (T019–T022)
5. **STOP and VALIDATE**: `npm test` all green, curl examples pass
6. Done — single endpoint fully functional

---

## Notes

- [P] tasks = different files or no inter-dependencies — safe to parallelize
- [US1] label maps each task to User Story 1 for traceability
- Tests MUST be written before implementation and MUST fail first (red-green-refactor)
- `src/app.ts` exports the Express app without calling `listen` — this allows Supertest to import it cleanly in tests
- No database, no auth, no pagination — any added complexity violates the constitution
