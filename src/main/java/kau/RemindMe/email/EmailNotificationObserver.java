package kau.RemindMe.email;

import jakarta.annotation.PostConstruct;
import kau.RemindMe.model.Document;
import kau.RemindMe.observer.DocumentObserver;
import kau.RemindMe.observer.DocumentSubject;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements DocumentObserver {

    private final EmailService emailService;
    private final DocumentSubject documentSubject;

    public EmailNotificationObserver(EmailService emailService, DocumentSubject documentSubject) {
        this.emailService = emailService;
        this.documentSubject = documentSubject;
    }

    @PostConstruct
    public void register() {
        documentSubject.addObserver(this);
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
