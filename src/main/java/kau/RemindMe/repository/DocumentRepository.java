package kau.RemindMe.repository;
import kau.RemindMe.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCategory(String category);
    List<Document> findByUserEmail(String email);


}
