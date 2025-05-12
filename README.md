# AI Builder

## Setup Instructions

### Google Cloud Credentials

This application uses Google Cloud's Vertex AI Gemini API, which requires authentication credentials. For security reasons, these credentials are not included in the repository.

#### Option 1: Using Environment Variables (Recommended)

1. Obtain your Google Cloud service account credentials JSON file.
2. Set the `GOOGLE_APPLICATION_CREDENTIALS` environment variable to the path of your credentials file:

   ```bash
   export GOOGLE_APPLICATION_CREDENTIALS=/path/to/your/credentials.json
   ```

   For Windows:
   ```cmd
   set GOOGLE_APPLICATION_CREDENTIALS=C:\path\to\your\credentials.json
   ```

#### Option 2: Using Local Configuration

1. Copy the `src/main/resources/ai_key.json.template` file to `src/main/resources/ai_key.json`
2. Replace the placeholder values with your actual Google Cloud service account credentials.
3. Make sure not to commit this file to the repository (it's already in .gitignore).

### Running the Application

1. Make sure you have PostgreSQL running on port 5434 with a database named `ai_database`.
2. Build and run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

## Security Notes

- Never commit sensitive credentials to the repository.
- The `ai_key.json` file is listed in `.gitignore` to prevent accidental commits.
- For production environments, always use environment variables or a secure secrets management solution.