package stage2.Tasks3;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Inquire {

    public int createMaze(Connection connection, int length) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter);
        String com = String.format("""
                insert into MAZE
                (LENGTH,DATE)
                values (%d,'%s');""", length,date);
        PreparedStatement statement = connection.prepareStatement(com, Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            statement.close();
        }
        throw new SQLException("Error");
    }



    public void updateCellValue(Connection connection,int idMaze, int x, int y, String value) throws SQLException {
        Statement stat = connection.createStatement();
        String com = String.format("""
                UPDATE CELL SET CELL_VALUE='%s'
                WHERE ID_MAZE=%d and X=%d and Y=%d;""", value,idMaze,x,y);
        stat.execute(com);
        stat.close();
    }

    public void createCell(Connection connection, String value, int x, int y, int idMaze) throws SQLException {
        Statement stat = connection.createStatement();
        String ex = String.format("""
                insert into CELL
                (CELL_VALUE,X,Y,ID_MAZE)
                values ('%s',%d,%d,%d);""", value, x, y, idMaze);
        stat.execute(ex);
        stat.close();
    }
    public void showMazes(Connection connection) throws SQLException {
        Statement stat = connection.createStatement();
        stat.execute("SELECT ID_MAZE FROM MAZE");
        try (ResultSet rs = stat.executeQuery("SELECT * FROM MAZE")) {
            while (rs.next()) {
                System.out.println("Id: "+rs.getInt("ID_MAZE") +", Длина -"+rs.getInt("LENGTH")+" ,Дата - "+rs.getString("DATE"));
            }
        }
        stat.close();
    }


    public void printMazeById(Connection connection, int id) throws SQLException {
        Statement stat = connection.createStatement();
        try (ResultSet rs = stat.executeQuery("select LENGTH, CELL.ID_CELL, CELL.CELL_VALUE,CELL.X,CELL.Y from MAZE " +
                "INNER JOIN CELL ON CELL.ID_MAZE=MAZE.ID_MAZE WHERE MAZE.ID_MAZE=" + id)) {
            while (rs.next()) {
                System.out.print(rs.getString("CELL_VALUE") +" ");
                if (rs.getInt("ID_CELL") % rs.getInt("LENGTH") == 0) {
                    System.out.println();
                }
            }
        }
        stat.close();
    }
}
