package comunidad;

import entity_class.Iva;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class JDialogIVA extends javax.swing.JDialog {

    private Iva iva;
    private Query consultaIVA;

    public Iva getIva() {
        return iva;
    }

    public JDialogIVA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocationRelativeTo(null);

        consultaIVA = Main.entityManager.createNamedQuery("Iva.findAll");

        if (!consultaIVA.getResultList().isEmpty()) {
            iva = (Iva) consultaIVA.getResultList().get(0);

            jT_IVA_Agua.setText(String.valueOf(iva.getIvaAgua()));
            jT_IVA_Luz.setText(String.valueOf(iva.getIvaLuz()));
            jT_IVA_Basura.setText(String.valueOf(iva.getIvaBasura()));
            jT_IVA_ZOnas.setText(String.valueOf(iva.getIvaZonasComunes()));
            jT_IVA_Otros.setText(String.valueOf(iva.getIvaOtros()));

        } else {
            jT_IVA_Agua.setText("");
            jT_IVA_Luz.setText("");
            jT_IVA_Basura.setText("");
            jT_IVA_ZOnas.setText("");
            jT_IVA_Otros.setText("");
        }
    }

    private void controlDecimalIVA(JTextField jTextField, int precision, int scale) {
        boolean error = false;

        if (!jTextField.getText().isEmpty()) {
            try {
                // Convert written text to numeric type
                BigDecimal bigDecimal = new BigDecimal(jTextField.getText());
                // Round to scale decimals
                bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
                // Show number rounded
                jTextField.setText(bigDecimal.toString());
                // Check if lenth is greater than limit 
                if (jTextField.getText().length() > precision + 1) {
                    JOptionPane.showMessageDialog(this,
                            "Valor demasiado largo.\n"
                            + "Debe tener un máximo de " + precision + " dígitos\n"
                            + "incluyendo  " + scale + " decimales\n",
                            "Atención", JOptionPane.WARNING_MESSAGE
                    );
                    error = true;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor no numérico",
                        "Atención", JOptionPane.WARNING_MESSAGE);
                error = true;
            }
        }
        if (error) {
            // Stay on JTextField and preselect text
            jTextField.requestFocus();
            jTextField.setSelectionStart(0);
            jTextField.setSelectionEnd(jTextField.getText().length());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jT_IVA_Agua = new javax.swing.JTextField();
        jT_IVA_Luz = new javax.swing.JTextField();
        jT_IVA_Basura = new javax.swing.JTextField();
        jT_IVA_ZOnas = new javax.swing.JTextField();
        jT_IVA_Otros = new javax.swing.JTextField();
        botonActualizar = new javax.swing.JButton();
        botonCancelarIVA = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("IVA");
        jLabel1.setToolTipText("");

        jLabel2.setText("Agua:");

        jLabel3.setText("Luz:");

        jLabel4.setText("Basura:");

        jLabel5.setText("Zonas Comunes:");

        jLabel6.setText("Otros:");

        jT_IVA_Agua.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_IVA_AguaFocusLost(evt);
            }
        });

        jT_IVA_Luz.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_IVA_LuzFocusLost(evt);
            }
        });

        jT_IVA_Basura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_IVA_BasuraFocusLost(evt);
            }
        });

        jT_IVA_ZOnas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_IVA_ZOnasFocusLost(evt);
            }
        });

        jT_IVA_Otros.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_IVA_OtrosFocusLost(evt);
            }
        });

        botonActualizar.setText("Actualizar");
        botonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarActionPerformed(evt);
            }
        });

        botonCancelarIVA.setText("Cancelar");
        botonCancelarIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarIVAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jT_IVA_Agua, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jT_IVA_Luz, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jT_IVA_Basura, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jT_IVA_ZOnas, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jT_IVA_Otros, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(botonActualizar)
                        .addGap(18, 18, 18)
                        .addComponent(botonCancelarIVA)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jT_IVA_Agua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jT_IVA_Luz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jT_IVA_Basura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jT_IVA_ZOnas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jT_IVA_Otros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonActualizar)
                    .addComponent(botonCancelarIVA))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarActionPerformed
        if (consultaIVA.getResultList().isEmpty()) {
            System.out.println("vacio");
            iva = new Iva(null, Values.toBigDecimal(jT_IVA_Agua.getText()),
                    Values.toBigDecimal(jT_IVA_Luz.getText()), Values.toBigDecimal(jT_IVA_Basura.getText()),
                    Values.toBigDecimal(jT_IVA_ZOnas.getText()), Values.toBigDecimal(jT_IVA_Otros.getText()), BigDecimal.ZERO);
            Main.entityManager.getTransaction().begin();
            Main.entityManager.persist(iva);
            Main.entityManager.getTransaction().commit();
        } else {
            System.out.println("no vacio");
            iva.setIvaAgua(Values.toBigDecimal(jT_IVA_Agua.getText()));
            iva.setIvaLuz(Values.toBigDecimal(jT_IVA_Luz.getText()));
            iva.setIvaBasura(Values.toBigDecimal(jT_IVA_Basura.getText()));
            iva.setIvaZonasComunes(Values.toBigDecimal(jT_IVA_ZOnas.getText()));
            iva.setIvaOtros(Values.toBigDecimal(jT_IVA_Otros.getText()));
            Main.entityManager.getTransaction().begin();
            Main.entityManager.merge(iva);
            Main.entityManager.getTransaction().commit();
        }
    }//GEN-LAST:event_botonActualizarActionPerformed

    private void botonCancelarIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarIVAActionPerformed
        setVisible(false);
    }//GEN-LAST:event_botonCancelarIVAActionPerformed

    private void jT_IVA_AguaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_IVA_AguaFocusLost
        this.controlDecimalIVA(jT_IVA_Agua, 3, 2);
    }//GEN-LAST:event_jT_IVA_AguaFocusLost

    private void jT_IVA_LuzFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_IVA_LuzFocusLost
        this.controlDecimalIVA(jT_IVA_Luz, 3, 2);
    }//GEN-LAST:event_jT_IVA_LuzFocusLost

    private void jT_IVA_BasuraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_IVA_BasuraFocusLost
        this.controlDecimalIVA(jT_IVA_Basura, 3, 2);
    }//GEN-LAST:event_jT_IVA_BasuraFocusLost

    private void jT_IVA_ZOnasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_IVA_ZOnasFocusLost
        this.controlDecimalIVA(jT_IVA_ZOnas, 3, 2);
    }//GEN-LAST:event_jT_IVA_ZOnasFocusLost

    private void jT_IVA_OtrosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_IVA_OtrosFocusLost
        this.controlDecimalIVA(jT_IVA_Otros, 3, 2);
    }//GEN-LAST:event_jT_IVA_OtrosFocusLost

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
            java.util.logging.Logger.getLogger(JDialogIVA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogIVA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogIVA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogIVA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogIVA dialog = new JDialogIVA(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botonActualizar;
    private javax.swing.JButton botonCancelarIVA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jT_IVA_Agua;
    private javax.swing.JTextField jT_IVA_Basura;
    private javax.swing.JTextField jT_IVA_Luz;
    private javax.swing.JTextField jT_IVA_Otros;
    private javax.swing.JTextField jT_IVA_ZOnas;
    // End of variables declaration//GEN-END:variables
}
