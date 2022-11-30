package net.codejava;

import java.util.Scanner;
//import java.io.*;
import java.sql.*;

public class Case {
        public static void main(String[] args) {
            String jdbcURL = "jdbc:postgresql://mouse.db.elephantsql.com:5432/oswgfrke";
            String username = "oswgfrke";
            String password = "9rDeFx9-Z4r7n8QP0iXqwZgHeewlI98n";

            Scanner scanner = new Scanner(System.in);
            Integer command;
            String confirm;

            try {
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                System.out.println("Подключение к БД PostreSQL успешно!");
                System.out.println();

                System.out.println("//===============     Реализация методов курсора     ===============//");
                System.out.println();

                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {}

                System.out.print("//===============     Запуск курсора     ===============//");
                System.out.println();

                try {
                    Thread.sleep(1500);
                } catch(InterruptedException ex) {}

                System.out.println();
                System.out.println("//===============     Курсор запущен     ===============//");

                try {
                    Thread.sleep(600);
                } catch(InterruptedException ex) {}

                do {

                    System.out.println();
                    System.out.println("//===============     Команды методов курсора     ===============//");

                    System.out.println();
                    System.out.println(" --------------------------------------");
                    System.out.println("│ Код команды │" + "   " + "│Наименование команды│");
                    System.out.print("│      1.     │" + "   │     Метод next()   │\n");
                    System.out.print("│      2.     │" + "   │     Метод first()  │\n");
                    System.out.print("│      3.     │" + "   │     Метод last()   │\n");
                    System.out.print("│      4.     │" + "   │     Метод prev()   │\n");
                    System.out.print("│      5.     │" + "   │  Метод getString() │\n");
                    System.out.println("│      6.     │" + "   │ Метод getColumns() │");
                    System.out.println(" --------------------------------------");
                    System.out.println("//===========================================================//");

                    System.out.print("Введите наименование команды: ");
                    command = scanner.nextInt();

                    Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    String query = "select user_id, user_name from users";
                    ResultSet rs = stmt.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    switch (command) {
                        case 1:
                            System.out.println();
                            System.out.println("Вывод всех строк в таблице users: " + "метод next()");

                            while (rs.next()) {
                                System.out.print("ID: "+rs.getInt("user_id")+", ");
                                System.out.print("Имя: "+rs.getString("user_name")+", ");
                                System.out.println();
                            }
                            break;
                        case 2:
                            System.out.println();
                            System.out.println("Вывод первой записи: " + "метод first()");
                            rs.first();
                            System.out.print("ID: "+rs.getInt("user_id")+", ");
                            System.out.print("Имя: "+rs.getString("user_name")+", ");
                            System.out.println();
                            break;
                        case 3:
                            System.out.println();
                            System.out.println("Вывод последней строки в таблице users: " + "метод last()");
                            rs.last();
                            System.out.print("ID: "+rs.getInt("user_id")+", ");
                            System.out.print("Имя: "+rs.getString("user_name")+", ");
                            System.out.println();
                            break;
                        case 4:
                            System.out.println();
                            System.out.println("Содержание последних двух записей в таблице: " + "метод prev()");
                            rs.afterLast();
                            rs.previous();
                            System.out.print("ID: "+rs.getInt("user_id")+", ");
                            System.out.print("Имя: "+rs.getString("user_name")+", ");
                            System.out.println();
                            rs.previous();
                            System.out.print("ID: "+rs.getInt("user_id")+", ");
                            System.out.print("Имя: "+rs.getString("user_name")+", ");
                            System.out.println();
                            break;
                        case 5:
                            System.out.println();
                            System.out.println("Метод getString()");
                            while(rs.next()) {
                                String user_name = rs.getString("user_name");
                                System.out.println("Имя юзера = " + user_name);
                            }
                            break;
                        case 6:
                            int columnCount = rsmd.getColumnCount();
                            System.out.println();
                            System.out.println("Метод getColumns()");
                            System.out.println("Количество столбцов: " + columnCount);
                            System.out.println();

                            for (int column = 1; column <= columnCount; column++) {
                                System.out.println("Имя столбца: " + rsmd.getColumnName(column));
                                System.out.println("Тип столбца: " + rsmd.getColumnTypeName(column));
                                System.out.println("Null: " + rsmd.isNullable(column));
                                System.out.println("Auto Increment: " + rsmd.isAutoIncrement(column));
                            }
                            break;
                        default:
                            System.out.println("Такой команды метода курсора как: " + command + " не существует! " + "Попробуйте ввести другую команду" + "\n");
                            break;
                    }
                    System.out.println();
                    System.out.println("Вы хотите продолжить? Введите Y или N");
                    Scanner scanner_2 = new Scanner(System.in);
                    confirm = scanner_2.nextLine();
                } while (confirm.equalsIgnoreCase("Y"));
            } catch (SQLException e) {
                System.out.println("Ошибка подключения к БД PostgreSQL");
                e.printStackTrace();
            }
        }
}

