import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



public class mainPage extends JPanel{
	
	JPanel mainabove;
	JButton notifyButton,memberButton,calendarButton,logoutButton;
	GridBagLayout centerLayout,leftLayout,rightLayout;
	GridLayout mainLayout;
	GridBagConstraints constraints;
	private static final mainPage singleton= new mainPage();  
	public static mainPage getSingleton(){
	    	return singleton;
	}
	mainPage(){
		
		mainLayout=new GridLayout();
		constraints=new GridBagConstraints();
		
		
		mainabove=new JPanel();
		mainabove.setLayout(mainLayout);
		
		//setting layout
		setLayout(new BorderLayout());
		
		mainabove.add(notifyButton=new JButton("Notifications"));
		mainabove.add(memberButton=new JButton("Member Management"));
		mainabove.add(calendarButton=new JButton("     Calendar     "));
		
		add(mainabove,BorderLayout.NORTH);
		add(logoutButton=new JButton("Logout"),BorderLayout.SOUTH);
		
		memberButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"membermanagement");
			}
		});
		notifyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"notifications");
			}
		});
		calendarButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"calendar");
			}
		});
		
		logoutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"login");
			}
		});
	}

}
