package kau.RemindMe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class OcrProxyTest {

    private OCRProxy ocrProxy;
    private String dummyTessdataPath;

    @BeforeEach
    void setUp() {
        // Point to a safe workspace path location for testing instantiation
        dummyTessdataPath = System.getProperty("user.dir");
        ocrProxy = new OCRProxy(dummyTessdataPath, "test_card.png");
    }

    @Test
    @DisplayName("Proxy should catch native initialization exceptions gracefully and return an empty string")
    void testProxyHandlesExceptionGracefully() {
        // Arrange: Create a temporary blank text file that will fail Tesseract's image reader
        File nonImageFile = new File(dummyTessdataPath, "invalid_file.png");

        // Act & Assert
        assertDoesNotThrow(() -> {
            String result = ocrProxy.extractText(nonImageFile);
            // The proxy catches the Tesseract crash internally and returns an empty string safely
            assertEquals("", result, "Should return empty string on engine processing fault instead of crashing server");
        });
    }
}