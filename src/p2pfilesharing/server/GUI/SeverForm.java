package p2pfilesharing.server.GUI;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.table.DefaultTableModel;
import p2pfilesharing.server.BLL.fileBLL;
import p2pfilesharing.server.BLL.logBLL;
import p2pfilesharing.server.DAL.onlinePeerManage;

public class SeverForm extends javax.swing.JFrame {
    public SeverForm() {
        initComponents();
        logModel = (DefaultTableModel)table_logs.getModel();
        fileModel = (DefaultTableModel)table_files.getModel();
        this.setTitle("Sever: P2P Sharing file System");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_StartOrStop = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_logs = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_files = new javax.swing.JTable();
        txt_onlineUsers = new javax.swing.JTextField();
        btn_ShowAllUserOnline = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        txt_startSever = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(67, 108, 154));

        btn_StartOrStop.setBackground(new java.awt.Color(189, 239, 208));
        btn_StartOrStop.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_StartOrStop.setText("Start Server");
        btn_StartOrStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_StartOrStopActionPerformed(evt);
            }
        });

        table_logs.setBackground(new java.awt.Color(243, 213, 213));
        table_logs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Time", "User", "Activity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_logs.getTableHeader().setResizingAllowed(false);
        table_logs.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table_logs);
        if (table_logs.getColumnModel().getColumnCount() > 0) {
            table_logs.getColumnModel().getColumn(0).setResizable(false);
            table_logs.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_logs.getColumnModel().getColumn(1).setResizable(false);
            table_logs.getColumnModel().getColumn(1).setPreferredWidth(50);
            table_logs.getColumnModel().getColumn(2).setResizable(false);
            table_logs.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        table_files.setBackground(new java.awt.Color(243, 213, 213));
        table_files.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File ID", "Name", "Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_files.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table_files.getTableHeader().setResizingAllowed(false);
        table_files.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(table_files);
        if (table_files.getColumnModel().getColumnCount() > 0) {
            table_files.getColumnModel().getColumn(0).setResizable(false);
            table_files.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_files.getColumnModel().getColumn(1).setResizable(false);
            table_files.getColumnModel().getColumn(1).setPreferredWidth(200);
            table_files.getColumnModel().getColumn(2).setResizable(false);
            table_files.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        txt_onlineUsers.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_onlineUsers.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_onlineUsers.setText("XXX user are online. ");
        txt_onlineUsers.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txt_onlineUsers.setFocusable(false);

        btn_ShowAllUserOnline.setBackground(new java.awt.Color(189, 239, 208));
        btn_ShowAllUserOnline.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btn_ShowAllUserOnline.setText("Show All online User");
        btn_ShowAllUserOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ShowAllUserOnlineActionPerformed(evt);
            }
        });

        btn_refresh.setBackground(new java.awt.Color(189, 239, 208));
        btn_refresh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(67, 108, 154));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Logs");
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.setFocusable(false);

        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(67, 108, 154));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("Files");
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField2.setFocusable(false);

        txt_startSever.setBackground(new java.awt.Color(67, 108, 154));
        txt_startSever.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        txt_startSever.setForeground(new java.awt.Color(26, 239, 208));
        txt_startSever.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_startSever.setText("Server Closed");
        txt_startSever.setBorder(null);
        txt_startSever.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(338, 338, 338)
                .addComponent(btn_StartOrStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(337, 337, 337))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(txt_onlineUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(btn_ShowAllUserOnline))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)))))
            .addComponent(txt_startSever, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_StartOrStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_startSever, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_onlineUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_ShowAllUserOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_StartOrStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_StartOrStopActionPerformed
        if("Start Server".equals(btn_StartOrStop.getText())){
            new Thread(() -> {
                server.startServer(); // Chạy server trên một luồng riêng
            }).start();
            try {
                txt_startSever.setText("Server is listening on IP: " + InetAddress.getLocalHost().getHostAddress() + " and port: 6969");
            } catch (UnknownHostException e) {
                System.err.println("Error:" + e);
            }
            btn_StartOrStop.setText("Stop Server");
            updateFilesTable();
            updateOnlinePeers();
            updateLogsTable();    
        } else{
            txt_startSever.setText("Server Closed");
            btn_StartOrStop.setText("Start Server");
            server.stopServer();
            
        }
    }//GEN-LAST:event_btn_StartOrStopActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        updateFilesTable();
        updateLogsTable();
        updateOnlinePeers();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_ShowAllUserOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ShowAllUserOnlineActionPerformed
        onlinePeerDialog form = new onlinePeerDialog(this);
        form.setVisible(true);
    }//GEN-LAST:event_btn_ShowAllUserOnlineActionPerformed


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SeverForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SeverForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SeverForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SeverForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SeverForm().setVisible(true);
            }
        });
    }

    public void updateLogsTable()
    {
        //clear table
        logModel.setRowCount(0);
        
        //load table
        String[][] logs = logBLL.getInstance().getAllLogs();
        for(int i = logs.length -1; i >= 0; i--)
        {
            logModel.addRow(new Object[]{logs[i][0], logs[i][1], logs[i][2]});
        }
    }
    public void updateFilesTable()
    {
        //clear table
        fileModel.setRowCount(0);
        
        //load table
        String[][] files = fileBLL.getInstance().getAllFiles();
        for(int i = 0; i < files.length; i++)
        {
            fileModel.addRow(new Object[]{files[i][0], files[i][1], files[i][2]});
        }
    }
    
    public void updateOnlinePeers()
    {
        txt_onlineUsers.setText(onlinePeerManage.getInstance().getOnlinePeerCount() + " user are online. ");
    }
    

    p2pfilesharing.server.BLL.server server = new p2pfilesharing.server.BLL.server();
    DefaultTableModel logModel, fileModel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ShowAllUserOnline;
    private javax.swing.JButton btn_StartOrStop;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable table_files;
    private javax.swing.JTable table_logs;
    private javax.swing.JTextField txt_onlineUsers;
    private javax.swing.JTextField txt_startSever;
    // End of variables declaration//GEN-END:variables

}
