package sa.edu.kau.fcit.cpit252.project.factory;

import sa.edu.kau.fcit.cpit252.project.model.Document;
import sa.edu.kau.fcit.cpit252.project.model.PassportDocument;
import sa.edu.kau.fcit.cpit252.project.model.DrivingLicenseDocument;
import sa.edu.kau.fcit.cpit252.project.model.NationalIDDocument;

public class DocumentFactory {
    public static Document createDocument(String type, String ownerName, String expiryDate) {
        switch (type.toLowerCase()) {
            case "passport":
                return new PassportDocument(ownerName, expiryDate);
            case "driving license":
                return new DrivingLicenseDocument(ownerName, expiryDate);
            case "national id":
                return new NationalIDDocument(ownerName, expiryDate);
            default:
                throw new IllegalArgumentException("Unknown document type: " + type);
        }
    }
}