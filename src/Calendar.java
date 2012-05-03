import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Calendar extends JPanel{
	JLabel details;
	JButton back;
	JPanel center=new JPanel();
	GridBagLayout constraints=new GridBagLayout();
	
	private static final Calendar singleton=new Calendar();
	public static Calendar getSingleton(){
		return singleton;
		
	}
	Calendar(){
		
		setLayout(new BorderLayout());
		
		center.setLayout(new GridBagLayout());
		center.add(details=new JLabel("The Calendar!"),constraints);
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
