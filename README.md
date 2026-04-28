<div align="center">
  <img width="120" height="120" alt="ic_launcher" src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" />
  <h1>iFood Android</h1>
  <p>Meal scheduling app with AI-powered restaurant recommendations</p>

  ![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-7F52FF?logo=kotlin&logoColor=white)
  ![Android](https://img.shields.io/badge/API-29%2B-green?logo=android&logoColor=white)
  ![License](https://img.shields.io/badge/License-All%20rights%20reserved-red)
  ![CI](https://img.shields.io/github/actions/workflow/status/caramelool/ifood-android/pr-checks.yml?label=CI&logo=github)
</div>

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Kotlin 2.3.21 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt 2.59.2 |
| Local DB | Room 2.8.4 |
| Preferences | DataStore 1.2.1 |
| Networking | Retrofit 3.0.0 + OkHttp 5.3.2 + Moshi 1.15.2 |
| Background | WorkManager 2.11.2 + AlarmManager |
| Navigation | Navigation Compose 2.9.8 |
| Testing | JUnit 4.13.2 · MockK 1.14.9 · Kover 0.9.8 · Espresso · Compose Test |
| CI | GitHub Actions |

---

## Quick Start

```bash
git clone https://github.com/caramelool/ifood-android.git
cd ifood-android

# Build & install debug
./gradlew installDebug

# Run unit tests
./gradlew testDebugUnitTest

# Generate coverage report
./gradlew koverHtmlReportDebug
# → app/build/reports/kover/htmlDebug/index.html
```

> **Backend:** The recommendation API lives in the `ifood-backend/` folder (Node.js/TypeScript) and is already deployed on Vercel — the app points to it automatically via a `BuildConfig` field baked into the build. To run the backend locally, start it separately and override `BASE_URL` in `local.properties`.

---

## How It Works

Alarms fire **30 minutes before** each scheduled meal. A `WorkManager` job fetches an AI restaurant recommendation from the backend and posts a rich notification. Tapping it opens the full detail view inside the app.

→ Full user journey and feature list: [docs/overview.md](docs/overview.md)  
→ Architecture diagrams and notification pipeline: [docs/architecture.md](docs/architecture.md)

---

## Documentation

| Document | Contents |
|----------|----------|
| [Overview](docs/overview.md) | App purpose, user journey, and full feature list |
| [Architecture](docs/architecture.md) | Clean Architecture layers, MVVM, DI modules, notification pipeline, Room schema |
| [Dependencies](docs/dependencies.md) | All libraries with versions and rationale |
| [Testing](docs/testing.md) | Test strategy, coverage breakdown, and tooling |
| [CI](docs/ci.md) | GitHub Actions pipelines: PR checks and signed release publishing |
| [Roadmap](docs/roadmap.md) | Roadmap — Future Improvements & Technical Debt |

---

## Project Structure

```
app/src/main/java/com/lc/ifood/
├── data/
│   ├── db/             # AppDatabase, DAOs, entities, migrations
│   ├── permission/
│   ├── remote/
│   └── repository/
├── di/
├── domain/
│   ├── model/
│   ├── permission/
│   ├── repository/
│   └── usecase/        # 13 use cases
├── ui/
│   ├── composable/
│   ├── home/
│   ├── onboarding/
│   ├── preference/
│   ├── schedule/
│   ├── splash/
│   ├── navigation/
│   └── theme/
├── worker/             # AlarmReceiver, BootReceiver, MealRecommendationScheduler, MealRecommendationWorker
├── MainActivity.kt
└── MainApplication.kt
```

---

## License

This project was created as part of a technical selection process. All rights reserved.
