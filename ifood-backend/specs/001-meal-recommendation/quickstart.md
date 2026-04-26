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
curl "http://localhost:3000/recommendation?userName=Lucas&mealType=lunch&preferences=vegan&preferences=spicy"

# Happy path — single preference
curl "http://localhost:3000/recommendation?userName=Ana&mealType=breakfast&preferences=healthy"

# Missing mealType → 400
curl "http://localhost:3000/recommendation?userName=Pedro&preferences=vegan"

# Missing preferences → 400
curl "http://localhost:3000/recommendation?userName=Pedro&mealType=lunch"

# Unknown mealType → 404
curl "http://localhost:3000/recommendation?userName=Maria&mealType=brunch&preferences=vegan"
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
