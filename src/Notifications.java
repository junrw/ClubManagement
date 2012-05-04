import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Notifications extends JPanel {
	JLabel details;
	JLabel from, fromPerson,to, content;
	JButton back,back2,submitPublic;
	JButton newPublicNote,newPrivateNote;
	JPanel publicNotePanel,privateNotePanel;
	JPanel center,centerTop,centerMiddle,centerBottom,bottom;
	JScrollPane allNotificationsScroll;
	JTextField toPerson;
	JTextArea contentText;
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
		

		publicNotePanel=new JPanel();
		publicNotePanel.setLayout(new BorderLayout());
		back2=new JButton("Back");
		publicNotePanel.add(back2,BorderLayout.SOUTH);
		
		
		
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"mainPage");
			}
		});
		
		back2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"notifications");
			}
		});
		
		newPublicNote.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().mainPanel.add(publicNotePanel,"publicNotification");
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"publicNotification");
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
				//tempNote.add(new JLabel("Time: " + timeAt));
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
	
	public void makePublicNoteGUI(){
		JPanel addPanel=new JPanel();
		addPanel.setLayout(new GridBagLayout());
		GridBagConstraints addConstraints=new GridBagConstraints();
		addConstraints.gridx=0;
		addConstraints.gridy=0;
		addPanel.add(from=new JLabel("From:"),addConstraints);
		addConstraints.gridx=1;
		addConstraints.gridy=0;
		addPanel.add(fromPerson=new JLabel(login.currentMember.FirstName),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=1;
		addPanel.add(to=new JLabel("To ID:"),addConstraints);
		addConstraints.gridx=1;
		addConstraints.gridy=1;
		addPanel.add(new JLabel("All Members"),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=2;
		addPanel.add(content=new JLabel("Content:"),addConstraints);
		addConstraints.gridx=1;
		addConstraints.gridy=2;
		addPanel.add(contentText=new JTextArea(4,20),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=3;
		addConstraints.gridwidth=2;
		addPanel.add(submitPublic=new JButton("Send"),addConstraints);
		
		publicNotePanel.add(addPanel,BorderLayout.CENTER);
		submitPublic.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Note publicNote=new Note();
				publicNote.content=contentText.getText();
				publicNote.senderId=login.currentMember.MemberId;
				sendPublicNotification(publicNote);
			}
		});
	}
	
	
	public void sendPublicNotification(Note publicNote){
		String notificationsQuery;
		notificationsQuery="insert into notifications values(null,null,'"+publicNote.senderId+"','"+publicNote.content+"',null);";
		try{
			int lastNote;
			ResultSet lastNoteResult;
			
			Konnection.getSingleton().update(notificationsQuery);
			lastNoteResult=Konnection.getSingleton().query("Select max(NotificationId) as NotificationId from notifications");
			lastNoteResult.next();
			lastNote=lastNoteResult.getInt("NotificationId");
			
			String membernotificationQuery;
			membernotificationQuery="insert into notificationmember values('"+lastNote+"','0');";
			Konnection.getSingleton().update(membernotificationQuery);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		refreshNotifications();
	}
	
	public void sendPrivateNotification(Note privateNote){
		String updateNotifications;
		String updateNotificationMember;
		int notificationId;
		try{
			updateNotifications="insert into notifications values(null,null,'"+privateNote.senderId+"','" + privateNote.content + "',null);";
			Konnection.getSingleton().update(updateNotifications);
			
			ResultSet temp=Konnection.getSingleton().query("select max(NotificationId) as NotificationId from notifications");
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
	
	public Note makePrivateNote(String content, int id[]){
		Note privateNote=new Note();
		privateNote.senderId=login.currentMember.MemberId;
		privateNote.content=content;
		return privateNote;
	}
	
	
}
