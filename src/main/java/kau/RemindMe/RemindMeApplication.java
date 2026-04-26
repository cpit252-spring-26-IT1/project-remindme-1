package kau.RemindMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
public class RemindMeApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RemindMeApplication.class, args);

        String path = "C:\\Program Files\\Tesseract-OCR\\tessdata";
        OCRManager ocr = new OCRManager(path);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] images = resolver.getResources("classpath:images/*.png");

        System.out.println("--- OCR Results ---");

        for (Resource image : images) {
            String result = ocr.extractText(image.getFile().getAbsolutePath(), "ara+eng");

            System.out.println("File: " + image.getFilename());
            System.out.println(result);
            System.out.println("----------------------");
        }
    }
}