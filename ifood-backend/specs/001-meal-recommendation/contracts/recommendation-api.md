# API Contract: Meal Recommendation

## Endpoint

```
GET /recommendation
```

## Query Parameters

| Parameter     | Type     | Required | Example                        |
|---------------|----------|----------|--------------------------------|
| `userName`    | string   | yes      | `Lucas`                        |
| `mealType`    | string   | yes      | `lunch`                        |
| `mealTime`    | string   | yes      | `12:30`                        |
| `preferences` | string[] | no       | `preferences=vegan&preferences=spicy` |

### `mealType` allowed values
`breakfast` | `lunch` | `dinner`

### `mealTime` format
`HH:MM` (24-hour clock, e.g. `08:00`, `13:45`)

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
  "preferences": ["vegan"]
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
  "error": "Missing required parameter: userName"
}
```

### Validation rules
- `userName` MUST be a non-empty string.
- `mealType` MUST be one of `breakfast`, `lunch`, `dinner`.
- `mealTime` MUST match `HH:MM` pattern.

## Example Requests

```
GET /recommendation?userName=Lucas&mealType=lunch&mealTime=12:30&preferences=vegan&preferences=spicy
GET /recommendation?userName=Ana&mealType=breakfast&mealTime=08:00
GET /recommendation?userName=Pedro&mealType=dinner&mealTime=20:00&preferences=italian
```
