import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

/**
 * This class is for design the new request JPanel that includes 2 part
 * and can change in JPanel
 */
public class NewRequest {

    private JPanel header;
    private JPanel formData;
    private Color b = new Color(180, 170, 220);
    private JTextField UrL = new JTextField("URL");
    private JComboBox METHOD;

    private int counterHeader = 0;
    private int counterFormData = 0;
    private String saveHeader[];
    private String saveFormData[];
    private String nameRequest;
    private Respond RP;
    private JPanel rp;


    public NewRequest(String nameRequest){
        this.nameRequest = nameRequest;
    }


    public String getNameRequest() { return nameRequest; }

    public void setNameRequest(String nameRequest) { this.nameRequest = nameRequest; }

    public JTextField getURL() { return UrL; }

    public JComboBox getMETHOD() {
        return METHOD;
    }

    public JPanel getFormData() { return formData; }

    public JPanel getHeader() {
        return header;
    }


    /**
     * open a respond class that we can have access
     * to the info
     * @return (JPanel) respond
     */
    public JPanel ResPond(){
        RP = new Respond();
        rp = RP.rspond();
        return rp;
    }

    /**
     * this method use to built a north JPanel and add it to the JPanel to north
     * and a JTabbedPane that have 4 option(Body, Header, Query, Auth)
     * that Body includes another tab (From Data, Jason) and add it in center
     * of JPanel
     * @return JPanel newRequest that we can add it to the JFrame
     */
    public JPanel newRquest(){
        JPanel newRequest = new JPanel(new BorderLayout(0,0));
        newRequest.setBackground(b);
        newRequest.updateUI();
        newRequest.setVisible(true);
        newRequest.revalidate();
        newRequest.repaint();
        JPanel north = North();
        JPanel formData = FormData();


        JTabbedPane tab = new JTabbedPane();
        JPanel header  = Header();
        tab.add("Form Data" , formData);
        tab.add("Header", header);

        newRequest.add(north, BorderLayout.NORTH);
        newRequest.add(tab, BorderLayout.CENTER);

        return newRequest;
    }

