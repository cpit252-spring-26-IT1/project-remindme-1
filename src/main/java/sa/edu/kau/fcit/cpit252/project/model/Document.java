package sa.edu.kau.fcit.cpit252.project.model;

public abstract class Document {
    protected String ownerName;
    protected String expiryDate;

    public Document(String ownerName, String expiryDate) {
        this.ownerName = ownerName;
        this.expiryDate = expiryDate;
    }

    public abstract String getDocumentType();
    public abstract int getReminderDaysBefore();

    @Override
    public String toString() {
        return getDocumentType() + " | Owner: " + ownerName + " | Expires: " + expiryDate;
    }
}