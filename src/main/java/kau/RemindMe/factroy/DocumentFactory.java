package kau.RemindMe.factroy;

import kau.RemindMe.model.Document;
import java.time.LocalDate;

public class DocumentFactory {
    public static Document createDocument(String name, String type, LocalDate expiry, String email, String owner, String category) {
        Document doc = new Document();
        doc.setDocumentName(name);
        doc.setDocumentType(type);
        doc.setExpiryDate(expiry);
        doc.setUserEmail(email);
        doc.setOwnerName(owner);
        doc.setCategory(category); // "Personal" or "Children"
        return doc;
    }
}