# Car Quiz

Toddler-friendly Android app focused on car logo recognition.

## PR-01 Scope

This PR bootstraps the app architecture and first slice of business logic.

## Architecture (PR-01)

- `ui/` - Compose UI + `QuizViewModel`
- `domain/` - entities, repository contract, use cases
- `data/` - in-memory repository with seed brands
- `core/` - reserved for shared app primitives

### Domain model

- `CarBrand`
- `QuizQuestion`
- `QuizRoundResult`

### Use cases

- `GenerateQuizQuestionUseCase`
- `SubmitAnswerUseCase`

## Tooling and quality

- Material 3 + Jetpack Compose
- Kotlin Android plugin + Compose plugin
- Ktlint via `org.jlleitschuh.gradle.ktlint`
- Unit tests with JUnit4 + MockK

## Run locally

```bash
./gradlew ktlintCheck
./gradlew testDebugUnitTest
./gradlew assembleDebug
```

If Android SDK is not configured, create `local.properties`:

```properties
sdk.dir=/path/to/Android/sdk
```

## Security notes

- Uses stable official AndroidX libraries
- No network layer yet (reduces attack surface in this phase)
- No static context storage in architecture (avoids common leak source)

## Next planned PRs

1. PR-02: Quiz round engine (multi-question rounds + score persistence)
2. PR-03: Home screen and game mode selection
3. PR-04: Parent settings (difficulty, sounds, round length)
4. PR-05: Audio prompts and accessibility polish
