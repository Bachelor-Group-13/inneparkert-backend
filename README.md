# Inneparkert Backend 
Backend with License Plate Recognition in Java Spring Boot

## Setting Up the Project

### 1. Clone the Repository

```bash
git clone <repository-url>
cd backend
```

### 2. Create the .env file
Create a `.env`file in the root directory with the following variables:

```bash
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000 
COMPUTER_VISION_SUBSCRIPTION_KEY=your_azure_subscription_key
COMPUTER_VISION_ENDPOINT=your_azure_endpoint
```

### 3. Start the PostgreSQL Database  
The project uses Docker to run a PostgreSQL database. Start the database using the provided docker-compose.yml file:
`docker-compose up -d`

### 4. Build and Run the application
To build and run the application: 
`mvn spring-boot:run`

### 5. Format before pushing
To format and verify it's formatted before pushing 
`mvn spotless:verify` and `mvn spotless:apply`
