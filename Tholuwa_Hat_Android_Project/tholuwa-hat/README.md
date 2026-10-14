# Tholuwa Hat (থলুৱা হাট)

A maintainable starter monorepo for an Assam-wide local marketplace. This repository contains a native Android Compose client, a Node.js API, PostgreSQL schema/migrations, a responsive admin interface, domain tests, and deployment/setup notes.

> **Honest status:** This is an implementation foundation, not a claim of production launch readiness. Production SMS OTP, Razorpay credentials/webhooks, maps, object storage, Firebase push, legal review, security review, and real-device QA require owner accounts and verification. The Android SDK/Gradle are not installed in the generation environment, so an APK has not been built here.

## Architecture
- `android/`: Kotlin + Jetpack Compose Android app, Assamese/English language choice and five-tab navigation.
- `backend/`: Node.js 22 + Express API, PostgreSQL, Zod validation, auth/shops/products/orders/subscriptions/referrals/community/admin route foundations.
- `backend/migrations/`: normalized PostgreSQL schema and configurable Assam district seed data.
- `admin/`: responsive browser-based admin dashboard starter; calls backend API.
- `docs/`: deployment, security, provider setup, policies requiring legal review.

## Quick start (backend)
1. Install Node.js 22+ and PostgreSQL 15+.
2. `cd backend && cp .env.example .env`
3. Fill in `DATABASE_URL` and a long random `JWT_SECRET`.
4. `npm install`
5. `npm run migrate`
6. `npm test`
7. `npm run dev`
8. Open `admin/index.html` locally or host `admin/` behind your HTTPS domain; set API base URL in its settings.

Development OTP is only available when `OTP_MODE=development`; it is not suitable for production. Production OTP requests fail closed until an SMS provider adapter is configured.

## Android build
Open `android/` in Android Studio with Android SDK installed, sync Gradle, then run `:app:assembleDebug`. For a signed release use Android Studio's **Build > Generate Signed Bundle / APK** and keep keystore credentials out of source control. This environment did not include Gradle or Android SDK, so no APK/build result is claimed.

See `docs/SETUP.md`, `docs/SECURITY.md`, and `docs/POLICIES.md` before any real launch.
