import java.sql.*;

public class Member {

	public String FirstName;
	public String LastName;
	public String JoinDate;
	public String Position;
	public String Address;
	public int Authority;
	public int MemberId;
	
	public void updateMember(ResultSet memberDetails){
		try{
			MemberId=memberDetails.getInt("ID");
			FirstName=memberDetails.getString("FirstName");
			LastName=memberDetails.getString("LastName");
			Address=memberDetails.getString("Address");
			Position=memberDetails.getString("Position");
			Authority=memberDetails.getInt("Authority");
			JoinDate=memberDetails.getString("JoinDate");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void ConsoleOutput(){
		System.out.println("\nMember Id: "+ MemberId);
		System.out.println("First Name: " + FirstName);
		System.out.println("Last Name: " + LastName);
		System.out.println("Address: " + Address);
		System.out.println("Position: " + Position);
		System.out.println("Authority: " + Authority);
		System.out.println("Join Date: " + JoinDate);
		
	}
	
	
}
