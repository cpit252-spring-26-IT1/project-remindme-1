package kau.RemindMe.service;

import kau.RemindMe.factroy.DocumentFactory;
import kau.RemindMe.model.Document;
import kau.RemindMe.observer.DocumentSubject;
import kau.RemindMe.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repo;
    private final DocumentSubject subject;

    public DocumentServiceImpl(DocumentRepository repo, DocumentSubject subject) {
        this.repo = repo;
        this.subject = subject;
    }

    @Override
    public Document addDocument(String name, String type, LocalDate expiry, String email, String owner, String category) {
        Document doc = DocumentFactory.createDocument(name, type, expiry, email, owner, category);
        doc = repo.save(doc);
        subject.notifyRegistered(doc);
        return doc;
    }

    @Override
    public Document updateDocument(Long id, String name, String type, LocalDate expiry, String email, String owner, String category) {
        Document existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        existing.setDocumentName(name);
        existing.setDocumentType(type);
        existing.setExpiryDate(expiry);
        existing.setUserEmail(email);
        existing.setOwnerName(owner);
        existing.setCategory(category);
        return repo.save(existing);
    }

    @Override
    public List<Document> getAllDocuments(String sortBy, String ownerFilter, String userEmail) {

        List<Document> docs = repo.findByUserEmail(userEmail);

        if (ownerFilter != null && !ownerFilter.isEmpty()) {
            docs = docs.stream()
                    .filter(d -> d.getOwnerName().toLowerCase().contains(ownerFilter.toLowerCase()))
                    .toList();
        }

        if (sortBy != null && !sortBy.equals("default")) {
            if ("expiryDate".equals(sortBy)) {
                docs = docs.stream().sorted((d1, d2) -> d1.getExpiryDate().compareTo(d2.getExpiryDate())).toList();
            } else if ("documentName".equals(sortBy)) {
                docs = docs.stream().sorted((d1, d2) -> d1.getDocumentName().compareToIgnoreCase(d2.getDocumentName())).toList();
            } else if ("category".equals(sortBy)) {
                docs = docs.stream().sorted((d1, d2) -> d2.getCategory().compareToIgnoreCase(d1.getCategory())).toList();
            }
        }
        return docs;
    }

    @Override
    public long daysUntilExpiry(Document doc) {
        return ChronoUnit.DAYS.between(LocalDate.now(), doc.getExpiryDate());
    }

    @Override
    public Document getDocumentById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public void deleteDocument(Long id) {
        repo.deleteById(id);
    }
}
