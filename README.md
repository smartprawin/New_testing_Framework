# Automation Framework (Java + Playwright + Cucumber + Rest Assured)

A simple, well-commented test framework for learning. It covers **UI testing**
with Playwright (the modern replacement for Selenium) and **API testing** with
Rest Assured, both written in Cucumber (BDD) style.

---

## Tech stack

| Purpose      | Library                              |
|--------------|--------------------------------------|
| UI testing   | Playwright for Java (`com.microsoft.playwright`) |
| BDD          | Cucumber-JVM 7 + JUnit 5 Platform    |
| API testing  | Rest Assured                         |
| Build        | Maven                                |

---

## Prerequisites

1. **JDK 17+** (Playwright + modern libs need Java 11+). Set `JAVA_HOME`.
   - This project was verified on **JDK 26**; Java 17+ also works.
2. **Apache Maven 3.9+** on your `PATH`.

Check with:
```bash
java -version
mvn -version
```

---

## Project structure

```
demo/src/test/java/com/qa/automation/
  runners/TestRunner.java            # @Suite entry point (run from your IDE)
  hooks/Hooks.java                   # Browser lifecycle (launch/close, screenshots)
  utils/
    ConfigReader.java                # Reads config.properties
    ScenarioContext.java             # Shares the Playwright Page per scenario
    ApiClient.java                   # Rest Assured wrapper
  pages/
    BasePage.java                    # Base class: common UI helpers + Selenium→Playwright cheat-sheet
    LoginPage.java                   # Example page object (extends BasePage)
    SearchPage.java                  # Second example page object
  stepdefinitions/
    BaseStepDefinition.java          # Base class: gives step defs the Playwright Page
    ui/
      LoginStepDefs.java             # Steps for login.feature
      SearchStepDefs.java            # Steps for search.feature
    api/
      ApiStepDefs.java               # Steps for users.feature

demo/src/test/resources/
  config/config.properties           # URLs, browser choice, headless flag
  features/
    ui/login.feature
    ui/search.feature
    api/users.feature
```

> This is a Maven multi-module build: the root `pom.xml` is a parent (`packaging
> pom`) and the actual tests live in the **`demo`** module. Always build/run with
> `-pl demo -am`.

---

## Run the tests

Tests are run via the Cucumber CLI (wired into Maven's `test` phase with the
`exec-maven-plugin`) rather than Surefire, to avoid a JUnit-Platform crash on
newer JDKs. Use the `-pl demo -am` flags so Maven builds the `demo` module.

```bash
# Run everything (UI + API)
mvn clean test -pl demo -am -DskipTests -Dcucumber.tags="@api or @ui"

# Run only one tag
mvn test -pl demo -am -DskipTests -Dcucumber.tags="@ui"
mvn test -pl demo -am -DskipTests -Dcucumber.tags="@api"
```

> Tip: add tags like `@ui` / `@api` above a `Feature:` or `Scenario:` line in a
> `.feature` file to filter runs.

You can also run the `TestRunner` `@Suite` class directly from your IDE (IntelliJ
/ Eclipse), which uses the JUnit Platform.

**Reports:** open `demo/target/cucumber-report.html` after a run. A JSON version
is also written to `demo/target/cucumber.json`.

---

## How to add a new UI test (the pattern)

1. **Feature file** (`demo/src/test/resources/features/ui/xxx.feature`) — write the
   steps in plain English (Given / When / Then).
2. **Page object** (`demo/src/test/java/.../pages/XxxPage.java`) — `extends BasePage`,
   declare locators in the constructor, expose friendly methods.
3. **Step definitions** (`demo/src/test/java/.../stepdefinitions/ui/XxxStepDefs.java`)
   — `extends BaseStepDefinition`, create your page in an `@Before`, then write
   `@Given/@When/@Then` methods whose text matches the feature file.

See `LoginPage` + `LoginStepDefs` + `login.feature` (and the same trio for
`SearchPage`) as worked examples. `BasePage` explains every helper and how it
maps to the Selenium methods you already know.

---

## How to add a new API test

1. Add a scenario in `demo/src/test/resources/features/api/xxx.feature`.
2. Add matching `@Given/@When/@Then` methods in a step-def class (see
   `ApiStepDefs`).
3. Use `ApiClient.get(path)` / `ApiClient.post(path, body)` and assert on the
   returned `Response` (status code, JSON fields via `jsonPath`).

---

## Configuration

Edit `demo/src/test/resources/config/config.properties`:

| Key             | Meaning                                         |
|-----------------|-------------------------------------------------|
| `browser`       | `chromium` | `firefox` | `webkit`             |
| `headless`      | `true` runs without a visible window (CI). Set `false` to watch the browser while learning. |
| `slowMo`        | Milliseconds of delay added to each action. **Keep at `0`** — a non-zero value can make actions time out in the current Playwright version. Watch the browser with `headless=false` instead. |
| `base.url`      | App URL for UI tests (the-internet demo login)  |
| `api.base.uri`  | Base URL for API tests (jsonplaceholder)        |
| `ui.search.url` | URL used by the Wikipedia search example        |

**Example sites used** (free, no-auth, so the demos run out of the box):
- UI login → `https://the-internet.herokuapp.com/login` (user `tomsmith`, password `SuperSecretPassword!`)
- UI search → `https://en.wikipedia.org`
- API → `https://jsonplaceholder.typicode.com`

---

## CI

A GitHub Actions workflow lives at `.github/workflows/ci.yml`. It installs JDK 17,
the Linux libraries Playwright needs, downloads the browsers, runs
`mvn test -pl demo -am -DskipTests -Dcucumber.tags="@api or @ui"`,
and uploads the Cucumber HTML report as a build artifact.
