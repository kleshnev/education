package stage2.Tasks2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Inquiry {

    public void showAllLocalities(Connection connection) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select * from Locality")) {
            while (rs.next()) {
                System.out.println("-"+rs.getString("NAME"));
            }
        }
        stat.close();
    }
    public void showAllStreets(Connection connection) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select * from STREET")) {
            while (rs.next()) {
                System.out.println("-"+rs.getString("STREET_NAME"));
            }
        }
        stat.close();
    }


    public int getLocalityIdByName(Connection connection, String name) throws SQLException{
        Statement stat = connection.createStatement();
        int id;
        try (ResultSet rs = stat.executeQuery("SELECT ID_LOCALITY FROM LOCALITY WHERE NAME='"+name+"'")) {
            if (rs.next()) {
                return rs.getInt("ID_LOCALITY");
            }

        }
        stat.close();
        throw new IllegalArgumentException();
    }
    public int getStreetIdByName(Connection connection, String name) throws SQLException{
        Statement stat = connection.createStatement();
        int id;
        try (ResultSet rs = stat.executeQuery("SELECT ID_STREET FROM STREET WHERE STREET_NAME='"+name+"'")) {
            if (rs.next()) {
                return rs.getInt("ID_STREET");
            }
        }
        stat.close();
        throw new IllegalArgumentException();
    }

    public void createLocality(Connection connection, Locality locality) throws SQLException {
        Statement stat = connection.createStatement();
        String loc = String.format("""
                insert into LOCALITY (
                    NAME,
                    FOUNDATION_DATE,
                    LOCALITY_AREA,
                    LOCALITY_TYPE_ID,
                    POPULATION)
                values ('%s',%d,%d,%d,%d);""", locality.getName(), locality.getDate(), locality.getSquare(), locality.getType(), locality.getPopulation());
        stat.execute(loc);
        stat.close();
    }

    public void deleteLocality(Connection connection, String locName) throws SQLException {
        Statement stat = connection.createStatement();
        stat.execute("DELETE FROM LOCALITY WHERE NAME='"+locName+"'");
        stat.close();
    }
    public void modifyLocality(Connection connection, String name, String column, String userStr) throws SQLException {
        Statement stat = connection.createStatement();
        stat.execute("UPDATE LOCALITY SET "+column+"="+"'"+userStr+"'"+" WHERE NAME="+"'"+name+"'");
        stat.close();
    }
    public void deleteStreet(Connection connection, String streetName) throws SQLException {
        Statement stat = connection.createStatement();
        stat.execute("DELETE FROM STREET WHERE STREET_NAME='"+streetName+"'");
        stat.close();
    }
    public void modifyStreet(Connection connection, String name, String column, String userStr) throws SQLException {
        Statement stat = connection.createStatement();
        stat.execute("UPDATE STREET SET " + column + "=" + "'" + userStr + "'" + " WHERE STREET_NAME=" + "'" + name + "'");
        stat.close();
    }


    public void createStreet(Connection connection, String streetName, int streetType) throws SQLException {
        Statement stat = connection.createStatement();
        String str = String.format("""
                insert into STREET (
                    ID_STREET_TYPE,
                    STREET_NAME)
                values (%d,'%s');""", streetType, streetName);
        stat.execute(str);
        stat.close();
    }

    public void addStreetToLocality(Connection connection, int localityId, int streetId) throws SQLException {
        Statement stat = connection.createStatement();
        String str = String.format("""
                insert into LOCALITY_STREET (
                    ID_LOCALITY,
                    ID_STREET)
                values (%d,%d);""", localityId, streetId);
        stat.execute(str);
        stat.close();
    }

    public void getFullInfoByLocalityName(Connection connection, String localityName) throws SQLException {
        Statement stat2 = connection.createStatement();
        try (ResultSet rs = stat2.executeQuery("select LOCALITY.ID_LOCALITY, LOCALITY_TYPE.LOCALITY_TYPE_NAME,NAME, POPULATION, LOCALITY_AREA,FOUNDATION_DATE from Locality " +
                "INNER JOIN LOCALITY_TYPE ON ID_LOCALITY_TYPE=LOCALITY.LOCALITY_TYPE_ID " +
                "WHERE LOCALITY.NAME='" + localityName + "'")) {
            System.out.println("_____________________");
            while (rs.next()) {
                System.out.println("Id: " + rs.getString("ID_LOCALITY"));
                System.out.println("Название: " + rs.getString("NAME"));
                System.out.println("Тип нас. пункта: " + rs.getString("LOCALITY_TYPE_NAME"));
                System.out.println("Дата основания: " + rs.getString("FOUNDATION_DATE"));
                System.out.println("Площадь: " + rs.getString("LOCALITY_AREA"));
                System.out.println("Количество жителей (тысяч): " + rs.getString("POPULATION"));
            }
        }
        stat2.close();
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select STREET_TYPE_NAME,STREET.STREET_NAME from Locality " +
                "INNER JOIN STREET_TYPE ON STREET_TYPE.ID_STREET_TYPE = STREET.ID_STREET_TYPE" +
                " INNER JOIN LOCALITY_STREET ON LOCALITY_STREET.ID_LOCALITY = LOCALITY.ID_LOCALITY " +
                "INNER JOIN STREET ON STREET.ID_STREET = LOCALITY_STREET.ID_STREET WHERE NAME='" + localityName + "'")) {
            System.out.println("  Улицы:");
            while (rs.next()) {
                System.out.println("-" + rs.getString("STREET_TYPE_NAME") + " " + rs.getString("STREET_NAME"));
            }
        }
        stat.close();
    }


    public void streetExistsByName(Connection connection, String localityName, String streetName) throws SQLException {

        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,STREET.STREET_NAME,STREET_TYPE.STREET_TYPE_NAME from Locality " +
                "INNER JOIN LOCALITY_STREET ON LOCALITY_STREET.ID_LOCALITY = LOCALITY.ID_LOCALITY " +
                "INNER JOIN STREET_TYPE ON STREET_TYPE.ID_STREET_TYPE = STREET.ID_STREET_TYPE "+
                "INNER JOIN STREET ON STREET.ID_STREET = LOCALITY_STREET.ID_STREET WHERE NAME='" + localityName + "' and STREET_NAME='" + streetName + "'")) {
            boolean exists = false;
            while (rs.next()) {
                System.out.println("—" + rs.getString("STREET_TYPE_NAME") + " " + rs.getString("STREET_NAME") + " присутствует в городе " + localityName);
                exists = true;
            }
            if (!exists) {
                System.out.println("Улицы " + streetName + " нет в городе " + localityName);
            }
        }
        stat.close();
    }
    public void findLocalityByStreetName(Connection connection,  String pattern) throws SQLException {

        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,STREET.STREET_NAME,STREET_TYPE.STREET_TYPE_NAME from Locality " +
                "INNER JOIN LOCALITY_STREET ON LOCALITY_STREET.ID_LOCALITY = LOCALITY.ID_LOCALITY " +
                "INNER JOIN STREET_TYPE ON STREET_TYPE.ID_STREET_TYPE = STREET.ID_STREET_TYPE " +
                "INNER JOIN STREET ON STREET.ID_STREET = LOCALITY_STREET.ID_STREET WHERE STREET_NAME LIKE'%" + pattern + "%'")) {
            boolean exists = false;
            while (rs.next()) {
                System.out.println("—" + rs.getString("NAME") + ", " + rs.getString("STREET_TYPE_NAME") + " " + rs.getString("STREET_NAME"));
                exists = true;
            }
            if (!exists) {
                System.out.println("Улицы c паттерном \"" + pattern + "\" не существует в базе");
            }
        }
        stat.close();
    }
    public void streetExistsByLikeName(Connection connection,  String pattern) throws SQLException {

        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,STREET.STREET_NAME,STREET_TYPE.STREET_TYPE_NAME from Locality " +
                "INNER JOIN LOCALITY_STREET ON LOCALITY_STREET.ID_LOCALITY = LOCALITY.ID_LOCALITY " +
                "INNER JOIN STREET_TYPE ON STREET_TYPE.ID_STREET_TYPE = STREET.ID_STREET_TYPE " +
                "INNER JOIN STREET ON STREET.ID_STREET = LOCALITY_STREET.ID_STREET WHERE STREET_NAME LIKE'%" + pattern + "%'")) {
            boolean exists = false;
            while (rs.next()) {
                System.out.println("—" + rs.getString("STREET_TYPE_NAME") + " " + rs.getString("STREET_NAME") + " присутствует в городе " +rs.getString("NAME"));
                exists = true;
            }
            if (!exists) {
                System.out.println("Улицы c паттерном \"" + pattern + "\" не существует в базе");
            }
        }
        stat.close();
    }

    public void findLocalityByLikeName(Connection connection, String pattern) throws SQLException {

        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select DISTINCT NAME from Locality WHERE NAME LIKE'%" + pattern + "%'")) {
            boolean exists = false;
            while (rs.next()) {
                System.out.println("Паттерн '"+pattern+"'- найден город " + rs.getString("NAME"));
                exists = true;
            }
            if (!exists) {
                System.out.println("Города с паттерном \"" + pattern + "\" не существует в базе");
            }
        }
        stat.close();
    }
    public void findLocalityByPop(Connection connection, int pop,String pattern) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,POPULATION from Locality WHERE POPULATION"+pattern+pop)) {
            boolean exists = false;
            System.out.println("Найдены города "+pattern+pop+"т. человек");
            while (rs.next()) {
                System.out.println(rs.getString("NAME") +" — " +rs.getString("POPULATION")+"тыс. человек");
                exists = true;
            }
            if (!exists) {
                System.out.println("Такие города отстутствуют.");
            }
        }
        stat.close();
    }
    public void findLocalityByAreaSize(Connection connection, int areaSize,String pattern) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,LOCALITY_AREA from Locality WHERE LOCALITY_AREA"+pattern+areaSize)) {
            boolean exists = false;
            System.out.println("Найдены города с площадью "+pattern+areaSize);
            while (rs.next()) {
                System.out.println(rs.getString("NAME") +" — " +rs.getString("LOCALITY_AREA")+"м^2");
                exists = true;
            }
            if (!exists) {
                System.out.println("Такие города отстутствуют.");
            }
        }
        stat.close();
    }

    public void findStreetByType(Connection connection,String streetType) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,STREET.STREET_NAME,STREET_TYPE.STREET_TYPE_NAME from Locality " +
                "INNER JOIN LOCALITY_STREET ON LOCALITY_STREET.ID_LOCALITY = LOCALITY.ID_LOCALITY " +
                "INNER JOIN STREET ON STREET.ID_STREET = LOCALITY_STREET.ID_STREET "+
                "INNER JOIN STREET_TYPE ON STREET_TYPE.ID_STREET_TYPE = STREET.ID_STREET_TYPE WHERE STREET_TYPE_NAME='"+streetType+"'")) {
            boolean exists = false;
            System.out.println("Найдены города c улицами типа: " +streetType);
            while (rs.next()) {
                System.out.println("—"+rs.getString("NAME") + ", — "+ rs.getString("STREET_TYPE_NAME") +" "+ rs.getString("STREET_NAME"));
                exists = true;
            }
            if (!exists) {
                System.out.println("—Такие города отстутствуют.");
            }
        }
        stat.close();
    }

    public void findLocalityByDate(Connection connection, int date) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,FOUNDATION_DATE from Locality WHERE FOUNDATION_DATE="+date)) {
            boolean exists = false;
            System.out.println("Найдены города:");
            while (rs.next()) {
                System.out.println(rs.getString("NAME") +" — " +rs.getString("FOUNDATION_DATE")+"г.");
                exists = true;
            }
            if (!exists) {
                System.out.println("Такие города отстутствуют.");
            }
        }
        stat.close();
    }

    public void findLocalityByDateInterval(Connection connection, int dateStart, int dateEnd) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,FOUNDATION_DATE from Locality WHERE FOUNDATION_DATE>="+dateStart+"and FOUNDATION_DATE<="+dateEnd)) {
            boolean exists = false;
            System.out.println("Найдены города:");
            while (rs.next()) {
                System.out.println(rs.getString("NAME") +" — " +rs.getString("FOUNDATION_DATE")+"г.");
                exists = true;
            }
            if (!exists) {
                System.out.println("Такие города отстутствуют.");
            }
        }
        stat.close();
    }

}
