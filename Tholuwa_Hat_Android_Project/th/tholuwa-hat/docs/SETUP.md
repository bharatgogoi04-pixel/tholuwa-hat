# Tholuwa Hat setup guide

## Android app
1. Install Android Studio, Android SDK 35, and JDK 17.
2. Open the `android/` folder as a project and allow Gradle sync.
3. Run the `app` configuration on an Android device/emulator, or run `./gradlew :app:assembleDebug` from `android/` (Windows: `gradlew.bat :app:assembleDebug`).
4. APK output after a successful build: `android/app/build/outputs/apk/debug/app-debug.apk`.
5. The current client is a functional UI starter with language toggle, district selection, search/category filtering and bottom navigation. Sample listings are explicitly marked. Live account, shop, payment and nearby integrations are not yet wired to the Android client.

## Backend
Requires Node.js 22+ and PostgreSQL 15+.
1. `cd backend`
2. Copy `.env.example` to `.env` and set `DATABASE_URL` and a long random `JWT_SECRET`.
3. `npm install`
4. `npm run migrate`
5. `npm test`
6. `npm run dev`
7. Check `http://localhost:8080/health`.

Development OTP mode uses `OTP_MODE=development` and `OTP_DEV_CODE` only. Production OTP is deliberately fail-closed until a real provider adapter is configured.

## Admin preview
Serve `admin/` with any static web server and set the API URL to your backend. The dashboard preview is read-only and does not expose privileged administrative mutation routes. Protect it behind authentication/HTTPS before deployment.

## Integrations awaiting owner credentials
- SMS/OTP provider and approved sender/template.
- Razorpay account, API keys and webhook secret; use server-side order creation, signature verification, and reconciliation before activating subscriptions.
- Maps/geocoding provider and location permission flow.
- Private object storage and signed upload/download URLs.
- Firebase project/service account for push notifications.
- Production domain, TLS, monitoring, backup schedule and data retention policies.

Do not use sample products or development OTP for a public launch.
