/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AHCISystem;

import static AHCISystem.dashboard.time;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Reycham
 */
public class Login extends javax.swing.JFrame {
    Date d;
    SimpleDateFormat dd;
    /**
     * Creates new form Login
     */
     public Connection conn;
    public Statement st;
    int totalAttempt = 5;
    public Login() {
        initComponents();
        username.requestFocus();
         showDate();
         time.setVisible(false);
         date.setVisible(false);
         
     try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter", "root", "");
            st = conn.createStatement();
         //   JOptionPane.showMessageDialog(this, "Connected to database..","Success",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception c) {
            JOptionPane.showMessageDialog(this, c,"Database connection failed..",JOptionPane.ERROR_MESSAGE);
        }
    }
     public void login(){

        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "SELECT Username, Password FROM `admin_acct` WHERE Username=? AND Password=?";
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter", "root", "");
            ps = conn.prepareStatement(sql);
            ps.setString(1, username.getText());
            ps.setString(2, String.valueOf(password.getPassword()));
            ResultSet result = ps.executeQuery();

            if(totalAttempt !=0){
            if (result.next()) {
                JOptionPane.showMessageDialog(this, "LOGIN SUCCESSFULLY!\n Welcome "+username.getText()+"...!", "Success!", JOptionPane.INFORMATION_MESSAGE);

               try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into login (username, date, time, status) values ('"+username.getText()+"','"+date.getText()+"','"+time.getText()+"','Admin logged in')";
                st.executeUpdate(sql);
                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }  
                
                
                new dashboard().setVisible(true);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Try Again\n You only have "+totalAttempt+" attempts left", "FAILED", JOptionPane.ERROR_MESSAGE);
                totalAttempt --;
                username.requestFocus();
            }
            } else{
                JOptionPane.showMessageDialog(this, "Please contact your administrator!", "FAILED", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
     }
    public void staffLogin(){
     Connection conn = null;
        PreparedStatement ps = null;
        String sql = "SELECT Username, Password FROM `users` WHERE Username=? AND Password=?";
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter", "root", "");
            ps = conn.prepareStatement(sql);
            ps.setString(1, username.getText());
            ps.setString(2, String.valueOf(password.getPassword()));

            ResultSet result = ps.executeQuery();

            if(totalAttempt !=0){
            if (result.next()) {
                JOptionPane.showMessageDialog(this, "LOGIN SUCCESSFULLY!\n Welcome "+username.getText()+"...!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                
                 try{  
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthcenter","root","");
                st = conn.createStatement();
                sql= "insert into login (username, date, time, status) values ('"+username.getText()+"','"+date.getText()+"','"+time.getText()+"','Staff logged in')";
                st.executeUpdate(sql);
                  }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,e);
            }  
                        
                new dashboard().setVisible(true);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Try Again\n You only have "+totalAttempt+" attempts left", "FAILED", JOptionPane.ERROR_MESSAGE);
                totalAttempt --;
                username.requestFocus();
            }
            } else{
                JOptionPane.showMessageDialog(this, "Please contact your administrator!", "FAILED", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "ERROR!", JOptionPane.ERROR_MESSAGE);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usertype = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        login = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 70, 25));

        time.setText("00:00:00");
        jPanel2.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        date.setText("00-00-0000");
        jPanel2.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 580, 60);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setText("User type:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(60, 100, 84, 20);

        usertype.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        usertype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Admin", "Staff" }));
        usertype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usertypeActionPerformed(evt);
            }
        });
        jPanel1.add(usertype);
        usertype.setBounds(210, 90, 240, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setText("Username:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(60, 150, 88, 20);

        username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });
        username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameKeyPressed(evt);
            }
        });
        jPanel1.add(username);
        username.setBounds(210, 140, 240, 28);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setText("Password:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(60, 200, 84, 20);

        password.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });
        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordKeyPressed(evt);
            }
        });
        jPanel1.add(password);
        password.setBounds(210, 190, 240, 28);

        login.setBackground(new java.awt.Color(0, 153, 153));
        login.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginKeyPressed(evt);
            }
        });
        jPanel1.add(login);
        login.setBounds(210, 240, 240, 29);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("No account yet?");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(60, 300, 128, 22);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("Sign up here");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(210, 300, 240, 31);

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(210, 370, 87, 29);

        jButton1.setBackground(new java.awt.Color(255, 255, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(350, 370, 100, 29);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AHCISystem/IMG_20191219_200441.jpg"))); // NOI18N
        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 60, 580, 390);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usertypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usertypeActionPerformed
        // TODO add your handling code here:
        username.requestFocus();

    }//GEN-LAST:event_usertypeActionPerformed

    private void usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyPressed
        // TODO add your handling code here:

        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(username.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Username is empty!", "OPS!", JOptionPane.ERROR_MESSAGE);
            }else{
                password.requestFocus();
            }
        }
    }//GEN-LAST:event_usernameKeyPressed

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_passwordActionPerformed

    private void passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyPressed
        // TODO add your handling code here:
        String selectedValue = usertype.getSelectedItem().toString();

        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(password.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Password is empty!", "OPS!", JOptionPane.ERROR_MESSAGE);

            }else{

                switch (selectedValue) {
                    case "Admin":
                    login();

                    username.setText("");
                    password.setText("");
                    username.requestFocus();
                    break;
                    case "Staff":
                    staffLogin();

                    username.setText("");
                    password.setText("");
                    username.requestFocus();
                    break;
                    default:
                    JOptionPane.showMessageDialog(null, "Please select Usertype.");
                    break;
                }

            }
        }
    }//GEN-LAST:event_passwordKeyPressed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        // TODO add your handling code here:
        String selectedValue = usertype.getSelectedItem().toString();
        if(password.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Username or Password is empty.", "OPS!", JOptionPane.ERROR_MESSAGE);
            username.requestFocus();
        }else{
            switch (selectedValue) {
                case "Admin":
                login();

                username.setText("");
                password.setText("");
                username.requestFocus();
                break;
                case "Staff":
                staffLogin();

                username.setText("");
                password.setText("");
                username.requestFocus();
                break;
                default:
                JOptionPane.showMessageDialog(null, "Please select Usertype.");
                break;
            }

        }

    }//GEN-LAST:event_loginActionPerformed

    private void loginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginKeyPressed
        // TODO add your handling code here:
        String selectedValue = usertype.getSelectedItem().toString();
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){

            switch (selectedValue) {
                case "Admin":
                login();
                break;
                case "Staff":
                staffLogin();
                break;
                default:

                break;
            }

            username.setText("");
            password.setText("");

            username.requestFocus();

        }

    }//GEN-LAST:event_loginKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new userSignUp().setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        usertype.setSelectedItem("Select");
        username.setText("");
        password.setText("");

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Exit the system
        int opt = JOptionPane.showConfirmDialog(this, "Would you like to exit the system? \n Click 'YES' or 'ENTER' to continue.", "Message", JOptionPane.YES_NO_OPTION);
        if(opt==0){
            System.exit(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton login;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel time;
    public static javax.swing.JTextField username;
    public static javax.swing.JComboBox<String> usertype;
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
