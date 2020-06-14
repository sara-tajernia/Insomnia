import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class is for design the request JPanel that includes 3 part
 * one for label twe a JButton to add new request three a JPanel
 * that save all the requests
 */
public class Requests {

    private JPanel requests;
    private JPanel req;
    private JPanel req2;
    private int size;
    private NewRequest NR;
    private JPanel nr;
    private String nameRequest;

    public NewRequest getNR() { return NR; }

    /**
     * open a newRequest class that we can have access
     * to the info
     * @return (JPanel) newRequest
     */

    public JPanel newRequest(String themColor, boolean followRedirect){
//        System.out.println(followRedirect);
        NR = new NewRequest(nameRequest, followRedirect);
        nr = NR.newRquest(themColor);
        return nr;
    }

    public void ColorRequests(String them){

    }

    /**
     * we use this method to create a label and add it to north of JPanel
     * and create a new JPanel to save all te requests and add it in center
     * and a JButton that we can add a new constrain in JPanel
     *
     * @return JPanel request that we can add it to the JFrame
     */

    public JPanel request(String them) {
        requests = new JPanel(new BorderLayout());
        JLabel lable = new JLabel("Graphical User Interface");

        lable.setFont(new Font("Serif", Font.BOLD, 30));
        lable.setHorizontalAlignment(SwingConstants.CENTER);
        lable.setOpaque(true);
//        int hight = lable.getPreferredSize().height + 20;
        lable.setPreferredSize(new Dimension(lable.getPreferredSize().width, lable.getPreferredSize().height + 20));

        req = new JPanel(new BorderLayout());
        req.setBorder(new EmptyBorder(0,0,0,0));
        req2 = new JPanel();
        JButton create = new JButton("Create a new request");
        create.setPreferredSize(new Dimension(create.getPreferredSize().width, create.getPreferredSize().height + 15));
//        create.setHorizontalAlignment(SwingConstants.LEFT);
        create.setOpaque(true);


        if (them == "blue") {
            lable.setBackground(new Color(70, 30, 200));
            req2.setBackground(new Color(100, 130, 180));
            create.setBackground(new Color(70, 30, 200));
            lable.setForeground(Color.white);
        }
        else if (them == "light"){
            lable.setBackground(new Color(169, 169, 169));
            req2.setBackground(new Color(220, 220, 220));
            create.setBackground(new Color(169, 169, 169));
            lable.setForeground(Color.darkGray);
        }
        else {
            lable.setBackground(Color.black);
            req2.setBackground(new Color(98, 98, 98));
            create.setBackground(new Color(55, 55, 55));
            lable.setForeground(Color.white);
        }



        File folder = new File("/Users/sara/IdeaProjects/GUI!");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains("request_")) {
                String split[] = file.getName().split("_|\\.");
                String name = split[1];
                JButton built = new JButton(name);
                if(!(name == null || (name != null && ("".equals(name)))))
                    builtRequest(built, them);
            }
        }

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                nameRequest = JOptionPane.showInputDialog("Enter the name of your new request");
                JButton built = new JButton(nameRequest);
                if(!(nameRequest == null || (nameRequest != null && ("".equals(nameRequest)))))
                    builtRequest(built, them);
