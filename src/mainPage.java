import javax.swing.*;

import java.awt.*;
import java.awt.event.*;



public class mainPage extends JPanel{
	
	JPanel left,center,right;
	JButton notifyButton,memberButton,calendarButton;
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
		
		setLayout(mainLayout);
		
		center=new JPanel();
		center.setLayout(centerLayout=new GridBagLayout());
		left=new JPanel();
		left.setLayout(leftLayout=new GridBagLayout());
		right=new JPanel();
		right.setLayout(rightLayout=new GridBagLayout());
		
		center.add(notifyButton=new JButton("Notifications"));
		right.add(memberButton=new JButton("Member Management"));
		left.add(calendarButton=new JButton("Calendar"));
		
		add(left,constraints);
		add(center,constraints);
		add(right,constraints);
		
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
	}

}
