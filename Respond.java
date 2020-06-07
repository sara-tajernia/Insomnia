import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * This class is for design the respond JPanel that includes 2 part
 * and can change in JPanel
 */

public class Respond {



    private String headerRespond;
    private String RaW;
    private JPanel header;
    private JLabel ok;
    private JLabel quality;
    private JLabel time;
    private JTextArea info;
    private JTextArea raw;
    private JLabel visual;

    public JLabel getOk() {
        return ok;
    }

    public JLabel getQuality() {
        return quality;
    }

    public JTextArea getInfo() {
        return info;
    }

    public JTextArea getRaw() {
        return raw;
    }

    public JLabel getVisual() {
        return visual;
    }

    public JLabel getTime() { return time; }

    /**
     * this method use to built a north JPanel and add it to the JPanel to north
     * and a JTabbedPane that have 2 option(Header, Preview)
     * Header show us the header of respond
     * Preview include another 2 tab (Raw Data, Vision Preview)
     * Raw Date show use the byte of respond as a String
     * Vision Preview show us a picture if its a ,png type
     * @return JPanel respond that we can add it to the JFrame
     */
    public JPanel rspond(){

        JPanel respond = new JPanel(new BorderLayout());
        respond.setBackground(new Color(90, 90, 120));

        JTabbedPane tab = new JTabbedPane();
        JPanel header  = HeaderRespond();

        JTabbedPane preview = new JTabbedPane();

        JPanel RAW = new JPanel();
        raw = new JTextArea(RaW, 38 , 30);
        raw.setLineWrap(true);
        raw.setEnabled(false);
        RAW.add(new JScrollPane(raw));


        JPanel VISUAL = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Photo");
        title.setFont(new Font("Serif", Font.BOLD, 25));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        int hight = title.getPreferredSize().height + 20;
        title.setPreferredSize(new Dimension(title.getPreferredSize().width, hight));

        visual = new JLabel();
        visual.setHorizontalAlignment(SwingConstants.CENTER);
        VISUAL.add(title, BorderLayout.NORTH);
        VISUAL.add(new JScrollPane(visual), BorderLayout.CENTER);

        preview.add("Raw Data", RAW);
        preview.add("Vision Preview" , VISUAL);

        tab.add("Header", header);
        tab.add("Preview" , preview);

        JPanel north = North();

        respond.add(north, BorderLayout.NORTH);
        respond.add(tab, BorderLayout.CENTER);

        return respond;
    }

    /**
     * we use this method to create the JPanel that add in header tab
     * hear we have 2 label(name, value) and a JTextArea to show the
     * result and a JButton in the end of JPanel that can copy the
     * header information
     * @return JPanel header to add it in the header tab
     */
    private JPanel HeaderRespond(){

        header  = new JPanel();
        header.setLayout(new GridBagLayout());
        GridBagConstraints gbl = new GridBagConstraints();
        gbl.fill = GridBagConstraints.HORIZONTAL;

        JLabel name = new JLabel("Name");
        name.setBackground(Color.gray);
        name.setOpaque(true);
        name.setFont(new Font("Arial", Font.ITALIC, 15));
        int height = name.getPreferredSize().height +20;
        name.setPreferredSize(new Dimension(name.getPreferredSize().width+5, height));

        JLabel value = new JLabel("Value");
        value.setBackground(Color.gray);
        value.setOpaque(true);
        value.setFont(new Font("Arial", Font.ITALIC, 15));
        value.setPreferredSize(new Dimension(value.getPreferredSize().width +5, height));


        JPanel test = new JPanel();
        info = new JTextArea(headerRespond, 36 , 32);
        info.setLineWrap(true);
        info.setEnabled(false);
        test.add(new JScrollPane(info));


        JButton copy = new JButton("Copy to Clipboard");
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(info.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        gbl.fill = GridBagConstraints.HORIZONTAL;
        gbl.ipady = 20;
        gbl.weightx = 0.5;
        gbl.anchor = GridBagConstraints.FIRST_LINE_START;
        header.add(name, gbl);

        gbl.anchor = GridBagConstraints.FIRST_LINE_END;
        gbl.fill = GridBagConstraints.HORIZONTAL;
        gbl.weightx = 0.5;
        header.add(value, gbl);

        gbl.fill = GridBagConstraints.HORIZONTAL;
        gbl.ipady = 550;
        gbl.gridwidth = 3;
        gbl.gridx = 0;
        gbl.gridy = 1;
        header.add(test, gbl);

        gbl.fill = GridBagConstraints.HORIZONTAL;
        gbl.weighty = 1.0;
        gbl.ipady =10;
        gbl.gridx =1;
        gbl.gridy =2;
        gbl.anchor = GridBagConstraints.LAST_LINE_END;
        header.add(copy, gbl);

        return header;
    }

    /**
     * JPanel than includes 3 JLabel(ok, time, quality)
     * @return JPanel north to add it in north of the respond JPanel
     */
    private JPanel North(){

        JPanel north = new JPanel(new BorderLayout());
        int height = north.getPreferredSize().height +40;
        north.setPreferredSize(new Dimension(north.getPreferredSize().width, height));

        ok = new JLabel("Code: " +0);
        ok.setBackground(Color.pink);
        ok.setOpaque(true);
        ok.setFont(new Font("Serif", Font.BOLD, 15));
        int width1 = ok.getPreferredSize().width +105;
        ok.setPreferredSize(new Dimension(width1, ok.getPreferredSize().height));
        ok.setHorizontalAlignment(SwingConstants.CENTER);

        time = new JLabel("Time");
        time.setBackground(new Color(140, 100, 200));
        time.setOpaque(true);
        time.setFont(new Font("Serif", Font.BOLD, 15));
        time.setHorizontalAlignment(SwingConstants.CENTER);

        quality = new JLabel("Respond");
        quality.setBackground(Color.CYAN);
        quality.setOpaque(true);
        quality.setFont(new Font("Serif", Font.BOLD, 15));
        quality.setPreferredSize(new Dimension(width1, quality.getPreferredSize().height));
        quality.setHorizontalAlignment(SwingConstants.CENTER);

        north.add(ok, BorderLayout.WEST);
        north.add(time, BorderLayout.CENTER);
        north.add(quality, BorderLayout.EAST);

        return north;

    }

}
