package kau.RemindMe.service;

import kau.RemindMe.model.Document;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendConfirmationEmail(Document document) {
        Context context = new Context();
        context.setVariable("documentName", document.getDocumentName());
        context.setVariable("expiryDate", document.getExpiryDate());
        context.setVariable("ownerName", document.getOwnerName());

        String html = templateEngine.process("confirmation-email", context);
        sendEmail(document.getUserEmail(), "Document Registered - " + document.getDocumentName(), html);
    }

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
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + to + ": " + e.getMessage());
        }
    }
}
