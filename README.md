# AndroidTests

A Kotlin/Android project used as a reference and learning resource for unit testing behavior of Android and related libraries. Contains no main app functionality — purely a test container.

## Test Coverage

| Test Class | What It Covers |
|---|---|
| `CoroutineScopeTest` | Exception propagation in `CoroutineScope`: sibling cancellation, `CancellationException` handling |
| `SupervisorJobTest` | `SupervisorJob` isolation: direct children are isolated from each other, grandchildren are not |
| `SupervisorScopeTest` | `supervisorScope` acting as a child root scope |
| `CoroutineExceptionHandlerTest` | `CoroutineExceptionHandler` behavior across 9 cases: `launch` vs `async`, caught exceptions, `CancellationException`, child vs root coroutine |
| `InlineValueClassTest` | Kotlin inline value class behavior: `toString`, `equals`, `hashCode`, boxing via interface and nullable, `init` blocks, member functions |
| `OperatorTest` | Kotlin operator overloading: `plus`, `minus`, `times`, `div`, `unaryMinus`, `unaryPlus`, `rem`, `inc`, `dec`, `get`, `set`, `contains`, `compareTo`, `invoke` |
| `InfixTest` | Kotlin infix functions: built-in `to`, `and`, `or`; custom `travelTo` infix function |

## Tech Stack

- **Language:** Kotlin 2.1.0
- **Java:** 21
- **Testing:** JUnit 4, Kotlinx Coroutines Test, MockK, AndroidX Test
- **Min SDK:** API 24 (Android 7.0)
- **Target SDK:** API 36

## Running Tests

Run unit tests only:

```bash
./gradlew testDebugUnitTest
```

## CI

GitHub Actions runs unit tests on every push to `master` and `develop` branches.
