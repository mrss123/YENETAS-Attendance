# Release Signing & CI/CD Guide

This document outlines how to securely generate, configure, and inject release signing credentials (`KEYSTORE_PATH`, `STORE_PASSWORD`, and `KEY_PASSWORD`) for local builds and automated CI/CD pipelines (e.g., GitHub Actions, GitLab CI/CD, Bitrise).

---

## 1. Environment Variables Overview

The release configuration in `app/build.gradle.kts` uses the following environment variables:

| Environment Variable | Description | Default / Example |
| :--- | :--- | :--- |
| `KEYSTORE_PATH` | Absolute or relative path to the `.jks` or `.keystore` file | `<project_root>/my-upload-key.jks` |
| `STORE_PASSWORD` | Password used to secure the keystore file | Sensitive / Injected via CI secret |
| `KEY_PASSWORD` | Password for the key entry inside the keystore | Sensitive / Injected via CI secret |

> **Note:** The key alias is configured as `"upload"` by default in `app/build.gradle.kts`.

---

## 2. Generating the Release Keystore

Generate your release key with the alias `upload` using Java's `keytool`:

```bash
keytool -genkeypair \
    -v \
    -keystore my-upload-key.jks \
    -storetype JKS \
    -storepass "<YOUR_SECURE_PASSWORD>" \
    -alias upload \
    -keypass "<YOUR_SECURE_PASSWORD>" \
    -keyalg RSA \
    -keysize 2048 \
    -validity 10000 \
    -dname "CN=Android Release, O=Organization, C=US"
```

> ⚠️ **Security Rule:** Never commit `my-upload-key.jks` to a public source repository. Ensure `*.jks` and `*.keystore` are included in your `.gitignore`.

---

## 3. Local Release Builds

### Method A: Export Variables in Terminal

```bash
export KEYSTORE_PATH="$(pwd)/my-upload-key.jks"
export STORE_PASSWORD="your-secure-password"
export KEY_PASSWORD="your-secure-password"

# Build Release APK
./gradlew :app:assembleRelease

# Or Build Release Android App Bundle (AAB for Google Play)
./gradlew :app:bundleRelease
```

### Method B: One-Line Inline Execution

```bash
KEYSTORE_PATH="$(pwd)/my-upload-key.jks" STORE_PASSWORD="your-password" KEY_PASSWORD="your-password" ./gradlew :app:assembleRelease
```

---

## 4. CI/CD Configuration (e.g., GitHub Actions)

In CI/CD environments, keystore files are typically converted to Base64 strings and stored in repository secrets along with the passwords.

### Step 1: Encode the Keystore to Base64

Run this command locally to encode your `.jks` file:

```bash
# macOS / Linux
base64 -w 0 my-upload-key.jks > keystore_base64.txt
# (On macOS without -w 0, use: base64 -i my-upload-key.jks | tr -d '\n' > keystore_base64.txt)
```

### Step 2: Add Secrets to GitHub Actions

Go to **Repository Settings** → **Secrets and variables** → **Actions** → **New repository secret**:

- `RELEASE_KEYSTORE_BASE64`: Paste the contents of `keystore_base64.txt`
- `STORE_PASSWORD`: Keystore password
- `KEY_PASSWORD`: Key password

### Step 3: GitHub Actions Workflow Example (`.github/workflows/release.yml`)

```yaml
name: Build Signed Release APK

on:
  push:
    tags:
      - 'v*'

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
          cache: 'gradle'

      - name: Decode Keystore
        run: |
          echo "${{ secrets.RELEASE_KEYSTORE_BASE64 }}" | base64 --decode > ${{ runner.temp }}/my-upload-key.jks

      - name: Build Release APK
        env:
          KEYSTORE_PATH: ${{ runner.temp }}/my-upload-key.jks
          STORE_PASSWORD: ${{ secrets.STORE_PASSWORD }}
          KEY_PASSWORD: ${{ secrets.KEY_PASSWORD }}
        run: ./gradlew :app:assembleRelease

      - name: Upload Release APK
        uses: actions/upload-artifact@v4
        with:
          name: release-apk
          path: app/build/outputs/apk/release/app-release.apk
```

---

## 5. Verifying the Signed Release APK

Verify that the output APK was signed correctly with v1/v2/v3 signatures:

```bash
apksigner verify --verbose app/build/outputs/apk/release/app-release.apk
```
