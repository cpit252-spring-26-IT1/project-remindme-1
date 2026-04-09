package sa.edu.kau.fcit.cpit252.project.model;

public class DrivingLicenseDocument extends Document {
    public DrivingLicenseDocument(String ownerName, String expiryDate) {
        super(ownerName, expiryDate);
    }

    @Override
    public String getDocumentType() { return "Driving License"; }

    @Override
    public int getReminderDaysBefore() { return 30; }
}