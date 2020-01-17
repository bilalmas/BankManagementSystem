package Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RenewATMCardService {
	public RenewATMCardOutput renewATMCardService(RenewATMCardInput input) throws Exception {
		String atm_card_no ="847317"+Integer.toString((int)Math.round((Math.random()*((999999999-100000000)+1))+100000000));
		try{
			String driver = "com.postgresql.jdbc.Driver";
			String url = "jdbc:postgresql://ec2-23-23-92-204.compute-1.amazonaws.com:5432/d7kk9bebf5f9cm?sslmode=require";
			String username= "yadbkanvxyneec";
			String password= "054fadff9d5900842e0c785e6b985cf53b413ffd5196a32513b06b58e3eeb1d4";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,password);
			
			con.setAutoCommit(false);
			PreparedStatement update1 = con.prepareStatement("UPDATE customerdata SET ATM_Card_No='"+atm_card_no+"', ATM_Pin='"+input.atm_pin+"'  WHERE Customer_ID='"+input.customer_id+"'");
			update1.executeUpdate();
			
			int rf = update1.executeUpdate();
			if( rf ==1){
			con.commit();
			}
			else{
				con.rollback();
			}
		}
		catch(Exception e){
			System.out.println(e);
			RenewATMCardOutput out = new RenewATMCardOutput();
		    out.response = "An Error Occurred. Please try again.";
			return out;
		}
		finally {
			
		}
		RenewATMCardOutput out = new RenewATMCardOutput();
	    out.response = "Your request has been received.You will receive your new ATM card in a few days.";
		return out;
	}
}
