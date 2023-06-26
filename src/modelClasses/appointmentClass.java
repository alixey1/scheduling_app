package modelClasses;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class appointmentClass {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    String startDate;
    String endDate;

    String createDate;
    String createBy;
    String lastUpdateDate;
    String lastUpdateBy;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    int customerId;
    int userId;
    int contactId;

    String contactName;

    /**
     * Constructor for the appointment class
     * @param appointmentId appointment id
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startDate startDate
     * @param endDate endDate
     * @param createDate createDate
     * @param createBy createBy
     * @param lastUpdateDate lastUpdateDate
     * @param lastUpdateBy lastUpdateBy
     * @param customerId customerId
     * @param userId userId
     * @param contactId contactId
     */
    public appointmentClass(int appointmentId, String title, String description, String location, String type, String startDate, String endDate,
                       String createDate, String createBy, String lastUpdateDate, String lastUpdateBy, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateBy = lastUpdateBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;

    }
    public appointmentClass(int appointmentId, String title, String description, String location, String type, String startDate, String endDate, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public appointmentClass(String location, String startDate) {
        this.location = location;
        this.startDate = startDate;
    }


    public appointmentClass() {
    }



    /**
     * Getter method for contact name
     * @return the contactname
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for contact name
     * @param contactName set the contactname variable
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for appointmentId
     * @return the appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Setter for appointmentId
     * @param appointmentId appointmentId to set.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * getter for title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The getter for the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for location
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for location
     * @param location location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for startDate
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * setter for StartDate
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter for endDate
     * @return endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Setter for endDate
     * @param endDate endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter for createDate
     * @return createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Setter for createDate
     * @param createDate createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for createBy
     * @return createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Setter for createBy
     * @param createBy createBy to set
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Getter for lastupdatedate
     * @return lastUpdateDate
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Setter for lastUpdateDate
     * @param lastUpdateDate lastUpdateDate to set.
     */
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Getter for lastUpdateBy
     * @return lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Setter for lastUpdateBy
     * @param lastUpdateBy lastUpdateBy to set.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Getter for CustomerId
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter for CustomerId
     * @param customerId customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for userId
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for userId
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for contactId
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Setter for contactid
     * @param contactId contactid to set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public Timestamp getStart() {
        return null;
    }
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

}
