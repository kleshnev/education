package stage2.Tasks2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String pathToDB =
            "jdbc:h2:file:C:/Users/Denis/IdeaProjects/education/database;AUTO_SERVER=TRUE";
    public Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(pathToDB, "sa", "");
    }

}