import java.sql.ResultSet;
import java.sql.SQLException;

public class KonnectionTest {

	public static void main(String[] args) {
		ResultSet testResult;
		Konnection test= Konnection.getSingleton();
		String testQuery="Select * from members where FirstName='Abhijit';";
		testResult=test.query(testQuery);
		try{
			
			while(testResult.next()){
		
				System.out.println(testResult.getString("LastName"));
			
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

	}

}
