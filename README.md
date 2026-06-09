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
## Application Walkthrough

<div align="center">
  <img src="https://github.com/user-attachments/assets/955467ef-0e96-4767-a41d-813e8cf99a38" width="600" alt="Login Page Screen"/>
  <br>
  <p align="left" style="max-width: 600px; margin: 10px auto;"><strong>Step 1: Login Page</strong> — Secure authentication portal allowing registered users to access their private document vault.</p>
</div>

---

<div align="center">
  <img src="https://github.com/user-attachments/assets/5a3615b4-4ff7-4349-b074-0d8706cb295e" width="600" alt="Main Dashboard Screen"/>
  <br>
  <p align="left" style="max-width: 600px; margin: 10px auto;"><strong>Step 2: Main Dashboard</strong> — Displays all active document reminders in a single unified view, categorized with dynamic status badges.</p>
</div>

---

<div align="center">
  <img src="https://github.com/user-attachments/assets/61970b3a-0ea4-4444-b97c-f2f13c118b69" width="600" alt="OCR Extraction Interface Screen"/>
  <br>
  <p align="left" style="max-width: 600px; margin: 10px auto;"><strong>Step 3: OCR Extraction Interface</strong> — Uploading a document image automatically triggers the underlying Tesseract engine to parse text and autofill form details.</p>
</div>

---

<div align="center">
  <img src="https://github.com/user-attachments/assets/7fd99cde-cd17-49de-b604-0a4f215dad52" width="600" alt="Saved Reminder Operations Screen"/>
  <br>
  <p align="left" style="max-width: 600px; margin: 10px auto;"><strong>Step 4: Saved Reminder Operations</strong> — Confirms the record is successfully saved in the system, where it can now be modified, updated, or deleted as needed.</p>
</div>

---

<div align="center">
  <img src="https://github.com/user-attachments/assets/7d2442ab-2930-40af-bc44-1a42c6a741fb" width="600" alt="Automated Email Notifications Screen"/>
  <br>
  <p align="left" style="max-width: 600px; margin: 10px auto;"><strong>Step 5: Automated Email Notifications</strong> — A confirmation email is instantly dispatched upon creation. Automated expiration alerts follow systematically at the 30, 14, and 2-day marks.</p>
</div>

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
---

## AI Utilization Disclosure

This section outlines how AI was used as a development assistant during the RemindMe project.

AI tools were leveraged to improve development efficiency and accelerate troubleshooting in the following areas:

### 1. Frontend Development & UI Engineering
AI assisted in structuring the dashboard layout, applying CSS styles, and writing JavaScript functions responsible for client-side data rendering, table state management, and dynamic status badge logic based on days remaining until expiry.

### 2. Debugging & Troubleshooting
AI acted as a debugging assistant to help isolate runtime exceptions, resolve Spring Boot configuration issues, and refactor code during architectural changes.

**Statement of Academic Integrity**
All core backend architecture, database design, service layer logic, and Design Pattern implementations including the Factory, Proxy, and Observer patterns were independently conceptualized and implemented by the development team to meet the course requirements.
