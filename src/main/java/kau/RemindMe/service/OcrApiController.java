package kau.RemindMe.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@RestController
@RequestMapping("/api/ocr")
public class OcrApiController {

    private final OcrParserService ocrParserService = new OcrParserService();

    @PostMapping("/process")
    public ResponseEntity<OcrFormResponse> processImage(@RequestParam("file") MultipartFile file) {
        File tempFile = null;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("mac")) {
                System.setProperty("jna.library.path", "/opt/homebrew/lib");
            }

            String tessdataPath = os.contains("win")
                    ? "C:\\Program Files\\Tesseract-OCR\\tessdata"
                    : "/opt/homebrew/share/tessdata";

            tempFile = File.createTempFile("ocr-", file.getOriginalFilename());
            file.transferTo(tempFile);


            OCRService ocrService = new OCRProxy(tessdataPath, file.getOriginalFilename());
            String rawText = ocrService.extractText(tempFile);

            OcrFormResponse response = ocrParserService.parseDocumentText(rawText);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}