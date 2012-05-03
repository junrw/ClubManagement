import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Konnection {
	protected static Connection link;
	protected static Statement statement;
    protected static ResultSet set;
    
    private final static Konnection singleton= new Konnection();
    
    Konnection(){
    	String dbUrl="jdbc:mysql://127.0.0.1:3306/SampleClub";
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		link=DriverManager.getConnection(dbUrl, "root", "smo");
    		statement=link.createStatement();
    	}
    	catch(ClassNotFoundException e){
    		e.printStackTrace();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	
    }
    
    public static Konnection getSingleton(){
    	return singleton;
    }
    
    public ResultSet query(String a){
    	try{
    		set=statement.executeQuery(a);
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	return set;
    }
    
    public int update(String a){
    	int rowsAffected=0;
    	try{
    		rowsAffected=statement.executeUpdate(a);
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	return rowsAffected;
    }
    
    public void close(){
    	try{
    		link.close();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    }
}
