package net.codejava;

import java.sql.*;

public class DBQueryGetString {
    public static void main(String[] argv) {
        String jdbcURL = "jdbc:postgresql://mouse.db.elephantsql.com:5432/oswgfrke";
        String username = "oswgfrke";
        String password = "9rDeFx9-Z4r7n8QP0iXqwZgHeewlI98n";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Подключение к БД PostreSQL успешно!");


            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT user_name FROM users");

            while(rs.next()){
                String user_name = rs.getString("user_name");

                System.out.println("user_name="+user_name);
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД PostgreSQL");
            e.printStackTrace();
        }
    }
}
