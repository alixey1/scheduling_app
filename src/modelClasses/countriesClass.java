package modelClasses;

public class countriesClass {
    private int countryID;
    private String countryName;

    /**
     *
     * @param countryID
     * @param countryName
     */
    public void Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     *
     * @return countryID
     */
    public int getCountryID() {

        return countryID;
    }

    /**
     *
     * @return countryName
     */
    public String getCountryName() {

        return countryName;
    }
}
