package kau.RemindMe.observer;

import kau.RemindMe.model.Document;

public interface DocumentObserver {
    void onDocumentRegistered(Document document);
    void onDocumentExpiring(Document document, int daysLeft);
}
