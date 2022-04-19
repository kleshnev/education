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
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("""
                    Выберите действие:
                    1.Создать,модифицировать,удалить информацию о населенном пункте или улице
                    2.Получить полную инф. о населенном пункте
                    3.Получить инф. о наличии улицы
                    4.Получить инф. о наличии населенного пункта по критериям
                    5.Завершить работу""");
            int userChoice = sc.nextInt();
            switch (userChoice) {
                case 1 -> {
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
                                new Inquiry().showAllLocalities(connection);
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
                                    case 1 -> {column = "NAME";System.out.println("Введите новое название нас.пункта: ");}
                                    case 2 -> {column = "LOCALITY_AREA";System.out.println("Введите новую площадь пункта: ");}
                                    case 3 -> {column = "LOCALITY_TYPE_ID";System.out.println("Введите новый тип\n1.Город\n2.Деревня\n3.Село ");}
                                    case 4 -> {column = "FOUNDATION_DATE";System.out.println("Введите год основания: ");}
                                    case 5 -> {column = "POPULATION";System.out.println("Введите количество населения в тысячах: ");}
                                }
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
                                new Inquiry().showAllLocalities(connection);
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

                        userIn = sc2.nextInt();
                        sc2.nextLine();
                        switch (userIn) {
                            case 1 -> {
                                System.out.println("Введите название улицы");
                                String streetName = sc2.nextLine();
                                //sc2.nextLine();
                                System.out.println("Введите тип улицы");
                                System.out.println("""
                                        1.Проспект
                                        2.Улица
                                        3.Бульвар""");
                                int type = sc2.nextInt();
                                new Inquiry().createStreet(connection, streetName, type);
                                System.out.println("В какой город добавить улицу? (Введите полное название)");
                                new Inquiry().showAllLocalities(connection);
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
                                System.out.println("""
                                        Выберите, что изменить:
                                        1.Тип улицы
                                        2.Название
                                        """);
                                userIn = sc2.nextInt();
                                sc2.nextLine();
                                String column;
                                String userInputStr;
                                switch (userIn) {
                                    case 1 -> {
                                        column = "ID_STREET_TYPE";
                                        System.out.println("""
                                                Введите номер типа улицы: +
                                                1.Проспект
                                                2.Улица"
                                                3.Бульвар""");
                                    }
                                    case 2 -> {
                                        column = "STREET_NAME";
                                        System.out.println("Введите название улицы");
                                    }
                                    default -> {
                                        System.out.println("Ошибка ввода");
                                        return;
                                    }
                                }

                                userInputStr = sc2.nextLine();
                                new Inquiry().modifyStreet(connection, streetName, column, userInputStr);
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

                }
                case 2 -> {
                    System.out.println("В базе есть нас. пункты:");
                    new Inquiry().showAllLocalities(connection);
                    System.out.println("Введите полное название населенного пункта:");
                    sc.nextLine();
                    String userInput = sc.nextLine();
                    new Inquiry().getFullInfoByLocalityName(connection, userInput);

                }
                case 3 -> {
                    sc.nextLine();
                    System.out.println("1.По названию пункта (строгое соответствие)\n2.По названию улицы(like запрос)");
                    userChoice = sc.nextInt();
                    switch (userChoice) {
                        case 1 -> {
                            sc.nextLine();
                            System.out.println(" Существующие города:");
                            new Inquiry().showAllLocalities(connection);
                            System.out.println(" Существующие улицы:");
                            new Inquiry().showAllStreets(connection);

                            System.out.println(" Введите название города");
                            String localityName = sc.nextLine();
                            System.out.println(" Введите название улицы(строго)");
                            String streetName = sc.nextLine();
                            new Inquiry().streetExistsByName(connection, localityName, streetName);
                        }
                        case 2 -> {
                            sc.nextLine();
                            System.out.println(" Существующие города:");
                            new Inquiry().showAllLocalities(connection);
                            System.out.println(" Существующие улицы:");
                            new Inquiry().showAllStreets(connection);
                            System.out.println(" Введите название улицы (паттерн like запроса)");
                            String pattern = sc.nextLine();
                            new Inquiry().streetExistsByLikeName(connection, pattern);
                        }
                    }
                }
                case 4 -> {
                    System.out.println("""
                            Выберите действие:
                            1. по названию населенного пункта(like запрос)
                            2. по количеству жителей (>,<,=,!=,<=,>=)
                            3. по площади (>,<,=,!=,<=,>=)
                            4. по дате основание (строго и диапазон)
                            5. по названию улицы (like запрос)
                            6. по типу улицы (строго тип)""");

                    sc.nextLine();
                    userChoice = sc.nextInt();
                    sc.nextLine();
                    switch (userChoice) {
                        case 1: {
                            System.out.println("Введите название нас. пункта (pattern like запроса)");
                            String pattern = sc.nextLine();
                            new Inquiry().findLocalityByLikeName(connection, pattern);
                            break;
                        }
                        case 2: {
                            System.out.println("Введите количество жителей (в тысячах)");
                            int pop = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Введите критерий поиска - (>,<,=,!=,<=,>=)");
                            String pattern = sc.nextLine();
                            new Inquiry().findLocalityByPop(connection, pop, pattern);
                            break;
                        }
                        case 3: {
                            System.out.println("Введите площадь нас. пункта");
                            int areaSize = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Введите критерий поиска - (>,<,=,!=,<=,>=)");
                            String pattern = sc.nextLine();
                            new Inquiry().findLocalityByAreaSize(connection, areaSize, pattern);
                            break;
                        }
                        case 4: {
                            System.out.println("Выберите режим поиска: \n1.Строгое соответствие\n2.Диапазон");
                            userChoice = sc.nextInt();
                            while (userChoice < 1 || userChoice > 2) {
                                System.out.println("Выберите верный индекс");
                                userChoice = sc.nextInt();
                            }
                            if (userChoice == 1) {
                                System.out.println("Введите год основания пункта: ");
                                int year = sc.nextInt();
                                new Inquiry().findLocalityByDate(connection, year);
                            } else {
                                System.out.println("Введите диапазон годов основания пункта:\nВведите год ОТ которого нужно искать)");
                                int yearFrom = sc.nextInt();
                                System.out.println("Введите год ДО которого нужно искать)");
                                int yearTo = sc.nextInt();
                                new Inquiry().findLocalityByDateInterval(connection, yearFrom, yearTo);
                                break;
                            }
                        }
                        case 5: {
                            System.out.println("Введите название улицы (pattern like запроса)");
                            String pattern = sc.nextLine();
                            new Inquiry().findLocalityByStreetName(connection, pattern);
                            break;
                        }
                        case 6: {
                            System.out.println("Введите тип улицы (строгое соответствие) (Проспект,Улица,Бульвар)");
                            String type = sc.nextLine();
                            new Inquiry().findStreetByType(connection, type);
                            break;
                        }
                    }

                }
                case 5 -> {
                    connection.close();
                    return;
                }
                default -> System.out.println("Введите правильный индекс действия.");

            }
        }

    }

}