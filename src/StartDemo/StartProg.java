package startdemo;

import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import progui.MainFrame2;

/**. 
 *��Ŀ������  
 */

public class StartProg {
  /**.  
  */
  public static final Logger log = Logger.getLogger(StartProg.class.toString());

  
  /**.  
   */
  
  public static void main(final String[] args) {                                  
    final String lookAndFeel = UIManager.getSystemLookAndFeelClassName();

    try {
      UIManager.setLookAndFeel(lookAndFeel);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
      | UnsupportedLookAndFeelException e) {
      // TODO �Զ����ɵ� catch ��
      log.warning("Ops!");
      
    }

       
    final MainFrame2 progframe = new MainFrame2();
    progframe.goMainFrame();
  }
}
