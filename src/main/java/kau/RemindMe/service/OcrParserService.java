package kau.RemindMe.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrParserService {

    public OcrFormResponse parseDocumentText(String rawText) {
        OcrFormResponse response = new OcrFormResponse();
        String cleanText = rawText.toUpperCase().replaceAll("\\s+", " ");

        // --- 1. DETECTION LOGIC ---

        // Check for Bank Card (Keywords)
        if (cleanText.contains("MADA") || cleanText.contains("DEBIT") || cleanText.contains("SNB")) {
            processBankCard(rawText, cleanText, response);
        }
        // Check for University Card (Look for the 7-digit ID pattern)
        else if (cleanText.matches(".*\\b\\d{7}\\b.*")) {
            processUniversityCard(cleanText, response);
        }
        else {
            response.setDocumentType("General");
        }

        return response;
    }

    private void processBankCard(String rawText, String cleanText, OcrFormResponse response) {
        response.setDocumentType("Bank Card");

        // Expiry Date (MM/YY)
        Pattern expiryPattern = Pattern.compile("(\\d{2})\\s*/\\s*(\\d{2,4})");
        Matcher matcher = expiryPattern.matcher(cleanText);
        if (matcher.find()) {
            String year = matcher.group(2).length() == 2 ? "20" + matcher.group(2) : matcher.group(2);
            response.setExpiryDate(year + "-" + matcher.group(1) + "-01");
        }

        // Name Extraction
        String ownerName = null;
        String[] lines = rawText.split("\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            String lettersOnly = lines[i].replaceAll("[^A-Z\\s]", "").trim();
            if (lettersOnly.length() > 5 && !lettersOnly.contains("DEBIT") && !lettersOnly.contains("GOLD")) {
                ownerName = lettersOnly;
                break;
            }
        }
        if (ownerName != null) {
            response.setOwnerName(ownerName);
            response.setReminderName("Bank Card For " + ownerName);
        }
    }

    private void processUniversityCard(String cleanText, OcrFormResponse response) {
        response.setDocumentType("University Card");

        // 1. Look for the 4-digit Year (2024)
        // We look for a 4-digit number starting with 20
        Pattern yearPattern = Pattern.compile("\\b(20\\d{2})\\b");
        Matcher yearMatcher = yearPattern.matcher(cleanText);

        if (yearMatcher.find()) {
            String year = yearMatcher.group(1);
            // Default to January 1st as requested: YYYY-01-01
            response.setExpiryDate(year + "-01-01");
        }

        // 2. Look for the 7-digit ID (For reference, though not needed for the form)
        Pattern idPattern = Pattern.compile("\\b(\\d{7})\\b");
        Matcher idMatcher = idPattern.matcher(cleanText);
        String universityId = idMatcher.find() ? idMatcher.group(1) : "";

        // 3. Set standard Reminder Name
        response.setReminderName("University Card For " + universityId);
        response.setOwnerName(""); // Skipping name as it's in Arabic
    }
}