import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class MemberManagement extends JPanel{
	JLabel details;
	JPanel center;
	JButton back;
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
		
		center=new JPanel();
		center.setLayout(new GridBagLayout());
		
		details=new JLabel("Hello member !");
		center.add(details,constraints);
		add(center,BorderLayout.CENTER);
		
		
	}
	
}
