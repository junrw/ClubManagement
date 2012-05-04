import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Notifications extends JPanel {
	JLabel details;
	JButton back;
	JButton newPublicNote,newPrivateNote;
	JPanel center,centerTop,centerMiddle,centerBottom,bottom;
	JScrollPane allNotificationsScroll;
	GridBagConstraints constraints=new GridBagConstraints();
	JPanel allNotifications,notificationArea;
	private static final Notifications singleton=new Notifications();
	public static Notifications getSingleton(){
		return singleton;
		
	}
	
	Notifications(){
		
		
		setLayout(new BorderLayout());
		
		
		
		center=new JPanel();
		
		center.setLayout(new GridBagLayout());
		
		centerTop=new JPanel();
		centerTop.setLayout(new GridBagLayout());
		centerTop.add(details=new JLabel("Recent Notifications !"),constraints);
		center.add(centerTop,constraints);
		
		notificationArea=new JPanel();
		add(notificationArea,BorderLayout.CENTER);
		bottom=new JPanel();
		bottom.setLayout(new GridLayout(1,0));
		newPublicNote=new JButton("Notification to all");
		newPrivateNote=new JButton("Notification to specific member");
		bottom.add(newPublicNote);
		bottom.add(newPrivateNote);
		add(bottom,BorderLayout.SOUTH);
		
		//centerMiddle=new JPanel();
		
		//centerMiddle.add(allNotificationsScroll);
		//add(allNotificationsScroll,BorderLayout.CENTER);
		//refreshNotifications();
		
		//center.add(centerMiddle,constraints);
		add(center,BorderLayout.WEST);
		back=new JButton("Back to mainpage");
		add(back,BorderLayout.NORTH);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"mainPage");
			}
		});
	}
	
	public void refreshNotifications(){
		if(allNotificationsScroll!=null){
			remove(allNotificationsScroll);
		}
		if(notificationArea!=null){
			remove(notificationArea);
			notificationArea=new JPanel();
			add(notificationArea,BorderLayout.CENTER);
			
		}
		allNotifications=new JPanel();
		allNotifications.setLayout(new GridLayout(0,1));
		String currentNotificationsQuery;
		ResultSet currentNotificationResult;
		try{
			currentNotificationsQuery="select * from notifications where NotificationId IN (Select NotificationId from notificationmember where MemberId=' "+ login.currentMember.MemberId + "' OR MemberId='0');";
			System.out.println(currentNotificationsQuery);
			currentNotificationResult=Konnection.getSingleton().query(currentNotificationsQuery);
			while(currentNotificationResult.next()){
				JPanel tempNote= new JPanel();
				tempNote.setLayout(new GridLayout(0,1));
				String memberFrom=currentNotificationResult.getString("MemberFrom");
				String message=currentNotificationResult.getString("Content");
				String timeAt=currentNotificationResult.getString("Time");
				tempNote.add(new JLabel("From: " + memberFrom));
				tempNote.add(new JLabel("Time: " + timeAt));
				tempNote.add(new JLabel("Message: "+ message));
				tempNote.setBorder(BorderFactory.createTitledBorder(timeAt));
				//tempNote.setBorder(BorderFactory.createLineBorder(Color.black));
				System.out.println("\nFrom:" +memberFrom+"\nTime: "+timeAt+"\nMessage: "+message);
				allNotifications.add(tempNote);
			}
			
			allNotificationsScroll=new JScrollPane((allNotifications));
			notificationArea.add(allNotificationsScroll);
			notificationArea.validate();
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
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
