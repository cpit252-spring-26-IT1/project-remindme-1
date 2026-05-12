package kau.RemindMe.observer;

import kau.RemindMe.model.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentSubject {

    private final List<DocumentObserver> observers = new ArrayList<>();

    public void addObserver(DocumentObserver observer) {
        observers.add(observer);
    }

    public void notifyRegistered(Document document) {
        for (DocumentObserver observer : observers) {
            observer.onDocumentRegistered(document);
        }
    }

    public void notifyExpiring(Document document, int daysLeft) {
        for (DocumentObserver observer : observers) {
            observer.onDocumentExpiring(document, daysLeft);
        }
    }
}
