package kau.RemindMe.controller;

import kau.RemindMe.model.Document;
import kau.RemindMe.service.DocumentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }
    // Get all reminders
    @GetMapping("/documents")
    public List<Document> getAll(){
        return service.getAllDocuments();
    }
    // Add new reminder
    @PostMapping("/documents")
    public Document add(@RequestBody Map<String, String> body) {
        return service.addDocument(
                body.get("documentName"),
                body.get("documentType"),
                LocalDate.parse(body.get("expiryDate")),
                body.get("userEmail"),
                body.get("ownerName"),
                body.get("category")
        );
    }
    // Days until expiry for a document
    @GetMapping("/documents/{id}/days")
    public long days(@PathVariable Long id) {
        Document doc = service.getDocumentById(id);
        return service.daysUntilExpiry(doc);
    }
    @DeleteMapping("/documents/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteDocument(id);
    }

}
