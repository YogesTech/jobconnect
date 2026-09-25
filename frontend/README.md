# JobConnect frontend

## Local development

```powershell
npm install
npm run dev
```

The frontend expects the Spring Boot API at `http://localhost:8080/api` by default. Copy `.env.example` to `.env.local` when you need a different API URL.

## Netlify

The repository root contains `netlify.toml`. Set `VITE_API_BASE_URL` in Netlify to the deployed Render API URL ending in `/api`.
