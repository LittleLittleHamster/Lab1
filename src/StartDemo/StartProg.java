package StartDemo;

import javax.swing.UIManager;

import ProgUI.*;

public class StartProg {
	public static void main(String[] args){
		//���������۸�Ϊϵͳ�����
		try {  
            String lookAndFeel =   
                UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);  
        } catch (Exception e) {}  
		MainFrame ProgFrame = new MainFrame();
	}
	//This is a new line on
	//****
	//3-6
}
