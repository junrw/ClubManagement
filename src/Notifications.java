import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Notifications extends JPanel {
	JLabel details;
	JLabel from, fromPerson,to, content;
	JButton back;
	
	JButton submitPrivate, submitPublic;
	JButton newPublicNote,newPrivateNote;
	JPanel publicNotePanel,privateNotePanel;
	JPanel centerLeft,centerLeftTop,centerMiddle,centerBottom,bottom;
	JScrollPane allNotificationsScroll;
	
	JTextArea publicContentText,privateContentText;
	JComboBox toPersonChoice;
	GridBagConstraints constraints=new GridBagConstraints();
	JPanel allNotifications,notificationArea;
	String sendSuccess="<html><font color=green> Notification Sent </font></html>";
	JLabel sendSuccessLabel=new JLabel(sendSuccess);
	
	private static final Notifications singleton=new Notifications();
	public static Notifications getSingleton(){
		return singleton;
		
	}
	
	Notifications(){
		
		
		setLayout(new BorderLayout());
		centerLeft=new JPanel();
		
		centerLeft.setLayout(new GridBagLayout());
		
		GridBagConstraints centerLeftConstraints=new GridBagConstraints();
		centerLeftTop=new JPanel();
		centerLeftTop.setLayout(new GridBagLayout());
		
		centerLeftConstraints.gridx=0;
		centerLeftConstraints.gridy=0;
		
		centerLeftConstraints.anchor=GridBagConstraints.FIRST_LINE_START;
		centerLeftTop.add(new JLabel(new ImageIcon("Mail-icon.png")),centerLeftConstraints);
		
		centerLeftConstraints.gridx=0;
		centerLeftConstraints.gridy=1;
		centerLeftTop.add(details=new JLabel("Recent Notifications"),centerLeftConstraints);
		
		centerLeft.add(centerLeftTop,constraints);
		
		notificationArea=new JPanel();
		add(notificationArea,BorderLayout.CENTER);
		bottom=new JPanel();
		bottom.setLayout(new GridLayout(2,0));
		newPublicNote=new JButton("Notification to all",new ImageIcon("Y-Birdie-icon.png"));
		newPublicNote.setHorizontalTextPosition(SwingConstants.CENTER);
		newPublicNote.setVerticalTextPosition(SwingConstants.BOTTOM);
		newPrivateNote=new JButton("Notification to specific member",new ImageIcon("Y-Mail-icon.png"));
		newPrivateNote.setHorizontalTextPosition(SwingConstants.CENTER);
		newPrivateNote.setVerticalTextPosition(SwingConstants.BOTTOM);
		bottom.add(newPublicNote);
		bottom.add(newPrivateNote);
		add(bottom,BorderLayout.WEST);
		
		//centerMiddle=new JPanel();
		
		//centerMiddle.add(allNotificationsScroll);
		//add(allNotificationsScroll,BorderLayout.CENTER);
		//refreshNotifications();
		
		//center.add(centerMiddle,constraints);
		add(centerLeft,BorderLayout.EAST);
		back=new JButton("Back to mainpage");
		add(back,BorderLayout.NORTH);
		
		
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"mainPage");
			}
		});
		
		
		
		newPublicNote.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(login.currentMember.Authority==4){
					MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"ERROR");
				}
				else{
					MainFrame.getSingleton().mainPanel.add(publicNotePanel,"publicNotification");
					MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"publicNotification");
				}
			}
		});
		
		newPrivateNote.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().mainPanel.add(privateNotePanel,"privateNotification");
				updatePrivateNotificationMemberList();
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"privateNotification");
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
			allNotificationsScroll.setBorder(null);
			notificationArea.add(allNotificationsScroll);
			allNotifications.setBorder(BorderFactory.createTitledBorder("Notification Feed"));
			notificationArea.validate();
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void makePublicNoteGUI(){
		publicNotePanel=new JPanel();
		publicNotePanel.setLayout(new BorderLayout());
		
		JPanel addPanel=new JPanel();
		JScrollPane m=new JScrollPane(publicContentText=new JTextArea(10,30));
		JPanel q=new JPanel();
		q.setBorder(BorderFactory.createTitledBorder("Details"));
		q.setLayout(new GridBagLayout());
		addPanel.setLayout(new GridBagLayout());
		m.setBorder(BorderFactory.createTitledBorder("New Message"));
		GridBagConstraints addConstraints=new GridBagConstraints();
		addConstraints.gridx=0;
		addConstraints.gridy=0;
		q.add(from=new JLabel("<html><b>From:</b></html>         "),addConstraints);
		addConstraints.gridx=1;
		addConstraints.gridy=0;
		q.add(fromPerson=new JLabel(login.currentMember.FirstName),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=1;
		q.add(to=new JLabel("<html><b>To:</b></html>          "),addConstraints);
		addConstraints.gridx=1;
		addConstraints.gridy=1;
		q.add(new JLabel("All Members"),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=0;
		addConstraints.gridwidth=2;
		addPanel.add(q,addConstraints);
		//addPanel.add(content=new JLabel("Content:        "),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=1;
		addConstraints.gridwidth=1;
		addConstraints.anchor=GridBagConstraints.CENTER;
		addPanel.add(m,addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=3;
		addConstraints.gridwidth=2;
		addPanel.add(submitPublic=new JButton("Send"),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=4;
		addConstraints.gridwidth=2;
		addPanel.add(sendSuccessLabel,addConstraints);
		sendSuccessLabel.setVisible(false);
		addPanel.validate();
		
		publicNotePanel.add(addPanel,BorderLayout.CENTER);
		
		
		JButton back2=new JButton("Back");
		publicNotePanel.add(back2,BorderLayout.SOUTH);
		
		publicNotePanel.validate();
		
		submitPublic.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Note publicNote=new Note();
				publicNote.content=publicContentText.getText();
				publicNote.senderId=login.currentMember.MemberId;
				sendPublicNotification(publicNote);
				sendSuccessLabel.setVisible(true);
			}
		});
		
		back2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"notifications");
				sendSuccessLabel.setVisible(false);
			}
		});
	}
	
	public void makePrivateNoteGUI(){
		privateNotePanel=new JPanel();
		privateContentText=new JTextArea(10,30);
		privateNotePanel.setLayout(new BorderLayout());
		
		JScrollPane n=new JScrollPane(privateContentText);
		n.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		privateContentText.setLineWrap(true);
		JPanel addPanel=new JPanel();
		JPanel pro=new JPanel();
		

		
		pro.setLayout(new GridBagLayout());
		pro.setBorder(BorderFactory.createTitledBorder("Details"));
		
		addPanel.setLayout(new GridBagLayout());
		n.setBorder(BorderFactory.createTitledBorder("New Message"));
		GridBagConstraints addConstraints=new GridBagConstraints();
		addConstraints.gridx=0;
		addConstraints.gridy=0;
		addConstraints.gridwidth=2;
		pro.add(from=new JLabel("<html><b>From:</b></html>           "),addConstraints);
		addConstraints.gridx=3;
		addConstraints.gridy=0;
		pro.add(fromPerson=new JLabel(login.currentMember.FirstName),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=2;
		pro.add(to=new JLabel("<html><b>To:</b></html>                "),addConstraints);
		addConstraints.gridx=3;
		addConstraints.gridy=2;
		pro.add(toPersonChoice=new JComboBox(),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=0;
		addConstraints.gridwidth=1;
		addPanel.add(pro,addConstraints);
		//addPanel.add(content=new JLabel("Content:          "),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=1;
		addPanel.add(n,addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=3;
		addConstraints.gridwidth=2;
		addPanel.add(submitPrivate=new JButton("Send"),addConstraints);
		addConstraints.gridx=0;
		addConstraints.gridy=4;
		addConstraints.gridwidth=2;
		addPanel.add(sendSuccessLabel,addConstraints);
		sendSuccessLabel.setVisible(false);
		addPanel.validate();
		
		privateNotePanel.add(addPanel,BorderLayout.CENTER);
		
		JButton back2=new JButton("Back");
		privateNotePanel.add(back2,BorderLayout.SOUTH);
		
		privateNotePanel.validate();
		
		submitPrivate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int ids[]=new int[1];
				String selectedMember=(String)toPersonChoice.getSelectedItem();
				String getMemberIdQuery="SELECT ID FROM members WHERE CONCAT( FirstName,  ' ', LastName ) =  '"+selectedMember+"'; ";
				ResultSet getMemberIdResult=Konnection.getSingleton().query(getMemberIdQuery);
				try{
					getMemberIdResult.next();
					ids[0]=getMemberIdResult.getInt("ID");
				}
				catch(SQLException e){
					e.printStackTrace();
				}
				Note privateNote=makePrivateNote(privateContentText.getText(),ids);
				sendPrivateNotification(privateNote);
				sendSuccessLabel.setVisible(true);
			}
		});
		
		back2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"notifications");
				sendSuccessLabel.setVisible(false);
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
		privateNote.destinationIds=id;
		return privateNote;
	}
	
	public void updatePrivateNotificationMemberList(){
		toPersonChoice.removeAllItems();
		String getMemberNameListQuery;
		ResultSet getMemberNameList;
		getMemberNameListQuery="select CONCAT(FirstName, ' ', LastName) as Name from members;";
		try{
			getMemberNameList=Konnection.getSingleton().query(getMemberNameListQuery);
			while(getMemberNameList.next()){
				String memberName=getMemberNameList.getString("Name");
				toPersonChoice.addItem(memberName);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	
}
