/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AHCISystem;

import com.sun.glass.events.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Reycham
 */
public class patientForm extends javax.swing.JFrame {
     Date d;
    SimpleDateFormat dd;
    /**
     * Creates new form NewJFrame1
     */
    public Statement st;
    public Connection conn;
    public String sql;
    public patientForm() {
        initComponents();
        deactivate();
        showDate();
        this.user.setText(Login.usertype.getSelectedItem().toString());
        
        //Connect to database
        try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
           st = conn.createStatement();
         //  JOptionPane.showMessageDialog(null, "CONNECTED");
        }catch(Exception e){
         JOptionPane.showMessageDialog(this, "NOT CONNECTED","FAILED" , JOptionPane.ERROR_MESSAGE);
        }
    }
   
    public void activate(){
        searchbtn.setEnabled(true);
        patientidtf.setEnabled(true);
        fnametf.setEnabled(true);
        mnametf.setEnabled(true);
        lnametf.setEnabled(true);
        agetf.setEnabled(true);
        gender.setEnabled(true);
        householdtf.setEnabled(true);
        puroktf.setEnabled(true);
        civilstatus.setEnabled(true);
        bdatetf.setEnabled(true);
        occupationtf.setEnabled(true);
        okbtn.setEnabled(true);
        displaybtn.setEnabled(true);
        clearbtn.setEnabled(true);
        mobiletf.setEnabled(true);
    }
    public void deactivate(){
        searchbtn.setEnabled(false);
        patientidtf.setEnabled(false);
        fnametf.setEnabled(false);
        mnametf.setEnabled(false);
        lnametf.setEnabled(false);
        agetf.setEnabled(false);
        gender.setEnabled(false);
        householdtf.setEnabled(false);
        puroktf.setEnabled(false);
        civilstatus.setEnabled(false);  
        bdatetf.setEnabled(false);
        occupationtf.setEnabled(false);
        okbtn.setEnabled(false);
        clearbtn.setEnabled(false);
        displaybtn.setEnabled(false);
        mobiletf.setEnabled(false);
    }
    public void clear(){
        patientidtf.setText("");
        fnametf.setText("");
        mnametf.setText("");
        lnametf.setText("");
        agetf.setText("");
        householdtf.setText("");
        puroktf.setText("");
        bdatetf.setText("");
        occupationtf.setText("");
        gender.setSelectedItem("Select");
        civilstatus.setSelectedItem("Select");
        mobiletf.setText("");
            
    }
   
     public void update(){
         try{
           String sql = "UPDATE `patient_info` SET `patient_id`='"+patientidtf.getText()+"',`f_name`='"+fnametf.getText()+"',`m_name`='"+mnametf.getText()+"',`l_name`='"+lnametf.getText()+"',`age`='"+agetf.getText()+"',`gender`='"+gender.getSelectedItem()+"',`household`='"+householdtf.getText()+"',`purok`='"+puroktf.getText()+"',`civil_status`='"+civilstatus.getSelectedItem()+"',`b_date`='"+bdatetf.getText()+"',`occupation`='"+occupationtf.getText()+"',mobile='"+mobiletf.getText()+"' WHERE patient_id="+patientidtf.getText();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Updated");
         }catch(Exception e){
            JOptionPane.showMessageDialog(null, e); 
         }
           try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into patient_reports (ID, date, time, status) values ('"+patientidtf.getText()+"','"+date.getText()+"','"+time.getText()+"','Patient Record Updated')";
                st.executeUpdate(sql);
                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
     }
   
    public void delete(){
         try{
         String sql = "DELETE FROM `patient_info` WHERE patient_id = "+patientidtf.getText();    
         st.executeUpdate(sql);
         JOptionPane.showMessageDialog(null, "Deleted");
         }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);    
         }
         
           try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into patient_reports (ID, date, time, status) values ('"+patientidtf.getText()+"','"+date.getText()+"','"+time.getText()+"','Patient Record Deleted')";
                st.executeUpdate(sql);
                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }
    }
    public void search(){
         try{
          String sql = "SELECT * FROM `patient_info` WHERE patient_id=?";  
          PreparedStatement psmt = conn.prepareStatement(sql);
          psmt.setString(1, patientidtf.getText());
          
          ResultSet rs = psmt.executeQuery();
          DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
          model.setRowCount(0);
          if(rs.next()){
              //display data to textfield
 
              patientidtf.setText(rs.getString("patient_id"));
              fnametf.setText(rs.getString("f_name"));
              mnametf.setText(rs.getString("m_name"));
              lnametf.setText(rs.getString("l_name"));
              agetf.setText(rs.getString("age"));
              gender.setSelectedItem(rs.getString("gender"));
              householdtf.setText(rs.getString("household"));
              puroktf.setText(rs.getString("purok"));
              civilstatus.setSelectedItem(rs.getString("civil_status"));
              bdatetf.setText(rs.getString("b_date"));
              occupationtf.setText(rs.getString("occupation"));
              mobiletf.setText(rs.getString("mobile"));
              //display data to table
              Object ob [] = {rs.getInt("patient_id"),rs.getString("f_name"),rs.getString("m_name"),rs.getString("l_name"),rs.getString("age"),rs.getString("gender"),rs.getInt("household"),rs.getInt("purok"),rs.getString("civil_status"),rs.getString("b_date"),rs.getString("occupation"),rs.getString("mobile")};
              model.addRow(ob);
          
            
          }else{
              JOptionPane.showMessageDialog(null, "No data found");
              
         }      
          
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
    }
     public void showDataTable(){
          try{
           PreparedStatement st = conn.prepareStatement("Select * from patient_info");
           ResultSet rs = st.executeQuery();
           DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
           model.setRowCount(0);
           while(rs.next()){
           Object ob [] = {rs.getInt("patient_id"),rs.getString("f_name"),rs.getString("m_name"),rs.getString("l_name"),rs.getString("age"),rs.getString("gender"),rs.getInt("household"),rs.getInt("purok"),rs.getString("civil_status"),rs.getString("b_date"),rs.getString("occupation"),rs.getString("mobile")};
           model.addRow(ob);
           }
          }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
          }
     }
      public void reflect(){
        int i = jTable1.getSelectedRow();
        TableModel model = jTable1.getModel();
        patientidtf.setText(model.getValueAt(i, 0).toString());
        fnametf.setText(model.getValueAt(i, 1).toString());
        mnametf.setText(model.getValueAt(i, 2).toString());
        lnametf.setText(model.getValueAt(i, 3).toString());
        agetf.setText(model.getValueAt(i, 4).toString());
        gender.setSelectedItem(model.getValueAt(i, 5).toString());
        householdtf.setText(model.getValueAt(i, 6).toString());
        puroktf.setText(model.getValueAt(i, 7).toString());
        civilstatus.setSelectedItem(model.getValueAt(i, 8).toString());
        bdatetf.setText(model.getValueAt(i, 9).toString());
        occupationtf.setText(model.getValueAt(i, 10).toString());
        mobiletf.setText(model.getValueAt(i, 11).toString());
     }
        public void refresh(){
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        model.setRowCount(0);  
        showDataTable();
      }
        public void filterSave(){
            try{
          String sql = "SELECT patient_id FROM `patient_info` WHERE patient_id = "+patientidtf.getText();  
          PreparedStatement psmt = conn.prepareStatement(sql);
          ResultSet rs = psmt.executeQuery();    
         if(rs.next()) {
  
           JOptionPane.showMessageDialog(null, "ID already taken");
         }
         else{
            // add_patient();
           sql = "INSERT INTO patient_info(patient_id, f_name, m_name, l_name, age, gender, household, purok, civil_status, b_date, occupation, mobile) "
                   + "VALUES ('"+patientidtf.getText()+"','"+fnametf.getText()+"','"+mnametf.getText()+"','"+lnametf.getText()+"','"+agetf.getText()+"','"+gender.getSelectedItem()+"',"
                   + "'"+householdtf.getText()+"','"+puroktf.getText()+"','"+civilstatus.getSelectedItem()+"','"+bdatetf.getText()+"','"+occupationtf.getText()+"','"+mobiletf.getText()+"')"; 
           st.executeUpdate(sql);
           
           sql= "insert into patient_reports (ID, date, time, status) values ('"+patientidtf.getText()+"','"+date.getText()+"','"+time.getText()+"','New Patient Added')";
           st.executeUpdate(sql);
           JOptionPane.showMessageDialog(null, "Inserted");     
              
         }
           }catch(Exception e){
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        patientidtf = new javax.swing.JTextField();
        searchbtn = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        okbtn = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        householdtf = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        puroktf = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        mnametf = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        gender = new javax.swing.JComboBox<>();
        agetf = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lnametf = new javax.swing.JTextField();
        civilstatus = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        bdatetf = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        occupationtf = new javax.swing.JTextField();
        clearbtn = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        mobiletf = new javax.swing.JTextField();
        fnametf = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        user = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        displaybtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));
        setLocation(new java.awt.Point(110, 0));
        setMinimumSize(new java.awt.Dimension(1500, 1000));
        setPreferredSize(new java.awt.Dimension(1500, 1000));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("ADD PATIENT");
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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 210, 50));

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("UPDATE PATIENT");
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
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 210, 50));

        jPanel11.setBackground(new java.awt.Color(51, 51, 51));

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("SEARCH PATIENT");
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
            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 210, 50));

        jPanel13.setBackground(new java.awt.Color(51, 51, 51));

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("DELETE PATIENT");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 210, 50));

        jPanel18.setBackground(new java.awt.Color(51, 51, 51));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel18.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 210, -1));

        jPanel9.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 210, 50));

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Menu");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel1)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 50));

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
        jPanel19.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 210, -1));

        jPanel9.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, -1, 50));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 210, 440));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 230, 530));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel7.setText("Patient Information Form");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 340, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setText("Patient ID:");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));

        patientidtf.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        patientidtf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                patientidtfMouseClicked(evt);
            }
        });
        patientidtf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                patientidtfKeyTyped(evt);
            }
        });
        jPanel5.add(patientidtf, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 90, -1));

        searchbtn.setBackground(new java.awt.Color(0, 153, 153));
        searchbtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        searchbtn.setText("Search");
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });
        jPanel5.add(searchbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, -1, -1));

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        okbtn.setBackground(new java.awt.Color(0, 153, 153));
        okbtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        okbtn.setText("OK");
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
        jPanel12.add(okbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 300, 150, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setText("Household No:");
        jPanel12.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 50, -1, -1));

        householdtf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        householdtf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                householdtfKeyTyped(evt);
            }
        });
        jPanel12.add(householdtf, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 50, 170, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setText("Purok:");
        jPanel12.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 110, -1, -1));

        puroktf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        puroktf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                puroktfKeyTyped(evt);
            }
        });
        jPanel12.add(puroktf, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 110, 170, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setText("First Name:");
        jPanel12.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        mnametf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        mnametf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mnametfKeyPressed(evt);
            }
        });
        jPanel12.add(mnametf, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 160, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setText("Gender:");
        jPanel12.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, -1, -1));

        gender.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female" }));
        jPanel12.add(gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 150, -1));

        agetf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        agetf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                agetfKeyTyped(evt);
            }
        });
        jPanel12.add(agetf, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 160, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel11.setText("Age:");
        jPanel12.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        lnametf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lnametf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lnametfKeyPressed(evt);
            }
        });
        jPanel12.add(lnametf, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 160, -1));

        civilstatus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        civilstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Single", "Married", "Widow", "Separated", "Divorced" }));
        jPanel12.add(civilstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 150, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setText("Civil Status:");
        jPanel12.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setText("Middle Name:");
        jPanel12.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setText("Last Name:");
        jPanel12.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setText("Date of Birth:");
        jPanel12.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, -1, -1));

        bdatetf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel12.add(bdatetf, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 170, 150, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel16.setText("Occupation:");
        jPanel12.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, -1, 20));

        occupationtf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        occupationtf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                occupationtfKeyPressed(evt);
            }
        });
        jPanel12.add(occupationtf, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, 150, -1));

        clearbtn.setBackground(new java.awt.Color(255, 255, 0));
        clearbtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        clearbtn.setText("CLEAR");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });
        jPanel12.add(clearbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 300, 120, 40));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel17.setText("Mobile No:");
        jPanel12.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 170, -1, -1));

        mobiletf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        mobiletf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mobiletfKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mobiletfKeyTyped(evt);
            }
        });
        jPanel12.add(mobiletf, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 170, 170, -1));

        fnametf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        fnametf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fnametfKeyPressed(evt);
            }
        });
        jPanel12.add(fnametf, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 160, -1));

        jPanel5.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 150, 1270, 390));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setText("Date:");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 70, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel15.setText("Time:");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 100, -1, -1));

        date.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        date.setText("00-00-0000");
        jPanel5.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 70, 150, -1));

        time.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        time.setText("00:00:00");
        jPanel5.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 100, 140, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel18.setText("Logged in as:");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, -1));

        user.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        user.setText("Admin");
        jPanel5.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, -1, -1));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 1260, 530));

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setBackground(new java.awt.Color(0, 153, 153));
        jTable1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient_ID", "First_Name", "Middle_Name", "Last_Name", "Age", "Gender", "Household_No", "Purok", "CivilStatus", "Birthday", "Occupation", "Phone"
            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel8.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 1420, 240));

        displaybtn.setBackground(new java.awt.Color(0, 153, 153));
        displaybtn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        displaybtn.setText("Display Patients");
        displaybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displaybtnActionPerformed(evt);
            }
        });
        jPanel8.add(displaybtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, -1, -1));

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 1610, 300));

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
                .addContainerGap(387, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(353, 353, 353))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1500, 80));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1930, 930));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton2KeyTyped

    private void jButton3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jButton3KeyTyped

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        activate();
        searchbtn.setEnabled(false);
         okbtn.setVisible(true);
        okbtn.setLabel("Save");
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        activate();
        searchbtn.setEnabled(false);
         okbtn.setVisible(true);
        okbtn.setLabel("Update");
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        activate();
        okbtn.setVisible(false);
      //  okbtn.setLabel("");
       // okbtn.setEnabled(false);
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int opt = JOptionPane.showConfirmDialog(this, "Would you like to EXIT? \n Click 'YES' or 'ENTER' to proceed.", "Message", JOptionPane.YES_NO_OPTION);
        if(opt==0){
       System.exit(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void patientidtfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patientidtfMouseClicked
        // TODO add your handling code here:
        if(okbtn.getLabel()=="Save"){
            searchbtn.setEnabled(false);
        }else{
        searchbtn.setEnabled(true);
        }
    }//GEN-LAST:event_patientidtfMouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        activate();
         okbtn.setVisible(true);
        okbtn.setLabel("Delete");
    }//GEN-LAST:event_jButton5MouseClicked

    private void okbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okbtnMouseClicked
        // TODO add your handling code here:
     //   clear();
     JOptionPane.showMessageDialog(null, "Choose an action to perform");
    }//GEN-LAST:event_okbtnMouseClicked

    private void okbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okbtnActionPerformed
        // TODO add your handling code here:
        
         int opt;
        if(okbtn.getLabel()=="Save"){
            if(patientidtf.getText().trim().isEmpty()){
           JOptionPane.showMessageDialog(this, "Fields are empty.", "OPS!", JOptionPane.ERROR_MESSAGE);
           }else{ 
          opt =  JOptionPane.showConfirmDialog(this, "Would you like to save? \nClick 'YES' or 'ENTER' to continue.", "Message", JOptionPane.YES_NO_OPTION);
         if(opt==0){
     
       filterSave();
          }
         }
        }
         if(okbtn.getLabel()=="Update"){
            if(patientidtf.getText().trim().isEmpty()){
           JOptionPane.showMessageDialog(this, "Select patient info to update.", "OPS!", JOptionPane.ERROR_MESSAGE); 
           }else{
          opt =  JOptionPane.showConfirmDialog(this, "Would you like to update patient information?\n Click 'YES' or 'ENTER' to continue.", "Message", JOptionPane.YES_NO_OPTION); 
          if(opt==0){
            update();
            refresh();  
          }
          }
         }
          if(okbtn.getLabel()=="Delete"){
          if(patientidtf.getText().trim().isEmpty()){
             JOptionPane.showMessageDialog(this, "Choose staff to delete.", "OPS!", JOptionPane.ERROR_MESSAGE); 
          }else{
             opt = JOptionPane.showConfirmDialog(this, "Would you like to delete? \n This cannot be undone. \n Data will be permanently deleted from the database.\nClick 'YES' or 'ENTER' to continue.\n'NO' to cancel.", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
          if(opt==0){
          try{
            sql = "SELECT * from patient_info WHERE patient_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, patientidtf.getText());
            ResultSet rs = pst.executeQuery();
          if(rs.next()){
            delete(); 
            refresh();
            clear();
          }else{
              JOptionPane.showMessageDialog(null, "No data found");  
           }
           }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
        }
       }  
      }
      
    }//GEN-LAST:event_okbtnActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        // TODO add your handling code here:
         if(patientidtf.getText().trim().isEmpty()){
           JOptionPane.showMessageDialog(this, "Enter ID to search.", "OPS!", JOptionPane.ERROR_MESSAGE); 
           }else{
        search();
         }
    }//GEN-LAST:event_searchbtnActionPerformed

    private void displaybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displaybtnActionPerformed
        // TODO add your handling code here:
        showDataTable();
    }//GEN-LAST:event_displaybtnActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // Reflect to textfielf when row is clicked
        if(okbtn.getLabel()==""){
           okbtn.setLabel("Ok");
        }
        activate();
        reflect();
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_clearbtnActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        clear();
        patientidtf.requestFocus();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
       new dashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void fnametfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fnametfKeyPressed
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        
        if(Character.isLetter(c)|| Character.isWhitespace(c)|| Character.isISOControl(c)){
            fnametf.setEditable(true);
        }else{
            fnametf.setEditable(false);
        }
        
        
    }//GEN-LAST:event_fnametfKeyPressed

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

    private void occupationtfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_occupationtfKeyPressed
        // TODO add your handling code here:
         char c = evt.getKeyChar();
        
        if(Character.isLetter(c)|| Character.isWhitespace(c)|| Character.isISOControl(c)){
            occupationtf.setEditable(true);
        }else{
            occupationtf.setEditable(false);
        }
    }//GEN-LAST:event_occupationtfKeyPressed

    private void patientidtfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_patientidtfKeyTyped
        // TODO add your handling code here:
         char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
    }//GEN-LAST:event_patientidtfKeyTyped

    private void householdtfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_householdtfKeyTyped
        // TODO add your handling code here:
         char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
    }//GEN-LAST:event_householdtfKeyTyped

    private void puroktfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_puroktfKeyTyped
        // TODO add your handling code here:
         char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
    }//GEN-LAST:event_puroktfKeyTyped

    private void agetfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agetfKeyTyped
        // TODO add your handling code here:
         char input = evt.getKeyChar();
       if(!(Character.isDigit(input))
               ||(input == KeyEvent.VK_BACKSPACE)
               ||(input == KeyEvent.VK_DELETE)){
           evt.consume();
       }
    }//GEN-LAST:event_agetfKeyTyped

    private void mobiletfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mobiletfKeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_mobiletfKeyTyped

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
            java.util.logging.Logger.getLogger(patientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(patientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(patientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(patientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new patientForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField agetf;
    private javax.swing.JTextField bdatetf;
    private javax.swing.JComboBox<String> civilstatus;
    private javax.swing.JButton clearbtn;
    private javax.swing.JLabel date;
    private javax.swing.JButton displaybtn;
    private javax.swing.JTextField fnametf;
    private javax.swing.JComboBox<String> gender;
    private javax.swing.JTextField householdtf;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField lnametf;
    private javax.swing.JTextField mnametf;
    private javax.swing.JTextField mobiletf;
    private javax.swing.JTextField occupationtf;
    private javax.swing.JButton okbtn;
    private javax.swing.JTextField patientidtf;
    private javax.swing.JTextField puroktf;
    private javax.swing.JButton searchbtn;
    private javax.swing.JLabel time;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
 private void showDate(){
        d = new Date();
        dd = new SimpleDateFormat("dd-MM-yyyy");
        date.setText(dd.format(d));
        
        new Timer(0,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date d = new Date();
                SimpleDateFormat dd = new SimpleDateFormat("hh:mm:ss a");
                time.setText(dd.format(d));
            }
        }).start();
    }
}


