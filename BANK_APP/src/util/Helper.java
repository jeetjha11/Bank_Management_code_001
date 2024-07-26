package util;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Helper {

    public static String generateAccountNumber(Connection connection,String user_id) throws IOException {
        Properties properties=new Properties();
        properties.load(new FileInputStream("details.properties"));
        String defaultBankPass=properties.getProperty("BANK_NAME").substring(1,4)+
                properties.getProperty("BANK_IFSC_CODE").substring(1,5)+
                properties.getProperty("BANK_BRANCH_NAME");


        int randomNumber= new Random().nextInt(4999);
        String  bank_account_number=defaultBankPass.concat(String.valueOf(randomNumber));

        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select accountnumber from accountdetails where userid= ? ");
            preparedStatement.setString(1,user_id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                return bank_account_number;
            }

            while (resultSet.next())
            {

                if(resultSet.getString(1).equals(bank_account_number))
                {
                    generateAccountNumber(connection,user_id);
                }
                else {
                    return bank_account_number;
                }
            }
            return null;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public static Map<Boolean,String> validateAccountCredentials(Connection connection)
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the account number : ");
        final String  accountNumber=scanner.nextLine();
        System.out.println("Enter the Ifsc code: ");
        final String ifscCode=scanner.nextLine();
        Map<Boolean,String> resultMap=new HashMap<>();

        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement("select accountnumber, ifsccode from accountdetails where accountnumber = ? ");
            preparedStatement.setString(1,accountNumber);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next())
            {
                if(resultSet.getString(1).equals(accountNumber) && resultSet.getString(2).equals(ifscCode))
                {
                   resultMap.put(true,accountNumber);
                   return resultMap;
                }
            }
            resultMap.put(false,null);
            System.out.println("Given Account credentials are wrong Please Check the Credentials once again!! ");
            return resultMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
