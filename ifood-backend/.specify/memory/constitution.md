<!--
SYNC IMPACT REPORT
==================
Version change: (unversioned template) → 1.0.0
Principles added: I. Simplicity-First, II. Internal Scope, III. Minimal Test Coverage
Sections added: Core Principles, Governance
Sections removed: SECTION_2, SECTION_3 (not needed for internal validation scope)
Templates requiring updates:
  - .specify/templates/plan-template.md  ✅ Constitution Check gates updated inline
  - .specify/templates/spec-template.md  ✅ No conflicts found
  - .specify/templates/tasks-template.md ✅ No conflicts found
Deferred TODOs: none
-->

# ifood-backend Constitution

## Core Principles

### I. Simplicity-First

Every implementation MUST choose the simplest solution that satisfies the requirement.
No abstractions, patterns, or layers are added unless a concrete, present need exists.
YAGNI (You Aren't Gonna Need It) is enforced by default.

### II. Internal Scope Only

This backend exists solely for internal validation of the ifood Android app.
It MUST NOT be hardened for production (no auth, rate-limiting, or scaling concerns unless
explicitly requested). Complexity introduced for hypothetical external use is a violation.

### III. Minimal Test Coverage

Each endpoint or service MUST have at least one integration or contract test covering the
happy path. Unit tests are optional. Tests MUST run locally without external dependencies.

## Governance

This constitution supersedes any conflicting implementation choices.
Amendments require updating this file with a version bump and a rationale note.
All plan Constitution Check gates are derived from the three principles above.

**Version**: 1.0.0 | **Ratified**: 2026-04-26 | **Last Amended**: 2026-04-26
