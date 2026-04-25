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

## Estrutura de módulos (feature packages)

```
app/src/main/java/com/lc/ifood/
├── core/          # DB (Room), DI base, domínio compartilhado, navegação, tema
├── home/          # Tela principal: lista refeições e preferências
├── onboarding/    # Fluxo de primeiro acesso
├── preference/    # Adicionar/remover preferências alimentares
├── schedule/      # Ajuste de horários de refeições
├── splash/        # Splash screen + verificação de onboarding
└── worker/        # WorkManager: notificações de lembrete
```

Cada feature segue a estrutura: `data/`, `domain/`, `ui/`, `di/`.

## Banco de dados (Room)

- `MealScheduleEntity` + `MealScheduleDao` — horários de refeições
- `UserPreferenceEntity` + `UserPreferenceDao` — preferências do usuário
- `AppDatabase` com `MealTypeConverter`

## Backend

Pasta `backend/` contém um projeto Node.js/TypeScript separado (não faz parte do build Android).

## Convenções

- ViewModels expõem `StateFlow<UiState>` imutável
- Use Cases são classes com operador `invoke`
- Repositórios têm interface em `domain/` e impl em `data/`
- Sem comentários no código, exceto quando o "porquê" não é óbvio
