package account_teller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WithdrawMoneyService {
	public WithdrawMoneyOutput withdrawMoneyService(WithdrawMoneyInput input) throws Exception {
		int check=1;
		try{
			String driver = "com.postgresql.jdbc.Driver";
			String url = "jdbc:postgresql://ec2-23-23-92-204.compute-1.amazonaws.com:5432/d7kk9bebf5f9cm?sslmode=require";
			String username= "yadbkanvxyneec";
			String password= "054fadff9d5900842e0c785e6b985cf53b413ffd5196a32513b06b58e3eeb1d4";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,password);
						
			con.setAutoCommit(false);

			PreparedStatement select1=con.prepareStatement("SELECT Account_No FROM account_data WHERE Account_No='"+input.account_no+"'");
			ResultSet rs = select1.executeQuery();
			int own_account=0;
			rs.next();
			if (rs!=null)
			{
				own_account = rs.getInt("Account_No");
			}
			else
			{
				check=0;			
			}
			
			PreparedStatement select2=con.prepareStatement("SELECT Balance FROM account_data WHERE Account_No='"+input.account_no+"'");
			ResultSet rs3 = select2.executeQuery();
			rs3.next();
			int own_account_amount = rs3.getInt("Balance");
			int new_balance=own_account_amount;
			if(own_account_amount >= input.withdraw_amount)
			{
				new_balance = own_account_amount - input.withdraw_amount;
			}
			else
			{
				check = 0;
			}

			
			
			PreparedStatement update1=con.prepareStatement("UPDATE account_data SET Balance = '"+new_balance+"' WHERE Account_No='"+own_account+"'");
			update1.executeUpdate();
			
			
			if(check == 1){
				con.commit();
				WithdrawMoneyOutput out = new WithdrawMoneyOutput();
			    out.response = "Your Money has been Withdrawed.";
				return out;
			}
			else{
				con.rollback();
				WithdrawMoneyOutput out = new WithdrawMoneyOutput();
			    out.response = "Not Enough Balance.";
				return out;
			}
		}
		catch(Exception e){
			System.out.println(e);
			WithdrawMoneyOutput out = new WithdrawMoneyOutput();
		    out.response = "An Error Occurred. Please try again.";
			return out;
		}
		finally {
			
		}
	}
}
