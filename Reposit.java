import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@SuppressWarnings("serial")
public class Reposit extends JFrame{
    JButton button1;
    JButton button2;
    JLabel label;
    JTextField textID;
    JTextField textNAME;
    JTextArea area;
    String s;

    public Reposit() {
        super("insert image to database in java");

        button1 = new JButton("ADD");
        button1.setBounds(200, 300, 100, 40);

        button2 = new JButton("Browse");
        button2.setBounds(80, 300, 100, 40);

        textID = new JTextField("ID");
        textID.setBounds(320, 290, 100, 20);

        textNAME = new JTextField("Name");
        textNAME.setBounds(320, 330, 100, 20);

        area = new JTextArea("Description", 100, 100);

        JScrollPane pane = new JScrollPane(area);
        pane.setBounds(450, 270, 200, 100);

        label = new JLabel();
        label.setBounds(10, 10, 670, 250);

        // Button to insert image into mysql database
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3305/photorepository","root","");
                    PreparedStatement ps = con.prepareStatement("insert into myimages(ID,Name,Description,Image) values(?,?,?,?)");
                    InputStream is = new FileInputStream(new File(s));
                    ps.setString(1, textID.getText());
                    ps.setString(2, textNAME.getText());
                    ps.setString(3, area.getText());
                    ps.setBlob(4, is);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Inserted");
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Button to browse image into jlabel
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    //label.setIcon(ResizeImage(path));
                    s = path;
                }
                else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("No Data");
                }
            }
        });
    }

    public ImageIcon ResizeImage(String imgPath) {
        //
    }
}
