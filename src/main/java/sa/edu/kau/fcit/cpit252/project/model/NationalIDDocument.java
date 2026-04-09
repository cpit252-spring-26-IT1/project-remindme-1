package sa.edu.kau.fcit.cpit252.project.model;

public class NationalIDDocument extends Document {
    public NationalIDDocument(String ownerName, String expiryDate) {
        super(ownerName, expiryDate);
    }

    @Override
    public String getDocumentType() { return "National ID"; }

    @Override
    public int getReminderDaysBefore() { return 30; }
}