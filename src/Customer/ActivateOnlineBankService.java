package Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;




public class ActivateOnlineBankService {


public ActivateOnlineBankOutput activateOnlineBanking(ActivateOnlineBankInput input ) throws Exception{
		
		try{
			String driver = "com.postgresql.jdbc.Driver";
			String url = "jdbc:postgresql://ec2-23-23-92-204.compute-1.amazonaws.com:5432/d7kk9bebf5f9cm?sslmode=require";
			String username= "yadbkanvxyneec";
			String password= "054fadff9d5900842e0c785e6b985cf53b413ffd5196a32513b06b58e3eeb1d4";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,password);
			
			con.setAutoCommit(false);
			PreparedStatement posted1 = con.prepareStatement("INSERT INTO customerdata (Username, Password, Ans_SQ, ATM_Card_No, ATM_Pin, Name, CNIC, Address, DOB, Gender, Email, Contact, Nationality, Salary) VALUES ('"+input.username+"', '"+input.password+"', '"+input.ans_sq+"', '"+input.atm_card_no+"', '"+input.atm_pin+"', '"+input.name+"', '"+input.cnic+"', '"+input.address+"', '"+input.dob+"', '"+input.gender+"', '"+input.email+"', '"+input.contact+"', '"+input.nationality+"', '"+input.salary+"');");
			posted1.executeUpdate();
			PreparedStatement posted = con.prepareStatement("SELECT Customer_ID FROM customerdata WHERE CNIC='"+input.cnic+"'");
			ResultSet rs = posted.executeQuery();
			rs.next();
			int cid = rs.getInt("Customer_ID");
			int zero=0;
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			PreparedStatement posted2 = con.prepareStatement("INSERT INTO account_data (Account_Type, Account_Status, Customer_ID, Balance, Open_Date) VALUES ('"+input.account_type+"', '"+input.account_status+"', '"+cid+"', '"+zero+"', '"+sqlDate+"')");
			
			int rf = posted2.executeUpdate();
			if( rf ==1){
			con.commit();
			
			}
			else{
				con.rollback();
			}
			//System.out.println(posted);
			
			
		}catch(Exception e){
			System.out.println(e);
			//con.rollback();
		}
		finally{ 
		    
		}
		ActivateOnlineBankOutput out = new ActivateOnlineBankOutput();
	    out.response = "account created";
		return out;
	}



}
