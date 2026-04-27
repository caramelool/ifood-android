# iFood Android — Meal Scheduling App

A native Android application built for the **iFood Selection Process**. The app lets users schedule daily meal times, define dietary preferences, and receive AI-powered restaurant recommendations 30 minutes before each meal via push notifications.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.3 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt 2.59 |
| Local DB | Room 2.8 |
| Preferences | DataStore |
| Networking | Retrofit 3 + OkHttp 5 + Moshi |
| Background | WorkManager + AlarmManager |
| Navigation | Navigation Compose 2.9 |
| Testing | JUnit 4 · MockK · Kover · Espresso · Compose Test |
| CI | GitHub Actions |

---

## Quick Start

```bash
git clone https://github.com/<your-org>/ifood-android.git
cd ifood-android

# Build & install debug
./gradlew installDebug

# Run unit tests
./gradlew testDebugUnitTest

# Generate coverage report
./gradlew koverHtmlReportDebug
# → app/build/reports/kover/htmlDebug/index.html
```

> **Backend:** The recommendation API lives in the `backend/` folder (Node.js/TypeScript). Start it separately and configure `BASE_URL` in `local.properties` before running the app.

---

## How It Works

```mermaid
flowchart LR
    User -->|sets schedule| App
    App -->|AlarmManager T-30min| Worker
    Worker -->|getRecommendation| Backend
    Backend -->|restaurant + dish| Worker
    Worker -->|notification| User
    User -->|taps| App
```

1. User sets meal times (Breakfast, Lunch, Afternoon Snack, Dinner).
2. App registers an exact alarm 30 minutes before each meal.
3. When the alarm fires, a WorkManager worker fetches an AI recommendation from the backend, filtered by the user's dietary preferences.
4. A rich notification shows the restaurant, dish, and price.
5. Tapping the notification opens the full recommendation in the app.

---

## Documentation

| Document | Contents |
|----------|---------|
| [Overview](docs/overview.md) | App purpose, user journey, and full feature list |
| [Architecture](docs/architecture.md) | Clean Architecture layers, MVVM, DI modules, notification pipeline, Room schema |
| [Dependencies](docs/dependencies.md) | All libraries with versions and rationale |
| [Testing](docs/testing.md) | Test strategy, coverage breakdown, and tooling |
| [CI](docs/ci.md) | GitHub Actions pipeline, build matrix, and coverage reporting |

---

## Project Structure

```
app/src/main/java/com/lc/ifood/
├── data/          # Room DAOs, entities, Retrofit service, repository implementations
├── di/            # Hilt modules (App, Network, Dao, Repository)
├── domain/        # Models, repository interfaces, use cases
├── ui/            # Composable screens, ViewModels, UiState classes
├── worker/        # AlarmReceiver, BootReceiver, MealRecommendationWorker, Scheduler
├── MainActivity.kt
└── MainApplication.kt
```

---

## License

This project was created as part of a technical selection process. All rights reserved.
