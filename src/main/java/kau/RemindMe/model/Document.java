package kau.RemindMe.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data //this automatically creates getters and setters
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String documentName;// Dads Passport or Kids National ID
    private String documentType;
    private LocalDate expiryDate;//The date OCR or the user enters
    private String userEmail;//The date OCR or the user enters
    //family logic
    private String category; // Values: "Personal" or "Children"
    private String ownerName;
}
