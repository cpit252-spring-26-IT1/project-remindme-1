package sa.edu.kau.fcit.cpit252.project;

import sa.edu.kau.fcit.cpit252.project.factory.DocumentFactory;
import sa.edu.kau.fcit.cpit252.project.model.Document;

public class App {
    public static void main(String[] args) {
        System.out.println("=== RemindMe System ===");

        Document doc1 = DocumentFactory.createDocument("passport", "Ahmed Ali", "2026-08-15");
        Document doc2 = DocumentFactory.createDocument("driving license", "Sara Khalid", "2025-12-01");
        Document doc3 = DocumentFactory.createDocument("national id", "Omar Hassan", "2027-03-20");

        System.out.println(doc1);
        System.out.println("Reminder: " + doc1.getReminderDaysBefore() + " days before expiry");

        System.out.println(doc2);
        System.out.println("Reminder: " + doc2.getReminderDaysBefore() + " days before expiry");

        System.out.println(doc3);
        System.out.println("Reminder: " + doc3.getReminderDaysBefore() + " days before expiry");
    }
}