package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQueryLast {
    public static void main(String[] argv) {
        String jdbcURL = "jdbc:postgresql://mouse.db.elephantsql.com:5432/oswgfrke";
        String username = "oswgfrke";
        String password = "9rDeFx9-Z4r7n8QP0iXqwZgHeewlI98n";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Подключение к БД PostreSQL успешно!");
            System.out.println();

            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String query2 = "select * from users";
            ResultSet rs2 = stmt.executeQuery(query2);

            System.out.println("Вывод всех строк в таблице users: ");

            while (rs2.next()) {
                System.out.print("ID: "+rs2.getInt("user_id")+", ");
                System.out.print("Имя: "+rs2.getString("user_name")+", ");
                System.out.println();
            }

            String query = "select * from users";
            ResultSet rs = stmt.executeQuery(query);
            rs.last();

            System.out.println();
            System.out.println("Вывод последней строки в таблице users ");
            System.out.print("ID: "+rs.getInt("user_id")+", ");
            System.out.print("Имя: "+rs.getString("user_name")+", ");
            System.out.println();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД PostgreSQL");
            e.printStackTrace();
        }
    }
}
