# Troubleshooting Guide - Bahai Community App

## Häufigste Lokale Probleme und Lösungen

### Problem 1: Java nicht installiert oder JAVA_HOME nicht gesetzt

**Symptome:**
```
ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
bash: java: command not found
```

**Lösung (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk' >> ~/.bashrc
source ~/.bashrc
```

**Lösung (CentOS/RHEL/Fedora):**
```bash
sudo yum install java-17-openjdk-devel
# oder für neuere Versionen:
sudo dnf install java-17-openjdk-devel
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk' >> ~/.bashrc
source ~/.bashrc
```

**Lösung (macOS):**
```bash
brew install openjdk@17
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
source ~/.zshrc
```

**Lösung (Windows):**
1. Download Java 17 von https://adoptium.net/
2. Installieren und JAVA_HOME setzen:
```cmd
setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-17.0.8.1-hotspot"
```

**Verifikation:**
```bash
java -version
echo $JAVA_HOME
```

---

### Problem 2: Gradle Wrapper Line Ending Probleme

**Symptome:**
```
gradlew: line 1: //: Is a directory
./gradlew: Permission denied
```

**Lösung (Alle Plattformen):**
```bash
# Konvertiere CRLF zu LF Zeilenenden
dos2unix gradlew gradlew.bat

# Oder manuell mit sed:
sed -i 's/\r$//' gradlew gradlew.bat

# Mache ausführbar
chmod +x gradlew gradlew.bat
```

**Alternative Lösung (falls dos2unix nicht verfügbar):**
```bash
# Mit Python
python3 -c "
import os
for filename in ['gradlew', 'gradlew.bat']:
    with open(filename, 'rb') as f:
        content = f.read()
    with open(filename, 'wb') as f:
        f.write(content.replace(b'\r\n', b'\n').replace(b'\r', b'\n'))
    os.chmod(filename, 0o755)
"
```

**Verifikation:**
```bash
file gradlew
# Sollte zeigen: Bourne-Again shell script executable
```

---

### Problem 3: Android SDK nicht installiert oder ANDROID_HOME nicht gesetzt

**Symptome:**
```
SDK location not found
ANDROID_HOME is not set
```

**Lösung (Manuelle Installation):**
```bash
# 1. Android Studio herunterladen von https://developer.android.com/studio
# 2. Oder nur SDK Tools:
wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
unzip commandlinetools-linux-9477386_latest.zip
mkdir -p ~/android-sdk/cmdline-tools
mv cmdline-tools ~/android-sdk/cmdline-tools/latest

# 3. ANDROID_HOME setzen
export ANDROID_HOME=~/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# 4. SDK installieren
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# 5. Permanent machen
echo 'export ANDROID_HOME=~/android-sdk' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools' >> ~/.bashrc
source ~/.bashrc
```

**Lösung (Ubuntu/Debian via snap):**
```bash
sudo snap install android-studio --classic
# ANDROID_HOME wird automatisch gesetzt
```

**Verifikation:**
```bash
echo $ANDROID_HOME
adb version
sdkmanager --list
```

---

### Problem 4: npm Berechtigungsprobleme beim Backend

**Symptome:**
```
npm ERR! code EACCES
npm ERR! syscall mkdir
npm ERR! path /usr/local/lib/node_modules
```

**Lösung (Backend Stub):**
```bash
cd backend-stub

# Option 1: Lokale Installation ohne global
npm install --no-package-lock --prefix .

# Option 2: NPM Prefix setzen
mkdir ~/.npm-global
npm config set prefix '~/.npm-global'
export PATH=~/.npm-global/bin:$PATH
npm install

# Option 3: Mit sudo (nicht empfohlen für Entwicklung)
sudo npm install
```

**Alternative Backend Setup:**
```bash
# Falls npm komplett nicht funktioniert, erstelle node_modules manuell:
mkdir -p backend-stub/node_modules

# Installiere Pakete einzeln:
cd backend-stub
npm init -y
npm install express cors body-parser uuid --no-package-lock
```

**Verifikation:**
```bash
cd backend-stub
node server.js
# In neuem Terminal:
curl http://localhost:3000/health
```

---

### Problem 5: Gradle Build fehler

**Symptome:**
```
FAILURE: Build failed with an exception.
Could not resolve dependencies
```

**Lösung (Gradle Cache leeren):**
```bash
# Gradle Cache löschen
rm -rf ~/.gradle/caches
rm -rf ~/.gradle/wrapper

# Projekt-Build leeren
./gradlew clean

# Erneut bauen
./gradlew :app:assembleDebug --stacktrace
```

**Lösung (Proxy/Firewall Probleme):**
```bash
# Gradle Proxy setzen
echo 'systemProp.http.proxyHost=your-proxy-host' >> gradle.properties
echo 'systemProp.http.proxyPort=your-proxy-port' >> gradle.properties
echo 'systemProp.https.proxyHost=your-proxy-host' >> gradle.properties
echo 'systemProp.https.proxyPort=your-proxy-port' >> gradle.properties
```

**Lösung (Offline Mode):**
```bash
# Erste Online Build, dann Offline
./gradlew --refresh-dependencies :app:assembleDebug
./gradlew --offline :app:assembleDebug
```

---

### Problem 6: Android Emulator/Device Verbindung

**Symptome:**
```
adb: no devices/emulators found
Failed to install app-debug.apk
```

**Lösung (Emulator starten):**
```bash
# Emulator verfügbar
emulator -list-avds
emulator -avd <avd_name>

# Oder über Android Studio AVD Manager
```

**Lösung (Device verbinden):**
```bash
# USB Debugging aktivieren am Device
# 1. Einstellungen -> Über Telefon -> Build-Nummer 7x tippen
# 2. Entwickleroptionen aktivieren
# 3. USB-Debugging aktivieren

# Gerät verbinden und prüfen
adb devices
adb kill-server
adb start-server
adb devices
```

**Lösung (APK manuell installieren):**
```bash
# Falls adb nicht funktioniert
adb install app/build/outputs/apk/debug/app-debug.apk

# Alternative: Datei übertragen
adb push app/build/outputs/apk/debug/app-debug.apk /sdcard/
# Dann am Device: Datei-Manager -> APK öffnen
```

---

### Problem 7: Compose Preview funktioniert nicht

**Symptome:**
```
Preview not working
Compose preview shows error
```

**Lösung:**
```bash
# 1. Android Studio neustarten
# 2. Build Clean und Rebuild
./gradlew clean
./gradlew build

# 3. Compose Compiler Version prüfen
# In build.gradle.kts der App:
composeOptions {
    kotlinCompilerExtensionVersion = "1.5.4"  # Aktuelle Version
}

# 4. Invalidate Caches and Restart
# Android Studio: File -> Invalidate Caches and Restart
```

---

## Umgebung-Setup für Linux Mint (Spezifisch)

```bash
# 1. Java 17 installieren
sudo apt update
sudo apt install openjdk-17-jdk

# 2. Android Studio via Flatpak (empfohlen)
flatpak install flathub com.google.AndroidStudio
flatpak run com.google.AndroidStudio

# 3. Oder manuelle Android SDK Installation
wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
unzip commandlinetools-linux-9477386_latest.zip
mkdir -p ~/Android/Sdk/cmdline-tools
mv cmdline-tools ~/Android/Sdk/cmdline-tools/latest

# 4. Umgebungsvariablen setzen
cat >> ~/.bashrc << 'EOF'
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export ANDROID_HOME=~/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/emulator
EOF
source ~/.bashrc

# 5. Android SDK Tools installieren
sdkmanager "platform-tools"
sdkmanager "platforms;android-34"
sdkmanager "build-tools;34.0.0"

# 6. Projekt bauen
cd CommunityApp
chmod +x gradlew gradlew.bat
dos2unix gradlew gradlew.bat
./gradlew :app:assembleDebug
```

## Komplettes Setup Script für Linux Mint

```bash
#!/bin/bash
echo "Setting up Bahai Community App development environment..."

# Java installieren
sudo apt update && sudo apt install -y openjdk-17-jdk dos2unix

# Umgebungsvariablen setzen
cat >> ~/.bashrc << 'EOF'
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export ANDROID_HOME=~/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator
EOF
source ~/.bashrc

# Android SDK installieren
wget -q https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
unzip -q commandlinetools-linux-9477386_latest.zip
mkdir -p $ANDROID_HOME/cmdline-tools
mv cmdline-tools $ANDROID_HOME/cmdline-tools/latest
rm commandlinetools-linux-9477386_latest.zip

# SDK Komponenten installieren
yes | sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Gradle Wrapper reparieren
cd CommunityApp
dos2unix gradlew gradlew.bat
chmod +x gradlew gradlew.bat

echo "Environment setup complete!"
echo "Run: ./gradlew :app:assembleDebug"
```

Speichern als `setup_environment.sh` und ausführen:
```bash
chmod +x setup_environment.sh
./setup_environment.sh
```

## Support

Falls Probleme bestehen bleiben:
1. Überprüfe die BUILD_LOG.txt für spezifische Fehler
2. Teste mit `./gradlew --version` ob Gradle funktioniert
3. Prüfe Logs: `./gradlew :app:assembleDebug --stacktrace --info`
4. Erstelle ein Issue mit vollständiger Fehlermeldung
