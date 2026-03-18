## FinalDraw

FinalDraw is an Android drawing app with a configurable pen/canvas, local persistence via Room, and optional Firebase cloud save/load with native (JNI) image effects.

## Features

- Draw on a bitmap-backed canvas with:
  - Pen size (seek bar)
  - Pen color (color picker) and circle "stamp" mode
  - Eraser (implemented by drawing with white)
- Image effects powered by native C++ via JNI:
  - Add Noise
  - Invert Colors
- Save and load drawings:
  - Local: saves PNGs to the app's internal storage and tracks them in a Room database
  - Cloud: uses Firebase Auth (email/password) + Firebase Storage (PNG) + Firestore (metadata)
- UI built with Fragment navigation + Jetpack Compose for the drawing lists

## Tech Stack

- Kotlin, Android Gradle Plugin (8.3.2), Jetpack Compose
- Room (2.6.1) for local metadata
- Firebase:
  - Authentication (email/password)
  - Storage (drawings PNGs)
  - Firestore (document per drawing with filename + storage path + timestamp)
- Native layer:
  - C++ (CMake) + JNI for bitmap effects

## Prerequisites

- Android Studio with Android SDK for `compileSdk = 34`
- A Firebase project configured for the app package name `com.example.finaldraw`
- An emulator/device running Android 13+ (`minSdk = 33`)

## Firebase Setup

1. Add/keep `app/google-services.json` in sync with your Firebase project.
2. Enable **Email/Password** authentication in Firebase Console.
3. Ensure Firebase Storage + Firestore are available in your project.
4. (Recommended) Update Firebase security rules to match your intended data access model.

Cloud upload flow implemented by the app:
- Drawing bitmap is compressed to PNG bytes.
- File is uploaded to Firebase Storage at: `<userUid>/drawing_<timestamp>.png`
- Firestore document is written under:
  - `userCollection/<userUid>/drawings/<autoId>`
  - Fields: `fileName`, `filePath`, `timestamp`

## Local Storage

- PNGs are saved to the app's internal files directory (the view returns a `drawing_<timestamp>.png` filename).
- Room stores local metadata via `Drawing(fileName: String)`.
- When loading a local drawing, the app decodes the PNG from `context.filesDir/<fileName>`.

## Native (JNI) Effects

- `CustomView` loads the native library `finaldraw`.
- JNI entrypoints apply bitmap transforms:
  - `addNoise(Bitmap)`
  - `invertColors(Bitmap)`

## Running the App

- Open the project in Android Studio.
- Sync Gradle and run on a device/emulator with API level 33+.
- Use `Draw` -> `Load` -> `Load Local` or `Load Firebase`.

## Tests

- Unit tests for ViewModel behavior (`SimpleViewModelTest`).
- Basic Espresso UI tests for login-related screens (`FireBaseAndLoginFragmentTest`).
