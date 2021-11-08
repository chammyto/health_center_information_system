/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AHCISystem;

import static AHCISystem.dashboard.time;
import com.sun.glass.events.KeyEvent;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Reycham
 */
public class staffInfo extends javax.swing.JFrame {
     Date d;
    SimpleDateFormat dd;

    /**
     * Creates new form staffInfo
     */
    public Connection conn;
    public Statement st;
    public String sql;
    public String selectedImagePath = "";
    String r;
    DefaultTableModel model;

    public staffInfo() {
        initComponents();
      //  addTableHeader();
        deactivate();
         showDate();
         changebtn.setVisible(false);
         clickbtn.setVisible(false);
       

        //CONNECT TO DATABASE
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter", "root", "");
            st = conn.createStatement();
            // JOptionPane.showMessageDialog(null, "Connected");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "FAILED", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void activate() {
        searchkey.setEnabled(true);
        searchbtn.setEnabled(true);
        fnametf.setEnabled(true);
        mnametf.setEnabled(true);
        lnametf.setEnabled(true);
        bdatetf.setEnabled(true);
        agetf.setEnabled(true);
        gender.setEnabled(true);
        status.setEnabled(true);
        position.setEnabled(true);
        mobiletf.setEnabled(true);
        addresstf.setEnabled(true);
        startedtf.setEnabled(true);
        clearbtn.setEnabled(true);
        okbtn.setEnabled(true);
        displaybtn.setEnabled(true);
        browsebtn.setEnabled(true);
        picture.setEnabled(true);
        idtf.setEnabled(true);
    }

    public void deactivate() {
        searchkey.setEnabled(false);
        searchbtn.setEnabled(false);
        fnametf.setEnabled(false);
        mnametf.setEnabled(false);
        lnametf.setEnabled(false);
        bdatetf.setEnabled(false);
        agetf.setEnabled(false);
        gender.setEnabled(false);
        status.setEnabled(false);
        position.setEnabled(false);
        mobiletf.setEnabled(false);
        addresstf.setEnabled(false);
        startedtf.setEnabled(false);
        clearbtn.setEnabled(false);
        okbtn.setEnabled(false);
        displaybtn.setEnabled(false);
        browsebtn.setEnabled(false);
        picture.setEnabled(false);
        idtf.setEnabled(false);
    }

    public void refresh() {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        model.setRowCount(0);
        showDataTable();
    }

    public void reflect() {
        
        int i = jTable.getSelectedRow();
        TableModel model = jTable.getModel();
        idtf.setText(model.getValueAt(i, 0).toString());
        fnametf.setText(model.getValueAt(i, 1).toString());
        mnametf.setText(model.getValueAt(i, 2).toString());
        lnametf.setText(model.getValueAt(i, 3).toString());
        bdatetf.setText(model.getValueAt(i, 4).toString());
        agetf.setText(model.getValueAt(i, 5).toString());
        gender.setSelectedItem(model.getValueAt(i, 6).toString());
        status.setSelectedItem(model.getValueAt(i, 7).toString());
        position.setSelectedItem(model.getValueAt(i, 8).toString());
        mobiletf.setText(model.getValueAt(i, 9).toString());
        addresstf.setText(model.getValueAt(i, 10).toString());
        startedtf.setText(model.getValueAt(i, 11).toString());
        
     
    }

