import javax.swing.*;

import java.awt.event.*;
import java.awt.*;


public class MainFrame {
	
	JFrame frame;
	JPanel mainPanel;
	CardLayout lay;
	
	private static MainFrame singleton;
	
	public static MainFrame getSingleton(){
		return singleton;
	}
	MainFrame(){
		singleton=this;
		frame=new JFrame();
	    mainPanel=new JPanel();
		lay=new CardLayout();
		mainPanel.setLayout(lay);
		
		
		mainPanel.add(login.getSingleton(),"login");
		mainPanel.add(mainPage.getSingleton(),"mainPage");
		mainPanel.add(MemberManagement.getSingleton(),"membermanagement");
		mainPanel.add(Notifications.getSingleton(),"notifications");
		mainPanel.add(Calendar.getSingleton(),"calendar");
		
		lay.show(mainPanel,"login");
		
		frame.add(mainPanel);
		
		frame.setSize(700,510);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String args[]){
		try{
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					try{
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
						SwingUtilities.updateComponentTreeUI(new MainFrame().frame);
						}catch(Exception e){System.out.println(e);}
					
				}
			});
		}catch(Exception e){}
	}
	
	
	
	
}
