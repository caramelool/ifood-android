# AI-Assisted Development

AI tools were used throughout this project as a productivity and quality accelerator — acting as a collaborator, not as the author. All architectural decisions, design choices, and final implementations were made and reviewed by the developer. AI assisted in generating candidates, surfacing issues, and drafting content that was then adapted, validated, or discarded based on project judgment.

---

## Methodology

Three complementary approaches guided how AI was applied during development:

| Approach | Description |
|---|---|
| **Vibe Coding** | Rapid iteration on UI and logic using AI-generated scaffolding as a starting point, then shaping the output to fit project standards and architecture |
| **AI-Assisted Programming** | Pair-programming style interaction — proposing implementations, catching edge cases, and suggesting refactors with full project context provided |
| **Spec-Driven Development** | Writing specs and requirements first, then using AI to validate that the implementation aligned with the stated intent before committing |

---

## Tools

| Tool | Role |
|---|---|
| **Claude** (Anthropic) | Primary tool — code generation, architecture discussion, code review, and documentation drafting |
| **Gemini** (Google) | Secondary tool — alternative perspective on implementations and Kotlin/Android-specific suggestions |

---

## Areas of Application

### Development
AI assisted in generating boilerplate and initial implementations — including Room entities, DAOs, Use Case classes, ViewModel scaffolding, and Hilt modules. Generated code was always reviewed against the project's Clean Architecture conventions before being accepted.

### Code Review
AI was used to review pull requests and isolated changes, checking for:
- Deviations from MVVM and Clean Architecture layer boundaries
- Inconsistent naming or missing conventions
- Logic issues not caught during development

Human review remained the final gate for all merges.

### Documentation
AI helped draft and review content across `/docs/`, `README.md`, and `CLAUDE.md`. Structure, tone, and accuracy were verified by the developer before publishing.

---

## Scope and Human Oversight

- All architectural decisions — layer boundaries, DI strategy, navigation approach, database schema — were defined by the developer
- AI suggestions were always reviewed; many were adapted, some were rejected entirely
- No AI tool had direct access to the repository or made autonomous commits
- The developer retains full understanding and ownership of every part of the codebase
