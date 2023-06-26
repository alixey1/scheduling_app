package modelClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class customerClass {
    private ObservableList<appointmentClass> assocAppointment =  FXCollections.observableArrayList();
    private int id;
    private String name;
    private String address;
    private String postalCode;

    private String phone;

    private String division;
    private String country;
    private static String fullAddress;
    private String createdDate;
    private String createdBy;
    private String updateDate;
    private String updateBy;

    /**
     * Constructor for Customer class
     * @param id id
     * @param name name
     * @param address address
     * @param postalCode postalCode
     * @param phone phone
     * @param division division
     * @param country country
     * @param createdDate createdDate
     * @param createdBy createdBy
     * @param updateDate updateDate
     * @param updateBy updateBy
     */
    public customerClass(int id, String name, String address, String postalCode, String phone, String division, String country,
                    String createdDate, String createdBy, String updateDate, String updateBy) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updateDate = updateDate;
        this.updateBy = updateBy;
    }

    /**
     * Add associated appointment to the associatedappointment list
     * @param appointment appointment to add
     */
    public void addAssociatedAppointment(appointmentClass appointment) {
        assocAppointment.add(appointment);
    }

    /**
     * Remove an appointment from the assocAppointment list
     * @param appointment appointment to be removed
     */
    public void removeAssociatedAppointment(appointmentClass appointment) {
        assocAppointment.remove(appointment);
    }

    /**
     * Set the full Address
     * @param fullAddress fullAddress to set
     */
    public static void setFullAddress(String fullAddress) {
        customerClass.fullAddress = fullAddress;
    }

    /**
     * Getter for createdDate
     * @return createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Setter for createDate
     * @param createdDate createDate to set.
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Getter for createdBy
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for createdBy
     * @param createdBy createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for updateDate
     * @return updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * Setter for updateDate
     * @param updateDate updateDate to set
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Getter for updateBy
     * @return updateBy
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * Setter for updateBy
     * @param updateBy updateDate to set
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * Getter for the assocAppointment list
     *
     * @return the assocAppointmentList
     */
    public ObservableList<appointmentClass> getAssocAppointment() {
        return assocAppointment;
    }


    /**
     * Getter for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address
     * @param address address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for postal code
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for postal code
     * @param postalCode postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for phone
     * @param phone phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for division
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter to division
     * @param division division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter for country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for country
     * @param country country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for the fulladdress
     * @return full address.
     */
    public String getFullAddress() {
        String address = this.address;
        String division = this.division;
        String country = this.country;
        return address + ", " + division + ", " + country;
    }
    private static customerClass selectedCustomer;


    public static customerClass getSelectedCustomer() {
        return selectedCustomer;
    }

}
