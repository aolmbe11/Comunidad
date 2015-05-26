package comunidad;

import entity_class.Comunidad;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.ComunidadTableModel;

/**
 *
 * @author Araceli Ramirez
 */
public class JDialogInicio extends javax.swing.JDialog {

    private ComunidadTableModel comunidadTableModel = new ComunidadTableModel();

    private Query consultaComundidades;
    private Comunidad comunidad;
    private Comunidades comunidades = new Comunidades();

    public Comunidad getComunidadSelected() {
        Comunidad comunidadSeleccionada = (Comunidad) Comunidades.getComunidad(tablaComunidades.getSelectedRow());
        return comunidadSeleccionada;
    }

    public void setClearSelection(boolean clear) {
        if (clear) {
            tablaComunidades.clearSelection();
        }
    }

    private void insertComunidad(Comunidad comunidad) {
        Main.entityManager.persist(comunidad);
        comunidades.setComunidad(comunidad);
    }

    private void camposComunidadEditable(boolean editable) {
        if (editable) {
            comunidadTableModel.setEditable(true);
            botonAñadirComunidad.setEnabled(true);
            botonGuardarCambios.setEnabled(true);
            botonCancelarCambios.setEnabled(true);

        } else {
            comunidadTableModel.setEditable(false);
            botonAñadirComunidad.setEnabled(false);
            botonGuardarCambios.setEnabled(false);
            botonCancelarCambios.setEnabled(false);
        }
    }

    public JDialogInicio(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocationRelativeTo(null);

        botonAdministarComunidad.setEnabled(false);
        this.camposComunidadEditable(false);

        consultaComundidades = Main.entityManager.createNamedQuery("Comunidad.findAll");
        Comunidades.setListaComunidades(consultaComundidades.getResultList());

        tablaComunidades.setModel(comunidadTableModel);
        Main.texFieldinCellEditor(tablaComunidades, 0, Values.TAM_NOMBRE_COMUNIDAD);
        Main.texFieldinCellEditor(tablaComunidades, 1, Values.TAM_DIRECCION_COMUNIDAD);

        tablaComunidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaComunidades.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int filaSeleccionada = tablaComunidades.getSelectedRow();
                if (filaSeleccionada < 0) {
                    botonAdministarComunidad.setEnabled(false);
                } else {
                    botonAdministarComunidad.setEnabled(true);
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaLogo = new javax.swing.JLabel();
        botonAdministarComunidad = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaComunidades = new javax.swing.JTable();
        botonAñadirComunidad = new javax.swing.JButton();
        botonEditarComunidad = new javax.swing.JButton();
        botonGuardarCambios = new javax.swing.JButton();
        botonCancelarCambios = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        etiquetaLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo.jpg"))); // NOI18N

        botonAdministarComunidad.setText("Administrar Comunidad");
        botonAdministarComunidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAdministarComunidadActionPerformed(evt);
            }
        });

        jLabel2.setText("Mis Comunidades:");

        tablaComunidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaComunidades);

        botonAñadirComunidad.setText("Añadir Comunidad");
        botonAñadirComunidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAñadirComunidadActionPerformed(evt);
            }
        });

        botonEditarComunidad.setText("Editar");
        botonEditarComunidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarComunidadActionPerformed(evt);
            }
        });

        botonGuardarCambios.setText("Guardar Cambios");
        botonGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarCambiosActionPerformed(evt);
            }
        });

        botonCancelarCambios.setText("Cancelar Cambios");
        botonCancelarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarCambiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(botonEditarComunidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonAñadirComunidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonGuardarCambios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonCancelarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonAdministarComunidad))
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(etiquetaLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonEditarComunidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonAñadirComunidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonGuardarCambios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonCancelarCambios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonAdministarComunidad)))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAdministarComunidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAdministarComunidadActionPerformed
        if (Main.entityManager.getTransaction().isActive()) {
            JOptionPane.showMessageDialog(this, "Debe terminar de editar la comunidad");
        } else {
            setVisible(false);
        }


    }//GEN-LAST:event_botonAdministarComunidadActionPerformed

    private void botonAñadirComunidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAñadirComunidadActionPerformed

        comunidad = new Comunidad();
        this.insertComunidad(comunidad);
        comunidadTableModel.fireTableRowsInserted(Comunidades.getListaComunidades().size() - 1, Comunidades.getListaComunidades().size() - 1);
        tablaComunidades.setRowSelectionInterval(Comunidades.getListaComunidades().size() - 1, Comunidades.getListaComunidades().size() - 1);
    }//GEN-LAST:event_botonAñadirComunidadActionPerformed

    private void botonEditarComunidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarComunidadActionPerformed
        Main.entityManager.getTransaction().begin();
        this.camposComunidadEditable(true);
    }//GEN-LAST:event_botonEditarComunidadActionPerformed

    private void botonGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarCambiosActionPerformed
        Main.entityManager.getTransaction().commit();
        this.camposComunidadEditable(false);
    }//GEN-LAST:event_botonGuardarCambiosActionPerformed

    private void botonCancelarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarCambiosActionPerformed
        Main.entityManager.getTransaction().rollback();
        Comunidades.setListaComunidades(consultaComundidades.getResultList());
        comunidadTableModel.fireTableDataChanged();
        this.camposComunidadEditable(false);
    }//GEN-LAST:event_botonCancelarCambiosActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Main.entityManager.close();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(JDialogInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogInicio dialog = new JDialogInicio(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botonAdministarComunidad;
    private javax.swing.JButton botonAñadirComunidad;
    private javax.swing.JButton botonCancelarCambios;
    private javax.swing.JButton botonEditarComunidad;
    private javax.swing.JButton botonGuardarCambios;
    private javax.swing.JLabel etiquetaLogo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaComunidades;
    // End of variables declaration//GEN-END:variables
}
