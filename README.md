# የኔታ (Yeneta) — Traditional Ethiopian Church School Management System

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat&logo=android&logoColor=white)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin%202.0-7F52FF?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20%2F%20Material%203-4285F4?style=flat&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Clean%20Architecture-blue.svg)](https://developer.android.com/topic/architecture)
[![Database](https://img.shields.io/badge/Storage-Room%20(Offline--First)-00599C?style=flat&logo=sqlite&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![R8 Full Mode](https://img.shields.io/badge/Optimization-R8%20Full%20Mode%20%2B%20Shrinking-success.svg)](https://developer.android.com/studio/build/shrink-code)

**የኔታ (Yeneta)** is a specialized, production-ready, 100% offline-first Android application designed for traditional Ethiopian Orthodox Tewahedo Church schools (*Abinet Timhirt Bet* / የአብነት ትምህርት ቤት). It empowers traditional educators (*Memhiran* / መምህራን) to track student enrollments, multi-session daily attendance, and subject-level learning progression across classical curricula with native Ethiopian calendar support.

---

## 🌟 Executive Summary & Impact (For Recruiters)

Traditional Ethiopian Church education has preserved centuries of heritage through rigorous oral and manuscript learning across disciplines like **Ge'ez Qene**, **Deggwa Zema**, **Aquaquam**, and **Biblical Hermeneutics**. However, institutional tracking has historically relied on physical paper logs prone to damage or loss.

**Yeneta bridges traditional heritage with modern engineering:**
- **Zero Cloud / Zero Network Footprint**: Fully functional in remote monastic settlements and rural hermitages without cell coverage or internet connection.
- **Native Ge'ez / Ethiopian Calendar Engine**: Converts Gregorian timestamps into exact Ethiopian 13-month calendar dates (*Meskerem* to *Pagumen*) and calculates Day-of-Week offsets in real time.
- **Enterprise-Grade Android Standards**: Built following Google's official modern Android guidelines—100% Jetpack Compose UI, Material 3 design system, reactive StateFlow streams, offline Room persistence, and zero memory leaks.

---

## 📸 Key Features & Capabilities

### 1. 📅 Native Ethiopian Calendar & Multi-Session Attendance
- Daily session tracking: **Morning (የረፋድ/የጧት)**, **Afternoon (የከሰዓት)**, and **Evening/Night (የምሽት/የሌሊት)**.
- Attendance states: **Present (ተገኝቷል)**, **Absent (ቀረ)**, **Permission/Excused (ፈቃድ)**, and **Late (አረፈደ)**.
- Automated date conversion algorithm calculating Ge'ez years, leap years (4-year solar cycles), and Ethiopian month names without external web APIs.

### 2. 🏛️ Comprehensive Department Management
Pre-seeded and fully configurable classical church school departments:
- **ንባብና ጸሎት (Reading & Prayer)**: Fidel, Epistle of John, Gospels, Book of Psalms (*Dawit*), Weddase Maryam.
- **ዜማ (Chant / Deggwa)**: St. Yared classical chants (Ge'ez, Ezel, Araray), Tsome Deggwa, Zimare, Mewas'it.
- **አቋቋም (Sacred Movement / Liturgical Dance)**: Mahlete Tsige, Senasel, Kebero rhythms, Meqwamiya.
- **ቅኔ (Sem-ina-Werq / Ge'ez Poetry)**: Guba'e Qana, Ze'amlakye, Mibezhu, Wazema, Selassie, Grammatical Analysis.
- **ቅዳሴ (Liturgy & Sacraments)**: Order of Service for Priests & Deacons, Anaphoras (14 Qeddases).
- **መጻሕፍት / ትርጓሜ (Biblical & Patristic Exegesis)**: Old Testament, New Testament, Faith of the Fathers (*Haymanote Abew*), Monastic Fathers (*Mar Isaac*, *Aragawi Menfesawi*), *Fetha Negest*.

### 3. 📈 Individual Progress & Learning Milestones
- Tracks current reading/chant chapters (*Kifil*), recitation milestones, and examination levels.
- Student profiles with guardian contacts, residence information, and enrollment history.

### 4. 📊 Analytics, Statistics & Local Reporting
- Real-time attendance rate visualization per student and department.
- Daily summaries showing present/absent counts with interactive filtering.

---

## 🛠️ Architecture & Tech Stack (For Developers)

The codebase strictly adheres to **Clean Architecture** and **MVVM (Model-View-ViewModel)** with reactive unidirectional data flow (UDF).

```
┌─────────────────────────────────────────────────────────────┐
│                   Jetpack Compose UI (M3)                   │
│   (Screens, Custom Components, Theme, TopBars, Navigation)   │
└──────────────────────────────┬──────────────────────────────┘
                               │ Observes UI State (StateFlow)
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                    ViewModel Layer                          │
│   (StateFlow, combine, flatMapLatest, CoroutineScope)       │
└──────────────────────────────┬──────────────────────────────┘
                               │ Invokes CRUD Operations
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                    Repository Layer                         │
│   (Single source of truth, data caching, auto-seeding)      │
└──────────────────────────────┬──────────────────────────────┘
                               │ Room Kotlin Flow Queries
                               ▼
┌─────────────────────────────────────────────────────────────┐
│             Local SQLite Database (Room & KSP)              │
│       (Entities, DAOs, Indices, Zero-Network Storage)       │
└─────────────────────────────────────────────────────────────┘
```

### Technology Highlights:
- **Language**: Kotlin 2.0 (100% Kotlin codebase, strict type safety, zero legacy Java files).
- **UI Framework**: Jetpack Compose with Material 3 dynamic color scheme, dark/light theme support, and edge-to-edge system insets.
- **Concurrency & Reactivity**: Kotlin Coroutines, `Flow`, `StateFlow`, `SharingStarted.WhileSubscribed(5000)`.
- **Database**: Room Database with compile-time SQL verification via Google's Kotlin Symbol Processing (KSP).
- **Code Optimization**: R8 in Full Optimization Mode with customized ProGuard rules and aggressive resource shrinking.
- **Security & Sandboxing**: `android:allowBackup="false"`, zero external network dependencies, zero exported components, zero hardcoded API keys.

---

## 📂 Project Structure

```
├── app
│   ├── src/main/java/com/example/
│   │   ├── MainActivity.kt               # Single-activity Compose entry point & navigation host
│   │   ├── data/
│   │   │   ├── AppDao.kt                 # SQLite queries for students, attendance, & progress
│   │   │   ├── AppDatabase.kt            # Room database definition with schema versioning
│   │   │   ├── AttendanceRecord.kt       # Attendance entity with composite keys & foreign keys
│   │   │   ├── AttendanceRepository.kt   # Clean repository pattern & data seeding logic
│   │   │   ├── AttendanceStatus.kt       # Enum definitions (Present, Absent, Excused, Late)
│   │   │   ├── Department.kt             # Classical school departments, descriptions & subjects
│   │   │   ├── EthiopianDateUtils.kt     # High-precision Julian Day Number ↔ Ge'ez Calendar converter
│   │   │   ├── SessionType.kt            # Morning / Afternoon / Evening session types
│   │   │   └── Student.kt                # Student profile entity with contact & department data
│   │   └── ui/
│   │       ├── MainViewModel.kt          # State management & business logic coordination
│   │       ├── components/               # Reusable Material 3 composables (TopBars, Dialogs)
│   │       ├── screens/                  # Feature screens (Attendance, Students, Progress, Reports)
│   │       └── theme/                    # Ethiopian ecclesiastical inspired M3 theme & typography
│   ├── proguard-rules.pro                # Tailored R8/ProGuard preservation rules
│   └── build.gradle.kts                  # App build configuration & dependencies
├── README.md                             # Comprehensive project documentation
└── README_SIGNING.md                     # CI/CD & release keystore signing documentation
```

---

## 🚀 Getting Started & Local Setup

### Prerequisites
- **Android Studio**: Ladybug (2024.2+) or newer
- **JDK**: Java Development Kit 17 or 21
- **Android SDK**: `minSdk 24` (Android 7.0) / `targetSdk 36` / `compileSdk 36`

### Cloning & Building
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/yeneta-abinet-app.git
   cd yeneta-abinet-app
   ```

2. Open the project in **Android Studio** and let Gradle synchronize.

3. Run the application on an emulator or connected physical Android device:
   ```bash
   ./gradlew :app:installDebug
   ```

---

## 🔐 Production Release & Optimization

This app is configured for production releases with code minification, obfuscation, and asset optimization:

```bash
# Build optimized release APK
./gradlew :app:assembleRelease

# Build Google Play App Bundle (AAB)
./gradlew :app:bundleRelease
```

For instructions on generating a secure upload keystore and configuring environment variables (`KEYSTORE_PATH`, `STORE_PASSWORD`, `KEY_PASSWORD`) in local environments or GitHub Actions CI/CD, refer to [**README_SIGNING.md**](./README_SIGNING.md).

---

## 💡 Key Engineering Decisions & Best Practices

| Area | Decision & Justification |
| :--- | :--- |
| **Offline-First Storage** | Used Room SQLite over external cloud databases to guarantee 100% uptime and accessibility in remote monasteries with zero internet connectivity. |
| **Julian Day Calendar Math** | Engineered a zero-dependency mathematical algorithm in `EthiopianDateUtils.kt` to map Gregorian `Calendar` instances to Ethiopian Ge'ez calendar days accurately, eliminating heavy third-party calendar libraries. |
| **StateFlow Unidirectional Flow** | Used `combine()` and `flatMapLatest()` within `MainViewModel` to construct reactive state streams that update UI screens instantly upon database mutations. |
| **R8 Full Mode & Shrink Resources** | Enabled `android.enableR8.fullMode=true` and `isShrinkResources=true` to reduce the final APK size significantly while maintaining rock-solid runtime stability. |

---

## 📜 License

This project is licensed under the **MIT License** — feel free to use, modify, and distribute with attribution.

---

<p align="center">
  <b>Built with devotion for the preservation of Ethiopian Church Education & Heritage 🇪🇹</b>
</p>
