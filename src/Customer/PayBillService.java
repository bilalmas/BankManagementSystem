package Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PayBillService {
	public PayBillOutput payBillService(PayBillInput input) throws Exception {
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
			else{
				check=0;			
			}
			
			
			PreparedStatement select2=con.prepareStatement("SELECT Account_No FROM account_data WHERE Account_No='"+input.bill_account_no+"'");
			ResultSet rs2 = select2.executeQuery();
			int bill_account=0;
			rs2.next();
			if (rs2!=null)
			{
				bill_account = rs2.getInt("Account_No");
			}
			else{
				check=0;
			}
			
			PreparedStatement select3=con.prepareStatement("SELECT Balance FROM account_data WHERE Account_No='"+input.account_no+"'");
			ResultSet rs3 = select3.executeQuery();
			rs3.next();
			int own_account_amount = rs3.getInt("Balance");
			
			if(own_account_amount> input.bill_amount)
			{
				own_account_amount = own_account_amount - input.bill_amount;
			}
			else
			{
				check=0;
			}
					
			PreparedStatement select4=con.prepareStatement("SELECT Balance FROM account_data WHERE Account_No='"+input.bill_account_no+"'");
			ResultSet rs4 = select4.executeQuery();
			rs4.next();
			int bill_account_amount = rs4.getInt("Balance");
			bill_account_amount = bill_account_amount + input.bill_amount;
			
			PreparedStatement insert1=con.prepareStatement("INSERT INTO transactiondata (Account_No, Amount, Other_Account_No) VALUES ('"+input.account_no+"', '"+input.bill_amount+"', '"+input.bill_account_no+"')");
			insert1.executeUpdate();
			PreparedStatement update1=con.prepareStatement("UPDATE account_data SET Balance = '"+own_account_amount+"' WHERE Account_No='"+own_account+"'");
			update1.executeUpdate();
			PreparedStatement update2=con.prepareStatement("UPDATE account_data SET Balance = '"+bill_account_amount+"' WHERE Account_No='"+bill_account+"'");
			update2.executeUpdate();
			
			
			if( check ==1){
				con.commit();
				System.out.println("Bill paid successfully.");
			}
			else{
				con.rollback();
				PayBillOutput out = new PayBillOutput();
			    out.response = "Insufficient Balance";
				return out;
			}
		}
		catch(Exception e){
			System.out.println(e);
			PayBillOutput out = new PayBillOutput();
		    out.response = "Error Occurred in Transaction";
			return out;
		}
		finally {
			
		}
		PayBillOutput out = new PayBillOutput();
	    out.response = "Bill Paid";
		return out;
	}
}
