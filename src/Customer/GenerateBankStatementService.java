package Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GenerateBankStatementService {
	public GenerateBankStatementOutput generateBankStatementService(GenerateBankStatementInput input) throws Exception {
		String bank_statement="";
		try{
			String driver = "com.postgresql.jdbc.Driver";
			String url = "jdbc:postgresql://ec2-23-23-92-204.compute-1.amazonaws.com:5432/d7kk9bebf5f9cm?sslmode=require";
			String username= "yadbkanvxyneec";
			String password= "054fadff9d5900842e0c785e6b985cf53b413ffd5196a32513b06b58e3eeb1d4";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,password);
			
			
			con.setAutoCommit(false);
			PreparedStatement select1=con.prepareStatement("SELECT * FROM transactiondata WHERE Account_No='"+input.account_no+"' OR Other_Account_No='"+input.account_no+"'");
			ResultSet rs = select1.executeQuery();
			while(rs.next())
			{
				bank_statement+="Transaction ID:";
				bank_statement+=Integer.toString(rs.getInt("Transaction_ID"));
				bank_statement+=" Amount:";
				bank_statement+=Integer.toString(rs.getInt("Amount"));
				bank_statement+=" From:Account No.";
				bank_statement+=Integer.toString(rs.getInt("Account_No"));
				bank_statement+=" To:Account No.";
				bank_statement+=Integer.toString(rs.getInt("Other_Account_No"));
				bank_statement+=" Date:";
				bank_statement+=rs.getString("Date_of_Transaction");
				bank_statement+="\r\n";
			}
			PreparedStatement select2=con.prepareStatement("SELECT * FROM account_data WHERE Account_No='"+input.account_no+"'");
			ResultSet rs1 = select2.executeQuery();
			while(rs1.next())
			{
				bank_statement+="Closing Balance:";
				bank_statement+=Integer.toString(rs1.getInt("Balance"));
				bank_statement+="\r\n";
			}
			if (bank_statement=="")
			{
				System.out.println("no transactions found");
			}
			else{
				System.out.println(bank_statement);
				System.out.println("Bank Statement Generated");
			}
		}
		catch(Exception e){
			System.out.println(e);
			GenerateBankStatementOutput out = new GenerateBankStatementOutput();
		    out.bankstatement = "Error Occurred";
			return out;
		}
		finally {
			
		}
		GenerateBankStatementOutput out = new GenerateBankStatementOutput();
	    out.bankstatement = bank_statement;
		return out;
	}
}
