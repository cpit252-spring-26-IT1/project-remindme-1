package kau.RemindMe.service;

import net.sourceforge.tess4j.Tesseract;
import javax.imageio.ImageIO;
import java.io.File;

public class RealOCRService implements OCRService {

    private final Tesseract tesseract;

    public RealOCRService(String tessdataPath) {
        this.tesseract = new Tesseract();
        this.tesseract.setDatapath(tessdataPath);
        this.tesseract.setLanguage("eng");
    }

    @Override
    public String extractText(File file) {
        try {

            return tesseract.doOCR(ImageIO.read(file));
        } catch (Exception e) {
            throw new RuntimeException("Tesseract engine processing failed", e);
        }
    }
}