package entity;

import java.util.UUID;

public class AccountDetails {

    // TODO: 7/8/2024   assign ifsc code and bank name branch name  make this as final
    private static String ifscCode;
    private static String branchName;
    private static String bankName;
    private UUID accountId;
    private String accountType;
    private String accountNumber;
    private UUID userId;

    private double balance;


    public AccountDetails(UUID accountId, String accountType, String accountNumber, UUID userId,double balance) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.balance=balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static String getIfscCode() {
        return ifscCode;
    }

    public static void setIfscCode(String ifscCode) {
        AccountDetails.ifscCode = ifscCode;
    }

    public static String getBranchName() {
        return branchName;
    }

    public static void setBranchName(String branchName) {
        AccountDetails.branchName = branchName;
    }

    public static String getBankName() {
        return bankName;
    }

    public static void setBankName(String bankName) {
        AccountDetails.bankName = bankName;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "AccountDetails{" +
                "accountId=" + accountId +
                ", accountType='" + accountType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", userId=" + userId +
                '}';
    }
}