    /**
     * JPanel than includes 2 JButton(send, save) and a JComboBox
     * (GET, POST, PUT, PATCH, DELETE, OPTION, HEAD) and a JTextField
     * to get the URL
     * in action performed send we can send a request and wait to
     * show the respond
     * in action performed save we can save a request in the file that
     * we can have the info even for the next run
     * @return JPanel north to add it in north of the newRequest JPanel
     */
    public JPanel North(){

        JPanel north = new JPanel(new BorderLayout());
        JPanel north1 = new JPanel(new BorderLayout());
        int width = north1.getPreferredSize().width +310;
        north1.setPreferredSize(new Dimension(width, north1.getPreferredSize().height));
        JPanel north2 = new JPanel(new BorderLayout());

        String method[] = {"GET", "POST", "PUT", "PATCH", "DELETE", "OPTION", "HEAD"};
        METHOD = new JComboBox(method);
        north1.add(METHOD, BorderLayout.WEST);
        UrL.setFont(new Font("Serif", Font.PLAIN, 18));
        int hight0 = UrL.getPreferredSize().height +20;
        UrL.setPreferredSize(new Dimension(UrL.getPreferredSize().width, hight0));
        north1.add(UrL, BorderLayout.CENTER);

        JButton save = new JButton("Save");
        int width1 = save.getPreferredSize().width -15;
        save.setPreferredSize(new Dimension(width1, hight0));
        north2.add(save, BorderLayout.WEST);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int CounterHeader = 0;
                for (Component c : header.getComponents()){
                    if (c instanceof JPanel){
                        String name = "";
                        String Value = "";
                        int count = 0;
                        for (Component cmp : ((JPanel) c).getComponents()){

                            if (cmp instanceof JTextField){
                                if (name.equals("")){
                                    name = ((JTextField) cmp).getText();
                                }
                                else {
                                    Value = ((JTextField) cmp).getText();
                                }
                            }
                            saveHeader[CounterHeader] = name +":" +Value;
                        }
                        CounterHeader++;

                    }
                }

                saveFormData = new String[counterFormData];
                int CounterFormData = 0;

                for (Component c : formData.getComponents()){
                    if (c instanceof JPanel){
                        String name = "";
                        String Value = "";

                        for (Component cmp : ((JPanel) c).getComponents()){

                            if (cmp instanceof JTextField){
                                if (name.equals("")){
                                    name = ((JTextField) cmp).getText();
                                }
                                else {
                                    Value = ((JTextField) cmp).getText();
                                }
                            }
                            saveFormData[CounterFormData] = name +"=" +Value;
                        }
                        CounterFormData++;

                    }
                }

                try {
                    URL url = new URL(UrL.getText());
                    RequestGUI requestGUI = new RequestGUI(url, METHOD.getSelectedItem().toString(), saveFormData, saveHeader);
                    Save save1 = new Save(nameRequest, requestGUI);
                    save1.execute();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "You cant have this URL", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton send = new JButton("Send");
        send.setPreferredSize(new Dimension(width1, hight0));
        north2.add(send, BorderLayout.EAST);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long t1 = System.currentTimeMillis();
                    System.out.println(System.currentTimeMillis());
                    URL url = new URL(UrL.getText());
                    RequestGUI requestGUI = new RequestGUI(url, METHOD.getSelectedItem().toString(), saveFormData, saveHeader);
                    requestGUI.createRequest(false, "", true, false);
                    System.out.println(System.currentTimeMillis());
                    long t2 = System.currentTimeMillis();

                    JLabel test = RP.getTime();
                    String resulte = String.valueOf(t2-t1);
                    test.setText(resulte +" ms");

                    JLabel test1 = RP.getOk();
                    test1.setText("Code: " +Integer.toString(requestGUI.getCode()));

                    JLabel test2 = RP.getQuality();
                    test2.setText(requestGUI.getRespondMessage());

                    String check[] = requestGUI.getUrlCon().getContentType().split("/|\\;");
                    System.out.println("output " +check[1]);
                    if (check[1].equals("png")){
                        File file = new File(nameRequest +".png");
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(requestGUI.getByte());
                        fos.close();
                        ImageIcon image = new ImageIcon(nameRequest +".png");
                        JLabel test3 = RP.getVisual();
                        test3.setIcon(image);
                    }
                    else {
                        JLabel test3 = RP.getVisual();
                        test3.setIcon(null);
                    }

                    JTextArea test3 = RP.getInfo();
                    test3.setText("");
                    for (String i : requestGUI.getHeaderRespond()){
                        String split[] = i.split("___");
                        test3.setText(test3.getText() +split[0] +"  ---->  " +split[1] +"\n");
                    }

                    JTextArea test4 = RP.getRaw();
                    test4.setText(requestGUI.getOutput());



                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "You cant have this URL", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        north.add(north1, BorderLayout.CENTER);
        north.add(north2, BorderLayout.EAST);
        return north;
    }

    /**
     * we use this method to create the JPanel that add in header tab
     * hear we have a + button that if we press that 2 JTextField(header
     * ,value) and a JCheckBox and a trash button add in the JPanel that
     * if we press the trash button add these field delete
     * @return JPanel header to add it in the header tab
     */

    private JPanel Header(){
        header = new JPanel(new BorderLayout(0,0));
        header.setBorder(new EmptyBorder(0, 5, 5, 5));

        JButton plus = new JButton("+");

        plus.setHorizontalAlignment(SwingConstants.CENTER);
        plus.setFocusable(true);
        header.add(plus, BorderLayout.NORTH);
        plus.setBackground(new Color(150, 140, 230));
        plus.setOpaque(true);

        addNew("Header", "Value", "header");
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNew("Header", "Value", "header");
            }
        });
        return header;
    }
    /**
     * if we press the trash button add those field that added delete
     */
    public void addNew(String name, String Value, String type){

        JPanel x = new JPanel();
        x.setLayout(new GridBagLayout());
        GridBagConstraints gbl = new GridBagConstraints();
        gbl.fill = GridBagConstraints.HORIZONTAL;
        JTextField key = new JTextField(name);
        JTextField value = new JTextField(Value);
        JCheckBox checkBox = new JCheckBox();
        JButton trash = new JButton("X");

        gbl.weightx = 15;
        gbl.ipady = 15;
        x.add(key, gbl);
        x.add(value, gbl);
        gbl.weightx = 0;
        gbl.ipady = 5;
        x.add(checkBox, gbl);
        x.add(trash, gbl);

        if (type.equals("header")) {
            header.setLayout(new GridLayout(15, 4));
            header.add(x);
            counterHeader++;
        }

        if (type.equals("Form Data")) {
            formData.setLayout(new GridLayout(15, 4));
            formData.add(x);
            counterFormData++;
        }

        trash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame j =new JFrame();
                j.add(x);
            }
        });
    }

    /**
     * we use this method to create the JPanel that add in header tab
     * hear we have a + button that if we press that 2 JTextField(name
     * ,value) and a JCheckBox and a trash button add in the JPanel
     * @return JPanel query to add it in the query tab
     */
    private JPanel FormData() {

        formData = new JPanel(new BorderLayout(0, 0));
        formData.setBorder(new EmptyBorder(0, 5, 5, 5));
        JButton plus = new JButton("+");
        plus.setBackground(new Color(150, 140, 230));
        plus.setOpaque(true);
        plus.setHorizontalAlignment(SwingConstants.CENTER);
        plus.setFocusable(true);
        formData.add(plus, BorderLayout.NORTH);

        addNew("Name", "Value", "Form Data");
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNew("Name", "Value", "Form Data");;
            }
        });

        return formData;
    }
}
