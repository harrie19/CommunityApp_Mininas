# Bahai Community Android App - Complete Project

## Übersicht

Das Projekt "Bahai Community" ist eine vollständige Android-App in Kotlin mit Jetpack Compose, die für die Bahá'í-Gemeinschaft entwickelt wurde. Die App folgt modernen Android-Architektur-Praktiken mit MVVM, Dependency Injection und Offline-First Design.

## Technologie-Stack

- **UI Framework**: Jetpack Compose 1.6.0
- **Architecture**: MVVM mit Hilt DI
- **Database**: Room Database
- **Networking**: Retrofit 2.9.0 + Moshi
- **Image Loading**: Coil
- **Language**: Kotlin 1.9.10
- **Build System**: Gradle 8.0, AGP 8.1.0

## Projektstruktur

```
CommunityApp/
├── app/                              # Android application module
│   ├── build.gradle.kts              # App-level build configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml   # App manifest
│   │   │   ├── java/com/bahaicommunity/
│   │   │   │   ├── BahaiCommunityApplication.kt  # Hilt Application class
│   │   │   │   ├── MainActivity.kt              # Main activity with Compose
│   │   │   │   ├── data/
│   │   │   │   │   ├── api/                     # API definitions
│   │   │   │   │   │   ├── ApiService.kt
│   │   │   │   │   │   └── models/ApiModels.kt
│   │   │   │   │   ├── database/                # Room database
│   │   │   │   │   │   ├── BahaiDatabase.kt
│   │   │   │   │   │   ├── dao/
│   │   │   │   │   │   │   ├── PostDao.kt
│   │   │   │   │   │   │   └── UserDao.kt
│   │   │   │   │   │   └── entities/
│   │   │   │   │   │       ├── PostEntity.kt
│   │   │   │   │   │       ├── UserEntity.kt
│   │   │   │   │   │       └── Converters.kt
│   │   │   │   │   └── repository/              # Repository layer
│   │   │   │   │       ├── PostRepository.kt
│   │   │   │   │       └── UserRepository.kt
│   │   │   │   ├── di/                          # Dependency injection modules
│   │   │   │   │   ├── NetworkModule.kt
│   │   │   │   │   ├── DatabaseModule.kt
│   │   │   │   │   └── RepositoryModule.kt
│   │   │   │   ├── ui/                          # UI layer
│   │   │   │   │   ├── feed/                    # Feed screen
│   │   │   │   │   │   ├── FeedViewModel.kt
│   │   │   │   │   │   └── FeedScreen.kt
│   │   │   │   │   ├── createpost/              # Create post screen
│   │   │   │   │   │   ├── CreatePostViewModel.kt
│   │   │   │   │   │   └── CreatePostScreen.kt
│   │   │   │   │   ├── postdetail/              # Post detail screen
│   │   │   │   │   │   ├── PostDetailViewModel.kt
│   │   │   │   │   │   └── PostDetailScreen.kt
│   │   │   │   │   ├── profile/                 # Profile screen
│   │   │   │   │   │   ├── ProfileViewModel.kt
│   │   │   │   │   │   └── ProfileScreen.kt
│   │   │   │   │   ├── chat/                    # Chat screen (stub)
│   │   │   │   │   │   └── ChatScreen.kt
│   │   │   │   │   ├── navigation/              # Navigation setup
│   │   │   │   │   │   └── AppNavigation.kt
│   │   │   │   │   └── theme/                   # UI theme
│   │   │   │   │       ├── Color.kt
│   │   │   │   │       ├── Theme.kt
│   │   │   │   │       └── Type.kt
│   │   │   │   └── utils/                       # Utility classes
│   │   │   └── res/                             # Android resources
│   │   │       ├── values/
│   │   │       │   ├── strings.xml
│   │   │       │   ├── colors.xml
│   │   │       │   └── themes.xml
│   │   │       ├── xml/
│   │   │       │   ├── backup_rules.xml
│   │   │       │   └── data_extraction_rules.xml
│   │   │       └── mipmap-*/                    # Launcher icons
│   │   ├── test/                                # Unit tests
│   │   │   └── java/com/bahaicommunity/ui/feed/FeedViewModelTest.kt
│   │   └── androidTest/                         # UI tests
│   │       └── java/com/bahaicommunity/ui/feed/FeedScreenTest.kt
├── backend-stub/                    # Node.js backend stub
│   ├── package.json                 # Backend dependencies
│   ├── server.js                    # Express server with API endpoints
│   └── .npmrc                       # NPM configuration
├── ci/
│   └── android-ci.yml               # GitHub Actions CI/CD configuration
├── build.gradle.kts                 # Root build configuration
├── settings.gradle.kts              # Gradle settings
├── gradle.properties                # Gradle properties
├── gradle/                          # Gradle wrapper files
│   └── wrapper/
│       └── gradle-wrapper.properties
├── gradlew                          # Gradle wrapper script (Unix)
├── gradlew.bat                      # Gradle wrapper script (Windows)
├── setup_and_build.sh               # Automated build script
├── gradle_wrapper_fallback.sh       # Fallback instructions
└── README.md                        # Project documentation
```

## Features

