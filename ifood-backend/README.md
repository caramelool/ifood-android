# ifood-backend

REST API that serves meal recommendations to the **ifood-android** app. Returns a random restaurant suggestion based on meal type and user preferences.

MVP scope: single endpoint, static mock data, no authentication, no database.

---

## How it was built

This backend was developed using **SDD (Specification-Driven Development)** with the **SpecKit** framework. In this process, every implementation step is preceded by natural-language specification artifacts:

```
spec → plan → tasks → implement
```

Artifacts are located at `specs/001-meal-recommendation/`:

| File | Content |
|---|---|
| `spec.md` | User stories, requirements, and acceptance criteria |
| `plan.md` | Architecture decisions and technical structure |
| `data-model.md` | Request/response format and mock entities |
| `tasks.md` | Dependency-ordered task list |
| `contracts/recommendation-api.md` | Full API contract with examples |

Code was only written after all these artifacts were approved.

---

## Stack

- **Runtime:** Node.js 20 + TypeScript 5 (strict mode)
- **Framework:** Express 4
- **Testing:** Jest + Supertest
- **Deploy:** Vercel (serverless via `api/index.ts`)

---

## Endpoint

### `GET /recommendation`

**Query parameters:**

| Parameter | Type | Required | Description |
|---|---|---|---|
| `userName` | string | yes | User's name |
| `mealType` | string | yes | `breakfast`, `lunch`, `dinner`, `afternoon_snack` |
| `preferences` | string[] | yes | List of dietary preferences (at least 1) |

**Example:**

```bash
curl "http://localhost:3000/recommendation?userName=Lucas&mealType=lunch&preferences=vegetarian"
```

**200 Response:**

```json
{
  "userName": "Lucas",
  "mealType": "lunch",
  "placeName": "Restaurante Verde Folha",
  "placeAddress": "Rua das Flores, 123 - São Paulo, SP",
  "mealName": "Bowl de Quinoa com Legumes",
  "mealDescription": "Bowl nutritivo com quinoa, legumes grelhados e molho tahine.",
  "mealPrice": 32.90,
  "mealImageUrl": "https://..."
}
```

**Errors:**

| Status | Reason |
|---|---|
| 400 | `userName`, `mealType`, or `preferences` missing/invalid |
| 404 | No meal found for the given `mealType` |

---

## Running locally

```bash
npm install

# development (watch mode)
npm run dev

# tests
npm test

# production build
npm run build
npm start
```

---

## Structure

```
ifood-backend/
├── api/            # Vercel entry point (serverless)
├── src/
│   ├── app.ts      # Express app factory
│   ├── server.ts   # Local server startup
│   ├── routes/     # Endpoint handlers
│   └── mock/       # Static meal data
├── specs/          # SDD artifacts (spec, plan, tasks, contracts)
└── tests/          # Integration tests
```