    public void clear() {
        searchkey.setText("");
        fnametf.setText("");
        mnametf.setText("");
        lnametf.setText("");
        bdatetf.setText("");
        agetf.setText("");
        gender.setSelectedIndex(0);
        status.setSelectedIndex(0);
        position.setSelectedIndex(0);
        mobiletf.setText("");
        addresstf.setText("");
        startedtf.setText("");
        picture.setIcon(null);
        idtf.setText(null);
    }

  
     public void add_data(){
            // TODO add your handling code here:
        try{
       //   Class.forName("com.mysql.jdbc.Driver");
        //  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
          PreparedStatement ps = conn.prepareStatement("insert into staff_info(staff_id, f_name, m_name, l_name, birth_date, age, gender, status, position, phone, address, date_started, image)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
          InputStream is = new FileInputStream(new File(r));
          ps.setString(1, idtf.getText());
          ps.setString(2, fnametf.getText());
          ps.setString(3,mnametf.getText());
          ps.setString(4,lnametf.getText());
          ps.setString(5,bdatetf.getText());
          ps.setString(6,agetf.getText());
          ps.setString(7,gender.getSelectedItem().toString());
          ps.setString(8,status.getSelectedItem().toString());
          ps.setString(9, position.getSelectedItem().toString());
          ps.setString(10,mobiletf.getText());
          ps.setString(11,addresstf.getText());
          ps.setString(12,date.getText());
          ps.setBlob(13, is);
          ps.executeUpdate();
          JOptionPane.showMessageDialog(null, "Registered Successfully");
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }   
        
          try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into staff_reports (ID, Date, Time, Status) values ('"+idtf.getText()+"','"+date.getText()+"','"+time.getText()+"','New Staff Added')";
                st.executeUpdate(sql);
              

                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
    }
    public void updateStaff(){
         try{
          Class.forName("com.mysql.jdbc.Driver");
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
          PreparedStatement ps = conn.prepareStatement("UPDATE `staff_info` SET `staff_id`=?,`f_name`=?,`m_name`=?,`l_name`=?,`birth_date`=?,`age`=?,`gender`=?,"
                  + "`status`=?,`position`=?,`phone`=?,`address`=?,`date_started`=?  WHERE staff_id="+idtf.getText());
        //  InputStream is = new FileInputStream(new File(r));
          ps.setString(1, idtf.getText());
          ps.setString(2, fnametf.getText());
          ps.setString(3,mnametf.getText());
          ps.setString(4,lnametf.getText());
          ps.setString(5,bdatetf.getText());
          ps.setString(6,agetf.getText());
          ps.setString(7,gender.getSelectedItem().toString());
          ps.setString(8,status.getSelectedItem().toString());
          ps.setString(9, position.getSelectedItem().toString());
          ps.setString(10,mobiletf.getText());
          ps.setString(11,addresstf.getText());
          ps.setString(12,startedtf.getText());
        //  ps.setBlob(13, is);
          ps.executeUpdate();
          JOptionPane.showMessageDialog(null, "Updated successfully!");
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }    
         try{
               ResultSet rs = st.executeQuery("select * from staff_info where staff_id='" + idtf.getText() + "'");
          if (rs.next()) {
                byte[] img = rs.getBytes("image");
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myImg = im.getScaledInstance(picture.getWidth(), picture.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myImg);
                picture.setIcon(newImage);
            } else {
                JOptionPane.showMessageDialog(null, "No data found");
            }                        
         }  catch(Exception e){
             
         }   
         //Record to report
               try{
                st = conn.createStatement();
                sql= "insert into staff_reports (ID, Date, Time, Status) values ('"+idtf.getText()+"','"+date.getText()+"','"+time.getText()+"','Staff Record Updated')";
                st.executeUpdate(sql);  
               }catch(Exception e){
                   
               }
 }
    public void update_image(){
        try{
          Class.forName("com.mysql.jdbc.Driver");
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
          PreparedStatement ps = conn.prepareStatement("UPDATE `staff_info` SET image = ?  WHERE staff_id="+idtf.getText());
          InputStream is = new FileInputStream(new File(r));
          ps.setBlob(1, is);
          ps.executeUpdate();
          JOptionPane.showMessageDialog(null, "Updated successfully!");
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
        }
        //Record to reports
          try{
                st = conn.createStatement();
                sql= "insert into staff_reports (ID, Date, Time, Status) values ('"+idtf.getText()+"','"+date.getText()+"','"+time.getText()+"','Staff Record Updated')";
                st.executeUpdate(sql);  
               }catch(Exception e){
                   
               }
    }

    public void delete() {
        try {
            sql = "DELETE FROM `staff_info` WHERE staff_id =" + idtf.getText();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Staff Deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "FAILED", JOptionPane.ERROR_MESSAGE);
        }
        
          try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into staff_reports (ID, Date, Time, Status) values ('"+idtf.getText()+"','"+date.getText()+"','"+time.getText()+"','Staff Record Deleted')";
                st.executeUpdate(sql);
                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
    }

