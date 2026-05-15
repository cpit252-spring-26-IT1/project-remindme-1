package kau.RemindMe.service;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OcrFormResponse {
    private String documentType;
    private String expiryDate;
    private String ownerName;
    private String reminderName;
}