### Core Features
1. **Feed**: Zeigt alle Posts mit Autor, Titel, Inhalt und optionalem Bild
2. **Post erstellen**: Erstellen neuer Posts mit Titel, Inhalt und optionalem Bild
3. **Post Details**: Detaillierte Ansicht einzelner Posts
4. **Profil**: Benutzerprofil mit Login-Funktionalität (Stub)
5. **Chat**: Stub für zukünftige Chat-Funktionalität

### Technische Features
1. **Offline-First**: Room Database für lokale Datenspeicherung
2. **API Integration**: Retrofit für Backend-Kommunikation
3. **Dependency Injection**: Hilt für saubere Abhängigkeitsverwaltung
4. **Navigation**: Navigation Compose für Bildschirmnavigation
5. **State Management**: StateFlow für reactive UI-Updates
6. **Image Loading**: Coil für effizientes Bild-Loading
7. **Testing**: Unit Tests und UI Tests

## API Endpoints (Backend Stub)

Der Backend Stub bietet folgende Endpoints:

```
GET    /posts           - Alle Posts abrufen
GET    /posts/:id       - Bestimmten Post abrufen
POST   /posts           - Neuen Post erstellen
POST   /posts/upload    - Post mit Bild hochladen
POST   /auth/login      - Benutzeranmeldung
GET    /users/:id       - Benutzerdaten abrufen
GET    /health          - Gesundheitsstatus
```

## Build-Anforderungen

### Systemanforderungen
- Java 17 oder höher
- Android SDK (API Level 26-34)
- Node.js 18+ (für Backend)
- npm oder Yarn

### Umgebungsvariablen
```bash
export JAVA_HOME=/path/to/java17
export ANDROID_HOME=/path/to/android-sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

## Build-Anweisungen

### Automatischer Build
```bash
chmod +x setup_and_build.sh
./setup_and_build.sh
```

### Manueller Build
```bash
# 1. Backend starten (optional)
cd backend-stub
npm install
npm start &

# 2. Android App bauen
cd ..
./gradlew :app:assembleDebug
```

### APK Installation
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Bekannte Probleme und Lösungen

### 1. Gradle Wrapper Line Endings
**Problem**: gradlew hat CRLF statt LF Zeilenenden  
**Lösung**: 
```bash
dos2unix gradlew gradlew.bat
```

### 2. Java nicht installiert
**Problem**: JAVA_HOME nicht gesetzt  
**Lösung**:
```bash
# Ubuntu/Debian
sudo apt update && sudo apt install openjdk-17-jdk
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk

# CentOS/RHEL
sudo yum install java-17-openjdk-devel
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk

# macOS
brew install openjdk@17
export JAVA_HOME=/usr/local/opt/openjdk@17
```

### 3. npm Berechtigungsprobleme
**Problem**: npm install schlägt mit EACCES fehl  
**Lösung**:
```bash
cd backend-stub
npm install --no-package-lock
```

## Development Workflow

### Neuen Screen hinzufügen
1. Erstelle ViewModel in `ui/<screen>/<Screen>ViewModel.kt`
2. Erstelle Composable Screen in `ui/<screen>/<Screen>Screen.kt`
3. Füge Navigation Route in `ui/navigation/AppNavigation.kt` hinzu
4. Registriere DI Modul falls nötig

### Datenbank-Änderungen
1. Update Entity in `data/database/entities/`
2. Update DAO in `data/database/dao/`
3. Erhöhe Database Version in `BahaiDatabase.kt`
4. Erstelle Migration falls nötig

### API Integration
1. Definiere Models in `data/api/models/`
2. Implementiere Service Interface in `data/api/ApiService.kt`
3. Update Repository in `data/repository/`
4. Teste API Endpoints

## Testing

### Unit Tests
```bash
./gradlew test
```

### UI Tests
```bash
./gradlew connectedAndroidTest
```

### Spezifische Tests
```bash
# Feed ViewModel Tests
./gradlew :app:testDebugUnitTest --tests="com.bahaicommunity.ui.feed.FeedViewModelTest"

# Feed Screen Tests (benötigt emulator/device)
./gradlew :app:connectedDebugAndroidTest --tests="com.bahaicommunity.ui.feed.FeedScreenTest"
```

## Deployment

### Debug Build
```bash
./gradlew :app:assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

### Release Build (Signierung erforderlich)
```bash
./gradlew :app:assembleRelease
# APK: app/build/outputs/apk/release/app-release.apk
```

## CI/CD

Das Projekt enthält GitHub Actions Konfiguration für automatische Builds:
- Automatisches Testen bei Pull Requests
- APK Artefakt Upload
- Backend Integration Tests

## Contributing

1. Fork das Repository
2. Erstelle Feature Branch: `git checkout -b feature/amazing-feature`
3. Commit Changes: `git commit -m 'Add amazing feature'`
4. Push Branch: `git push origin feature/amazing-feature`
5. Öffne Pull Request

## Lizenz

MIT License - siehe LICENSE Datei für Details.

## Support

Bei Problemen oder Fragen:
1. Überprüfe das BUILD_LOG.txt für bekannte Probleme
2. Konsultiere die Troubleshooting-Sektion
3. Erstelle ein Issue im Repository

---

**Erstellt von**: MiniMax Agent  
**Datum**: 2025-10-29  
**Version**: 1.0.0
