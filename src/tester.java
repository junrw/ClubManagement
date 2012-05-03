import javax.swing.UIManager;


public class tester {
	
	public static void main(String args[]){
	UIManager.LookAndFeelInfo[] smo=UIManager.getInstalledLookAndFeels();
    
	for(UIManager.LookAndFeelInfo x:smo){
		System.out.println(x);
		
	}
}

}
