# ifood-android

App Android de agendamento de refeições com preferências alimentares.

## Stack

- **Linguagem:** Kotlin
- **UI:** Jetpack Compose
- **Arquitetura:** MVVM + Clean Architecture (domain / data / ui)
- **DI:** Hilt
- **Banco local:** Room
- **Navegação:** Navigation3 + Compose Navigation
- **Rede:** Retrofit + OkHttp + Moshi
- **Background:** WorkManager (`MealReminderWorker`)
- **Build:** Gradle KTS, Version Catalog (`gradle/libs.versions.toml`)

## Estrutura de pacotes

```
app/src/main/java/com/lc/ifood/
├── data/
│   ├── db/
│   │   ├── AppDatabase.kt
│   │   ├── dao/          # MealScheduleDao, UserPreferenceDao
│   │   └── entity/       # MealScheduleEntity, UserPreferenceEntity
│   ├── remote/           # MealReminderApiService, MealReminderRequest
│   └── repository/       # Impl: MealReminder, Onboarding, Preference, Schedule
├── di/                   # AppModule, DaoModule, NetworkModule, RepositoryModule
├── domain/
│   ├── model/            # MealSchedule, MealType, UserPreference
│   ├── repository/       # Interfaces: MealReminder, Onboarding, Preference, Schedule
│   └── usecase/          # CompleteOnboarding, DeletePreference, GetMealSchedules,
│                         # GetOnboardingStatus, GetPreferences, SavePreference,
│                         # UpdateMealSchedule
├── ui/
│   ├── home/             # HomeScreen, HomeViewModel, HomeUiState
│   ├── onboarding/       # OnboardingScreen, OnboardingViewModel, OnboardingUiState
│   ├── preference/       # AddPreferenceScreen, AddPreferenceViewModel, AddPreferenceUiState
│   ├── schedule/         # ScheduleAdjustmentScreen, ScheduleAdjustmentViewModel, ScheduleAdjustmentUiState
│   ├── splash/           # SplashScreen, SplashViewModel, SplashUiState
│   ├── navigation/       # AppRoutes
│   ├── theme/            # Color, Theme, Type, SystemStatusBar
│   └── MealTypeStrings.kt
├── worker/               # MealReminderWorker, MealReminderScheduler
├── MainActivity.kt
└── MainApplication.kt
```

## Banco de dados (Room)

- `MealScheduleEntity` + `MealScheduleDao` — horários de refeições
- `UserPreferenceEntity` + `UserPreferenceDao` — preferências do usuário
- `AppDatabase`

## Backend

Pasta `backend/` contém um projeto Node.js/TypeScript separado (não faz parte do build Android).

## Convenções

- ViewModels expõem `StateFlow<UiState>` imutável
- Use Cases são classes com operador `invoke`
- Repositórios têm interface em `domain/` e impl em `data/`
- Sem comentários no código, exceto quando o "porquê" não é óbvio
