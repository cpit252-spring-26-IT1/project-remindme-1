package kau.RemindMe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OcrParserServiceTest {

    private OcrParserService ocrParserService;

    @BeforeEach
    void setUp() {
        ocrParserService = new OcrParserService();
    }

    @Test
    @DisplayName("Should successfully parse a valid Bank Card OCR string")
    void testParseValidBankCard() {
        // Arrange: Use your exact confirmed functional OCR text string output
        String mockBankCardOcr = "platinum debit n ©\n" +
                "isiaall plug! SNB yal\n" +
                "Wessam Gold\n" +
                "=\n" +
                "= 5 ») mada\n" +
                "1234 5678 9102 3254\n" +
                "» ere 110/26\n" +
                "ABDULLAH KHALED\n" +
                "+";

        // Act
        OcrFormResponse response = ocrParserService.parseDocumentText(mockBankCardOcr);

        // Assert
        assertNotNull(response, "Response object should not be null");
        assertEquals("Bank Card", response.getDocumentType(), "Should identify document type as Bank Card");
        assertEquals("2026-10-01", response.getExpiryDate(), "Should parse and format expiry date to 2026-10-01");
        assertEquals("ABDULLAH KHALED", response.getOwnerName(), "Should extract owner name correctly");
        assertEquals("Bank Card For ABDULLAH KHALED", response.getReminderName(), "Should generate correct reminder name pattern");
    }

    @Test
    @DisplayName("Should successfully parse a messy Arabic University Card OCR string via pattern matching")
    void testParseValidUniversityCard() {
        // Arrange: Use your exact confirmed university card OCR text string
        String mockUniversityCardOcr = "Finan ty i a\n" +
                "mules 4\n" +
                "—_= juisilae Loli dzols\n" +
                "rs) 2237823: autall i 3\n" +
                "gotegle teas palace Saath | pall\n" +
                "pepeesce|) Cn yr come\n" +
                "te auttl 5S all: | Jal se\n" +
                "al i re | f Sy)\n" +
                "2024 Pais\n" +
                "i. ae";

        // Act
        OcrFormResponse response = ocrParserService.parseDocumentText(mockUniversityCardOcr);

        // Assert
        assertNotNull(response, "Response object should not be null");
        assertEquals("University Card", response.getDocumentType(), "Should identify card type as University Card via 7-digit check");
        assertEquals("2024-01-01", response.getExpiryDate(), "Should fall back default to 2024-01-01");
        assertEquals("", response.getOwnerName(), "Owner name should remain blank for Arabic cards");
        assertEquals("University Card For 2237823", response.getReminderName(), "Reminder name should include the matched ID");
    }

    @Test
    @DisplayName("Should degrade gracefully to General document type when provided unmatching text parameters")
    void testParseGeneralDocumentFallback() {
        // Arrange
        String unknownText = "This is a random document text string from a store receipt or book page.";

        // Act
        OcrFormResponse response = ocrParserService.parseDocumentText(unknownText);

        // Assert
        assertNotNull(response);
        assertEquals("General", response.getDocumentType(), "Unrecognized templates should be marked as General");
        assertNull(response.getExpiryDate(), "Expiry date should remain null if no date matches found");
        assertNull(response.getOwnerName(), "Owner name should remain null");
        assertNull(response.getReminderName(), "Reminder name should remain null");
    }

    @Test
    @DisplayName("Should handle completely empty strings safely without throwing exceptions")
    void testParseEmptyString() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            OcrFormResponse response = ocrParserService.parseDocumentText("");
            assertNotNull(response);
            assertEquals("General", response.getDocumentType());
        }, "Parsing empty content strings should never cause processing failures");
    }
}