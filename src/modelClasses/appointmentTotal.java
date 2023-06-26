package modelClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class appointmentTotal {
    private static ObservableList<appointmentClass> allAppointment = FXCollections.observableArrayList();

    /**
     * Add appointment into allAppointment list.
     * @param newAppointment appointment to add
     */
    public static void addAppointment(appointmentClass newAppointment) {
        allAppointment.add(newAppointment);
    }

    /**
     * Return all the appointment in the allAppointmentList
     * @return allAppointment list.
     */
    public static ObservableList<appointmentClass> getAllAppointment() {
        return allAppointment;
    }

    /**
     * Updates the appointment at a certain index of the allAppointment list
     * @param index index of the appointment to be updated
     * @param selectedAppointment appointment to be updated.
     */
    public static void updateList(int index, appointmentClass selectedAppointment) {
        allAppointment.set(index, selectedAppointment);
    }

    /**
     * Deletes an appointment from the list
     * @param selectedAppointment appointment to delete
     * @return true/false Deletes the appointment.
     */
    public static boolean deleteAppointment(appointmentClass selectedAppointment) {
        return allAppointment.remove(selectedAppointment);
    }

    /**
     * Finds the index of a certain appointment in the alAppointment list.
     * @param selectedAppointment
     * @return
     */
    public static int indexOf(appointmentClass selectedAppointment) {
        int index = 0;
        int size = allAppointment.size();

        for(index = 0; index<size; index++){
            if(selectedAppointment.equals(allAppointment.get(index))){
                return index;
            }
        }
        return index;
    }
}
