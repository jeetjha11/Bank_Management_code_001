package App;

import connectionConfig.EstablishConnection;
import features.Authentication;
import features.UserOperation;
import migration.DataBaseMigration;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BankApp {



    public static void main(String[] args) throws SQLException {

        Authentication authentication=new Authentication();
        String userId=authentication.getUserId();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Welcome>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("\n \n");

        // making Database Connection
        Connection connection;
        try {
            connection = EstablishConnection.makeConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // apply migration for first time

//        try {
//            DataBaseMigration.applyMigration(connection);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


        // Authenticating User;

        Authentication.isAuthenticated(connection);



        Scanner scanner=new Scanner(System.in);

        int mainManu, accountManu=-1;
        do {


            System.out.println(
                    "1. Update Profile"+ "\n" +
                    "2. Delete Profile"+ "\n" +
                    "3. Explore Bank Account"+"\n" +
                    "4. Exit"+"\n");

            System.out.println("Choose One Option Please" );
            mainManu=scanner.nextInt();

            switch (mainManu)
            {
                case 1:{
                    System.out.println("Update Profile");
                    boolean isUpdated=UserOperation.updateProfile(connection,userId);
                    if(isUpdated)
                    {
                        // display the data
                    }

                    break;
                }
                case 2:
                {
                    System.out.println("delete profile");

                    boolean isDeleted=UserOperation.deleteProfile(connection,userId);

                    break;
                }
                case 3:
                {
                    System.out.println("Explore bank Account");
                    break;
                }
                case 4:
                {
                    System.out.println("Success logout");
                    break;
                }
                default :
                {
                    System.out.println("Invalid Choice Given Please Choose form (1/2/3/4)");

                }
            }
        }
        while (mainManu!=4);



    }
}
