import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Notifications extends JPanel {
	JLabel details;
	JButton back;
	
	private static final Notifications singleton=new Notifications();
	public static Notifications getSingleton(){
		return singleton;
		
	}
	Notifications(){
		
		details=new JLabel("Here are your recent Notifications !");
		add(details);
		back=new JButton("Back to mainpage");
		add(back);
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"mainPage");
			}
		});
	}
}
