package stage2.Tasks3;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getDBConnection();


        while (true) {
            System.out.println("Выберите действие:\n1.Создать новый лабиринт.\n2.Вывести существующий лабиринт\n3.Завершить работу");
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.println("Введите размер лабиринта:");
                    int size = sc.nextInt();
                    Maze mz = new Maze(size, 2);
                    mz.generateMaze();
                    int idMaze = new Inquire().createMaze(connection, size);
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            new Inquire().createCell(connection, mz.maze[i][j].getValue(), mz.maze[i][j].getX(), mz.maze[i][j].getY(), idMaze);
                        }
                    }
                    mz.BFS(connection,idMaze);
                    mz.printMaze();
                    System.out.println("Готово!");
                }
                case 2 -> {
                    new Inquire().showMazes(connection);
                    System.out.println("Выберите лабиринт(Введите номер Id):");
                    int userInput = sc.nextInt();
                    new Inquire().printMazeById(connection, userInput);
                    System.out.println();
                }
                case 3 -> {
                    return;
                }
            }
        }
    }
}
