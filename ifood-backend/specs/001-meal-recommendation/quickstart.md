# Quickstart: Meal Recommendation Backend

## Prerequisites

- Node.js 20 LTS
- npm 9+

## Setup

```bash
cd ifood-backend
npm install
```

## Run (development)

```bash
npm run dev
# Server starts on http://localhost:3000
```

## Run (production build)

```bash
npm run build
npm start
```

## Test the endpoint

```bash
# Happy path — with preferences
curl "http://localhost:3000/recommendation?userName=Lucas&mealType=lunch&mealTime=12:30&preferences=vegan&preferences=spicy"

# Happy path — no preferences
curl "http://localhost:3000/recommendation?userName=Ana&mealType=breakfast&mealTime=08:00"

# Validation error
curl "http://localhost:3000/recommendation?mealType=lunch&mealTime=12:30"

# No match (invalid mealType triggers 400, out-of-window time triggers 404)
curl "http://localhost:3000/recommendation?userName=Pedro&mealType=dinner&mealTime=03:00"
```

## Run tests

```bash
npm test
```

## Project structure

```
ifood-backend/
├── src/
│   ├── app.ts              # Express app setup
│   ├── server.ts           # Entry point (starts HTTP server)
│   ├── routes/
│   │   └── recommendation.ts   # GET /recommendation handler
│   └── mock/
│       └── meals.ts            # Static mock meal data
├── tests/
│   └── recommendation.test.ts  # Integration tests (Jest + Supertest)
├── package.json
└── tsconfig.json
```
