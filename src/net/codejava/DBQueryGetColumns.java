package net.codejava;

import java.sql.*;

public class DBQueryGetColumns {
    public static void main(String[] argv) {
        String jdbcURL = "jdbc:postgresql://mouse.db.elephantsql.com:5432/oswgfrke";
        String username = "oswgfrke";
        String password = "9rDeFx9-Z4r7n8QP0iXqwZgHeewlI98n";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Подключение к БД PostreSQL успешно!");
            System.out.println();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from users");

            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            System.out.println("Количество столбцов: " + columnCount);
            System.out.println();

            for (int column = 1; column <= columnCount; column++) {
                System.out.println("Имя столбца: " + rsmd.getColumnName(column));
                System.out.println("Тип столбца: " + rsmd.getColumnTypeName(column));
                System.out.println("Null: " + rsmd.isNullable(column));
                System.out.println("Auto Increment: " + rsmd.isAutoIncrement(column));
                System.out.println();
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД PostgreSQL");
            e.printStackTrace();
        }
    }
}
