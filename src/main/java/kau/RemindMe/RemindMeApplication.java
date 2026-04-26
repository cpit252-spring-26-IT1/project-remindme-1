package kau.RemindMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RemindMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemindMeApplication.class, args);

        String path = "C:\\Program Files\\Tesseract-OCR\\tessdata";
        OCRManager ocr = new OCRManager(path);


        String myImage = "src/main/resources/images/sample.png";

        // OCR for Arabic and English combined
        String result = ocr.extractText(myImage, "ara+eng");

        System.out.println("--- OCR Results ---");
        System.out.println(result);
	}

}
