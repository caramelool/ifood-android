# ifood-android

Android meal scheduling app with dietary preference support.

## Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM + Clean Architecture (domain / data / ui)
- **DI:** Hilt
- **Local DB:** Room
- **Navigation:** Jetpack Navigation Compose (type-safe routes via `@Serializable` data objects)
- **Networking:** Retrofit + OkHttp + Moshi
- **Background:** WorkManager + AlarmManager (`MealRecommendationWorker`, `MealRecommendationScheduler`)
- **Build:** Gradle KTS, Version Catalog (`gradle/libs.versions.toml`)

## Package Structure

```
app/src/main/java/com/lc/ifood/
├── data/
│   ├── db/
│   │   ├── AppDatabase.kt
│   │   ├── dao/          # MealScheduleDao, UserPreferenceDao, UserDao
│   │   ├── entity/       # MealScheduleEntity, UserPreferenceEntity, UserEntity
│   │   └── migration/    # MIGRATION_1_2
│   ├── permission/       # NotificationPermissionCheckerImpl
│   ├── remote/           # MealReminderApiService, MealRecommendationResponse
│   └── repository/       # Impl: MealRecommendation, MealSchedule, Onboarding, Preference, User
├── di/                   # AppModule, DaoModule, NetworkModule, PermissionModule, RepositoryModule
├── domain/
│   ├── model/            # Meal, MealRecommendation, MealSchedule, MealType, User, UserPreference
│   ├── permission/       # NotificationPermissionChecker (interface)
│   ├── repository/       # Interfaces: MealRecommendation, MealSchedule, Onboarding, Preference, User
│   └── usecase/          # CompleteOnboarding, DeletePreference, GetMealRecommendation, GetMealSchedules,
│                         # GetMeals, GetOnboardingStatus, GetPreferences, GetPreferencesByMealType,
│                         # GetUser, SavePreference, SaveUser, SeedDefaultSchedules, UpdateMealSchedule
├── ui/
│   ├── composable/       # Shared composable components
│   ├── home/
│   │   ├── components/   # HomeHeader, MealSchedulesSection, PreferencesSection, MealRecommendationBottomSheet
│   │   ├── HomeScreen.kt
│   │   ├── HomeViewModel.kt
│   │   └── HomeUiState.kt
│   ├── onboarding/       # OnboardingScreen, OnboardingViewModel, OnboardingUiState
│   ├── preference/
│   │   ├── add/          # AddPreferenceScreen, AddPreferenceViewModel, AddPreferenceUiState
│   │   └── delete/       # DeletePreferenceViewModel, DeletePreferenceState, SwipeToDeletePreference
│   ├── schedule/         # ScheduleAdjustmentScreen, ScheduleAdjustmentViewModel, ScheduleAdjustmentUiState
│   ├── splash/           # SplashScreen, SplashViewModel, SplashUiState
│   ├── navigation/       # AppRoutes, MainNavHost
│   ├── theme/            # Color, Theme, Type, SystemStatusBar
│   └── PermissionDeniedDialog.kt
├── worker/               # AlarmReceiver, BootReceiver, MealRecommendationScheduler, MealRecommendationWorker
├── MainActivity.kt
└── MainApplication.kt
```

## Database (Room)

- `MealScheduleEntity` + `MealScheduleDao` — meal time slots
- `UserPreferenceEntity` + `UserPreferenceDao` — user dietary preferences
- `UserEntity` + `UserDao` — user profile
- `AppDatabase` — schema v2 (MIGRATION_1_2 adds the `users` table)

## Backend

The `ifood-backend/` folder contains a separate Node.js/TypeScript project (not part of the Android build).

## Documentation

- All documentation (README, specs, comments, CLAUDE.md) must be written in English.

## Conventions

- ViewModels expose an immutable `StateFlow<UiState>`
- Use Cases are classes with an `invoke` operator
- Repositories have an interface in `domain/` and an implementation in `data/`
- No comments in code unless the *why* is non-obvious
