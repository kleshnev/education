package stage2.Tasks2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    //укажите свой путь


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getDBConnection();
        System.out.println("""
                Выберите действие:
                1.Создать,модифицировать,удалить информацию о населенном пункте или улице
                2.Получить полную инф. о населенном пункте
                3.Получить инф. о наличии улицы
                4.Получить инф. о наличии населенного пункта по критериям""");
        Scanner sc = new Scanner(System.in);
        int userChoice = sc.nextInt();
        switch (userChoice) {
            case 1: {
                System.out.println("""
                        1.Изменить/добавить населенный пункт
                        2.Изменить/добавить улицу""");
                Scanner sc2 = new Scanner(System.in);
                int userIn = sc2.nextInt();
                if (userIn == 1) {
                    System.out.println("""
                            1.Добавить населенный пункт
                            2.Изменить населенный пункт
                            3.Удалить населенный пункт""");

                    userIn = sc2.nextInt();
                    sc2.nextLine();
                    //работа с нас. пунктами
                    switch (userIn) {
                        case 1 -> {
                            System.out.println("Введите название нас. пункта");
                            String name = sc2.nextLine();
                            System.out.println("Введите площадь(в кв. м.)");
                            int area = sc2.nextInt();
                            System.out.println("Введите тип пункта\n1.Город\n2.Деревня\n3.Село");
                            int type = sc2.nextInt();
                            System.out.println("Введите год основания нас. пункта");
                            int date = sc2.nextInt();
                            System.out.println("Введите количество населения(в тысячах)");
                            int pop = sc2.nextInt();
                            Locality loc = new Locality(name, area, type, date, pop);
                            try {
                                new Inquiry().createLocality(connection, loc);
                                System.out.println("Населенный пункт добавлен!");
                            } catch (SQLException ex) {
                                System.out.println("Ошибка, неверные данные");
                            }
                        }
                        case 2 -> {
                            System.out.println("В базе данных существуют:");
                            new Inquiry().showAll(connection);
                            System.out.println("Введите название нас. пункта:");
                            String name = sc2.nextLine();
                            System.out.println("""
                                    Выберите, что изменить:
                                    1.Название
                                    2.Площадь
                                    3.Тип пункта
                                    4.Год основания
                                    5.Количество населения""");
                            userIn = sc2.nextInt();
                            sc2.nextLine();
                            String column = "";
                            String userInputStr;
                            switch (userIn) {
                                case 1 -> column = "NAME";
                                case 2 -> column = "LOCALITY_AREA";
                                case 3 -> column = "LOCALITY_TYPE_ID";
                                case 4 -> column = "FOUNDATION_DATE";
                                case 5 -> column = "POPULATION";
                            }
                            System.out.println("Введите новые данные: ");
                            userInputStr = sc2.nextLine();
                            try {
                                new Inquiry().modifyLocality(connection, name, column, userInputStr);
                            } catch (SQLException ex) {
                                System.out.println("Ошибка, неверные данные");
                            }
                            System.out.println("Изменения успешны!");
                        }
                        case 3 -> {
                            System.out.println("В базе данных существуют:");
                            new Inquiry().showAll(connection);
                            System.out.println("Введите название нас. пункта:");
                            System.out.println("Введите название города для удаления:");
                            String name = sc2.nextLine();
                            try {
                                new Inquiry().deleteLocality(connection, name);
                            } catch (SQLException ex) {
                                System.out.println("Ошибка, неверные данные");
                            }
                            System.out.println("Удаление успешно!");
                        }
                    }
                } else if (userIn == 2) {
                    System.out.println("""
                            1.Добавить улицу
                            2.Изменить улицу
                            3.Удалить улицу""");
                }
                userIn = sc2.nextInt();
                switch (userIn) {
                    case 1 -> {
                        System.out.println("Введите название улицы");
                        String streetName = sc2.nextLine();
                        System.out.println("Введите тип улицы");
                        System.out.println("""
                                1.Проспект
                                2.Улица
                                3.Бульвар""");
                        sc2.nextLine();
                        int type = sc2.nextInt();
                        new Inquiry().createStreet(connection, streetName, type);
                        System.out.println("В какой город добавить улицу?");
                        new Inquiry().showAll(connection);
                        sc2.nextLine();
                        String locName = sc2.nextLine();
                        int idLoc = new Inquiry().getLocalityIdByName(connection, locName);
                        int idStr = new Inquiry().getStreetIdByName(connection, streetName);
                        new Inquiry().addStreetToLocality(connection, idLoc, idStr);
                        System.out.println("Улица добавлена!");
                    }
                    case 2 -> {
                        System.out.println("Улицы в базе данных: ");
                        new Inquiry().showAllStreets(connection);
                        System.out.println("Введите название улицы");
                        String streetName = sc2.nextLine();
                        String name = sc2.nextLine();
                        System.out.println("""
                                Выберите, что изменить:
                                1.Тип улицы
                                2.Название
                                """);
                        userIn = sc2.nextInt();
                        sc2.nextLine();
                        String column = "";
                        String userInputStr;
                        switch (userIn) {
                            case 1 -> column = "ID_STREET_TYPE";
                            case 2 -> column = "STREET_NAME";
                        }
                        System.out.println("Введите новые данные: ");
                        userInputStr = sc2.nextLine();
                        new Inquiry().modifyStreet(connection, name, column, userInputStr);
                        System.out.println("Данные обновлены!");
                    }
                    case 3 -> {
                        System.out.println("Введите название улицы");
                        String streetName = sc2.nextLine();
                        new Inquiry().deleteStreet(connection, streetName);
                        System.out.println("Улица удалена");
                    }
                }
            }
            case 2: {
                sc.nextLine();
                String userInput = sc.nextLine();
                new Inquiry().getFullInfoByLocalityName(connection,userInput);
            }
            case 3:{
                System.out.println("1.По названию пункта (строгое соответствие)\n2.По названию улицы(like запрос)");
                userChoice = sc.nextInt();
                switch (userChoice){
                    case 1:{
                        sc.nextLine();
                        System.out.println("Введите название города");
                        String localityName = sc.nextLine();
                        System.out.println("Введите название улицы(строго)");
                        String streetName = sc.nextLine();
                        new Inquiry().streetExistsByName(connection,localityName,streetName);
                    }
                    case 2:{
                        System.out.println("Введите название улицы (паттерн like запроса)");
                        String pattern = sc.nextLine();
                        new Inquiry().streetExistsByLikeName(connection,pattern);
                    }
                }
            }
        }
        connection.close();
    }

}