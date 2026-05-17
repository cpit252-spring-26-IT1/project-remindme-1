package kau.RemindMe.service;

import kau.RemindMe.factroy.DocumentFactory;
import kau.RemindMe.model.Document;
import kau.RemindMe.observer.DocumentSubject;
import kau.RemindMe.repository.DocumentRepository;
import kau.RemindMe.security.EncryptionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repo;
    private final DocumentSubject subject;
    private final EncryptionService encryptionService;

    public DocumentServiceImpl(DocumentRepository repo, DocumentSubject subject, EncryptionService encryptionService) {
        this.repo = repo;
        this.subject = subject;
        this.encryptionService = encryptionService;
    }

    private Document encrypt(Document doc) {
        doc.setDocumentName(encryptionService.encrypt(doc.getDocumentName()));
        doc.setOwnerName(encryptionService.encrypt(doc.getOwnerName()));
        doc.setUserEmail(encryptionService.encrypt(doc.getUserEmail()));
        return doc;
    }

    private Document decrypt(Document doc) {
        doc.setDocumentName(encryptionService.decrypt(doc.getDocumentName()));
        doc.setOwnerName(encryptionService.decrypt(doc.getOwnerName()));
        doc.setUserEmail(encryptionService.decrypt(doc.getUserEmail()));
        return doc;
    }

    @Override
    public Document addDocument(String name, String type, LocalDate expiry, String email, String owner, String category) {
        Document doc = DocumentFactory.createDocument(name, type, expiry, email, owner, category);
        encrypt(doc);
        doc = repo.save(doc);
        decrypt(doc);
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
        encrypt(existing);
        return decrypt(repo.save(existing));
    }

    @Override
    public List<Document> getAllDocuments(String sortBy, String ownerFilter, String userEmail) {

        List<Document> docs = repo.findByUserEmail(encryptionService.encrypt(userEmail));
        docs.forEach(this::decrypt);

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
        return decrypt(repo.findById(id).orElseThrow());
    }

    @Override
    public void deleteDocument(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Document> findAllDocuments() {
        List<Document> docs = repo.findAll();
        docs.forEach(this::decrypt);
        return docs;
    }
}
