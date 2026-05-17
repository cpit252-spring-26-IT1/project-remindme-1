package kau.RemindMe.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import net.sourceforge.tess4j.Tesseract;
import javax.imageio.ImageIO;

@RestController
@RequestMapping("/api/ocr")
public class OcrApiController {

    // Assuming you have your Tesseract instance setup or injected
    private final OcrParserService ocrParserService = new OcrParserService();

    @PostMapping("/process")
    public ResponseEntity<OcrFormResponse> processImage(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Convert MultipartFile text using your existing Tesseract instance
            // For example's sake, assuming you extract the text string here:
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("mac")) {
                System.setProperty("jna.library.path", "/opt/homebrew/lib");
            }

            Tesseract tesseract = new Tesseract();
            String tessdataPath = os.contains("win")
                    ? "C:\\Program Files\\Tesseract-OCR\\tessdata"
                    : "/opt/homebrew/share/tessdata";
            tesseract.setDatapath(tessdataPath);
            tesseract.setLanguage("eng");

            String rawText = tesseract.doOCR(ImageIO.read(file.getInputStream()));
            System.out.println("=== RAW OCR TEXT ===");
            System.out.println(rawText);
            System.out.println("====================");

            // 2. Parse the text using your service
            OcrFormResponse response = ocrParserService.parseDocumentText(rawText);

            // 3. Return the populated response object as JSON
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}