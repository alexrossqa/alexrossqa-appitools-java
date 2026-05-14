# Plex Visual Testing — Applitools Eyes + Selenium (Java)

Portfolio project demonstrating AI visual regression testing using [Applitools Eyes](https://applitools.com) with Selenium 4 and TestNG, targeting a local Plex Media Server.

## What it does

| Test | Description |
|---|---|
| `filmDetailBaseline` | Navigates to a Plex film detail page and takes a baseline snapshot. Applitools Visual AI uses this as the reference for all future runs. |
| `filmDetailPosterChanged` | Replaces the film poster with a broken image via JavaScript to simulate a visual regression. Applitools flags the diff against the baseline. |

On first run, Applitools marks both snapshots as **New** (baseline established). On the second run, `filmDetailPosterChanged` shows a **Diff** — the Visual AI highlights exactly what changed.

## Prerequisites

- Java 11+
- Maven
- Google Chrome
- A running local Plex Media Server
- An [Applitools Eyes](https://applitools.com) account (free trial, no card required)

## Setup

### 1. Applitools API key

Copy your API key from the Applitools dashboard and set it as an environment variable — never hardcode it.

```powershell
$env:APPLITOOLS_API_KEY = "your_api_key_here"
```

### 2. Config file

Copy the template:

```
cp src/test/resources/config.properties.template src/test/resources/config.properties
```

Then fill in:

- **plex.token** — Plex Web > any film > `...` > Get Info > View XML. Copy the `X-Plex-Token` value from the URL.
- **plex.film.url** — Navigate to any film's detail page in your browser and copy the full URL from the address bar.

### 3. Run

```
mvn test
```

## Viewing results

Open the [Applitools Dashboard](https://eyes.applitools.com). The first run establishes the baseline (marked **New**). Run again to see the regression flagged as a **Diff**, with the Visual AI drawing attention to exactly what changed.
