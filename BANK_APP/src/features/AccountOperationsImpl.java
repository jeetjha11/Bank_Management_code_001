package features;

import entity.AccountDetails;
import util.Helper;

import javax.sound.midi.Soundbank;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AccountOperationsImpl  implements AccountOperation {


    public static void accountOperation(String user_id, Connection connection) throws IOException, SQLException {
        AccountOperation accountOperation = new AccountOperationsImpl();
        Properties properties = new Properties();
        properties.load(new FileInputStream("details.properties"));
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("""
                    1. New Account
                    2. Existing Account
                    3. Exit""");

            choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    String account_number = accountOperation.openNewAccount(user_id, connection, properties.getProperty("BANK_BRANCH_NAME"), properties.getProperty("BANK_IFSC_CODE"), properties.getProperty("BANK_NAME"));
                    break;
                }
                case 2: {
                    int innerChoice;
                    do {
                        System.out.println("""
                                1. Credit Amount
                                2. Debit Amount
                                3. Check Balance
                                4. View Account Details
                                5. Exit""");
                        System.out.println("Please Choose Above Option: (1/2/3/4/5)");
                        innerChoice = scanner.nextInt();
                        Scanner scanner1 = new Scanner(System.in);

                        switch (innerChoice) {
                            case 1: {
                                final double finalAmount = accountOperation.creditAmount(user_id, connection);
                                break;
                            }
                            case 2: {
                                final double finalAmount = accountOperation.debitAmount(user_id, connection);
                                break;
                            }
                            case 3: {
                                final double finalAmount = accountOperation.viewBalance(user_id, connection);
                                break;
                            }
                            case 4: {


                            }
                            case 5: {

                            }
                            default: {
                                System.out.println("Invalid Choice Please Choose Again!1");
                            }

                        }
                    }
                    while (innerChoice != 5);
                }
            }
        } while (choice != 3);
    }


    @Override
    public String openNewAccount(String user_id, Connection connection, String branch_name, String ifsc_code, String bank_name) throws IOException, SQLException {

        Scanner scanner = new Scanner(System.in);
        String accountNumber = Helper.generateAccountNumber(connection, user_id);
        if (accountNumber == null) {
            accountNumber = Helper.generateAccountNumber(connection, user_id);
        }
        System.out.println("""
                Choose account type(S/C):\s
                1. Saving
                2. Current (C/S)
                """);
        String choice = String.valueOf(scanner.next().charAt(0)).toLowerCase();
        String accountType = (choice.equals("c")) ? "CURRENT" : "SAVING";
        System.out.println("Enter the Initial Balance: ");
        double initialBalance = scanner.nextDouble();
        String account_id = Authentication.validateUuidForAccount(connection);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into accountdetails(account_id,branchname,ifsccode,bankname,accounttype,userid,balance,accountnumber) values(?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, account_id);
            preparedStatement.setString(2, branch_name);
            preparedStatement.setString(3, ifsc_code);
            preparedStatement.setString(4, bank_name);
            preparedStatement.setString(5, accountType);
            preparedStatement.setString(6, user_id);
            preparedStatement.setDouble(7, initialBalance);
            preparedStatement.setString(8, accountNumber);
            int isCreatedAccountCount = preparedStatement.executeUpdate();
            if (isCreatedAccountCount > 0) {
                System.out.println("Your Account Has Been Created ACCOUNT NUMBER: " + accountNumber);
                return accountNumber;
            } else {
                System.out.println("Some Server Error Please");
                return "";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AccountDetails> viewAllAccounts(String userid) {


        return null;
    }

    @Override
    public AccountDetails viewAccountByAccountNumber(String AccountNumber, String user_id) {
        return null;
    }



    @Override
    public double creditAmount(String user_id, Connection connection) {
        Map<Boolean, String> authMap = new HashMap<>();
        authMap = Helper.validateAccountCredentials(connection);
        Scanner scanner = new Scanner(System.in);
        if (authMap.containsKey(true)) {
            System.out.println("Enter the Amount You want to credit: ");
            double amountToCredit = scanner.nextDouble();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select balance from accountdetails where accountnumber= ? ");
                preparedStatement.setString(1, authMap.get(true));
                ResultSet resultSet = preparedStatement.executeQuery();
                Double tempBalance = 0.0;
                while (resultSet.next()) {
                    tempBalance = resultSet.getDouble(1);
                }
                tempBalance = tempBalance + amountToCredit;
                PreparedStatement preparedStatement1 = connection.prepareStatement("update accountdetails set balance= ? where accountnumber= ? ");
                preparedStatement1.setDouble(1, tempBalance);
                preparedStatement1.setString(2, authMap.get(true));
                int creditResult = preparedStatement1.executeUpdate();
                if (creditResult > 0) {
                    System.out.println("Your Amount Has Been Credited Successfully\n" +
                            "Current Account Balance: " + tempBalance);

                } else {
                    System.out.println("Something went Wrong!! Please Try Again");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    @Override
    public double debitAmount(String user_id, Connection connection) {
        Map<Boolean, String> authMap = new HashMap<>();
        authMap = Helper.validateAccountCredentials(connection);
        Scanner scanner = new Scanner(System.in);
        if (authMap.containsKey(true)) {
            System.out.println("Enter the Amount You want to debit: ");
            double amountToDebit = scanner.nextDouble();
            Double tempBalance = 0.0;
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select balance from accountdetails where accountnumber= ? ");
                preparedStatement.setString(1, authMap.get(true));
                ResultSet resultSet = preparedStatement.executeQuery();


                while (resultSet.next()) {
                    tempBalance = resultSet.getDouble(1);
                }
                if (amountToDebit > tempBalance) {
                    System.out.println("Debit Failed Less Fund Available please try again with another amount!!\n" +
                            "Current Balance:" + tempBalance);
                }
                tempBalance = tempBalance - amountToDebit;
                PreparedStatement preparedStatement1 = connection.prepareStatement("update accountdetails set balance= ? where accountnumber= ? ");
                preparedStatement1.setDouble(1, tempBalance);
                preparedStatement1.setString(2, authMap.get(true));
                int creditResult = preparedStatement1.executeUpdate();
                if (creditResult > 0) {
                    System.out.println("Your Amount Has Been Debited Successfully Please Collect Cash>>\n" +
                            "Current Account Balance: " + tempBalance);

                } else {
                    System.out.println("Something went Wrong!! Please Try Again");
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        return 0;
    }

    @Override
    public double viewBalance(String user_id, Connection connection) {
        Map<Boolean, String> authMap = new HashMap<>();
        authMap = Helper.validateAccountCredentials(connection);


        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select balance from accountdetails where accountnumber= ? ");
            preparedStatement.setString(1, authMap.get(true));
            ResultSet resultSet = preparedStatement.executeQuery();

            Double tempBalance = 0.0;
            while (resultSet.next()) {
                tempBalance = resultSet.getDouble(1);
            }
            System.out.println("Current Balance : " + tempBalance);
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
