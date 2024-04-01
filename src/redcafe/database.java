
package redcafe;

import java.sql.Connection;
import java.sql.DriverManager;


public class database {
    public static Connection connectDB() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/redcafe", "root", "HafTaz13;)"); // LINK YOUR DATABASE
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
