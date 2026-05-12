package kau.RemindMe.observer;

import jakarta.annotation.PostConstruct;
import kau.RemindMe.model.Document;
import kau.RemindMe.service.EmailService;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements DocumentObserver {

    private final EmailService emailService;
    private final DocumentSubject subject;

    public EmailNotificationObserver(EmailService emailService, DocumentSubject subject) {
        this.emailService = emailService;
        this.subject = subject;
    }

    @PostConstruct
    public void register() {
        subject.addObserver(this);
    }

    @Override
    public void onDocumentRegistered(Document document) {
        emailService.sendConfirmationEmail(document);
    }

    @Override
    public void onDocumentExpiring(Document document, int daysLeft) {
        emailService.sendReminderEmail(document, daysLeft);
    }
}
