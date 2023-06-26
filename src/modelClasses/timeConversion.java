package modelClasses;
import java.time.*;


public class timeConversion {
    /**
     * Conversion from the local system to UTC.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @return
     */
    public static String localToUTC(int year, int month, int day, int hour, int min) {
        LocalDate myLD = LocalDate.of(year, month, day);
        LocalTime myLt =  LocalTime.of(hour, min);
        LocalDateTime myLDT = LocalDateTime.of(myLD, myLt);
        ZoneId myZoneId = ZoneId.systemDefault();
        ZonedDateTime myZDT = ZonedDateTime.of(myLDT, myZoneId);

        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), utcZoneId);
        String dateTime = utcZDT.toLocalDate().toString() + " " + utcZDT.toLocalTime().toString();
        return dateTime + ":00";
    }

    /**
     * Converts from UTC to local time.
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @return
     */
    public static String utcToLocal(int year, int month, int day, int hour, int min) {
        LocalDate utcLD = LocalDate.of(year, month, day);
        LocalTime utcLT =  LocalTime.of(hour, min);
        LocalDateTime utcLDT = LocalDateTime.of(utcLD, utcLT);
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.of(utcLDT, utcZoneId);

        ZoneId myZoneId = ZoneId.systemDefault();
        ZonedDateTime localZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), myZoneId);
        String dateTime = localZDT.toLocalDate().toString() + " " + localZDT.toLocalTime().toString();

        return dateTime + ":00";
    }

    /**
     * Converts local time to EST
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @return
     */
    public static int localToEST(int year, int month, int day, int hour, int min) {
        LocalDate myLD = LocalDate.of(year, month, day);
        LocalTime myLt =  LocalTime.of(hour, min);
        LocalDateTime myLDT = LocalDateTime.of(myLD, myLt);
        ZoneId myZoneId = ZoneId.systemDefault();
        ZonedDateTime myZDT = ZonedDateTime.of(myLDT, myZoneId);
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZonedDateTime estZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), estZoneId);
        int estHour = estZDT.getHour();
        return estHour;
    }
}


