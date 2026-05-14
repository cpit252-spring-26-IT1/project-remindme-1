package kau.RemindMe.email;

import kau.RemindMe.model.Document;

public interface EmailService {
    void sendConfirmationEmail(Document document);
    void sendReminderEmail(Document document, int daysLeft);
}
