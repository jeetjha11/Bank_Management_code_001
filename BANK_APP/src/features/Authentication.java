package features;


import com.mysql.cj.jdbc.ConnectionGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Authentication {


    public static void login(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the email id:");
        final String email = scanner.nextLine();
        System.out.println("Enter Password: ");
        final String password = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement("Select email from user");

    }


}
