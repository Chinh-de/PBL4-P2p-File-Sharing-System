/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package p2pfilesharing.server.GUI;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import p2pfilesharing.server.DAL.onlinePeerManage;

public class onlinePeerDialog extends javax.swing.JDialog {
    DefaultTableModel Model;
    public onlinePeerDialog(JFrame parent) {
        super(parent, "Users are online", true);
        initComponents();
        setLocationRelativeTo(parent);
        Model = (DefaultTableModel)table_op.getModel();
        loadTable();
        
    }

    private void loadTable(){
        String[][] allOP = onlinePeerManage.getInstance().getAllOnlinePeerToArray();
        Model.setRowCount(0);
        for(int i = 0; i < allOP.length; i++)
        {
            Model.addRow(new Object[]{ allOP[i][0], allOP[i][1], allOP[i][2], allOP[i][3], allOP[i][4]});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_op = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        table_op.setBackground(new java.awt.Color(243, 213, 213));
        table_op.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "username", "Sending", "Downloading", "IP", "Port"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_op);
        if (table_op.getColumnModel().getColumnCount() > 0) {
            table_op.getColumnModel().getColumn(0).setResizable(false);
            table_op.getColumnModel().getColumn(1).setResizable(false);
            table_op.getColumnModel().getColumn(2).setResizable(false);
            table_op.getColumnModel().getColumn(3).setResizable(false);
            table_op.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                onlinePeerDialog dialog = new onlinePeerDialog(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_op;
    // End of variables declaration//GEN-END:variables
}
