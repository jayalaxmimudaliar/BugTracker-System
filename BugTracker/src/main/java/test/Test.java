package test;
import java.sql.*;

public class Test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bugtracker";
        String user = "root";
        String pass = "root123";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
