package modelClasses;

public class contactsClass {
    public int contactID;
    public String contactName;
    public String contactEmail;

    public void Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     *
     * @return contactID
     */
    public int getId() {

        return contactID;
    }

    /**
     *
     * @return contactName
     */
    public String getContactName() {

        return contactName;
    }

    /**
     *
     * @return contactEmail
     */
    public String getContactEmail() {

        return contactEmail;
    }
}
