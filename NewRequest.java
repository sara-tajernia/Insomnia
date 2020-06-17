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
    private JTextField UrL = new JTextField("URL");
    private JComboBox METHOD;
    private int counterHeader = 0;
    private int counterFormData = 0;
    private String saveHeader[];
    private String saveFormData[];
    private boolean boxHeader[];
    private boolean boxFormData[];
    private String nameRequest;
    private Respond RP;
    private JPanel rp;
    private JPanel formData2;
    private JPanel header2;
    private boolean followRedirect = false;


    public NewRequest(String nameRequest, boolean followRedirect){
        this.nameRequest = nameRequest;
        this.followRedirect = followRedirect;
    }


    public String getNameRequest() { return nameRequest; }

    public void setNameRequest(String nameRequest) { this.nameRequest = nameRequest; }

    public JTextField getURL() { return UrL; }

    public JComboBox getMETHOD() { return METHOD; }

    public JPanel getFormData() { return formData; }

    public JPanel getHeader() { return header; }

    public JPanel getHeader2() {
        return header2;
    }

    public JPanel getFormData2() {
        return formData2;
    }

    /**
     * open a respond class that we can have access
     * to the info
     * @return (JPanel) respond
     */
    public JPanel ResPond(String themColor){
        RP = new Respond();
        rp = RP.rspond(themColor);
        return rp;
    }

    /**
     * this method use to built a north JPanel and add it to the JPanel to north
     * and a JTabbedPane that have 4 option(Body, Header, Query, Auth)
     * that Body includes another tab (From Data, Jason) and add it in center
     * of JPanel
     * @return JPanel newRequest that we can add it to the JFrame
     */
    public JPanel newRquest(String themColor){
        JPanel newRequest = new JPanel(new BorderLayout(0,0));
        if (themColor == "blue")
            newRequest.setBackground(new Color(180, 170, 220));
        else if (themColor == "light")
            newRequest.setBackground(new Color(220, 220, 220));
        else
            newRequest.setBackground(new Color(90, 90, 90));


        newRequest.updateUI();
        newRequest.setVisible(true);
        newRequest.revalidate();
        newRequest.repaint();
        JPanel north = North();
        JPanel formData = FormData(themColor);


        JTabbedPane tab = new JTabbedPane();
        JPanel header  = Header(themColor);
        tab.add("Form Data" , formData);
        tab.add("Header", header);

        newRequest.add(north, BorderLayout.NORTH);
        newRequest.add(tab, BorderLayout.CENTER);

        return newRequest;
    }


    public void Calculate1(int type){

        saveHeader = new String[counterHeader];
        int CounterHeader = 0;
        for (Component c : header2.getComponents()){
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
                    if (cmp instanceof JCheckBox){
                        if ((((JCheckBox) cmp).isSelected() && type==2) || type ==1)
                            saveHeader[CounterHeader] = name +":" +Value;
                    }
                }
                CounterHeader++;

            }
        }

        saveFormData = new String[counterFormData];
        boxFormData = new boolean[counterFormData];
        int CounterFormData = 0;
        for (Component c : formData2.getComponents()){
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
                    if (cmp instanceof JCheckBox){
                        boxFormData[CounterFormData] = ((JCheckBox) cmp).isSelected();
                        if ((boxFormData[CounterFormData] && type==2) || type ==1)
                            saveFormData[CounterFormData] = name + "=" + Value;
                    }
                }
                CounterFormData++;

            }
        }
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
                Calculate1(1);

                try {
                    URL url = new URL(UrL.getText());
                    RequestGUI requestGUI = new RequestGUI(url, METHOD.getSelectedItem().toString(), saveFormData, saveHeader);
                    Save save1 = new Save(nameRequest, requestGUI);
                    save1.execute();
                    JOptionPane.showMessageDialog(null, "The operation was successful", "Saving...", JOptionPane.INFORMATION_MESSAGE);
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
                Calculate1(2);
                try {
                    long t1 = System.currentTimeMillis();
                    URL url = new URL(UrL.getText());
                    RequestGUI requestGUI = new RequestGUI(url, METHOD.getSelectedItem().toString(), saveFormData, saveHeader);

                    requestGUI.createRequest(false, "", true, followRedirect);

                    long t2 = System.currentTimeMillis();

                    JLabel test = RP.getTime();
                    String resulte = String.valueOf(t2-t1);
                    test.setText(resulte +" ms");

                    JLabel test1 = RP.getOk();
                    test1.setText("Code: " +Integer.toString(requestGUI.getCode()));

                    JLabel test2 = RP.getQuality();
                    test2.setText(requestGUI.getRespondMessage());

                    String check[] = requestGUI.getUrlCon().getContentType().split("/|\\;");
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

    private JPanel Header(String themColor){

        header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(5, 5, 5, 5));
        header2 = new JPanel();

        JButton plus = new JButton("+");
        if (themColor == "blue")
            plus.setBackground(new Color(150, 140, 230));
        else if (themColor == "light")
            plus.setBackground(new Color(169, 169, 169));
        else {
            plus.setBackground(Color.black);
            plus.setForeground(Color.white);
        }

        plus.setPreferredSize(new Dimension(plus.getPreferredSize().width, plus.getPreferredSize().height +13));
        plus.setOpaque(true);
        plus.setHorizontalAlignment(SwingConstants.CENTER);
        plus.setFocusable(true);
        header.add(plus, BorderLayout.NORTH);

        addNew("Header", "Value", "header");
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNew("Header", "Value", "header");
                header2.updateUI();
            }
        });

        header.add(new JScrollPane(header2));
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
            if (counterHeader > 15)
                header2.setLayout(new GridLayout(counterHeader+1, 1));
            else
                header2.setLayout(new GridLayout(15, 1));
            header2.add(x);
            counterHeader++;
        }

        if (type.equals("Form Data")) {

            if (counterFormData > 15)
                formData2.setLayout(new GridLayout(counterFormData+1, 1));
            else
                formData2.setLayout(new GridLayout(15, 1));
            formData2.add(x);
            counterFormData++;
        }

        trash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame j =new JFrame();
                j.add(x);
                formData2.updateUI();
                header2.updateUI();
            }
        });
    }

    /**
     * we use this method to create the JPanel that add in header tab
     * hear we have a + button that if we press that 2 JTextField(name
     * ,value) and a JCheckBox and a trash button add in the JPanel
     * @return JPanel query to add it in the query tab
     */
    private JPanel FormData(String themColor) {

        formData = new JPanel(new BorderLayout());
        formData.setBorder(new EmptyBorder(5, 5, 5, 5));
        formData2 = new JPanel();


        JButton plus = new JButton("+");
        if (themColor == "blue")
            plus.setBackground(new Color(150, 140, 230));
        else if (themColor == "light")
            plus.setBackground(new Color(169, 169, 169));
        else {
            plus.setBackground(Color.black);
            plus.setForeground(Color.white);
        }

        plus.setPreferredSize(new Dimension(plus.getPreferredSize().width, plus.getPreferredSize().height +13));
        plus.setOpaque(true);
        plus.setHorizontalAlignment(SwingConstants.CENTER);
        plus.setFocusable(true);

        addNew("Name", "Value", "Form Data");
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNew("Name", "Value", "Form Data");
                formData2.updateUI();
            }
        });
        formData.add(plus, BorderLayout.NORTH);
        formData.add(new JScrollPane(formData2));
        return formData;
    }
}
