# RemindMe: Smart Document Expiry Reminder System

## Description
A Java application that helps users track and manage the expiration dates of important documents such as passports, driving licenses, and national IDs.

## Team Members
- Abdulaziz Melebari (ID: 2338705)
- Assaf Al-Awaji (ID: 2339019)
- Mohammad Alharbi (ID: 2337452)

## Features
- Track expiration dates for Passport, Driving License, and National ID
- Factory Method design pattern for creating document objects
- Smart reminders based on document type (90 days for passport, 30 days for others)

## Design Pattern: Factory Method
The `DocumentFactory` class creates different document types without exposing the creation logic to the client.

## Project Structure
- `factory/DocumentFactory.java` - Factory Method implementation
- `model/Document.java` - Abstract base class
- `model/PassportDocument.java` - Passport document type
- `model/DrivingLicenseDocument.java` - Driving license document type
- `model/NationalIDDocument.java` - National ID document type
- `App.java` - Main entry point

## Usage

To build and run the app:

```shell
.\mvnw package -DskipTests
java -jar target/course-project-1.0-SNAPSHOT.jar
```

## Expected Output

=== RemindMe System ===
Passport | Owner: Ahmed Ali | Expires: 2026-08-15
Reminder: 90 days before expiry
Driving License | Owner: Sara Khalid | Expires: 2025-12-01
Reminder: 30 days before expiry
National ID | Owner: Omar Hassan | Expires: 2027-03-20
Reminder: 30 days before expiry

## License
MIT