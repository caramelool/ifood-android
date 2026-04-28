# Dependencies

All library versions are managed centrally in [`gradle/libs.versions.toml`](../gradle/libs.versions.toml) using Gradle Version Catalog.

---

## Core

| Library | Version | Purpose |
|---------|---------|---------|
| `androidx.core:core-ktx` | 1.18.0 | Kotlin extensions for common Android APIs (Context, Bundle, etc.) |
| Kotlin | 2.3.21 | Primary language |
| AGP (Android Gradle Plugin) | 9.2.0 | Build toolchain |
| KSP (Kotlin Symbol Processing) | 2.3.5 | Annotation processor for Room, Hilt, and Moshi code generation |

---

## UI — Jetpack Compose

| Library | Version | Purpose |
|---------|---------|---------|
| `compose-bom` | 2026.04.01 | BOM that aligns all Compose library versions |
| `material3` | (BOM-managed) | Material Design 3 components (buttons, chips, time pickers, etc.) |
| `ui-tooling` / `ui-tooling-preview` | (BOM-managed) | Compose layout inspector and preview support |
| `activity-compose` | 1.13.0 | `ComponentActivity.setContent {}` integration |
| `lifecycle-runtime-compose` | 2.10.0 | `collectAsStateWithLifecycle` for safe Flow collection in Composables |
| `lifecycle-viewmodel-compose` | 2.10.0 | `hiltViewModel()` and `viewModel()` Composable helpers |

---

## Navigation

| Library | Version | Purpose |
|---------|---------|---------|
| `navigation-compose` | 2.9.8 | Type-safe Compose navigation graph with `NavHost` and `NavController` |
| `hilt-navigation-compose` | 1.3.0 | `hiltViewModel()` scoped to the current nav back-stack entry |

---

## Local Persistence

| Library | Version | Purpose |
|---------|---------|---------|
| `room-runtime` | 2.8.4 | SQLite ORM: entity mapping, query generation, migration support |
| `room-ktx` | 2.8.4 | Kotlin coroutines and Flow extensions for Room DAOs |
| `room-compiler` (KSP) | 2.8.4 | Generates DAO implementation code at compile time |
| `datastore-preferences` | 1.2.1 | Asynchronous key-value store (replaces SharedPreferences); used for the onboarding flag |

---

## Networking

| Library | Version | Purpose |
|---------|---------|---------|
| `retrofit` | 3.0.0 | HTTP client abstraction; turns interface methods into API calls |
| `converter-moshi` | 3.0.0 | Retrofit converter that serializes/deserializes JSON using Moshi |
| `okhttp` | 5.3.2 | Underlying HTTP engine used by Retrofit |
| `logging-interceptor` | 5.3.2 | OkHttp interceptor that logs request/response bodies for debugging |
| `moshi-kotlin-codegen` (KSP) | 1.15.2 | Generates type-safe Moshi adapters for Kotlin data classes |

---

## Dependency Injection

| Library | Version | Purpose |
|---------|---------|---------|
| `hilt-android` | 2.59.2 | Compile-time DI framework; generates Dagger components |
| `hilt-android-compiler` (KSP) | 2.59.2 | Annotation processor that generates Hilt binding code |
| `hilt-work` | 1.3.0 | Integrates Hilt with WorkManager via `HiltWorkerFactory` |
| `hilt-compiler` (KSP) | 1.3.0 | Generates `@HiltWorker` binding code |
| `work-runtime-ktx` | 2.11.2 | WorkManager with Kotlin coroutine support (`CoroutineWorker`) |

---

## Background & Scheduling

| Library | Version | Purpose |
|---------|---------|---------|
| `WorkManager` (work-runtime-ktx) | 2.11.2 | Reliable background execution for the recommendation fetch + notification |
| `AlarmManager` (Android framework) | — | Exact time triggers 30 minutes before each meal |
| `lifecycle-process` | 2.10.0 | Provides `ProcessLifecycleOwner` for coroutine scope in `MainApplication` |

---

## Image Loading

| Library | Version | Purpose |
|---------|---------|---------|
| `coil-compose` | 2.7.0 | Async image loading for Compose; used for any restaurant imagery |

---

## Testing

| Library | Version | Purpose |
|---------|---------|---------|
| `junit` | 4.13.2 | Unit test runner and assertion framework |
| `androidx-junit` | 1.3.0 | AndroidX JUnit extensions for instrumented tests |
| `espresso-core` | 3.7.0 | UI interaction testing on real/emulated devices |
| `androidx-core` (test) | 1.7.0 | Core utilities for instrumented tests |
| `androidx-runner` | 1.7.0 | AndroidX test runner |
| `compose-ui-test-junit4` | (BOM-managed) | Compose test rule and semantic tree queries |
| `mockk` | 1.14.9 | Kotlin-idiomatic mocking (supports coroutines and suspend functions) |
| `kotlinx-coroutines-test` | 1.10.2 | `runTest`, `TestDispatcher`, `advanceTimeBy` for coroutine-based tests |
| `kover` (plugin) | 0.9.8 | JVM code coverage measurement; enforces minimum thresholds in CI |
