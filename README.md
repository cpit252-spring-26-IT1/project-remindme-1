# RemindMe

A Spring Boot web app that helps users track expiration dates of important documents — passports, national IDs, driving licenses, bank cards, and more.

Built for the CPIT 252 at King Abdulaziz University.

---

## Features

- **User accounts** — Register and log in with BCrypt-hashed passwords
- **Document reminders** — Add, edit, and delete reminders with name, type, owner, expiry date, and category
- **Email notifications** — Confirmation email on document registration; automatic reminders at 30, 14, 7, and 2 days before expiry
- **OCR autofill** — Upload a photo of a bank card or university card and the form fills itself
- **Dashboard** — All reminders in one view with status badges (expired, urgent, upcoming, safe)
- **Sorting & filtering** — Sort by expiry date, name, or category; filter by owner name
- **Family support** — Track documents under Personal or Children categories
- **Encrypted storage** — Sensitive fields (document name, owner name, email) are AES-encrypted in the database

---

## Design Patterns

**Factory Pattern** — `DocumentFactory` centralizes Document creation so the rest of the code never builds one directly.

**Proxy Pattern** — `OCRProxy` wraps `RealOCRService` to add input validation, lazy initialization, and performance logging without touching the original class.

**Observer Pattern** — `DocumentSubject` notifies registered observers (like `EmailNotificationObserver`) when a document is added or expiring, keeping email logic fully decoupled from business logic.

---

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA + H2 (file-based)
- Thymeleaf
- Bootstrap 5
- Tesseract OCR (Tess4J)
- JavaMail (Gmail SMTP)

---

## Prerequisites

- **Java 17**
- **Tesseract OCR**
  - Mac: `brew install tesseract`
  - Windows: [Download installer](https://github.com/UB-Mannheim/tesseract/wiki) and install to `C:\Program Files\Tesseract-OCR`
- **Gmail App Password** for email notifications ([how to get one](https://support.google.com/accounts/answer/185833))

---

## Running the Project

### From IntelliJ

Clone the repo and open it in IntelliJ:

```bash
git clone https://github.com/cpit252-spring-26-IT1/project-remindme-1.git
```

Set the project SDK to **Java 17**, then run `RemindMeApplication.java`.

Open `http://localhost:8080` in your browser.

### From terminal (Mac)

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/<your-java-17>/Contents/Home ./mvnw spring-boot:run
```

### From JAR

Download the latest `.jar` from the [Releases page](../../releases) and run:

```bash
java -jar RemindMe-0.0.1-SNAPSHOT.jar
```

---

## Project Structure

```
src/main/java/kau/RemindMe/
├── config/        App configuration (PasswordEncoder bean)
├── controller/    REST endpoints + view routing
├── email/         Email service and observer
├── factroy/       Factory Pattern — DocumentFactory
├── model/         Document and User entities
├── observer/      Observer Pattern — DocumentSubject + DocumentObserver
├── repository/    Database access
├── scheduler/     Daily reminder job (runs at 8 AM)
├── security/      EncryptionService interface + AES implementation
└── service/       Business logic + OCR (Proxy Pattern)
```
