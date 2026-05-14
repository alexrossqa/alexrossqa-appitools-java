# Visual Regression Testing — Applitools Eyes + Selenium (Java)

Demo project showing AI visual regression testing using [Applitools Eyes](https://applitools.com) with Selenium 4 and TestNG.

## Demo

[Watch on Vimeo](https://vimeo.com/1192265401)

## What it does

| Test | Description |
|---|---|
| `loginPageBaseline` | Navigates to the demo app login page and takes a baseline snapshot. Applitools Visual AI uses this as the reference for all future runs. |
| `loginPageRegression` | Injects a CSS change to simulate a visual regression (button colour changed by a bad deploy). Applitools flags the diff against the baseline. |

On first run, Applitools marks snapshots as **New** (baseline established). After approval, subsequent runs compare against the baseline — any change is flagged as **Unresolved** with the diff highlighted by the Visual AI engine.

## Prerequisites

- Java 11+
- Maven
- Google Chrome
- An [Applitools Eyes](https://applitools.com) account (free trial)

## Setup

### 1. Applitools API key

Set as an environment variable — never hardcode it:

```powershell
$env:APPLITOOLS_API_KEY = "your_api_key_here"
```

### 2. Run

```
mvn test
```

## How the AI works

Applitools uses a Visual AI engine to compare screenshots — not pixel-by-pixel diffing. It understands what constitutes a meaningful visual change vs. irrelevant rendering noise (anti-aliasing, sub-pixel differences). This makes it practical in CI pipelines where dumb pixel comparison generates constant false positives.
