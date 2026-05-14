package kau.RemindMe.scheduler;

import kau.RemindMe.model.Document;
import kau.RemindMe.observer.DocumentSubject;
import kau.RemindMe.service.DocumentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Component
public class ReminderScheduler {

    private static final Set<Integer> ALERT_THRESHOLDS = Set.of(30, 14, 7, 2);

    private final DocumentService documentService;
    private final DocumentSubject documentSubject;

    public ReminderScheduler(DocumentService documentService, DocumentSubject documentSubject) {
        this.documentService = documentService;
        this.documentSubject = documentSubject;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void checkExpiringDocuments() {
        LocalDate today = LocalDate.now();

        documentService.findAllDocuments()
                .forEach(doc -> notifyIfExpiringSoon(doc, today));
    }

    private void notifyIfExpiringSoon(Document doc, LocalDate today) {
        int daysLeft = (int) ChronoUnit.DAYS.between(today, doc.getExpiryDate());

        if (ALERT_THRESHOLDS.contains(daysLeft)) {
            documentSubject.notifyExpiring(doc, daysLeft);
        }
    }
}
