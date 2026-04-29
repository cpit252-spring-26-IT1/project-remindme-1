RemindMe
A Spring Boot web app that helps users track expiration dates of important documents like passports, national IDs, driving licenses, and bank cards.
Built for the CPIT 252 (Design Patterns) course project.
Features

Add document reminders with name, type, owner, and expiry date
Dashboard shows all reminders with status badges based on days left
Supports personal documents and documents for family members (children)
OCR support to extract expiry dates from uploaded images

Design Patterns
Factory Pattern (Creational)
DocumentFactory centralizes the creation of Document objects so the rest of the code doesn't need to know how a Document is built.
Proxy Pattern (Structural)
OCRProxy wraps the real OCR service to add input validation, lazy initialization, and performance logging without modifying the original class.
Tech Stack

Java 17
Spring Boot
Spring Data JPA + H2 Database
Bootstrap 5
Tesseract OCR (Tess4J)

Running the project
Clone the repo and open it in IntelliJ:
bashgit clone https://github.com/cpit252-spring-26-IT1/project-remindme-1.git
Then run RemindMeApplication.java and open http://localhost:8080 in your browser.
You can also download the latest .jar from the Releases page and run it with:
bashjava -jar RemindMe-0.0.1-SNAPSHOT.jar
Project Structure
src/main/java/kau/RemindMe/
├── controller/    REST endpoints
├── factroy/       Factory Pattern
├── model/         Document entity
├── repository/    Database access
└── service/       Business logic + OCR (Proxy Pattern)
