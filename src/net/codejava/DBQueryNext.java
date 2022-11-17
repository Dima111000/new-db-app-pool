package net.codejava;

import java.sql.*;

public class DBQueryNext {
    public static void main(String[] argv) {
        String jdbcURL = "jdbc:postgresql://mouse.db.elephantsql.com:5432/oswgfrke";
        String username = "oswgfrke";
        String password = "9rDeFx9-Z4r7n8QP0iXqwZgHeewlI98n";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Подключение к БД PostreSQL успешно!");

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE user_id = 2");
            while (rs.next()) {
                System.out.print("Запрос вернул ID:"+rs.getInt("user_id")+", ");
                System.out.println();
                System.out.print("ID: "+rs.getInt("user_id")+", ");
                System.out.print("Имя: "+rs.getString("user_name")+", ");
                System.out.println();
            }

            st.close();
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД PostgreSQL");
            e.printStackTrace();
        }
    }
}