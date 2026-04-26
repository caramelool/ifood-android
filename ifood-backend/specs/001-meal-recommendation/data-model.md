# Data Model: Meal Recommendation

> No persistent storage. All entities are in-memory TypeScript types.

## Request Shape — `MealRecommendationQuery`

Received as GET query parameters.

| Field         | Type       | Required | Validation                              |
|---------------|------------|----------|-----------------------------------------|
| `userName`    | `string`   | no       | Optional; `null` in response if omitted |
| `mealType`    | `string`   | yes      | Any non-empty string (no enum constraint) |
| `preferences` | `string[]` | yes      | At least one free-text tag              |

## Response Shape — `MealRecommendationResponse`

Returned as JSON body.

| Field             | Type     | Description                          |
|-------------------|----------|--------------------------------------|
| `userName`        | `string` | Echoed from request                  |
| `mealType`        | `string` | Echoed from request                  |
| `placeName`       | `string` | Name of the restaurant/place         |
| `placeAddress`    | `string` | Street address of the place          |
| `mealName`        | `string` | Name of the recommended meal         |
| `mealDescription` | `string` | Short description of the meal        |
| `mealPrice`       | `number` | Price in BRL (e.g., `32.90`)         |

## Mock Meal Entity — `MockMeal`

Internal type used in `src/mock/meals.ts` seed data.

| Field             | Type     | Description               |
|-------------------|----------|---------------------------|
| `mealType`        | `string` | Meal type label used for filtering (`breakfast`, `lunch`, `dinner`, `afternoon_snack`) |
| `placeName`       | `string` | Restaurant name           |
| `placeAddress`    | `string` | Restaurant address        |
| `mealName`        | `string` | Meal name                 |
| `mealDescription` | `string` | Meal description          |
| `mealPrice`       | `number` | Price in BRL              |

## Selection Logic

1. Filter `MockMeal[]` by `mealType` (exact match).
2. If no meal matches, return HTTP 404 with a descriptive message.
3. Pick one meal at random from the filtered candidates.
4. `mealType` in the response is always the value echoed from the request.
5. `preferences` must be a non-empty array; missing or empty returns HTTP 400.