//                builtRequest(nameRequest);
                req2.updateUI();
            }
        });
        req.add(create, BorderLayout.NORTH);
        req.add(new JScrollPane(req2));
        requests.add(lable, BorderLayout.NORTH);
        requests.add(req, BorderLayout.CENTER);
        return requests;
    }


    /**
     * we use this method to add a new request to JPanel
     * by action performed we can have access to each request
     * button information
     * @param built that we got from input to save the request by it name
     */
    private void builtRequest(JButton built, String them) {

//        if(name == null || (name != null && ("".equals(name))))
//            return;

        JPanel x = new JPanel();
        x.setLayout(new GridBagLayout());
        GridBagConstraints gbl = new GridBagConstraints();
        gbl.fill = GridBagConstraints.HORIZONTAL;
        if (them == "blue")
            built.setBackground(new Color(80, 130, 180));
        else if (them == "light")
            built.setBackground(new Color(245, 245, 245));
        else
            built.setBackground(new Color(80, 80, 80));

        built.setOpaque(true);
        built.setFont(new Font("Arial", Font.PLAIN, 20));
//        built.setHorizontalAlignment(SwingConstants.LEFT);


//        System.out.println(size);
        if (size > 13)
            req2.setLayout(new GridLayout(size+1,  1));
        else
            req2.setLayout(new GridLayout(14, 1));
//        System.out.println(size);

        gbl.weightx = 15;
        gbl.ipady = 15;
        x.add(built, gbl);
        req2.add(x);

        size++;



        built.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (Component c : NR.getHeader2().getComponents()){
                    if (c instanceof JPanel){
                        JFrame x = new JFrame();
//                        System.out.println("num");
                        x.add(c);
                    }
                }

                for (Component cmp : NR.getFormData2().getComponents()){
                    if (cmp instanceof JPanel){
                        JFrame x = new JFrame();
                        x.add(cmp);
                    }
                }

                String NameRequest = built.getText();
                NR.setNameRequest(NameRequest);

                File folder = new File("/Users/sara/IdeaProjects/GUI!");
                File[] listOfFiles = folder.listFiles();

                int Count = 0;
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().contains("request_" +NR.getNameRequest())) {
//                        System.out.println(NR.getNameRequest());
                        Count++;
                        try {
                            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                            RequestGUI requestGui = (RequestGUI) in.readObject();

                            JTextField test1 = NR.getURL();
                            test1.setText(requestGui.getUrl().toString());

                            JComboBox test2 = NR.getMETHOD();
                            int num = changeMethod(requestGui.getMethod());
                            test2.setSelectedIndex(num);


                            for (Component c : NR.getHeader().getComponents()){
                                if (c instanceof JPanel){
                                    JFrame x = new JFrame();
                                    x.add(c);
                                }
                            }

                            for (Component cmp : NR.getFormData().getComponents()){
                                if (cmp instanceof JPanel){
                                    JFrame x = new JFrame();
                                    x.add(cmp);
                                }
                            }

                            for (String s: requestGui.getHeader()){
                                if (s != null) {
                                    String testHeader[] = s.split(":");
                                    NR.addNew(testHeader[0], testHeader[1], "header");
                                }
                            }

                            for (String c: requestGui.getBody()){
                                if (c != null) {
                                    String testFormData[] = c.split("=");
                                    NR.addNew(testFormData[0], testFormData[1], "Form Data");
                                }
                            }

                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (Count == 0){

//                    for (Component c : NR.getHeader2().getComponents()){
//                        if (c instanceof JPanel){
//                            JFrame x = new JFrame();
//                            System.out.println("num");
//                            x.add(c);
//                        }
//                    }
//
//                    for (Component cmp : NR.getFormData2().getComponents()){
//                        if (cmp instanceof JPanel){
//                            JFrame x = new JFrame();
//                            x.add(cmp);
//                        }
//                    }

//                    JTextField test1 = NR.getURL();
//                    test1.setText("URL");

                    JComboBox test2 = NR.getMETHOD();
                    test2.setSelectedIndex(0);
                    NR.addNew("Header", "Value", "header");
                    NR.addNew("Name", "Value", "Form Data");
//                    NR.getFormData2().updateUI();
//                    NR.getHeader2().updateUI();
//
//                    NR.getFormData().updateUI();
//                    NR.getHeader().updateUI();

                }
                NR.getFormData2().updateUI();
                NR.getHeader2().updateUI();
            }

        });
    }

    /**
     * find the index of methos in JComboBox
     * @param method (String) method
     * @return (index) method
     */

    public int changeMethod(String method){
        if (method.equals("POST")){
            return 1;
        }
        else if (method.equals("PUT")){
            return 2;
        }
        else if (method.equals("PATCH")){
            return 3;
        }
        else if (method.equals("DELETE")){
            return 4;
        }
        else if (method.equals("OPTION")){
            return 5;
        }
        else if (method.equals("HEAD")){
            return 6;
        }
        else {
            return 0;
        }
    }

}
