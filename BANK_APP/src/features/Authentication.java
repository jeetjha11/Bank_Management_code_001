package features;


import com.mysql.cj.jdbc.ConnectionGroup;
import entity.UserDetails;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Authentication {


    private String  USER_EMAIL="";
    private static String userId;

    public static int log_count_check=5;

    public String  getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail()
    {
        return this.USER_EMAIL;
    }
    public void setUSER_EMAIL(String user_email)
    {
        this.USER_EMAIL=user_email;
    }


    public static void isAuthenticated(Connection connection)
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println(".............................Please Choose.............................");
        System.out.println("1. New User "+"\n" +
                           "2. Existing User");

        int choice=scanner.nextInt();
        switch (choice)
        {
            case 1:
            {
               try {
                   registerNewUser(connection);
                   break;

               } catch (SQLException e) {
                   throw new RuntimeException(e);

               }
            }
            case 2:
            {
                try {
                    login(connection);
                    break;

                } catch (SQLException e) {
                    throw new RuntimeException(e);

                }
            }
            default:
            {
                System.out.println("Invalid Choice.. Please Choose Again");
            }
        }
    }




    public static void login(Connection connection) throws SQLException {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the email id: ");
            final String email = scanner.nextLine();
            System.out.println("Enter Password: ");
            final String password = scanner.nextLine();


            PreparedStatement preparedStatement = connection.prepareStatement("select * from userlogin where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {

                Authentication authentication=new Authentication();
                authentication.setUSER_EMAIL(resultSet.getString(2));
                authentication.setUserId(resultSet.getString(1));
                System.out.println("Welcome to the portal>>>");
                scanner.close();
            }
            else {
                System.out.println("Invalid Credential Given Please Try again!!! "+log_count_check +" times Left");
                log_count_check--;
                if(log_count_check==0)
                {
                    System.out.println("You have tried Most Time Please Try again after some time!!!");
                    return;
                }
                login(connection);
            }

            scanner.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public static void registerNewUser(Connection connection) throws SQLException {

        try {
            Scanner scanner=new Scanner(System.in);

            boolean isActive=true;
            String  id=validateUuid(connection);

            PreparedStatement preparedStatement=connection.prepareStatement("insert into usersdetails(user_id,username,email,phone,is_active,password,address) values(?,?,?,?,?,?,?)");
            preparedStatement.setString(1,id);
            System.out.println("Enter the user name: ");
            String userName=scanner.nextLine();
            preparedStatement.setString(2,userName);
            Statement statement=connection.createStatement();
            String tempEmail;
            while (true)
            {
                System.out.println("Enter the email: ");
                tempEmail=scanner.nextLine();
//                ResultSet resultSet=statement.executeQuery("select * from user where email="+tempEmail);
                PreparedStatement preparedStatement1 = connection.prepareStatement("select * from userlogin where email= ? ");
                preparedStatement1.setString(1, tempEmail);
                ResultSet resultSet=preparedStatement1.executeQuery();
                String email = null;
                while (resultSet.next())
                {
                    email=resultSet.getString(2);

                }
                if(Objects.equals(email, tempEmail))
                {
                    System.out.println("Given Email is already present Please Enter Another mail ");
                }
                else {
                    break;
                }
            }

            preparedStatement.setString(3,tempEmail);
            System.out.println("Enter the phone: ");
            String tempPhone=scanner.next();
            preparedStatement.setString(4,tempPhone);
            preparedStatement.setBoolean(5,isActive);
            System.out.println("Enter the password: ");
            String pass=scanner.next();


            preparedStatement.setString(6, pass);
            System.out.println("Enter the address: ");
            String tempAddress=scanner.nextLine();

            preparedStatement.setString(7, tempAddress);
            int insertResult=preparedStatement.executeUpdate();
            if (insertResult>0)
            {
                PreparedStatement preparedStatement1=connection.prepareStatement("insert into userlogin(user_id,email,password) values(?,?,?)");
                preparedStatement1.setString(1,id);
                preparedStatement1.setString(2,tempEmail);
                preparedStatement1.setString(3,pass);
                int loginResult=preparedStatement1.executeUpdate();
                if(loginResult>0)
                {
                    Authentication authentication=new Authentication();
                    authentication.setUSER_EMAIL(tempEmail);
                    authentication.setUserId(id);
                    System.out.println("Thankyou For registration!!!");
                    scanner.close();
                }

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }





    }

    public  static String  validateUuid(Connection connection) throws SQLException {
        String uuid="";
        boolean isUnique=false;
        while (true)
        {
            uuid = UUID.randomUUID().toString();
            PreparedStatement preparedStatement = connection.prepareStatement("select user_id from userlogin where user_id= ? ");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next())
            {
                System.out.println(uuid);
                System.out.println("ins");
                String uid=resultSet.getString(1);

                if(!Objects.equals(uid, uuid))
                {
                    isUnique=true;
                    break;
                }
            }
            return uuid;
        }

    }


}
