import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



public class mainPage extends JPanel{
	
	JPanel left,center,right,mainabove,mainbelow;
	JButton notifyButton,memberButton,calendarButton,logoutButton;
	GridBagLayout mainLayout,centerLayout,leftLayout,rightLayout;
	GridBagConstraints constraints;
	private static final mainPage singleton= new mainPage();  
	public static mainPage getSingleton(){
	    	return singleton;
	}
	mainPage(){
		
		mainLayout=new GridBagLayout();
		constraints=new GridBagConstraints();
		constraints.gridheight=2;
		
		mainbelow=new JPanel();
		mainbelow.setLayout(mainLayout);
		
		//setting layout
		setLayout(new BorderLayout());
		
		center=new JPanel();
		center.setLayout(centerLayout=new GridBagLayout());
		left=new JPanel();
		left.setLayout(leftLayout=new GridBagLayout());
		right=new JPanel();
		right.setLayout(rightLayout=new GridBagLayout());
		
		center.add(notifyButton=new JButton("Notifications"));
		right.add(memberButton=new JButton("Member Management"));
		left.add(calendarButton=new JButton("Calendar"));
		
		mainbelow.add(left,constraints);
		mainbelow.add(center,constraints);
		mainbelow.add(right,constraints);
		
		add(mainbelow,BorderLayout.CENTER);
		
		mainabove=new JPanel();
		mainabove.setLayout(mainLayout);
		constraints.ipady=30;
		constraints.ipadx=40;
		mainabove.add(logoutButton=new JButton("Logout"),constraints);
		add(mainabove,BorderLayout.SOUTH);
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
