import javax.swing.*;
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
    private int size = 1;
    private NewRequest NR;
    private JPanel nr;
    private String nameRequest;

    public NewRequest getNR() { return NR; }

    /**
     * open a newRequest class that we can have access
     * to the info
     * @return (JPanel) newRequest
     */

    public JPanel newRequest(){
        NR = new NewRequest(nameRequest);
        nr = NR.newRquest();
        return nr;
    }

    /**
     * we use this method to create a label and add it to north of JPanel
     * and create a new JPanel to save all te requests and add it in center
     * and a JButton that we can add a new constrain in JPanel
     *
     * @return JPanel request that we can add it to the JFrame
     */

    public JPanel request() {
        requests = new JPanel(new BorderLayout(0, 0));
        JLabel lable = new JLabel("Graphical User Interface");
        lable.setForeground(Color.white);
        lable.setBackground(new Color(70, 30, 200));
        lable.setFont(new Font("Serif", Font.BOLD, 30));
        lable.setHorizontalAlignment(SwingConstants.CENTER);
        lable.setOpaque(true);
        int hight = lable.getPreferredSize().height + 20;
        lable.setPreferredSize(new Dimension(lable.getPreferredSize().width, hight));

        req = new JPanel();
        req.setLayout(null);
        req.setBackground(new Color(100, 130, 180));
        JButton create = new JButton("Create a new request");
        create.setBounds(0, 0, 433, 50);
        create.setBackground(new Color(70, 30, 200));
        create.setOpaque(true);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                nameRequest = JOptionPane.showInputDialog("The name of your new request");
                builtRequest(nameRequest);
            }
        });

        req.add(create, BorderLayout.NORTH);
        requests.add(lable, BorderLayout.NORTH);
        requests.add(req, BorderLayout.CENTER);

        File folder = new File("/Users/sara/IdeaProjects/GUI!");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains("request_")) {
                String split[] = file.getName().split("_|\\.");
                String name = split[1];
                builtRequest(name);
            }
        }
        return requests;
    }


    /**
     * we use this method to add a new request to JPanel
     * by action performed we can have access to each request
     * button information
     * @param name that we got from input to save the request by it name
     */
    private void builtRequest(String name) {


        JButton built = new JButton(name);
        built.setBackground(new Color(80, 130, 180));
        built.setPreferredSize(new Dimension(built.getPreferredSize().width + 30, built.getPreferredSize().height + 10));
        built.setOpaque(true);
        built.setFont(new Font("Arial", Font.PLAIN, 20));
        built.setBounds(0, 50*(size ++), 433, 50);
        req.add(built);
        built.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String NameRequest = built.getText();
                NR.setNameRequest(NameRequest);

                File folder = new File("/Users/sara/IdeaProjects/GUI!");
                File[] listOfFiles = folder.listFiles();

                int Count = 0;
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().contains("request_" +NR.getNameRequest())) {
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

                    JTextField test1 = NR.getURL();
                    test1.setText("URL");

                    JComboBox test2 = NR.getMETHOD();
                    test2.setSelectedIndex(0);
                    NR.addNew("Header", "Value", "header");
                    NR.addNew("Name", "Value", "Form Data");

                }
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
