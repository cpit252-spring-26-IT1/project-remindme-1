package kau.RemindMe.service;

public class OCRProxy implements OCRService {

    private RealOCRService realOCR;
    private String tessdataPath;

    public OCRProxy(String tessdataPath) {
        this.tessdataPath = tessdataPath;
    }

    @Override
    public String extractText(String imagePath) {

        // =========================
        //  VALIDATION LAYER to make sure it is not empty
        // =========================

        if (imagePath == null || imagePath.isEmpty()) {
            return "Error: image path is null or empty";
        }

        java.io.File file = new java.io.File(imagePath);

        if (!file.exists()) {
            return "Error: file does not exist -> " + imagePath;
        }

        if (!file.isFile()) {
            return "Error: path is not a valid file -> " + imagePath;
        }

        String lower = file.getName().toLowerCase();
        if (!(lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg"))) {
            return "Error: unsupported file format -> " + file.getName();
        }

        // =========================
        // to only intialize if needed
        // =========================

        if (realOCR == null) {
            System.out.println("Initializing OCR engine...");
            realOCR = new RealOCRService(tessdataPath);
        }

        // =========================
        //  PERFORMANCE LOGGING tell me how long it has taken to do the OCR
        // =========================

        long startTime = System.currentTimeMillis();

        String result = realOCR.extractText(imagePath);

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        System.out.println("OCR processed file: " + file.getName());
        System.out.println("Processing time: " + duration + " ms");

        // =========================
        // RETURN RESULT
        // =========================

        return result;
    }
}