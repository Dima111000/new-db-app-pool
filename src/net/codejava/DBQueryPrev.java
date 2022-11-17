package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQueryPrev {
    public static void main(String[] argv) {
        String jdbcURL = "jdbc:postgresql://mouse.db.elephantsql.com:5432/oswgfrke";
        String username = "oswgfrke";
        String password = "9rDeFx9-Z4r7n8QP0iXqwZgHeewlI98n";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Подключение к БД PostreSQL успешно!");


            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "select * from users";
            ResultSet rs = stmt.executeQuery(query);

            // Вывод последних 2 записей
            System.out.println("Содержание последней записи: ");

            rs.afterLast();
            rs.previous();
            System.out.print("ID: "+rs.getInt("user_id")+", ");
            System.out.print("Имя: "+rs.getString("user_name")+", ");
            System.out.println();

            rs.previous();
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
