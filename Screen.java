import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is for design the screen (JPanel)
 * that includes 3 JFrame and connect between these
 * 3 classes
 */
public class Screen  {
    private JFrame screen;
    private JPanel requests;
    private JPanel newRequest ;
    private JPanel respond;
    private boolean full = false;
    private boolean show ;

    /**
     * manage the option of JFrame
     * @return the total JFrame
     */
    public JFrame screen(){

        screen = new JFrame();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setPreferredSize(new Dimension(1300, 800));
        screen.setSize(1300, 800);
        screen.setLocationRelativeTo(null);
        screen.setLayout(new GridLayout(1,3));          //3 cols for 3 JPanel
        screen.revalidate();
        screen.repaint();

        addMenu();
        return screen;
    }
    /**
     * we use this method to get all 3 JPanel from other
     * classes and put all of them in a screen JFrame
     */
    public void first(){
        Screen S = new Screen();
        Requests R = new Requests();

        screen = S.screen();
        requests = R.request();
        newRequest = R.newRequest();
        respond = R.getNR().ResPond();

        screen.add(requests, BorderLayout.WEST);
        screen.add(newRequest, BorderLayout.CENTER);
        screen.add(respond, BorderLayout.EAST);

        screen.setVisible(true);
    }

    public void showScreen(){

        if (show){
            screen.remove(requests);
        }
    }

    /**
     * we use this method to add a JMenuBar in the JFrame
     * includes(Application, View, Help) that each of them
     * includes some JMenuItem Application(option, Exit)
     * View(Toggle Full Screen, Toggle Sidebar), Help
     * (Help, About)
     */
    public void addMenu(){

        JMenuBar jmb = new JMenuBar();
        jmb.setForeground(Color.white);
        screen.setJMenuBar(jmb);

        JMenu app = new JMenu("Application");
        app.setMnemonic('A');

        JMenuItem option = new JMenuItem("Option");
        option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame opt = new JFrame("Option");
                opt.setSize(300, 150);
                opt.setLocationRelativeTo(screen);
                JPanel Opt = new JPanel();
                Opt.setLayout(new GridBagLayout());
                GridBagConstraints gbl = new GridBagConstraints();
                JCheckBox followRedirect = new JCheckBox("Follow Redirect");
                followRedirect.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange()==1){}
                        if (e.getStateChange()==2){}
                    }
                });
                JCheckBox systemTray = new JCheckBox("System Tray");
                systemTray.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange()==1){}
                        if (e.getStateChange()==2){}
                    }
                });
                String them[] = {"Dark them", "Light them", "Blue them"};
                JComboBox box = new JComboBox(them);
                box.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (box.getItemAt(box.getSelectedIndex()).equals("Light them")) {
                            them.equals("Light");
                            System.out.println("light");
                        }
                    }
                });

                gbl.gridx= 0;
                gbl.gridy= 0;
                Opt.add(followRedirect, gbl);
                gbl.gridy= 1;
                Opt.add(systemTray, gbl);
                gbl.gridy= 2;
                Opt.add(box, gbl);

                opt.add(Opt);
                opt.setVisible(true);
            }
        });
        option.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        app.add(option);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.CTRL_MASK));
        app.add(exit);


        JMenu view = new JMenu("View");
        view.setMnemonic('V');

        JMenuItem full_screen = new JMenuItem("Toggle Full Screen");
        full_screen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!full) {
                    screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    full = true;
                }
                else {
                    screen.setSize(screen.getPreferredSize());
                    full = false;
                }

            }
        });
        full_screen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        view.add(full_screen);

        JMenuItem sidebar = new JMenuItem("Toggle Sidebar");
        sidebar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show = !show;
                System.out.println(show);
                showScreen();
            }                                                                                         ///////////     //////////////////////////////
        });
        sidebar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        view.add(sidebar);


        JMenu help = new JMenu("Help");
        help.setMnemonic('H');

        JMenuItem help1 = new JMenuItem("Help");
        help1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "INFORMATION \n" +
                                "-d, --data <data>   HTTP POST data\n" +
                                "     --data-ascii <data> HTTP POST ASCII data\n" +
                                "     --data-binary <data> HTTP POST binary data\n" +
                                "     --data-raw <data> HTTP POST data, '@' allowed\n" +
                                "     --data-urlencode <data> HTTP POST data url encoded\n" +
                                "     --delegation <LEVEL> GSS-API delegation permission\n" +
                                "     --digest Use HTTP Digest Authentication\n" +
                                " -H, --header <header/@file> Pass custom header(s) to server\n" +
                                " -h, --help This help text\n" +
                                " -i  show whether the headers displayed or not.\n" +
                                " -List  show all the requests\n" +
                                " -O  --output Can save the respond in .txt file\n" +
                                " -S  --save Can save the request in .txt file\n", "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        help.add(help1);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sara Tajernia \n tajernia.sara@gmail.com \n 9831016", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        help.add(about);

        jmb.add(app);
        jmb.add(view);
        jmb.add(help);
        screen.setJMenuBar(jmb);
    }
}
