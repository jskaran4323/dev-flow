# DevFlow

Minimal issue tracker combining a Spring Boot backend with a Vue 3 frontend (DevFlow).

Key points
- Backend: Spring Boot (Java) — API, business logic, AI label suggestions.
- Frontend: Vue 3 + Vite + Pinia — simple UI, Kanban board, projects, issues, comments.
- Docker-ready: builds a runnable JAR and Docker image.

Quick start
- Backend (build & run): mvnw package && java -jar target/accesscontrol-system-0.0.1-SNAPSHOT.jar
- Frontend (dev): cd assets/devflow && npm install && npm run dev
- Docker: docker build -f src/Dockerfile -t devflow-app . && docker run -p 8081:8081 devflow-app

Important files
- Backend entry: [src/main/java/com/accesscontrol/AccesscontrolSystemApplication.java](src/main/java/com/accesscontrol/AccesscontrolSystemApplication.java)  
- Frontend entry: [assets/devflow/src/main.ts](assets/devflow/src/main.ts)  
- Dockerfile: [src/Dockerfile](src/Dockerfile)  
- Frontend README: [assets/devflow/README.md](assets/devflow/README.md)  
- Maven root: [pom.xml](pom.xml)  
- Node scripts: [package.json](package.json)

Notes
- AI label suggestions use Gemini integration (see [src/main/java/com/accesscontrol/impl/GeminiAiSuggestionService.java](src/main/java/com/accesscontrol/impl/GeminiAiSuggestionService.java)).
- Project/team flows and controllers live under [src/main/java/com/accesscontrol/controllers/](src/main/java/com/accesscontrol/controllers/).