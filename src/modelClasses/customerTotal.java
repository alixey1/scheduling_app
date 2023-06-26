package modelClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class customerTotal {
    private static ObservableList<customerClass> allCustomer = FXCollections.observableArrayList();

    /**
     * Adds customer to allCustomer lsit.
     * @param newCustomer customer to add
     */
    public static void addCustomer(customerClass newCustomer) {
        allCustomer.add(newCustomer);
    }

    /**
     * Returns all customer in allcustomer list
     * @return all customer
     */
    public static ObservableList<customerClass> getAllCustomer() {
        return allCustomer;
    }

    /**
     * Updates a customer in the allCustomer list by uindex
     * @param index index to update
     * @param selectedCustomer customer to update
     */
    public static void updateList(int index, customerClass selectedCustomer) {
        allCustomer.set(index, selectedCustomer);
    }

    /**
     * Delete customer from list
     * @param selectedCustomer customer to be deleted
     * @return true/false deletes the customer from list
     */
    public static boolean deleteCustomer(customerClass selectedCustomer) {
        return allCustomer.remove(selectedCustomer);
    }

    /**
     * Get an individual customer by their id/
     * @param id id of the customer
     * @return the customer.
     */
    public static customerClass getCustomer(int id) {
        customerClass customerHolder = null;
        for (customerClass customer : allCustomer) {
            if(customer.getId() == id) {
                customerHolder = customer;
            }

        }
        return customerHolder;
    }
}
