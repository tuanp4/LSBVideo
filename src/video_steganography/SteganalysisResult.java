package video_steganography;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Valdez
 */
public class SteganalysisResult extends javax.swing.JFrame {

    /**
     * Creates new form Result
     */
    public SteganalysisResult(String compare) {
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        compare_value.setEditable(false);
        compare_value.setText(compare);
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Compare = new javax.swing.JPanel();
        compare_area = new javax.swing.JScrollPane();
        compare_value = new javax.swing.JTextArea();
        ok_button = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("File has been changed!");
        setResizable(false);

        Compare.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Compare", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Comic Sans MS", 0, 20), new java.awt.Color(0, 0, 255))); // NOI18N

        compare_value.setColumns(20);
        compare_value.setLineWrap(true);
        compare_value.setRows(5);
        compare_area.setViewportView(compare_value);

        javax.swing.GroupLayout CompareLayout = new javax.swing.GroupLayout(Compare);
        Compare.setLayout(CompareLayout);
        CompareLayout.setHorizontalGroup(
            CompareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompareLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(compare_area, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addContainerGap())
        );
        CompareLayout.setVerticalGroup(
            CompareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompareLayout.createSequentialGroup()
                .addComponent(compare_area, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );

        ok_button.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        ok_button.setText("OK");
        ok_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ok_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Compare, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(192, Short.MAX_VALUE)
                .addComponent(ok_button, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Compare, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ok_button, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ok_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_buttonActionPerformed
        this.hide();
    }//GEN-LAST:event_ok_buttonActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Result().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Compare;
    private javax.swing.JScrollPane compare_area;
    private javax.swing.JTextArea compare_value;
    private javax.swing.JToggleButton ok_button;
    // End of variables declaration//GEN-END:variables
}
