package kau.RemindMe.service;

import kau.RemindMe.factroy.DocumentFactory;
import kau.RemindMe.model.Document;
import kau.RemindMe.repository.DocumentRepository;
import org.springframework.stereotype.Service;

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
    public List<Document> getAllDocuments() {
        return repo.findAll();
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
