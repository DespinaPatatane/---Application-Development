package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TeachersDB {
   private static final String URL = "jdbc:mysql://localhost:3306/TeachersDB";
   private static final String USER = "root";
   private static final String PASSWORD = "";
   
   public static Connection getConnection() throws SQLException {
	   return DriverManager.getConnection(URL, USER, PASSWORD);
   } 
   
   public static void main(String[] args) {
	   try (Connection conn = TeachersDB.getConnection()) {
		   System.out.println("Connection successful!");
	   }catch (SQLException e) {
		   e.printStackTrace();
	   }
   }
}

