# Architecture

The app follows **Clean Architecture** with three explicit layers — UI, Domain, and Data — plus a cross-cutting Worker layer for background processing.

---

## Layer Diagram

```mermaid
graph TD
    subgraph UI["UI Layer (Jetpack Compose + ViewModel)"]
        VM[ViewModel\nStateFlow&lt;UiState&gt;]
        Screen[Composable Screen]
        Screen -- observes --> VM
    end

    subgraph Domain["Domain Layer (pure Kotlin)"]
        UC[Use Cases\ninvoke operator]
        Model[Domain Models\nMealSchedule · UserPreference · MealRecommendation]
        Repo_IF[Repository Interfaces]
    end

    subgraph Data["Data Layer"]
        Repo_Impl[Repository Implementations]
        DAO[Room DAOs]
        DS[DataStore]
        API[Retrofit API Service]
    end

    subgraph External["External"]
        DB[(Room SQLite DB)]
        Backend[(Node.js Backend)]
    end

    VM --> UC
    UC --> Repo_IF
    Repo_IF -.implemented by.- Repo_Impl
    Repo_Impl --> DAO
    Repo_Impl --> DS
    Repo_Impl --> API
    DAO --> DB
    API --> Backend
```

---

## Notification Pipeline

```mermaid
sequenceDiagram
    participant App as MainApplication
    participant Sched as MealRecommendationScheduler
    participant AM as AlarmManager
    participant AR as AlarmReceiver
    participant WM as WorkManager
    participant Worker as MealRecommendationWorker
    participant API as Backend API
    participant NM as NotificationManager
    participant MA as MainActivity

    App->>Sched: scheduleAll()
    Sched->>AM: setExactAndAllowWhileIdle (T-30min)

    note over AM: 30 min before meal time
    AM-->>AR: onReceive(intent)
    AR->>WM: enqueue OneTimeWorkRequest

    WM->>Worker: doWork()
    Worker->>API: getRecommendation(name, mealType, preferences)
    API-->>Worker: MealRecommendationResponse
    Worker->>NM: notify(notification)
    Worker->>Sched: schedule(schedule) — next day

    note over NM: User taps notification
    NM-->>MA: PendingIntent → intentRecommendation()
```

---

## App Startup Flow

```mermaid
flowchart TD
    A([Application.onCreate]) --> B[scheduleAll]
    B --> C[collect getMealSchedules flow]
    C --> D{For each MealSchedule}
    D --> E[schedule alarm\nT-30min via AlarmManager]
    E --> D
    D --> F([Done — alarms armed])

    G([HomeViewModel.init]) --> H[seedDefaultsIfEmpty]
    H --> I{dao.count == 0?}
    I -- Yes --> J[insertAll default schedules]
    I -- No --> K([Skip])
    J --> K
```

---

## Room Database Schema

```mermaid
erDiagram
    meal_schedules {
        TEXT mealType PK
        INTEGER hour
        INTEGER minute
    }

    user_preferences {
        INTEGER id PK
        TEXT label
        TEXT mealTypes
    }

    users {
        INTEGER id PK
        TEXT name
    }
```

> `mealTypes` in `user_preferences` is a comma-separated list of `MealType` names (e.g. `"BREAKFAST,LUNCH"`). See [`UserPreferenceDao`](../app/src/main/java/com/lc/ifood/data/db/dao/UserPreferenceDao.kt) for the CSV-aware query.

---

## MVVM Pattern

Each screen has a dedicated **ViewModel** that:
- Exposes a single `StateFlow<UiState>` — immutable, collected by the Composable.
- Launches coroutines in `viewModelScope` for all async work.
- Delegates business logic entirely to **Use Cases**.

```
Screen ──collect──▶ StateFlow<UiState>  ◀──emit── ViewModel
                                                       │
                                               Use Case invoke()
                                                       │
                                              Repository interface
```

---

## Dependency Injection (Hilt)

| Module | Installs in | Provides |
|--------|------------|----------|
| `AppModule` | `SingletonComponent` | `DataStore`, `WorkManager`, `AppDatabase` |
| `NetworkModule` | `SingletonComponent` | `Moshi`, `OkHttpClient`, `Retrofit`, `MealReminderApiService` |
| `DaoModule` | `SingletonComponent` | `MealScheduleDao`, `UserPreferenceDao`, `UserDao` |
| `RepositoryModule` | `SingletonComponent` | All repository bindings (interface → impl) |

ViewModels are injected via `@HiltViewModel`. The `MealRecommendationWorker` uses `@HiltWorker` + `@AssistedInject` and requires the custom `HiltWorkerFactory` configured in `MainApplication`.

---

## Navigation

Navigation is handled by **Jetpack Navigation Compose** (`navigation-compose 2.9.x`). Routes are defined as sealed objects/data objects in `AppRoutes`. The `SplashViewModel` determines the initial destination (`Loading → Home | Onboarding`) by reading the DataStore-backed onboarding flag.
