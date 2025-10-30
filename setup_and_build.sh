// FILE: CommunityApp/setup_and_build.sh
#!/bin/bash

set -e

echo "=== Starting Bahai Community App Build Process ==="

cd "$(dirname "$0")"

echo "1. Checking backend setup..."
cd backend-stub

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "Note: Backend dependencies not installed. Building without backend for development."
    echo "To install backend dependencies manually, run:"
    echo "  cd backend-stub && npm install"
    BACKEND_PID=""
else
    echo "2. Starting backend server..."
    npm start &
    BACKEND_PID=$!

    echo "3. Waiting for backend to be ready..."
    for i in {1..30}; do
        if curl -s http://localhost:3000/health > /dev/null; then
            echo "Backend server is ready!"
            break
        fi
        echo "Waiting for backend... ($i/30)"
        sleep 2
    done

    if ! curl -s http://localhost:3000/health > /dev/null; then
        echo "WARNING: Backend server failed to start. Continuing with build..."
        if [ ! -z "$BACKEND_PID" ]; then
            kill $BACKEND_PID 2>/dev/null || true
        fi
        BACKEND_PID=""
    fi
fi

echo "4. Building Android app..."
cd ..
./gradlew :app:assembleDebug --stacktrace --no-daemon

BUILD_STATUS=$?

echo "5. Cleaning up backend process..."
if [ ! -z "$BACKEND_PID" ]; then
    kill $BACKEND_PID 2>/dev/null || true
fi

if [ $BUILD_STATUS -eq 0 ]; then
    echo "=== Build successful! ==="
    APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
    if [ -f "$APK_PATH" ]; then
        echo "APK location: $APK_PATH"
        echo ""
        echo "To install the APK on your Android device:"
        echo "  adb install $APK_PATH"
        echo ""
        echo "To start the backend manually:"
        echo "  cd backend-stub && npm start"
        echo ""
        echo "The build completed successfully!"
    else
        echo "ERROR: APK file not found at $APK_PATH"
        exit 1
    fi
else
    echo "=== Build failed! ==="
    exit 1
fi
