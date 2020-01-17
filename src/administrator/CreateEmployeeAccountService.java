package administrator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CreateEmployeeAccountService {
	public CreateEmployeeAccountOutput createEmployeeAccountService(CreateEmployeeAccountInput input) throws Exception {
		
		try{
			String driver = "com.postgresql.jdbc.Driver";
			String url = "jdbc:postgresql://ec2-23-23-92-204.compute-1.amazonaws.com:5432/d7kk9bebf5f9cm?sslmode=require";
			String username= "yadbkanvxyneec";
			String password= "054fadff9d5900842e0c785e6b985cf53b413ffd5196a32513b06b58e3eeb1d4";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,username,password);
			
			con.setAutoCommit(false);
			PreparedStatement posted1 = con.prepareStatement("INSERT INTO employeedata (Username, Password, Employee_Type, Name, Address, DOB, Gender, Email, Contact, Nationality, Salary) VALUES ('"+input.username+"', '"+input.password+"', '"+input.csr_or_at+"', '"+input.name+"', '"+input.address+"', '"+input.dob+"', '"+input.gender+"', '"+input.email+"', '"+input.contact+"', '"+input.nationality+"', '"+input.salary+"');");
			int rf = posted1.executeUpdate();
			PreparedStatement posted = con.prepareStatement("SELECT Employee_ID FROM employeedata WHERE Username='"+input.username+"'");
			ResultSet rs = posted.executeQuery();
			rs.next();
			int eid = rs.getInt("Employee_ID");
			if (input.csr_or_at.equals("csr")){
				PreparedStatement posted2 = con.prepareStatement("INSERT INTO csrdata (Employee_ID) VALUES ('"+eid+"')");
				posted2.executeUpdate();
			}
			else{
				PreparedStatement posted3 = con.prepareStatement("INSERT INTO accounttellerdata (Employee_ID) VALUES ('"+eid+"')");
				posted3.executeUpdate();
			}
			if( rf ==1){
			con.commit();
			CreateEmployeeAccountOutput out = new CreateEmployeeAccountOutput();
		    out.response = "Employee Account Created";
			return out;
			}
			else{
				con.rollback();
				CreateEmployeeAccountOutput out = new CreateEmployeeAccountOutput();
			    out.response = "Error Occurred";
				return out;
			}
		}
		catch(Exception e){
			System.out.println(e);
			CreateEmployeeAccountOutput out = new CreateEmployeeAccountOutput();
		    out.response = "Error Occurred";
			return out;
		}
		finally {
			
		}
	}
}
