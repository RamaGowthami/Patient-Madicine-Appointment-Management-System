# CircleConnect — A free Community Bonding App

CircleConnect helps local communities connect through interest groups, weekly challenges, photo/story sharing, points & badges, and event invites.

## Stack
- Backend: Java 17, Spring Boot (Web, Security, JPA, Validation), H2 (dev), Firebase Admin (Auth)
- Frontend: React + Vite + TypeScript, Firebase (Client SDK: Auth, Storage)

## Features
- Local Interest Groups: create, browse, join/leave
- Weekly Community Challenges: submit photos/stories, earn points
- Photo and Story Sharing: feed of posts
- Points & Badges: simple thresholds awarded on submissions
- Event Invites: create events, invite/RSVP

## Project Structure
```
circleconnect/
  backend/
  frontend/
```

## Prerequisites
- Java 17+
- Node.js 18+
- npm or pnpm or yarn

Optional (for running with Firebase verification):
- Firebase Project (Spark/free)
- Service Account key for Firebase Admin SDK JSON (download from Google Cloud Console; not committed)

## Environment Variables
Create a `.env` for the frontend and an `application.yml` (already templated) for the backend.

Frontend (`frontend/.env.local`):
```
VITE_FIREBASE_API_KEY=...
VITE_FIREBASE_AUTH_DOMAIN=...
VITE_FIREBASE_PROJECT_ID=...
VITE_FIREBASE_STORAGE_BUCKET=...
VITE_FIREBASE_MESSAGING_SENDER_ID=...
VITE_FIREBASE_APP_ID=...
VITE_API_BASE_URL=http://localhost:8080
```

Backend (`backend/src/main/resources/application.yml` already includes sensible defaults). To enable Firebase verification, set:
```
FIREBASE_ADMIN_CREDENTIALS=/absolute/path/to/serviceAccountKey.json
```

If `FIREBASE_ADMIN_CREDENTIALS` is not set, backend runs in dev mode and accepts a special header for testing without real Firebase:
```
Authorization: Dev uid=test-user-1
```

## Getting Started

### 1) Backend
```
cd backend
./mvnw spring-boot:run
```
- Runs on `http://localhost:8080`
- H2 console at `/h2-console` (username: `sa`, no password)

### 2) Frontend
```
cd frontend
npm install
npm run dev
```
- Runs on `http://localhost:5173`

## API Overview (high-level)
- `GET /api/me` — returns or creates a `UserProfile` for the authenticated user
- `GET /api/groups` / `POST /api/groups` — list/create groups
- `POST /api/groups/{id}/join` / `POST /api/groups/{id}/leave`
- `GET /api/challenges/current` — current weekly challenge
- `POST /api/challenges/{id}/submit` — submit photo/story; awards points
- `GET /api/posts` / `POST /api/posts` — feed and create posts
- `GET /api/events` / `POST /api/events` / `POST /api/events/{id}/rsvp`

All authenticated endpoints expect `Authorization: Bearer <Firebase ID Token>` or `Authorization: Dev uid=<local-uid>` in dev.

## Notes
- This is an MVP-grade implementation to get you moving quickly. Extend models and validation as needed.
- Badges are awarded at point thresholds: 100 (Bronze), 500 (Silver), 1000 (Gold).