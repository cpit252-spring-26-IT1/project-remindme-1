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
    public List<Document> getAllDocuments(String sortBy, String ownerFilter) {
        List<Document> docs;

        if (ownerFilter != null && !ownerFilter.isEmpty()) {
            docs = repo.findByOwnerName(ownerFilter);
        } else {
            docs = repo.findAll();
        }

        if (sortBy != null && !sortBy.isEmpty() && !sortBy.equals("default")) {
            Sort.Direction direction = Sort.Direction.ASC;
            if ("category".equalsIgnoreCase(sortBy)) {
                direction = Sort.Direction.DESC;
            }
            return repo.findAll(Sort.by(direction, sortBy));
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
