// FILE: CommunityApp/gradle_wrapper_fallback.sh
#!/bin/bash

echo "=== Gradle Wrapper Fallback Script ==="
echo "This script provides instructions for setting up the environment manually."
echo ""

# Check for Java
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed."
    echo ""
    echo "Please install Java 17 or later:"
    echo "  - Ubuntu/Debian: sudo apt update && sudo apt install openjdk-17-jdk"
    echo "  - CentOS/RHEL: sudo yum install java-17-openjdk-devel"
    echo "  - macOS: brew install openjdk@17"
    echo "  - Windows: Download from https://adoptium.net/"
    echo ""
    echo "After installing Java, set JAVA_HOME:"
    echo "  export JAVA_HOME=/usr/lib/jvm/java-17-openjdk"
    echo ""
else
    echo "Java is available:"
    java -version
    echo ""
fi

# Check for Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "WARNING: ANDROID_HOME is not set."
    echo "Please install Android SDK and set ANDROID_HOME:"
    echo "  export ANDROID_HOME=/path/to/android-sdk"
    echo "  export PATH=\$PATH:\$ANDROID_HOME/tools:\$ANDROID_HOME/platform-tools"
    echo ""
else
    echo "ANDROID_HOME is set to: $ANDROID_HOME"
    echo ""
fi

# Instructions for building
echo "To build the project manually:"
echo "1. Ensure Java 17+ and Android SDK are installed"
echo "2. cd /path/to/CommunityApp"
echo "3. ./gradlew :app:assembleDebug"
echo ""
echo "To start the backend:"
echo "1. cd /path/to/CommunityApp/backend-stub"
echo "2. npm install"
echo "3. npm start"
