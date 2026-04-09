package sa.edu.kau.fcit.cpit252.project.model;

public class PassportDocument extends Document {
    public PassportDocument(String ownerName, String expiryDate) {
        super(ownerName, expiryDate);
    }

    @Override
    public String getDocumentType() { return "Passport"; }

    @Override
    public int getReminderDaysBefore() { return 90; }
}