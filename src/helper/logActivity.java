package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class logActivity {
    public class LogActivity {
        private static String filename = "login_activity.txt";
        static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        static LocalDateTime now = LocalDateTime.now();

        /**
         * When run, writes into a txt file regardless of whether the login passed or failed.
         *
         * @param user username to be entered
         * @param pass test if the login passed or failed.
         */
        public static void logon(String user, boolean pass) {

            try (
                    FileWriter fw = new FileWriter(filename, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                if (pass) {
                    out.println("User " + user + " has logged in on " + dtf.format(now));
                } else {
                    out.println("User " + user + " failed to login on " + dtf.format(now));
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }

}
