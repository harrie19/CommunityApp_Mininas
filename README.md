// FILE: CommunityApp/README.md
# Bahai Community App

Eine Android-App für die Bahá'í-Gemeinschaft mit moderner Architektur.

## Features
- Feed mit Posts
- Post erstellen mit Bildern
- Benutzerprofile
- Chat (Stub-Implementierung)
- Offline-first mit Room Database
- Jetpack Compose UI

## Architektur
- MVVM Pattern mit Hilt DI
- Retrofit für API-Aufrufe
- Room für lokale Datenspeicherung
- StateFlow für reactive UIs

## Setup & Build
```bash
chmod +x setup_and_build.sh
./setup_and_build.sh
```

## Installation der APK
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Backend starten
```bash
cd backend-stub
npm install
node server.js
```
