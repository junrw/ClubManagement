import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class MemberManagement extends JPanel{
	JLabel details;
	
	JButton back;
	
	private static final MemberManagement singleton=new MemberManagement();
	public static MemberManagement getSingleton(){
		return singleton;
		
	}
	MemberManagement(){
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints=new GridBagConstraints();
		
		
		back=new JButton("Back to mainpage");
		add(back,constraints);
	
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				MainFrame.getSingleton().lay.show(MainFrame.getSingleton().mainPanel,"mainPage");
			}
		});
		
		constraints.anchor=GridBagConstraints.PAGE_END;
		constraints.weighty=0.3;
		details=new JLabel("Hello member !");
		add(details,constraints);
		
		
		
		
		
	}
	
}
