import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *  GUI
 *  This class is for use all the classes
 *  <h2>Library  simulation  class</h2>
 *  @author Sara Tajernia
 *  @version 1.00
 *  @since 1398-3-23
 */
public class Main {
    public static void main(String[] args) {
//        UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins",
//                new Insets(0, 0, 0, 0));
        Screen screen =new Screen();
        screen.first();
    }
}
