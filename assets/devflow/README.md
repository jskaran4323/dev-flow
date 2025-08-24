# DevFlow — Frontend

Vue 3 + TypeScript + Vite frontend for the DevFlow issue tracker.

Quick start
- Install deps:
  - npm install
- Run dev server:
  - npm run dev
- Build for production:
  - npm run build
- Preview production build:
  - npm run preview

Configuration
- API base: set VITE_API_BASE_URL to point to the backend (e.g. http://localhost:8081). The dev server proxies API requests when this points to localhost (see vite.config.ts).
- Default dev port: Vite (usually 5173). Override with VITE_PORT if needed.

Scripts
- npm run dev — start dev server
- npm run build — create production bundle
- npm run preview — serve the built bundle locally
- npm run lint — run linters (if configured)
- npm test — run frontend tests (if present)

Notes
- Uses Vue 3 `<script setup>`, Pinia for state, and Vite for fast HMR.
- Ensure the backend is running or VITE_API_BASE_URL points to a reachable API to use features that call the server (issues, comments, AI suggestions).
