/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AHCISystem;

import static AHCISystem.dashboard.time;
import com.sun.glass.events.KeyEvent;
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
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Reycham
 */
public class medicalHistory extends javax.swing.JFrame {
     Date d;
     SimpleDateFormat dd;

    /**
     * Creates new form medicalHistory
     */
    public Connection conn;
    public Statement st;
    public String sql;
    public medicalHistory() {
        initComponents();
        deactivate();
        showDate();
        timetf.setEditable(false);
        searchbtn.setEnabled(false);
        searchID.setEnabled(false);
        app.setEditable(false);
       
        
        //CONNECT TO DATABASE
        
        try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
           st = conn.createStatement();
          // JOptionPane.showMessageDialog(null, "Connected");
        }catch(Exception cham){
           JOptionPane.showMessageDialog(this, cham, "Message", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void insert(){
        try{
           sql = "INSERT INTO `medical_records`(`name`, `date_visit`, `gender`, `age`, `weight`, `height`, "
                   + "`blood_pressure`, `blood_type`, `heart_rate`, `symptoms`, `treatment`, `attend_staff`,"
                   + " `position`,finding,ID) VALUES ('"+nametf.getText()+"','"+datevisittf.getText()+"','"+gender.getSelectedItem()+"','"+agetf.getText()+"','"+weighttf.getText()+"','"+heighttf.getText()+"',"
                   + "'"+bptf.getText()+"','"+bloodtype.getSelectedItem()+"','"+heartratetf.getText()+"','"+symptomsta.getText()+"','"+treatmentta.getText()+"','"+attendtf.getText()+"','"+position.getSelectedItem()+"','"+jTextArea1.getText()+"','"+idtf.getText()+"')"; 
           st.executeUpdate(sql);
           JOptionPane.showMessageDialog(null, "Inserted");
        }catch(Exception cham){
           JOptionPane.showMessageDialog(this, cham, "FAILED", JOptionPane.ERROR_MESSAGE);
        }
        
           try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into medical_reports (ID, date, time, status) values ('"+idtf.getText()+"','"+date.getText()+"','"+timetf.getText()+"','Medical Record Added')";
                st.executeUpdate(sql);
                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
    }
    public void showDataTable(){
         try{
         PreparedStatement st = conn.prepareStatement("Select * from medical_records");
         ResultSet rs = st.executeQuery();
         DefaultTableModel model = (DefaultTableModel)jTable.getModel();
         model.setRowCount(0);
         
         while(rs.next()){
             Object ob [] ={rs.getInt("app"),rs.getString("name"),rs.getString("date_visit"),rs.getString("gender"),rs.getString("age")
             ,rs.getString("weight"),rs.getString("height"),rs.getString("blood_pressure"),rs.getString("blood_type"),rs.getString("heart_rate")
                     ,rs.getString("symptoms"),rs.getString("treatment"),rs.getString("attend_staff"),rs.getString("position"),rs.getString("finding"),rs.getString("ID")};
             model.addRow(ob);
         }
       } catch(Exception e){
           
       }  
    }
     public void reflect(){
      int i = jTable.getSelectedRow();
        TableModel model = jTable.getModel();
        app.setText(model.getValueAt(i, 0).toString());
        nametf.setText(model.getValueAt(i, 1).toString());
        datevisittf.setText(model.getValueAt(i, 2).toString());
        gender.setSelectedItem(model.getValueAt(i, 3).toString());
        agetf.setText(model.getValueAt(i, 4).toString());
        weighttf.setText(model.getValueAt(i, 5).toString());
        heighttf.setText(model.getValueAt(i, 6).toString());
        bptf.setText(model.getValueAt(i, 7).toString());
        bloodtype.setSelectedItem(model.getValueAt(i, 8).toString());
        heartratetf.setText(model.getValueAt(i, 9).toString());
        symptomsta.setText(model.getValueAt(i, 10).toString());
        treatmentta.setText(model.getValueAt(i, 11).toString());
        attendtf.setText(model.getValueAt(i, 12).toString());
        position.setSelectedItem(model.getValueAt(i, 13).toString());
        jTextArea1.setText(model.getValueAt(i, 14).toString());
        idtf.setText(model.getValueAt(i, 15).toString());
        
    }
     public void search(){
  
         String searchkey = searchID.getText();
         
        
         try{
          sql = "SELECT * from medical_records WHERE ID =? OR name=?";
        //  sql=" SELECT * FROM `medical_records` WHERE `name` LIKE '%"+searchkey+"%' ORDER BY `gender` ASC ";
          PreparedStatement pst = conn.prepareStatement(sql);
          pst.setString(1, searchkey);
          pst.setString(2, searchkey);
          ResultSet rs = pst.executeQuery();    
          DefaultTableModel model = (DefaultTableModel)jTable.getModel();
          model.setRowCount(0); 
          
           while(rs.next()){          
           Object ob [] ={rs.getInt("app"),rs.getString("name"),rs.getString("date_visit"),rs.getString("gender"),rs.getString("age")
             ,rs.getString("weight"),rs.getString("height"),rs.getString("blood_pressure"),rs.getString("blood_type"),rs.getString("heart_rate")
             ,rs.getString("symptoms"),rs.getString("treatment"),rs.getString("attend_staff"),rs.getString("position"),rs.getString("finding"),rs.getString("ID")};
             model.addRow(ob);  
         }
         } catch(Exception cham){
         }    
     }
     public void Update(){
           try{
          Class.forName("com.mysql.jdbc.Driver");
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
          PreparedStatement ps = conn.prepareStatement("UPDATE `medical_records` SET `app`=?,`name`=?,`date_visit`=?,`gender`=?,`age`=?,`weight`=?,`height`=?,"
                  + "`blood_pressure`=?,`blood_type`=?,`heart_rate`=?,`symptoms`=?,`treatment`=?,`attend_staff`=?,`position`=?,finding=?,ID=? WHERE app="+app.getText());
      //    InputStream is = new FileInputStream(new File(r));
          ps.setString(1, app.getText());
          ps.setString(2, nametf.getText());
          ps.setString(3,datevisittf.getText());
          ps.setString(4,gender.getSelectedItem().toString());
          ps.setString(5,agetf.getText());
          ps.setString(6, weighttf.getText());
          ps.setString(7,heighttf.getText());
          ps.setString(8, bptf.getText());
          ps.setString(9, bloodtype.getSelectedItem().toString());
          ps.setString(10,heartratetf.getText());
          ps.setString(11,symptomsta.getText());
          ps.setString(12,treatmentta.getText());
          ps.setString(13,attendtf.getText());
          ps.setString(14,position.getSelectedItem().toString());
           ps.setString(15,jTextArea1.getText());
          ps.setString(16,idtf.getText());
          ps.executeUpdate();
          JOptionPane.showMessageDialog(null, "Data updated");
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }
           
            try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into medical_reports (ID, date, time, status) values ('"+idtf.getText()+"','"+date.getText()+"','"+timetf.getText()+"','Medical Record Updated')";
                st.executeUpdate(sql);
                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
           refresh();
           
     }
     public void delete(){
        
        try {
            sql = "DELETE FROM `medical_records` WHERE app=" + app.getText();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "record Deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "FAILED", JOptionPane.ERROR_MESSAGE);
        }
         try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into medical_reports (ID, date, time, status) values ('"+idtf.getText()+"','"+date.getText()+"','"+timetf.getText()+"','Medical Record Deleted')";
                st.executeUpdate(sql);
                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
    
     }
     public void activate(){
       idtf.setEnabled(true);
       nametf.setEnabled(true);
       datevisittf.setEnabled(true);
       gender.setEnabled(true);
       agetf.setEnabled(true);
       weighttf.setEnabled(true);
       heighttf.setEnabled(true);
       bptf.setEnabled(true);
       bloodtype.setEnabled(true);
       heartratetf.setEnabled(true);
       symptomsta.setEnabled(true);
       treatmentta.setEnabled(true);
       attendtf.setEnabled(true);
       position.setEnabled(true);
       clearbtn.setEnabled(true);
       okbtn.setEnabled(true);
       app.setEnabled(true);
       jTextArea1.setEnabled(true);
       
     }
     public void deactivate(){
       idtf.setEnabled(false);
       nametf.setEnabled(false);
       datevisittf.setEnabled(false);
       gender.setEnabled(false);
       agetf.setEnabled(false);
       weighttf.setEnabled(false);
       heighttf.setEnabled(false);
       bptf.setEnabled(false);
       bloodtype.setEnabled(false);
       heartratetf.setEnabled(false);
       symptomsta.setEnabled(false);
       treatmentta.setEnabled(false);
       attendtf.setEnabled(false);
       position.setEnabled(false); 
       clearbtn.setEnabled(false);
       okbtn.setEnabled(false);
       app.setEnabled(false);
       jTextArea1.setEnabled(false);
     }
     public void clear(){
       idtf.setText("");
       nametf.setText("");
       app.setText("");
       gender.setSelectedIndex(0);
       agetf.setText("");
       weighttf.setText("");
       heighttf.setText("");
       bptf.setText("");
       bloodtype.setSelectedIndex(0);
       heartratetf.setText("");
       symptomsta.setText("");
       treatmentta.setText("");
       attendtf.setText("");
       position.setSelectedIndex(0);
       jTextArea1.setText(null);
     }
      public void refresh() {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        model.setRowCount(0);
        showDataTable();
    }

       public void noteditable(){
       idtf.setEditable(false);
       nametf.setEditable(false);
       datevisittf.setEditable(false);
       gender.setEditable(false);
       agetf.setEditable(false);
       weighttf.setEditable(false);
       heighttf.setEditable(false);
       bptf.setEditable(false);
       bloodtype.setEditable(false);
       heartratetf.setEditable(false);
       symptomsta.setEditable(false);
       treatmentta.setEditable(false);
       attendtf.setEditable(false);
       position.setEditable(false); 
       jTextArea1.setEditable(false);
     }
       public void editable(){
    //   idtf.setEditable(true);
       nametf.setEditable(true);
    //   datevisittf.setEditable(true);
      // gender.setEditable(true);
       agetf.setEditable(true);
       weighttf.setEditable(true);
       heighttf.setEditable(true);
       bptf.setEditable(true);
     //  bloodtype.setEditable(true);
       heartratetf.setEditable(true);
       symptomsta.setEditable(true);
       treatmentta.setEditable(true);
       attendtf.setEditable(true);
     //  position.setEditable(true); 
       jTextArea1.setEditable(true);
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        idtf = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        nametf = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        datevisittf = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        gender = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        agetf = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        weighttf = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        heighttf = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        bptf = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        bloodtype = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        heartratetf = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        symptomsta = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        treatmentta = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        attendtf = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        position = new javax.swing.JComboBox<>();
        clearbtn = new javax.swing.JButton();
        okbtn = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        app = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        searchID = new javax.swing.JTextField();
        searchbtn = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        timetf = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(110, 0));
        setMinimumSize(new java.awt.Dimension(1600, 1000));
        setPreferredSize(new java.awt.Dimension(1600, 1000));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("AMBUAN HEALTH CENTER INFORMATION SYSTEM");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Date:");

        date.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setText("00-00-0000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel4)
                .addGap(161, 161, 161)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date)
                .addContainerGap(576, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(date))
                    .addComponent(jLabel4))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 100));

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("ADD RECORD");
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
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 35, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 240, 60));

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("UPDATE RECORD");
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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jButton3)
                .addGap(0, 35, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 60));

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("SEARCH RECORD");
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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jButton4)
                .addGap(0, 5, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 210, 60));

        jPanel13.setBackground(new java.awt.Color(51, 51, 51));

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("DELETE RECORD");
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
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 210, 60));

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
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(64, 64, 64))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 50));

        jPanel7.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 210, 370));

        jButton12.setBackground(new java.awt.Color(0, 153, 153));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Dashboard");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 230, -1));

        jButton13.setBackground(new java.awt.Color(0, 153, 153));
        jButton13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Exit");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 230, -1));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 230, 470));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel2.setText("Patient Medical Records");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Patient ID:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        idtf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        idtf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idtfKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idtfKeyTyped(evt);
            }
        });
        jPanel1.add(idtf, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 180, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setText("Name:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        nametf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nametf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nametfKeyPressed(evt);
            }
        });
        jPanel1.add(nametf, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 180, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setText("Date of visit:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        datevisittf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        datevisittf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datevisittfActionPerformed(evt);
            }
        });
        jPanel1.add(datevisittf, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 180, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setText("Gender:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        gender.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female" }));
        jPanel1.add(gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 180, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setText("Age:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        agetf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        agetf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                agetfKeyTyped(evt);
            }
        });
        jPanel1.add(agetf, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 180, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel9.setText("Weight ( kilos ):");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, -1, -1));

        weighttf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        weighttf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                weighttfKeyTyped(evt);
            }
        });
        jPanel1.add(weighttf, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 150, 130, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setText("Height ( cm ):");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, -1, -1));

        heighttf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        heighttf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                heighttfKeyTyped(evt);
            }
        });
        jPanel1.add(heighttf, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 100, 130, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setText("Blood Pressure:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, -1, -1));

        bptf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(bptf, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 130, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setText("Blood Type:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, -1, -1));

        bloodtype.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bloodtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "A+", "B", "B+", "O", "O+", "AB" }));
        jPanel1.add(bloodtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 130, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setText("Heart Rate:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 300, -1, -1));

        heartratetf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(heartratetf, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 300, 130, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel14.setText("Symptoms:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, -1, -1));

        symptomsta.setColumns(20);
        symptomsta.setFont(new java.awt.Font("Monospaced", 0, 15)); // NOI18N
        symptomsta.setRows(5);
        jScrollPane2.setViewportView(symptomsta);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 100, -1, 90));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel15.setText("Treatment Given:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 340, -1, -1));

        treatmentta.setColumns(20);
        treatmentta.setFont(new java.awt.Font("Monospaced", 0, 15)); // NOI18N
        treatmentta.setRows(5);
        jScrollPane3.setViewportView(treatmentta);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 340, 190, 90));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel16.setText("Attending Staff:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, -1, -1));

        attendtf.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        attendtf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                attendtfKeyPressed(evt);
            }
        });
        jPanel1.add(attendtf, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 130, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel17.setText("Position:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 400, -1, -1));

        position.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        position.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Doctor", "Nurse", "Dentist", "Midwife", "BHW" }));
        jPanel1.add(position, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 400, 130, -1));

        clearbtn.setBackground(new java.awt.Color(255, 255, 0));
        clearbtn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        clearbtn.setText("Clear");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });
        jPanel1.add(clearbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 310, 120, -1));

        okbtn.setBackground(new java.awt.Color(0, 153, 153));
        okbtn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        okbtn.setText("OK");
        okbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okbtnActionPerformed(evt);
            }
        });
        jPanel1.add(okbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 360, 120, 40));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel22.setText("Appointment No:");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, -1, -1));

        app.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jPanel1.add(app, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 150, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel18.setText("Findings:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 230, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 220, 190, 90));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 1360, 480));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable.setBackground(new java.awt.Color(0, 153, 153));
        jTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Appointment#", "Name", "Date Visited", "Gender", "Age", "Weight", "Height", "Blood Pressure", "Blood Type", "Heart Rate", "Symptoms", "Treatment", "Attending Staff", "Position", "Findings", "ID"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1580, 330));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 650, 1600, 350));

        searchID.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        searchID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        searchID.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        searchID.setName(""); // NOI18N
        searchID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIDMouseClicked(evt);
            }
        });
        searchID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchIDKeyTyped(evt);
            }
        });
        getContentPane().add(searchID, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 600, 230, 40));

        searchbtn.setBackground(new java.awt.Color(0, 153, 153));
        searchbtn.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        searchbtn.setText("Search ");
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
        getContentPane().add(searchbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 230, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel19.setText("Time:");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 610, -1, -1));

        timetf.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        timetf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(timetf, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 610, 230, -1));

        jLabel21.setBackground(new java.awt.Color(0, 153, 153));
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("Name/ID");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 610, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
         okbtn.setVisible(true);
         editable();
       
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        activate();
        okbtn.setLabel("Save");
       idtf.setEditable(true);
       idtf.requestFocus();
       position.setEditable(false);
       gender.setEditable(false);
       bloodtype.setEditable(false);
       datevisittf.setEditable(false);
       clear();
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyTyped

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
         okbtn.setVisible(true);
       

    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        okbtn.setLabel("Update");
        activate();
        editable();
        idtf.setEditable(false);
        datevisittf.setEditable(false);
        gender.setEnabled(false);
        nametf.setEditable(false);
        agetf.setEditable(false);
        app.setEditable(false);
        position.setEnabled(false);
        bloodtype.setEnabled(false);
        attendtf.setEditable(false);
        
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3KeyTyped

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked

    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        activate();
        okbtn.setVisible(false);
    //  okbtn.setEnabled(false);
    //  okbtn.setText(null);
      idtf.setEditable(true);
      searchbtn.setEnabled(true);
      searchID.setEnabled(true);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
 okbtn.setVisible(true);       
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
        activate();
        idtf.setEditable(true);
        okbtn.setLabel("Delete");
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
       new dashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        // TODO add your handling code here:
        reflect();
        noteditable();
        searchID.setText(null);
    }//GEN-LAST:event_jTableMouseClicked

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        // TODO add your handling code here:
         if(searchID.getText().trim().isEmpty()){
        JOptionPane.showMessageDialog(null, "Enter ID or Name to search!"); 
         } else{
            search();    
                } 
    }//GEN-LAST:event_searchbtnActionPerformed

    private void okbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okbtnActionPerformed
        // TODO add your handling code here:
        int opt;
        if(okbtn.getLabel()=="Save"){
           
                if (idtf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No data to save. \n Please fill up all the fields..", "Message!", JOptionPane.ERROR_MESSAGE);
            } else {
                opt = JOptionPane.showConfirmDialog(this, "Would you like to Save patient information?\n Click 'YES' or 'ENTER' to continue.", "Message", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    insert();
                    refresh();
                }
            }
            
            
        }else if(okbtn.getLabel()=="Update"){
      
              if (idtf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Choose record to update.", "OPS!", JOptionPane.ERROR_MESSAGE);
            } else {
                opt = JOptionPane.showConfirmDialog(this, "Would you like to update patient information?\n Click 'YES' or 'ENTER' to continue.", "Message", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    Update();
                    refresh();
                }
            }
        
        }else if(okbtn.getLabel()=="Delete"){
             if (idtf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Choose record to delete.", "OPS!", JOptionPane.ERROR_MESSAGE);
            } else {
                opt = JOptionPane.showConfirmDialog(this, "Would you like to delete? \n This cannot be undone. \n Data will be permanently deleted from the database.\nClick 'YES' or 'ENTER' to continue.\n'NO' to cancel.", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (opt == 0) {
                    try {
                        sql = "SELECT * from medical_records WHERE app=?";
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setString(1, app.getText());
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

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_clearbtnActionPerformed

    private void nametfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nametfKeyPressed
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        
        if(Character.isLetter(c)|| Character.isWhitespace(c)|| Character.isISOControl(c)){
            nametf.setEditable(true);
        }else{
            nametf.setEditable(false);
        }
    }//GEN-LAST:event_nametfKeyPressed

    private void idtfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idtfKeyPressed
        // TODO add your handling code here:
        
         
    }//GEN-LAST:event_idtfKeyPressed

    private void idtfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idtfKeyTyped
        // TODO add your handling code here:
         char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
    }//GEN-LAST:event_idtfKeyTyped

    private void weighttfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weighttfKeyTyped
        // TODO add your handling code here:
         char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
    }//GEN-LAST:event_weighttfKeyTyped

    private void heighttfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_heighttfKeyTyped
        // TODO add your handling code here:
         char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
    }//GEN-LAST:event_heighttfKeyTyped

    private void agetfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agetfKeyTyped
        // TODO add your handling code here:
         char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
    }//GEN-LAST:event_agetfKeyTyped

    private void searchIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchIDKeyTyped
        // TODO add your handling code here:
   //      char input = evt.getKeyChar();
   //    if(!(Character.isDigit(input))
     //          ||(input == KeyEvent.VK_BACKSPACE)
     //          ||(input == KeyEvent.VK_DELETE)){
     //      evt.consume();
   //    }
    }//GEN-LAST:event_searchIDKeyTyped

    private void attendtfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_attendtfKeyPressed
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        
        if(Character.isLetter(c)|| Character.isWhitespace(c)|| Character.isISOControl(c)){
            attendtf.setEditable(true);
        }else{
            attendtf.setEditable(false);
        }
    }//GEN-LAST:event_attendtfKeyPressed

    private void searchbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchbtnMouseClicked

        
         String searchkey = searchID.getText();
        
         try{
          sql = "SELECT * from medical_records WHERE ID =? OR name=?";
          PreparedStatement pst = conn.prepareStatement(sql);
          pst.setString(1, searchkey);
          pst.setString(2, searchkey);
          ResultSet rs = pst.executeQuery();    
          
           if(rs.next()){          
          
         }else{
               JOptionPane.showMessageDialog(null, "No Records found!");
           }
         } catch(Exception cham){
         }    
    }//GEN-LAST:event_searchbtnMouseClicked

    private void datevisittfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datevisittfActionPerformed
        // TODO add your handling code here:
        showDate();
    
    }//GEN-LAST:event_datevisittfActionPerformed

    private void jTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseReleased
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jTableMouseReleased

    private void searchIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIDMouseClicked
        // TODO add your handling code here:
        searchID.setText(null);
        
    }//GEN-LAST:event_searchIDMouseClicked

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
            java.util.logging.Logger.getLogger(medicalHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(medicalHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(medicalHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(medicalHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new medicalHistory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField agetf;
    private javax.swing.JTextField app;
    private javax.swing.JTextField attendtf;
    private javax.swing.JComboBox<String> bloodtype;
    private javax.swing.JTextField bptf;
    private javax.swing.JButton clearbtn;
    private javax.swing.JLabel date;
    private javax.swing.JTextField datevisittf;
    private javax.swing.JComboBox<String> gender;
    private javax.swing.JTextField heartratetf;
    private javax.swing.JTextField heighttf;
    private javax.swing.JTextField idtf;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField nametf;
    private javax.swing.JButton okbtn;
    private javax.swing.JComboBox<String> position;
    private javax.swing.JTextField searchID;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextArea symptomsta;
    private javax.swing.JTextField timetf;
    private javax.swing.JTextArea treatmentta;
    private javax.swing.JTextField weighttf;
    // End of variables declaration//GEN-END:variables
 private void showDate(){
        d = new Date();
        dd = new SimpleDateFormat("dd-MM-yyyy");
        date.setText(dd.format(d));
        datevisittf.setText(dd.format(d));
        
        new Timer(0,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date d = new Date();
                SimpleDateFormat dd = new SimpleDateFormat("hh:mm:ss a");
                
                timetf.setText(dd.format(d));
            }
        }).start();
    }
}
