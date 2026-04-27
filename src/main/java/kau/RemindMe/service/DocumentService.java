package kau.RemindMe.service;

import kau.RemindMe.model.Document;

import java.time.LocalDate;
import java.util.List;

public interface DocumentService {
    Document addDocument(String name, String type, LocalDate expairy,String email,String owner,String category);
    List<Document> getAllDocuments();
    long daysUntilExpiry(Document doc);
    Document getDocumentById(Long id);
}
