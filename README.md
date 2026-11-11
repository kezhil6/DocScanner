# Doc Scanner (Android)

A simple Adobe Scan–style app: scan documents with the camera, auto edge detection and crop,
enhance with filters, multi-page, import from gallery, export as PDF.

Scanning is powered by Google's **ML Kit Document Scanner** (needs Google Play services,
so it works on most Android phones but not on devices without Play services).

## Features
- Camera capture with automatic edge detection, crop and perspective correction
- Rotate, clean/erase, and filters (color, grayscale, B&W) inside the scanner
- Multi-page documents (up to 50 pages) and gallery import
- Saved scans list: open, share, rename, delete
- Fully on-device, no accounts, no internet needed for scanning

## Build & run
1. Install Android Studio (Koala or newer).
2. File > Open > select this `DocScanner` folder. Let Gradle sync finish.
3. Plug in a phone (USB debugging on) or start an emulator with Google Play, then press Run.

First launch may download the scanner module via Play services (needs internet once).

## Project layout
- `app/src/main/java/com/example/docscanner/MainActivity.kt` - UI (Jetpack Compose) and scan flow
- `app/src/main/java/com/example/docscanner/ScanRepository.kt` - saving, listing, renaming, deleting PDFs
- `app/src/main/res/xml/file_paths.xml` - FileProvider config for sharing/opening PDFs

## Ideas for next steps
- OCR (ML Kit Text Recognition) to make PDFs searchable
- Folders/tags, search, cloud backup
- Password-protected PDFs, signature tool
- Change `applicationId` in `app/build.gradle.kts` before publishing

## Get an APK without Android Studio (GitHub Actions)
1. Create a free GitHub account and a new repository.
2. Upload the contents of this folder (including the hidden `.github` folder) to the repo.
3. Open the repo's **Actions** tab, pick **Build APK**, and wait for the green check (about 5 minutes).
   You can also press **Run workflow** to start it manually.
4. Open the finished run, download **DocScanner-debug-apk** from Artifacts, unzip it, and copy
   `app-debug.apk` to your phone. Allow "install unknown apps" when prompted.
