package kau.RemindMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import kau.RemindMe.service.OCRService;
import kau.RemindMe.service.OCRProxy;

@SpringBootApplication
public class RemindMeApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RemindMeApplication.class, args);
        // Mac support,with the already built windows.
        String os = System.getProperty("os.name").toLowerCase(); //gets operating system windows or mac.
        String path;
        if (os.contains("win")) {   //windows operating system
            path = "C:\\Program Files\\Tesseract-OCR\\tessdata";
        } else if (os.contains("mac")) { //mac operating system
            path = "/opt/homebrew/share/tessdata"; //mac brew
        } else {
            path = "/usr/share/tesseract-ocr/5/tessdata"; //mac port
        }
        //String path = "C:\\Program Files\\Tesseract-OCR\\tessdata";// old hard coded path (only runs on windows)
        OCRService ocr = new OCRProxy(path);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] images = resolver.getResources("classpath:images/*.png");

        System.out.println("--- OCR Results ---");

        for (Resource image : images) {
            String result = ocr.extractText(image.getFile().getPath());
            //System.out.println("File: " + image.getFilename());
            System.out.println(result);
            System.out.println("----------------------");
        }
    }
}