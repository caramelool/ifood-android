# Data Model: Meal Recommendation

> No persistent storage. All entities are in-memory TypeScript types.

## Request Shape — `MealRecommendationQuery`

Received as GET query parameters.

| Field         | Type       | Required | Validation                              |
|---------------|------------|----------|-----------------------------------------|
| `userName`    | `string`   | yes      | Non-empty                               |
| `mealType`    | `string`   | yes      | One of: `breakfast`, `lunch`, `dinner`  |
| `mealTime`    | `string`   | yes      | HH:MM format (e.g., `12:30`)            |
| `preferences` | `string[]` | no       | Zero or more free-text tags             |

## Response Shape — `MealRecommendationResponse`

Returned as JSON body.

| Field          | Type       | Description                              |
|----------------|------------|------------------------------------------|
| `userName`     | `string`   | Echoed from request                      |
| `mealType`     | `string`   | Echoed from request                      |
| `placeName`    | `string`   | Name of the restaurant/place             |
| `placeAddress` | `string`   | Street address of the place              |
| `mealName`     | `string`   | Name of the recommended meal             |
| `mealDescription` | `string` | Short description of the meal          |
| `mealPrice`    | `number`   | Price in BRL (e.g., `32.90`)             |
| `preferences`  | `string[]` | Matched preferences from the request     |

## Mock Meal Entity — `MockMeal`

Internal type used in `src/mock/meals.ts` seed data.

| Field          | Type       | Description                              |
|----------------|------------|------------------------------------------|
| `mealType`     | `string`   | `breakfast` / `lunch` / `dinner`         |
| `mealTimeStart`| `string`   | Serving window start (HH:MM)             |
| `mealTimeEnd`  | `string`   | Serving window end (HH:MM)               |
| `placeName`    | `string`   | Restaurant name                          |
| `placeAddress` | `string`   | Restaurant address                       |
| `mealName`     | `string`   | Meal name                                |
| `mealDescription` | `string` | Meal description                       |
| `mealPrice`    | `number`   | Price in BRL                             |
| `tags`         | `string[]` | Preference tags (e.g., `vegan`, `spicy`) |

## Scoring Logic

1. Filter `MockMeal[]` by `mealType` match.
2. Filter by `mealTime` within `[mealTimeStart, mealTimeEnd]` window.
3. Score each remaining meal: count of `preferences` strings that appear in `meal.tags`.
4. Return the meal with the highest score (ties broken by array order).
5. If no meal matches filters, return HTTP 404 with a descriptive message.
6. `preferences` in the response = intersection of request preferences and winning meal's tags.
