# <center>Inneparkert Backend</center>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
        <li><a href="#technologies">Technologies</a></li>
    <li>
      <a href="#setting-up-the-project">Setting Up the Project</a>
      <ul>
      </ul>
    </li>
    <li><a href="#contribuitons">Contributions</a></li>

  </ol>
</details>

### About the project

This is a bachelor project at NTNU in course [IDATA2900][IDATA2900-url]. The Project is develop based on a company´s description and desire.

We have had regular meetings with client and school supervisor to have a steady progress in the throghout the development.

---

### Technologies

Java Spring Boot
Open CV
Azure CV
Docker
Maven
Lombok
Yolo v8
Fast API - Python
Uvicorn - Python

---

### Setting Up the Project

#### 1. Clone the Repository

```bash
git clone <repository-url>
cd backend
```

#### 2. Create the .env file

Create a `.env`file in the root directory with the following variables:

```bash
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000
COMPUTER_VISION_SUBSCRIPTION_KEY=your_azure_subscription_key
COMPUTER_VISION_ENDPOINT=your_azure_endpoint
```

#### 3. Start the PostgreSQL Database

The project uses Docker to run a PostgreSQL database. Start the database using the provided docker-compose.yml file:
`docker-compose up -d`

#### 4. Build and Run the application

To build and run the application:
`mvn spring-boot:run`

#### 5. Format before pushing

To format and verify it's formatted before pushing
`mvn spotless:verify` and `mvn spotless:apply`

---

### Contributions

[Contributors][contributors-url]

[Viljar Hoem-Olsen][Viljar-url]
[Sander Grimstad][Sander-url]
[Thomas Aakre][Thomas-url]

---

[IDATA2900-url]: https://www.ntnu.no/studier/emner/IDATA2900/2024#tab=omEmnet
[Endpoint Badge]: https://img.shields.io/endpoint
[contributors-url]: https://github.com/Bachelor-Group-13/inneparkert-backend/graphs/contributors
[Backend-url]: https://github.com/Bachelor-Group-13/inneparkert-backend.git
[Repository-url]: https://github.com/Bachelor-Group-13
[Viljar-url]: https://github.com/viljarh
[Sander-url]: https://github.com/sagrimstad
[Thomas-url]: (https://github.com/thomasaakre)
