package kau.RemindMe.service;

import kau.RemindMe.factroy.DocumentFactory;
import kau.RemindMe.model.Document;
import kau.RemindMe.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repo;

    public DocumentServiceImpl(DocumentRepository repo) {
        this.repo = repo;
    }


    @Override
    public Document addDocument(String name, String type, LocalDate expairy, String email, String owner, String category) {
        Document doc = DocumentFactory.createDocument(name, type, expairy, email, owner, category);
        return repo.save(doc);
    }

    @Override
    public Document updateDocument(Long id, String name, String type, LocalDate expiry, String email, String owner, String category) {
        Document existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));

        // Update the fields
        existing.setDocumentName(name);
        existing.setDocumentType(type);
        existing.setExpiryDate(expiry);
        existing.setUserEmail(email);
        existing.setOwnerName(owner);
        existing.setCategory(category);

        return repo.save(existing);
    }

    @Override
    public List<Document> getAllDocuments(String sortBy) {
        if (sortBy == null || sortBy.isEmpty() || sortBy.equals("default")) {
            return repo.findAll();
        }
        Sort.Direction direction = Sort.Direction.ASC;

        if ("category".equalsIgnoreCase(sortBy)) {
            direction = Sort.Direction.DESC;
        }
        return repo.findAll(Sort.by(direction, sortBy));
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
    public void deleteDocument(Long id) { repo.deleteById(id); }

}
