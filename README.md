# JobConnect

JobConnect is a job portal web application built with Spring Boot, MySQL, Thymeleaf, and Spring Security. The application supports employer and job seeker workflows, allowing employers to post jobs and review applications while candidates can browse jobs and apply for suitable opportunities.

## Features

- User registration and login
- Role-based access for employers and job seekers
- Employer dashboard
- Create, view, edit, and manage job postings
- Candidate job browsing
- Job application submission
- Application review for employers
- MySQL-backed persistence using Spring Data JPA
- Layered Spring Boot structure with controllers, services, repositories, and models

## Tech Stack

- **Backend:** Java, Spring Boot
- **Frontend:** Thymeleaf, HTML, CSS
- **Database:** MySQL
- **Security:** Spring Security
- **Build Tool:** Maven
- **Deployment:** Docker-ready configuration

## Project Structure

```text
src/main/java/com/yogesh/jobconnect
├── config
├── controllers
├── models
├── repositories
├── security
└── services

src/main/resources
├── static/css
├── templates
└── application.properties
```

## Environment Variables

Create the required database configuration using environment variables:

```env
DB_URL=jdbc:mysql://localhost:3306/jobconnect
DB_USERNAME=root
DB_PASSWORD=your_password
```

## Local Setup

1. Clone the repository:

```bash
git clone https://github.com/YogesTech/jobconnect.git
cd jobconnect
```

2. Create a MySQL database:

```sql
CREATE DATABASE jobconnect;
```

3. Configure the environment variables listed above.

4. Run the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

5. Open the app:

```text
http://localhost:8080
```

## Testing

Run the test suite:

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

## Future Improvements

- Add REST API documentation
- Add screenshots and demo credentials
- Add pagination and search filters for jobs
- Add resume upload support for candidates
- Add email notifications for applications
- Add stronger validation and error handling

## Author

**Yogeshwaran B**  
GitHub: https://github.com/YogesTech  
Portfolio: https://yogeshdeveloper.netlify.app