    public void showDataTable() {
       
        try {
            PreparedStatement st = conn.prepareStatement("Select * from staff_info");
            ResultSet rs = st.executeQuery();
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Object ob[] = {rs.getInt("staff_id"), rs.getString("f_name"), rs.getString("m_name"), rs.getString("l_name"),
                    rs.getString("birth_date"), rs.getString("age"), rs.getString("gender"), rs.getString("status"), 
                    rs.getString("position"), rs.getString("phone"), rs.getString("address"), rs.getString("date_started")};
                model.addRow(ob);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void search() {
        
        try {
            sql = "SELECT * from staff_info WHERE staff_id=? OR f_name=? OR m_name=? OR l_name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, searchkey.getText());
             pst.setString(2, searchkey.getText());
              pst.setString(3, searchkey.getText());
               pst.setString(4, searchkey.getText());
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {        //Condition

                //Display data to textfield
                idtf.setText(rs.getString("staff_id"));
                fnametf.setText(rs.getString("f_name"));
                mnametf.setText(rs.getString("m_name"));
                lnametf.setText(rs.getString("l_name"));
                bdatetf.setText(rs.getString("birth_date"));
                agetf.setText(rs.getString("age"));
                gender.setSelectedItem(rs.getString("gender"));
                status.setSelectedItem(rs.getString("status"));
                position.setSelectedItem(rs.getString("position"));
                mobiletf.setText(rs.getString("phone"));
                addresstf.setText(rs.getString("address"));
                startedtf.setText(rs.getString("date_started"));

                Object ob[] = {rs.getInt("staff_id"), rs.getString("f_name"), rs.getString("m_name"), rs.getString("l_name"), 
                    rs.getString("birth_date"), rs.getString("age"), rs.getString("gender"), rs.getString("status"), rs.getString("position"), 
                    rs.getString("phone"), rs.getString("address"), rs.getString("date_started")};
      
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void display(){
         try {
            PreparedStatement st = conn.prepareStatement("SELECT * from staff_info WHERE staff_id=? OR f_name=? OR m_name=? OR l_name=?");
            st.setString(1, searchkey.getText());
             st.setString(2, searchkey.getText());
              st.setString(3, searchkey.getText());
               st.setString(4, searchkey.getText());
            ResultSet rs = st.executeQuery();
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                
                idtf.setText(rs.getString("staff_id"));
                fnametf.setText(rs.getString("f_name"));
                mnametf.setText(rs.getString("m_name"));
                lnametf.setText(rs.getString("l_name"));
                bdatetf.setText(rs.getString("birth_date"));
                agetf.setText(rs.getString("age"));
                gender.setSelectedItem(rs.getString("gender"));
                status.setSelectedItem(rs.getString("status"));
                position.setSelectedItem(rs.getString("position"));
                mobiletf.setText(rs.getString("phone"));
                addresstf.setText(rs.getString("address"));
                startedtf.setText(rs.getString("date_started")); 
                
                
                Object ob[] = {rs.getInt("staff_id"), rs.getString("f_name"), rs.getString("m_name"), rs.getString("l_name"),
                    rs.getString("birth_date"), rs.getString("age"), rs.getString("gender"), rs.getString("status"), 
                    rs.getString("position"), rs.getString("phone"), rs.getString("address"), rs.getString("date_started")};
                model.addRow(ob);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        mobiletf = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        picture = new javax.swing.JLabel();
        browsebtn = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        fnametf = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        mnametf = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox<>();
        gender = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        agetf = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        lnametf = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        startedtf = new javax.swing.JTextField();
        okbtn = new javax.swing.JButton();
        clearbtn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        bdatetf = new javax.swing.JTextField();
        position = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        addresstf = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        idtf = new javax.swing.JTextField();
        changebtn = new javax.swing.JButton();
        clickbtn = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        displaybtn = new javax.swing.JButton();
        searchbtn = new javax.swing.JButton();
        searchkey = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(110, 0));
        setMinimumSize(new java.awt.Dimension(1500, 1000));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("AMBUAN HEALTH CENTER INFORMATION SYSTEM");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(407, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(343, 343, 343))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel4)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1510, 110));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("ADD STAFF");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton2KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 240, 50));

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("SEARCH STAFF");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 240, 50));

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("UPDATE STAFF");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton3KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 240, 50));

        jPanel13.setBackground(new java.awt.Color(51, 51, 51));

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("DELETE STAFF");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 240, 50));

        jPanel18.setBackground(new java.awt.Color(51, 51, 51));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton6.setBackground(new java.awt.Color(0, 153, 153));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton6.setText("Exit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel18.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 240, -1));

        jPanel9.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 240, 50));

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Menu");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(66, 66, 66))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 50));

        jPanel19.setBackground(new java.awt.Color(51, 51, 51));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton7.setBackground(new java.awt.Color(0, 153, 153));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton7.setText("Dashboard");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel19.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 240, -1));

        jPanel9.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 240, 50));

        jPanel7.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 240, 460));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 260, 530));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 260, 540));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel2.setText("Staff Information Form");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setText("Date of Birth:");
        jPanel14.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, -1, -1));

        mobiletf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        mobiletf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mobiletfKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mobiletfKeyTyped(evt);
            }
        });
        jPanel14.add(mobiletf, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 250, 200, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setText("Address:");
        jPanel14.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setText("Age:");
        jPanel14.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        picture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel14.add(picture, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 30, 250, 210));

        browsebtn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        browsebtn.setText("Browse");
        browsebtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                browsebtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                browsebtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                browsebtnMouseExited(evt);
            }
        });
        browsebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browsebtnActionPerformed(evt);
            }
        });
        jPanel14.add(browsebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(913, 250, 250, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setText("First Name:");
        jPanel14.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        fnametf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        fnametf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fnametfKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fnametfKeyTyped(evt);
            }
        });
        jPanel14.add(fnametf, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 160, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setText("Middle Name:");
        jPanel14.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        mnametf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        mnametf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnametfActionPerformed(evt);
            }
        });
        mnametf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mnametfKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mnametfKeyTyped(evt);
            }
        });
        jPanel14.add(mnametf, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 160, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel11.setText("Last Name:");
        jPanel14.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setText("Position:");
        jPanel14.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, -1, -1));

        status.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Single", "Married", "Widowed", "Separated" }));
        status.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statusMouseClicked(evt);
            }
        });
        status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                statusKeyPressed(evt);
            }
        });
        jPanel14.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 160, -1));

        gender.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female" }));
        gender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                genderMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                genderMousePressed(evt);
            }
        });
        gender.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                genderKeyPressed(evt);
            }
        });
        jPanel14.add(gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 160, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setText("Date Started:");
        jPanel14.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 300, -1, -1));

        agetf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        agetf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                agetfKeyTyped(evt);
            }
        });
        jPanel14.add(agetf, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, 160, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setText("Gender:");
        jPanel14.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, -1));

        lnametf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lnametf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lnametfKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lnametfKeyTyped(evt);
            }
        });
        jPanel14.add(lnametf, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 160, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setText("Civil Status:");
        jPanel14.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, -1, -1));

        startedtf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        startedtf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                startedtfKeyTyped(evt);
            }
        });
        jPanel14.add(startedtf, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 300, 200, -1));

        okbtn.setBackground(new java.awt.Color(0, 153, 153));
        okbtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        okbtn.setText("Ok");
        okbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okbtnMouseClicked(evt);
            }
        });
        okbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okbtnActionPerformed(evt);
            }
        });
        jPanel14.add(okbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 390, 200, 40));

        clearbtn.setBackground(new java.awt.Color(255, 255, 0));
        clearbtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        clearbtn.setText("Clear");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });
        jPanel14.add(clearbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 340, 200, 40));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel15.setText("Mobile Phone:");
        jPanel14.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, -1, -1));

        bdatetf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bdatetf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                bdatetfKeyTyped(evt);
            }
        });
        jPanel14.add(bdatetf, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 200, -1));

        position.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        position.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Doctor", "Nurse", "Midwife", "Dentist", "BHW" }));
        position.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                positionMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                positionMousePressed(evt);
            }
        });
        jPanel14.add(position, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 200, 200, -1));

        addresstf.setColumns(20);
        addresstf.setFont(new java.awt.Font("Monospaced", 1, 15)); // NOI18N
        addresstf.setRows(5);
        addresstf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                addresstfKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(addresstf);

        jPanel14.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, 200, 90));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setText("Staff ID:");
        jPanel14.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

        idtf.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        idtf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel14.add(idtf, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 160, 40));

        changebtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        changebtn.setText("Change Profile Picture");
        changebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changebtnActionPerformed(evt);
            }
        });
        jPanel14.add(changebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 110, 10, -1));

        clickbtn.setText("jButton8");
        jPanel14.add(clickbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 170, 10, -1));

        jPanel4.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 90, 1230, 460));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel16.setText("Time:");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 50, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel17.setText("Date:");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, -1, -1));

        date.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        date.setText("00-00-0000");
        jPanel4.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, 140, 30));

        time.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        time.setText("00:00:00");
        jPanel4.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, 170, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 1230, 550));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable.setBackground(new java.awt.Color(0, 153, 153));
        jTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Staff ID", "First Name", "Middle Name", "Last Name", "Birthday", "Age", "Gender", "Civil Status", "Position", "Mobile Phone", "Address", "Date Started"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 1420, 220));

        displaybtn.setBackground(new java.awt.Color(0, 153, 153));
        displaybtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        displaybtn.setText("Display all staff");
        displaybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displaybtnActionPerformed(evt);
            }
        });
        jPanel5.add(displaybtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, 220, -1));

        searchbtn.setBackground(new java.awt.Color(0, 153, 153));
        searchbtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        searchbtn.setText("Search");
        searchbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchbtnMouseClicked(evt);
            }
        });
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });
        jPanel5.add(searchbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 140, 40));

        searchkey.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        searchkey.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchkeyMouseClicked(evt);
            }
        });
        searchkey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchkeyKeyTyped(evt);
            }
        });
        jPanel5.add(searchkey, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 310, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, 1500, 290));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1520, 1000));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        activate();    
         okbtn.setVisible(true);
        okbtn.setLabel("Save");
        browsebtn.setText("Browse");
      
        

    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton2KeyTyped

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        activate();
        okbtn.setVisible(true);
        okbtn.setText("Update");
      //  changebtn.setVisible(true);
      browsebtn.setText("Change Picture");

    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton3KeyTyped

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked

        activate();
       // okbtn.setLabel("");
        okbtn.setVisible(false);
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        activate();
         okbtn.setVisible(true);
        okbtn.setLabel("Delete");
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int opt = JOptionPane.showConfirmDialog(this, "Would you like to EXIT? \n Click 'YES' or 'ENTER' to proceed.", "Message", JOptionPane.YES_NO_OPTION);
        if (opt == 0) {
            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        new dashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void browsebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browsebtnActionPerformed
        // TODO add your handling code here:
   
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File(System.getProperty("user.home")));
         //filter image extensions
        FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
        jfc.addChoosableFileFilter(filter);
        int result = jfc.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String Path = selectedFile.getAbsolutePath();
            JOptionPane.showMessageDialog(null, Path);
            //display image on jTable
            ImageIcon ii = new ImageIcon(Path);

            //resize image to fit jLabel
            Image image = ii.getImage().getScaledInstance(picture.getWidth(), picture.getHeight(), Image.SCALE_SMOOTH);
            picture.setIcon(new ImageIcon(image));
            r = Path;
            
        }else{
            JOptionPane.showMessageDialog(null, "No data");
        }
        clickbtn.setText("go");
    }//GEN-LAST:event_browsebtnActionPerformed

    private void okbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okbtnActionPerformed
        // TODO add your handling code here:
        int opt;
        if (okbtn.getLabel() == "Save") {
            if (idtf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Some fields are empty!", "Message", JOptionPane.ERROR_MESSAGE);
          } else {

                opt = JOptionPane.showConfirmDialog(this, "Would you like to save? \nClick 'YES' or 'ENTER' to continue.", "Message", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                  
                   try{

                    Class.forName("com.mysql.jdbc.Driver");
                   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");

                   String sql = "Select staff_id from staff_info WHERE staff_id="+idtf.getText();
                   PreparedStatement ps = conn.prepareStatement(sql);
                   ResultSet rs = ps.executeQuery(); 

                   if(rs.next()){
                       JOptionPane.showMessageDialog(null, "This is invalid. \n Account has duplicate ID. \n Please craete a new one.");
                   }else{
                     add_data();  
                
                   } 
                }catch(Exception e){         
                }
                              
               }
            }
        }
        
        if (okbtn.getLabel() == "Update") {
            if (idtf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Choose staff to update.", "OPS!", JOptionPane.ERROR_MESSAGE);
            } else {
                opt = JOptionPane.showConfirmDialog(this, "Would you like to update patient information?\n Click 'YES' or 'ENTER' to continue.", "Message", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    
                    if(browsebtn.getText()=="Change Picture"){
                    updateStaff();
                    refresh();
                    
                }else if(browsebtn.getText()=="Browse"){
                        update_image();
           
            } else{
                    
                }
                    if(changebtn.getText()=="change" && clickbtn.getText()=="go"){
                    update_both();
            }
            }
            }
        }      
        if (okbtn.getLabel() == "Delete") {
            if (idtf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Choose staff to delete.", "OPS!", JOptionPane.ERROR_MESSAGE);
            } else {
                opt = JOptionPane.showConfirmDialog(this, "Would you like to delete? \n This cannot be undone. \n Data will be permanently deleted from the database.\nClick 'YES' or 'ENTER' to continue.\n'NO' to cancel.", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (opt == 0) {
                    try {
                        sql = "SELECT * from staff_info WHERE staff_id=?";
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setString(1, idtf.getText());
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
                            delete();
                            refresh();
                            clear();
                        } else {
                            JOptionPane.showMessageDialog(null, "No data found");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            
            }
        }   
    }//GEN-LAST:event_okbtnActionPerformed

    private void okbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okbtnMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_okbtnMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        clear();
        searchkey.requestFocus();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void searchkeyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchkeyMouseClicked
            searchkey.setText(null);
    }//GEN-LAST:event_searchkeyMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        searchkey.requestFocus();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        fnametf.requestFocus();
        searchbtn.setEnabled(true);
        idtf.setEditable(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_clearbtnActionPerformed

    private void displaybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displaybtnActionPerformed
        // Displaying data from database to Table
        showDataTable();
    }//GEN-LAST:event_displaybtnActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        // TODO add your handling code here:
         reflect();
           try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter?zeroDateTimeBehavior=convertToNull", "root", "");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from staff_info where staff_id='" + idtf.getText() + "'");
            if (rs.next()) {
                byte[] img = rs.getBytes("image");
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myImg = im.getScaledInstance(picture.getWidth(), picture.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myImg);
                picture.setIcon(newImage);
            } else {
                JOptionPane.showMessageDialog(null, "No data found");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
       


    }//GEN-LAST:event_jTableMouseClicked

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        // TODO add your handling code here:
        if (searchkey.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID or Name to Search.", "OPS!", JOptionPane.ERROR_MESSAGE);
        } else {
          // search();
         display();
        
        }
    }//GEN-LAST:event_searchbtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void searchbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchbtnMouseClicked
        // TODO add your handling code here:
      
       click();
    }//GEN-LAST:event_searchbtnMouseClicked

    private void fnametfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fnametfKeyPressed
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        
        if(Character.isLetter(c)|| Character.isWhitespace(c)|| Character.isISOControl(c)){
            fnametf.setEditable(true);
        }else{
            fnametf.setEditable(false);
        }
    }//GEN-LAST:event_fnametfKeyPressed

    private void mnametfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnametfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnametfActionPerformed

    private void mnametfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mnametfKeyPressed
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        
        if(Character.isLetter(c)|| Character.isWhitespace(c)|| Character.isISOControl(c)){
            mnametf.setEditable(true);
        }else{
            mnametf.setEditable(false);
        }
    }//GEN-LAST:event_mnametfKeyPressed

    private void lnametfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lnametfKeyPressed
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        
        if(Character.isLetter(c)|| Character.isWhitespace(c)|| Character.isISOControl(c)){
            lnametf.setEditable(true);
        }else{
            lnametf.setEditable(false);
        }
    }//GEN-LAST:event_lnametfKeyPressed

    private void mobiletfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mobiletfKeyPressed
        // TODO add your handling code here:
         String phoneNumber = mobiletf.getText();
        int length = phoneNumber.length();
        char c= evt.getKeyChar();
        
        if( c>='0'&& c <= '9'){
            if(length <= 10){
                mobiletf.setEditable(true);
            }else{
                mobiletf.setEditable(false);
            }
        }else{
            if(evt.getExtendedKeyCode()==KeyEvent.VK_BACKSPACE || evt.getExtendedKeyCode()==KeyEvent.VK_DELETE){
                mobiletf.setEditable(true);
            }else{
                mobiletf.setEditable(false);
            }
        }
    }//GEN-LAST:event_mobiletfKeyPressed

    private void searchkeyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchkeyKeyTyped
        // TODO add your handling code here:
   //        char input = evt.getKeyChar();
    //   if(!(Character.isDigit(input))
     //          ||(input == KeyEvent.VK_BACKSPACE)
      //         ||(input == KeyEvent.VK_DELETE)){
       //    evt.consume();
     //  }
    }//GEN-LAST:event_searchkeyKeyTyped

    private void agetfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agetfKeyTyped
        // TODO add your handling code here:
          char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
       changebtn.setText("change");
       
    }//GEN-LAST:event_agetfKeyTyped

    private void changebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changebtnActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_changebtnActionPerformed

    private void browsebtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browsebtnMouseEntered
        // TODO add your handling code here:
       
       browsebtn.setText("Browse");
    }//GEN-LAST:event_browsebtnMouseEntered

    private void browsebtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browsebtnMouseClicked
        // TODO add your handling code here:
        clickbtn.setText("go");
       
        
    }//GEN-LAST:event_browsebtnMouseClicked

    private void browsebtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browsebtnMouseExited
        // TODO add your handling code here:
        browsebtn.setText("Browse");
        
    }//GEN-LAST:event_browsebtnMouseExited

    private void fnametfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fnametfKeyTyped
        // TODO add your handling code here:
        changebtn.setText("change");
        
    }//GEN-LAST:event_fnametfKeyTyped

    private void mnametfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mnametfKeyTyped
        // TODO add your handling code here:
        changebtn.setText("change");
        
    }//GEN-LAST:event_mnametfKeyTyped

    private void lnametfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lnametfKeyTyped
        // TODO add your handling code here:
        changebtn.setText("change");
        
    }//GEN-LAST:event_lnametfKeyTyped

    private void genderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_genderKeyPressed
        // TODO add your handling code here:
        changebtn.setText("change");
    }//GEN-LAST:event_genderKeyPressed

    private void statusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_statusKeyPressed
        // TODO add your handling code here:
        changebtn.setText("change");
    }//GEN-LAST:event_statusKeyPressed

    private void bdatetfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bdatetfKeyTyped
        // TODO add your handling code here:
        changebtn.setText("change");
       
    }//GEN-LAST:event_bdatetfKeyTyped

    private void addresstfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addresstfKeyTyped
        // TODO add your handling code here:
        changebtn.setText("change");
       
    }//GEN-LAST:event_addresstfKeyTyped

    private void positionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_positionMousePressed
        // TODO add your handling code here:
        changebtn.setText("change");
    }//GEN-LAST:event_positionMousePressed

    private void genderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genderMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderMousePressed

    private void genderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genderMouseClicked
        // TODO add your handling code here:
        changebtn.setText("change");
        
    }//GEN-LAST:event_genderMouseClicked

    private void statusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statusMouseClicked
        // TODO add your handling code here:
        changebtn.setText("change");
        
    }//GEN-LAST:event_statusMouseClicked

    private void positionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_positionMouseClicked
        // TODO add your handling code here:
        changebtn.setText("change");
        
    }//GEN-LAST:event_positionMouseClicked

    private void mobiletfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mobiletfKeyTyped
        // TODO add your handling code here:
        changebtn.setText("change");
      
    }//GEN-LAST:event_mobiletfKeyTyped

    private void startedtfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_startedtfKeyTyped
        // TODO add your handling code here:
        changebtn.setText("change");
       
    }//GEN-LAST:event_startedtfKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(staffInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(staffInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(staffInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(staffInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new staffInfo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea addresstf;
    private javax.swing.JTextField agetf;
    private javax.swing.JTextField bdatetf;
    private javax.swing.JButton browsebtn;
    private javax.swing.JButton changebtn;
    private javax.swing.JButton clearbtn;
    private javax.swing.JButton clickbtn;
    private javax.swing.JLabel date;
    private javax.swing.JButton displaybtn;
    private javax.swing.JTextField fnametf;
    private javax.swing.JComboBox<String> gender;
    private javax.swing.JTextField idtf;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField lnametf;
    private javax.swing.JTextField mnametf;
    private javax.swing.JTextField mobiletf;
    private javax.swing.JButton okbtn;
    private javax.swing.JLabel picture;
    private javax.swing.JComboBox<String> position;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextField searchkey;
    private javax.swing.JTextField startedtf;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
 private void showDate(){
        d = new Date();
        dd = new SimpleDateFormat("dd-MM-yyyy");
        date.setText(dd.format(d));
        startedtf.setText(dd.format(d));
        
        new Timer(0,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date d = new Date();
                SimpleDateFormat dd = new SimpleDateFormat("hh:mm:ss a");
                time.setText(dd.format(d));
            }
        }).start();
    }
 private void update_both(){
 try{
          Class.forName("com.mysql.jdbc.Driver");
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
          PreparedStatement ps = conn.prepareStatement("UPDATE `staff_info` SET `staff_id`=?,`f_name`=?,`m_name`=?,`l_name`=?,`birth_date`=?,`age`=?,`gender`=?,"
                  + "`status`=?,`position`=?,`phone`=?,`address`=?,`date_started`=?,`image`=? WHERE staff_id="+idtf.getText());
          InputStream is = new FileInputStream(new File(r));
          ps.setString(1, idtf.getText());
          ps.setString(2, fnametf.getText());
          ps.setString(3,mnametf.getText());
          ps.setString(4,lnametf.getText());
          ps.setString(5,bdatetf.getText());
          ps.setString(6,agetf.getText());
          ps.setString(7,gender.getSelectedItem().toString());
          ps.setString(8,status.getSelectedItem().toString());
          ps.setString(9, position.getSelectedItem().toString());
          ps.setString(10,mobiletf.getText());
          ps.setString(11,addresstf.getText());
          ps.setString(12,startedtf.getText());
          ps.setBlob(13, is);
          ps.executeUpdate();
        //  JOptionPane.showMessageDialog(null, "Data Updated successfully!");
        refresh();
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
    }
 }
 public void click(){
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter?zeroDateTimeBehavior=convertToNull", "root", "");
            sql = "SELECT * from staff_info WHERE staff_id=? OR f_name=? OR m_name=? OR l_name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
          
            pst.setString(1, searchkey.getText());
             pst.setString(2, searchkey.getText());
              pst.setString(3, searchkey.getText());
               pst.setString(4, searchkey.getText());
               ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                byte[] img = rs.getBytes("image");
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myImg = im.getScaledInstance(picture.getWidth(), picture.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myImg);
                picture.setIcon(newImage);
            } else {
                JOptionPane.showMessageDialog(null, "No data found");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }}
 
}

