package stage2.Tasks2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    //укажите свой путь


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getDBConnection();
        Statement stat = connection.createStatement();
        Locality loca = new Locality("Площадь сам","Самара",3,"1855",10550);
        Locality loca2 = new Locality("Площадь ее","Тольятти",2,"1854",1500);
        //new Inquiry().createLocality(connection,loca);
       // new Inquiry().createLocality(connection,loca2);
        //new Inquiry().showAll(connection);
        //new Inquiry().getFullInfoByLocalityName(connection,"Самара");
        //new Inquiry().streetExistsByName(connection,"Самара","Цветочная");
        new Inquiry().streetExistsByLikeName(connection,"Самара","Цвет");
        new Inquiry().streetExistsByLikeName(connection,"Самара","Рапп");
        connection.close();
    }

}