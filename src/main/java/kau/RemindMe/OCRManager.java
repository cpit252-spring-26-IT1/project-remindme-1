package kau.RemindMe;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;

public class OCRManager {
    private final Tesseract tesseract;

    public OCRManager(String tessdataPath) {
        this.tesseract = new Tesseract();

        // Ensure the path exists before setting it
        File path = new File(tessdataPath);
        if (!path.exists()) {
            System.err.println("Warning: Tessdata path does not exist: " + tessdataPath);
        }

        this.tesseract.setDatapath(tessdataPath);
    }

    /**
     * Extracts text from an image file.
     * @param imagePath Path to the image file (png, jpg, etc.)
     * @param language Language code (e.g., "eng", "ara", or "eng+ara")
     * @return Extracted text or an error message.
     */
    public String extractText(String imagePath, String language) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                return "Error: Image file not found at " + imagePath;
            }

            this.tesseract.setLanguage(language);
            return this.tesseract.doOCR(imageFile);

        } catch (TesseractException e) {
            return "OCR Error: " + e.getMessage();
        } catch (Exception e) {
            return "General Error: " + e.getMessage();
        }
    }
}