import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Notifications extends JPanel {
	JLabel details;
	JButton back;
	JPanel center;
	GridBagConstraints constraints=new GridBagConstraints();
	
	private static final Notifications singleton=new Notifications();
	public static Notifications getSingleton(){
		return singleton;
		
	}
	
	Notifications(){
		
		
		setLayout(new BorderLayout());
		center=new JPanel();
		
		center.setLayout(new GridBagLayout());
		center.add(details=new JLabel("Here are your recent Notifications !"),constraints);
		add(center,BorderLayout.CENTER);
		
		
		back=new JButton("Back to mainpage");
		add(back,BorderLayout.NORTH);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"mainPage");
			}
		});
	}
	
	public void sendPublicNotification(Note publicNote){
		String updateNotifications;
		String updateNotificationMember;
		int notificationId;
		try{
			updateNotifications="insert into notifications values(null,CURRENT_TIMESTAMP,'"+publicNote.senderId+"','" + publicNote.content + "');";
			Konnection.getSingleton().update(updateNotifications);
			
			ResultSet temp=Konnection.getSingleton().query("select max(NotificationId) from notifications");
			temp.next();
			publicNote.noteId=temp.getInt("NotificationId");
			notificationId=publicNote.noteId;
			
			updateNotificationMember="insert into notificationmember values('"+ notificationId + "','0');";
			Konnection.getSingleton().update(updateNotificationMember);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void sendPrivateNotification(Note privateNote){
		String updateNotifications;
		String updateNotificationMember;
		int notificationId;
		try{
			updateNotifications="insert into notifications values(null,CURRENT_TIMESTAMP,'"+privateNote.senderId+"','" + privateNote.content + "');";
			Konnection.getSingleton().update(updateNotifications);
			
			ResultSet temp=Konnection.getSingleton().query("select max(NotificationId) from notifications");
			temp.next();
			privateNote.noteId=temp.getInt("NotificationId");
			notificationId=privateNote.noteId;
			for(int x:privateNote.destinationIds){
				updateNotificationMember="insert into notificationmember values('"+ notificationId + "','" + x + "');";
				Konnection.getSingleton().update(updateNotificationMember);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
}
