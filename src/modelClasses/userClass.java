package modelClasses;

import java.time.LocalDateTime;

public class userClass {
    private String username;
    private int userId;

    /**
     * Constructor for User
     * @param username username
     */
    public userClass(String username) {
        this.username = username;

    }

    /**
     * Getter for user
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username
     * @param username username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter for userId
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for userId
     * @param userId userId to set.
     */
    public void getUserId(int userId) {
        this.userId = userId;
    }

    public appointmentClass[] getAppointments() {
        return new appointmentClass[0];
    }
}
