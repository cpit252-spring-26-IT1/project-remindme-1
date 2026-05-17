package kau.RemindMe.email;

import kau.RemindMe.model.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendConfirmationEmail(Document document) {
        Context context = new Context();
        context.setVariable("documentName", document.getDocumentName());
        context.setVariable("expiryDate", document.getExpiryDate());
        context.setVariable("ownerName", document.getOwnerName());

        String html = templateEngine.process("confirmation-email", context);
        sendEmail(document.getUserEmail(), "Document Registered - " + document.getDocumentName(), html);
    }

    @Override
    public void sendReminderEmail(Document document, int daysLeft) {
        Context context = new Context();
        context.setVariable("documentName", document.getDocumentName());
        context.setVariable("expiryDate", document.getExpiryDate());
        context.setVariable("ownerName", document.getOwnerName());
        context.setVariable("daysLeft", daysLeft);

        String html = templateEngine.process("reminder-email", context);
        sendEmail(document.getUserEmail(), "Reminder: " + document.getDocumentName() + " expires in " + daysLeft + " days", html);
    }

    private void sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + to + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
