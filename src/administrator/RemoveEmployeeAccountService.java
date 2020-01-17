package administrator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RemoveEmployeeAccountService {
public RemoveEmployeeAccountOutput removeEmployeeAccountService(RemoveEmployeeAccountInput input) throws Exception {
		int check=1;
		try{
			String driver = "com.postgresql.jdbc.Driver";
			String url = "jdbc:postgresql://ec2-23-23-92-204.compute-1.amazonaws.com:5432/d7kk9bebf5f9cm?sslmode=require";
			String username= "yadbkanvxyneec";
			String password= "054fadff9d5900842e0c785e6b985cf53b413ffd5196a32513b06b58e3eeb1d4";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,password);
			
			
			con.setAutoCommit(false);
			PreparedStatement posted = con.prepareStatement("SELECT * FROM employeedata WHERE Employee_ID='"+input.eid+"'");
			ResultSet rs = posted.executeQuery();
			rs.next();
			if (rs!=null)
			{
				input.csr_or_at = rs.getString("Employee_Type");
				//System.out.println(csr_or_at);
			}
			else{
				check=0;			
			}
			PreparedStatement posted1 = con.prepareStatement("DELETE FROM employeedata WHERE Employee_ID='"+input.eid+"'");
			int rf = posted1.executeUpdate();
			
			if (input.csr_or_at.equals("csr")){
				//System.out.println("in csr if");
				PreparedStatement posted2 = con.prepareStatement("DELETE FROM csrdata WHERE Employee_ID='"+input.eid+"'");
				posted2.executeUpdate();
			}
			else{
				//System.out.println("in at if");
				PreparedStatement posted3 = con.prepareStatement("DELETE FROM accounttellerdata WHERE Employee_ID='"+input.eid+"'");
				posted3.executeUpdate();
			}
			
			
			
			
				
			if( rf ==1 && check==1){
			con.commit();
			RemoveEmployeeAccountOutput out = new RemoveEmployeeAccountOutput();
		    out.response = "Employee Account Removed";
			return out;
			}
			else{
				con.rollback();
				RemoveEmployeeAccountOutput out = new RemoveEmployeeAccountOutput();
			    out.response = "Error Occurred";
				return out;
			}
		}
		catch(Exception e){
			System.out.println(e);
			RemoveEmployeeAccountOutput out = new RemoveEmployeeAccountOutput();
		    out.response = "Error Occurred";
			return out;
		}
		finally {
			
		}
		
	}
}
