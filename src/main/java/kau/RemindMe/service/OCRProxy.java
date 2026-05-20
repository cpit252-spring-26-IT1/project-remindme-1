package kau.RemindMe.service;

import java.io.File;

public class OCRProxy implements OCRService {

    private RealOCRService realOCR;
    private final String tessdataPath;
    private final String cleanFileName;


    public OCRProxy(String tessdataPath, String cleanFileName) {
        this.tessdataPath = tessdataPath;
        this.cleanFileName = cleanFileName;
    }

    @Override
    public String extractText(File file) {
        if (realOCR == null) {
            System.out.println("[OCR Proxy] Initializing RealOCRService engine instance...");
            realOCR = new RealOCRService(tessdataPath);
        }

        long startTime = System.currentTimeMillis();
        String result;

        try {
            result = realOCR.extractText(file);
        } catch (Exception e) {
            System.err.println("[OCR Proxy Error] Native extraction failed: " + e.getMessage());
            return "";
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;


        //System.out.println("\n=== RAW OCR TEXT ===");
        //System.out.println(result);
        //System.out.println("==============================");


        System.out.println("----------------------------------------");
        System.out.println("OCR Processed File : " + cleanFileName);
        System.out.println("Processing Time    : " + duration + " ms");
        System.out.println("----------------------------------------\n");

        return result;
    }
}