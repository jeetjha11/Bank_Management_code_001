package features;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Scanner;

public class UserOperation {
   static Scanner scanner=new Scanner(System.in);

    public static boolean updateProfile(Connection connection,String id) throws SQLException {
        System.out.println("""
                Please Choose What you want to update: 1. Name
                2. Email
                3. Phone\s
                4. Address
                5. All
                6. Exit""");

        int choice=scanner.nextInt();
        switch (choice)
        {
            case 1:
            {

                PreparedStatement preparedStatement=connection.prepareStatement("update usersdetails set username= ? where user_id= ?");
                System.out.println("Enter the new name: ");
                preparedStatement.setString(1,scanner.next());
                preparedStatement.setString(2,id);
                if(preparedStatement.executeUpdate()>0)
                {
                    System.out.println("USER NAME HAS BEEN UPDATED");
                   return true;
                }
                else {
                    System.out.println("Some Error Please try again");
                    return false;
                }

            }
            case 2:
            {

                PreparedStatement preparedStatement=connection.prepareStatement("update usersdetails set email= ? where user_id= ?");
                System.out.println("Enter the email: ");
                String email=scanner.next();
                preparedStatement.setString(1,email);
                preparedStatement.setString(2,id);
                if((preparedStatement.executeUpdate())>0)
                {
                    PreparedStatement preparedStatement1=connection.prepareStatement("update userlogin set email= ? where user_id= ?");
                    preparedStatement1.setString(1,email);
                    preparedStatement1.setString(2,id);
                    if((preparedStatement1.executeUpdate())>0) {
                        System.out.println("USER EMAIL HAS BEEN UPDATED");
                        return true;
                    }
                    else
                    {
                        System.out.println("Some Error");
                        return false;
                    }
                }
                else {
                    System.out.println("Some Error Please try again");
                    return false;
                }


            }
            case 3:
            {
                PreparedStatement preparedStatement=connection.prepareStatement("update usersdetails set email= ? where user_id= ?");
                System.out.println("Enter the new Phone: ");
                preparedStatement.setString(1,scanner.next());
                preparedStatement.setString(2,id);
                if((preparedStatement.executeUpdate())>0)
                {
                    System.out.println("USER Phone HAS BEEN UPDATED");
                    return true;
                }
                else {
                    System.out.println("Some Error Please try again");
                    return false;
                }
            }
            case 4:
            {
                Scanner scanner1=new Scanner(System.in);
                PreparedStatement preparedStatement=connection.prepareStatement("update usersdetails set username= ? where user_id= ?");
                System.out.println("Enter the new Address: ");
                preparedStatement.setString(1,scanner1.nextLine());
                preparedStatement.setString(2,id);
                if((preparedStatement.executeUpdate())>0)
                {
                    System.out.println("USER Address HAS BEEN UPDATED");
                    return true;
                }
                else {
                    System.out.println("Some Error Please try again");
                    return false;
                }
            }
            case 5:
            {
                Scanner scanner1=new Scanner(System.in);
                PreparedStatement preparedStatement=connection.prepareStatement("update usersdetails set username= ?, phone= ?, address= ? where user_id= ?");
                System.out.println("Enter the new name: ");
                preparedStatement.setString(1,scanner1.nextLine());
                System.out.println("Enter the Phone: ");
                preparedStatement.setString(2,scanner1.nextLine());
                System.out.println("Enter the Address: ");
                preparedStatement.setString(3,scanner1.nextLine());
                preparedStatement.setString(4,id);
                if((preparedStatement.executeUpdate())>0)
                {

                    System.out.println("USER Details HAS BEEN UPDATED");
                    return true;
                }
                else {
                    System.out.println("Some Error Please try again");
                    return false;
                }
            }
            case 6:
            {
                break;
            }

        }
        return false;
    }

    public static boolean deleteProfile(Connection connection,String id) throws SQLException {
        System.out.println("Are You Sure ??  Y/N");
        final char deleteChoice=scanner.next().toLowerCase().charAt(0);
        switch (deleteChoice)
        {
            case 'y':
            {
                PreparedStatement preparedStatement=connection.prepareStatement("delete from usersdetails where user_id= ?");
                preparedStatement.setString(1,id);
                int deleteStatus= preparedStatement.executeUpdate();
                if(deleteStatus>0)
                {

                    PreparedStatement preparedStatement1=connection.prepareStatement("delete from userlogin where user_id= ?");
                    preparedStatement1.setString(1,id);
                    int deleteStatus1= preparedStatement.executeUpdate();
                    if (deleteStatus1>0)
                    {
                        System.out.println("User has been deleted Success");
                        return true;
                    }
                    return false;

                }
                return false;
            }

            case 'n':
            {
                break;
            }
        }
        return false;
    }




}
