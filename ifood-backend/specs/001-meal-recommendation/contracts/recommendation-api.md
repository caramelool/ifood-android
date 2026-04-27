# API Contract: Meal Recommendation

## Endpoint

```
GET /recommendation
```

## Query Parameters

| Parameter  | Type   | Required | Example |
|------------|--------|----------|---------|
| `userName` | string | yes      | `Lucas` |
| `mealType` | string | yes      | `lunch` |

### `mealType`
Any non-empty string matching one of the available meal types (`breakfast`, `lunch`, `dinner`, `afternoon_snack`).
A random meal for the given type is returned.

## Success Response — 200 OK

```json
{
  "userName": "Lucas",
  "mealType": "lunch",
  "placeName": "Restaurante Verde Folha",
  "placeAddress": "Rua das Flores, 123 - São Paulo, SP",
  "mealName": "Bowl de Quinoa com Legumes",
  "mealDescription": "Bowl nutritivo com quinoa, legumes grelhados e molho tahine.",
  "mealPrice": 32.90,
  "mealImageUrl": "https://loremflickr.com/400/300/quinoa,bowl"
}
```

## No Match Response — 404 Not Found

```json
{
  "error": "No meal found matching your preferences."
}
```

## Validation Error Response — 400 Bad Request

```json
{
  "error": "Missing required parameter: mealType"
}
```

### Validation rules
- `userName` MUST be present and non-empty; omitting it returns 400.
- `mealType` MUST be present (any non-empty string is accepted; unknown types return 404).

## Example Requests

```
GET /recommendation?userName=Lucas&mealType=lunch
GET /recommendation?userName=Ana&mealType=breakfast
GET /recommendation?userName=Pedro&mealType=dinner
```
