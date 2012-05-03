import java.sql.*;

public class Member {

	public String FirstName;
	public String LastName;
	public String JoinDate;
	public String Position;
	public String Address;
	public String Authority;
	
	public void updateMember(ResultSet memberDetails){
		try{
			FirstName=memberDetails.getString("FirstName");
			LastName=memberDetails.getString("LastName");
			Address=memberDetails.getString("Address");
			Position=memberDetails.getString("Position");
			Authority=memberDetails.getString("Authority");
			JoinDate=memberDetails.getString("JoinDate");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void ConsoleOutput(){
		System.out.println("\nFirst Name: " + FirstName);
		System.out.println("Last Name: " + LastName);
		System.out.println("Address: " + Address);
		System.out.println("Position: " + Position);
		System.out.println("Authority: " + Authority);
		System.out.println("Join Date: " + JoinDate);
		
	}
	
	
}
