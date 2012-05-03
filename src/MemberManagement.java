import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MemberManagement extends JPanel{
	JLabel details;
	JPanel center;
	JButton back;
	JTable table=new JTable();
	GridBagConstraints constraints;
	private static final MemberManagement singleton=new MemberManagement();
	public static MemberManagement getSingleton(){
		return singleton;
		
	}
	MemberManagement(){
		
		setLayout(new BorderLayout());
		constraints=new GridBagConstraints();
		
		
		
		back=new JButton("Back to mainpage");
		add(back,BorderLayout.NORTH);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"mainPage");
			}
		});
		
		
		int i=0;
		Object [][] display=new Object[10][10];
		String[] colHeads={"ID","FirstName","LastName","JoinDate","Position"}; 
		ResultSet testResult;
		Konnection test= Konnection.getSingleton();
		String testQuery="Select * from members;";
		testResult=test.query(testQuery);
		try{
			
			while(testResult.next()){
		
				display[i][0]=testResult.getString("ID");
				display[i][1]=testResult.getString("FirstName");
				display[i][2]=testResult.getString("LastName");
				display[i][3]=testResult.getString("JoinDate");
				display[i][4]=testResult.getString("Position");
				
				i++;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		
		center=new JPanel();
		center.setLayout(new GridBagLayout());

		details=new JLabel("Hello member !");
		center.add(details,constraints);
		add(center,BorderLayout.CENTER);
	
	}
}