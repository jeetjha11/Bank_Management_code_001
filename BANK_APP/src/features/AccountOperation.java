package features;

import entity.AccountDetails;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AccountOperation {

    String openNewAccount(String user_id, Connection connection,String branch_name,String ifsc_code,String bank_name) throws IOException, SQLException;

    List<AccountDetails> viewAllAccounts(String userid);

    AccountDetails viewAccountByAccountNumber(String AccountNumber,String user_id);

    double viewAccountBalance(String accountNumber,String user_id);

    double creditAmount(String user_id,Connection connection);

    double debitAmount(String accountNumber,String user_id);

    double viewBalance(String accountNumber,String user_id);

}
