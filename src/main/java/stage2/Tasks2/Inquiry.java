package stage2.Tasks2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Inquiry {

    public void showAll(Connection connection) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select * from Locality")) {
            while (rs.next()) {
                System.out.println(rs.getString("NAME"));
            }
        }
        stat.close();
    }


    public void createLocality(Connection connection, Locality locality) throws SQLException {
        Statement stat = connection.createStatement();
        String loc = String.format("""
                insert into LOCALITY (
                    NAME,
                    FOUNDATION_DATE,
                    LOCALITY_SQUARE,
                    LOCALITY_TYPE_ID,
                    POPULATION)
                values ('%s',%s,'%s',%d,%d);""", locality.getName(),locality.getDate(), locality.getSquare(), locality.getType(), locality.getPopulation());
        stat.execute(loc);
        stat.close();
    }
    public void createStreet(Connection connection, String streetName, int streetType) throws SQLException {
        Statement stat = connection.createStatement();
        String str = String.format("""
                insert into STREET (
                    ID_STREET_TYPE,
                    STREET_NAME)
                values (%d,'%s');""",streetType,streetName);
        stat.execute(str);
        stat.close();
    }

    public void addStreetToLocality(Connection connection, int localityId, int streetId) throws SQLException {
        Statement stat = connection.createStatement();
        String str = String.format("""
                insert into LOCALITY_STREET (
                    ID_LOCALITY,
                    ID_STREET)
                values (%d,%d);""",localityId,streetId);
        stat.execute(str);
        stat.close();
    }

    public void getFullInfoByLocalityName(Connection connection, String localityName) throws SQLException {
        Statement stat2 = connection.createStatement();
        try (ResultSet rs = stat2.executeQuery("select LOCALITY.ID_LOCALITY, LOCALITY_TYPE.LOCALITY_TYPE_NAME,NAME, POPULATION, LOCALITY_SQUARE,FOUNDATION_DATE from Locality " +
                "INNER JOIN LOCALITY_TYPE ON ID_LOCALITY_TYPE=LOCALITY.LOCALITY_TYPE_ID "+
                "WHERE LOCALITY.NAME='"+localityName+"'")) {
            System.out.println("_____________________");
            while (rs.next()) {
                System.out.println("Id: " +rs.getString("ID_LOCALITY"));
                System.out.println("Название: " +rs.getString("NAME"));
                System.out.println("Тип нас. пункта: " + rs.getString("LOCALITY_TYPE_NAME"));
                System.out.println("Дата основания: " +rs.getString("FOUNDATION_DATE"));
                System.out.println("Площадь: " +rs.getString("LOCALITY_SQUARE"));
                System.out.println("Количество жителей (тысяч): " +rs.getString("POPULATION"));
            }
        }
        stat2.close();
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select STREET.STREET_NAME from Locality " +
                "INNER JOIN LOCALITY_STREET ON LOCALITY_STREET.ID_LOCALITY = LOCALITY.ID_LOCALITY " +
                "INNER JOIN STREET ON STREET.ID_STREET = LOCALITY_STREET.ID_STREET WHERE NAME='"+localityName+"'")) {
            System.out.println("Улицы:");
            while (rs.next()) {
                System.out.println("-"+rs.getString("STREET_NAME"));
            }
        }
        stat.close();
    }



    public void streetExistsByName(Connection connection, String localityName,String streetName) throws SQLException {

        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,STREET.STREET_NAME from Locality " +
                "INNER JOIN LOCALITY_STREET ON LOCALITY_STREET.ID_LOCALITY = LOCALITY.ID_LOCALITY " +
                "INNER JOIN STREET ON STREET.ID_STREET = LOCALITY_STREET.ID_STREET WHERE NAME='"+localityName+"' and STREET_NAME='"+streetName+"'")) {
            boolean exists = false;
            while (rs.next()) {
                System.out.println("Улица "+rs.getString("STREET_NAME") + " присутствует в городе "+localityName);
                exists = true;
            }
            if (!exists){
                System.out.println("Улицы "+streetName+" нет в городе "+localityName);
            }
        }
        stat.close();
    }
    public void streetExistsByLikeName(Connection connection, String localityName,String pattern) throws SQLException {

        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select NAME,STREET.STREET_NAME from Locality " +
                "INNER JOIN LOCALITY_STREET ON LOCALITY_STREET.ID_LOCALITY = LOCALITY.ID_LOCALITY " +
                "INNER JOIN STREET ON STREET.ID_STREET = LOCALITY_STREET.ID_STREET WHERE NAME='"+localityName+"' and STREET_NAME LIKE'%"+pattern+"%'")) {
            boolean exists = false;
            while (rs.next()) {
                System.out.println("Улица "+rs.getString("STREET_NAME") + " присутствует в городе "+localityName);
                exists = true;
            }
            if (!exists){
                System.out.println("Улицы c паттерном \""+pattern+"\" нет в городе "+localityName);
            }
        }
        stat.close();
    }




}
