import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
    private boolean show = false;
    private String themColor = "blue";
    private boolean tray = false;
    private boolean FollowRedirect = false;

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

        return screen;
    }

    public void setFollowRedirect(boolean followRedirect) {
        FollowRedirect = followRedirect;
    }

    /**
     * we use this method to get all 3 JPanel from other
     * classes and put all of them in a screen JFrame
     */
    public void first(){
//        screen.setVisible(false);
        Screen S = new Screen();
        Requests R = new Requests();

        screen = S.screen();
        requests = R.request(themColor);
        newRequest = R.newRequest(themColor, FollowRedirect);
        respond = R.getNR().ResPond(themColor);

        screen.add(requests, BorderLayout.WEST);
        screen.add(newRequest, BorderLayout.CENTER);
        screen.add(respond, BorderLayout.EAST);


//        showScreen();
        addMenu();
        screen.setVisible(true);

    }

    public void showScreen(){

//        screen.add(requests, BorderLayout.WEST);
//        screen.add(newRequest, BorderLayout.CENTER);
//        screen.add(respond, BorderLayout.EAST);

        Screen S1 = new Screen();
        screen = S1.screen;

        screen.setVisible(true);

//        screen.add(requests);
//        screen.setVisible(true);
//        screen.add(newRequest, BorderLayout.CENTER);
//        screen.add(respond, BorderLayout.EAST);


//        JFrame x1 = new JFrame();
//        x1.setSize(500, 800);
//        if (show) {
//            x1.add(requests);
//            x1.setVisible(true);
//        }

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
                opt.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                opt.setSize(300, 150);
                opt.setLocationRelativeTo(screen);
                JPanel Opt = new JPanel();
                Opt.setLayout(new GridBagLayout());
                GridBagConstraints gbl = new GridBagConstraints();


                JCheckBox followRedirect = new JCheckBox("Follow Redirect");
                followRedirect.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
//                        System.out.println(systemTray.isSelected());
                        FollowRedirect = followRedirect.isSelected();
                        first();
//
//                        setFollowRedirect(followRedirect.isSelected());
//                        jmb.updateUI();
//                        screen.setVisible(false);
//                        if (e.getStateChange()==1){}
//                        if (e.getStateChange()==2){}
                    }
                });


                JCheckBox systemTray = new JCheckBox("System Tray");
                systemTray.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        tray = systemTray.isSelected();
                    }
                });

                String them[] = {"Dark them", "Light them", "Blue them"};
                JComboBox box = new JComboBox(them);
                box.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (box.getItemAt(box.getSelectedIndex()).equals("Blue them")) {
                            themColor = "blue";
//                            System.out.println(themColor);
                            screen.setVisible(false);
                            first();
                        }
                        else if (box.getItemAt(box.getSelectedIndex()).equals("Light them")){
                            themColor = "light";
//                            System.out.println(themColor);
                            screen.setVisible(false);
                            first();
                        }
                        else {
                            themColor = "dark";
//                            System.out.println(themColor);
                            screen.setVisible(false);
                            first();
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


                if (opt.getDefaultCloseOperation() == JFrame.EXIT_ON_CLOSE) {
                    File saveOption = new File("Option.txt");
                    if (saveOption.exists()) {
                        saveOption.delete();
                    }

                    try {
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveOption));
                        out.writeBoolean(FollowRedirect);
                        out.writeBoolean(tray);
                        out.writeBytes(themColor);
                        out.close();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }


            }
        });
        option.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        app.add(option);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(tray);
                if (!tray)
                    System.exit(0);
                else {
                    screen.setVisible(false);
                    if(!SystemTray.isSupported()){
                        System.out.println("System tray is not supported !!! ");
                        return ;
                    }

                    SystemTray systemTray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().getImage("src/images/1.gif");
                    PopupMenu trayPopupMenu = new PopupMenu();

                    MenuItem active = new MenuItem("Active");
                    active.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            screen.setVisible(true);
                        }
                    });
                    trayPopupMenu.add(active);


                    MenuItem Exit = new MenuItem("Exit");
                    Exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                    trayPopupMenu.add(Exit);

                    TrayIcon trayIcon = new TrayIcon(image, "SystemTray Demo", trayPopupMenu);
                    trayIcon.setImageAutoSize(true);

                    try{
                        systemTray.add(trayIcon);
                    }catch(AWTException awtException){
                        awtException.printStackTrace();
                    }
                }
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
//                first();
//                if (show)
//                    screen.remove(requests);
//                showScreen();
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
