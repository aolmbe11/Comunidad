package comunidad;

import models.PropietariosTableModel;
import models.CuotasTableModel;
import models.ViviendasTableModel;
import renders.PagadoRenderer;
import renders.FechaRenderer;
import renders.PrecioRenderer;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import renders.ListaViviendasRenderer;

public class Main extends javax.swing.JFrame {

    Vivienda vivienda;
    Vivienda vivienda1;
    Vivienda vivienda2;
    Propietario propietario;
    Cuota cuota;

    Cuotas cuotas = new Cuotas();
    Propietarios propietarios = new Propietarios();
    Viviendas viviendas = new Viviendas();

    public Main() {
        initComponents();
        //tablaViviendas.setModel(new ViviendasTableModel());
        //tablaPropietarios.setModel(new PropietariosTableModel());
        //tablaCuotas.setModel(new CuotasTableModel());
        botonModificarVivienda.setEnabled(false);
        botonModificarPropietario.setEnabled(false);
        botonModificarCuota.setEnabled(false);
        tablaViviendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaViviendas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                mostrarDetallesViviendas();
            }
        });

        tablaPropietarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPropietarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                mostrarDetallesPropietarios();
            }
        });

        tablaCuotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCuotas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                mostrarDetallesCuotas();
            }
        });

        vivienda = new Vivienda("1-B", "Sebastián", "1234");
        vivienda1 = new Vivienda("2-C", "Curro", "1234");
        vivienda2 = new Vivienda("3-A", "Javi", "1234");
        viviendas.setVivienda(vivienda);
        viviendas.setVivienda(vivienda1);
        viviendas.setVivienda(vivienda2);
        tablaViviendas.setModel(new ViviendasTableModel());

        jComboBoxViviendasPropietarios.setModel(new DefaultComboBoxModel(Viviendas.getListaViviendas().toArray()));
        jComboBoxViviendasPropietarios.setRenderer(new ListaViviendasRenderer());

        jComboBoxViviendasCuotas.setModel(new DefaultComboBoxModel(Viviendas.getListaViviendas().toArray()));
        jComboBoxViviendasCuotas.setRenderer(new ListaViviendasRenderer());

    }

    private void mostrarDetallesViviendas() {
        int filaSeleccionada = tablaViviendas.getSelectedRow();
        System.out.println(filaSeleccionada);
        if (filaSeleccionada < 0) {
            jT_Numero.setText("");
            jT_Propietario.setText("");
            jT_numCuenta.setText("");
            botonModificarVivienda.setEnabled(false);
        } else {
            botonModificarVivienda.setEnabled(true);
            jT_Numero.setText(Viviendas.getVivienda(filaSeleccionada).getNumero());
            jT_Propietario.setText(Viviendas.getVivienda(filaSeleccionada).getPropietario());
            jT_numCuenta.setText(Viviendas.getVivienda(filaSeleccionada).getNumCuenta());
        }
    }

    private void mostrarDetallesPropietarios() {
        int filaSeleccionada = tablaPropietarios.getSelectedRow();

        if (filaSeleccionada < 0) {
            jT_Nombre.setText("");
            jT_Apellidos.setText("");
            jT_Vivienda.setText("");
            jT_Email.setText("");
            jT_Telefono.setText("");
            botonModificarPropietario.setEnabled(false);
        } else {
            botonModificarPropietario.setEnabled(true);
            jT_Nombre.setText(Propietarios.getPropietario(filaSeleccionada).getNombre());
            jT_Apellidos.setText(Propietarios.getPropietario(filaSeleccionada).getApellidos());
            jT_Prop_Vivienda.setText(Propietarios.getPropietario(filaSeleccionada).getVivienda().getNumero());
            jT_Email.setText(Propietarios.getPropietario(filaSeleccionada).getEmail());
            jT_Telefono.setText(Propietarios.getPropietario(filaSeleccionada).getTelefono());
        }
    }

    private void mostrarDetallesCuotas() {
        int filaSeleccionada = tablaCuotas.getSelectedRow();

        NumberFormat formato = NumberFormat.getNumberInstance(Locale.ENGLISH);

        if (filaSeleccionada < 0) {
            jT_Vivienda.setText("");
            jT_Agua.setText("");
            jT_Luz.setText("");
            jT_Basura.setText("");
            jT_ZonasComunes.setText("");
            jT_Otros.setText("");
            jT_Total.setText("");
            jT_Fecha.setText("");
            jCheckBoxPagado.setSelected(false);
            botonModificarCuota.setEnabled(false);
        } else {
            botonModificarCuota.setEnabled(true);
            jT_Vivienda.setText(Cuotas.getCuota(filaSeleccionada).getVivienda().getNumero());
            jT_Agua.setText(String.valueOf(Cuotas.getCuota(filaSeleccionada).getAgua()));
            jT_Luz.setText(String.valueOf(Cuotas.getCuota(filaSeleccionada).getLuz()));
            jT_Basura.setText(String.valueOf(Cuotas.getCuota(filaSeleccionada).getBasura()));
            jT_ZonasComunes.setText(String.valueOf(Cuotas.getCuota(filaSeleccionada).getZonasComunes()));
            jT_Otros.setText(String.valueOf(Cuotas.getCuota(filaSeleccionada).getOtros()));
            jT_Total.setText(formato.format(Cuotas.getCuota(filaSeleccionada).getTotal()));
            jT_Fecha.setText(Cuotas.getCuota(filaSeleccionada).getFecha().toString());
            if (Cuotas.getCuota(filaSeleccionada).isPagado()) {
                jCheckBoxPagado.setSelected(true);
            } else {
                jCheckBoxPagado.setSelected(false);
            }

        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelViviendas = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaViviendas = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jT_Numero = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jT_Propietario = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jT_numCuenta = new javax.swing.JTextField();
        botonAñadirVivienda = new javax.swing.JButton();
        botonPruebaVIviendas = new javax.swing.JButton();
        botonModificarVivienda = new javax.swing.JButton();
        panelPropietarios = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPropietarios = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jT_Apellidos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jT_Nombre = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jT_Prop_Vivienda = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jT_Email = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jT_Telefono = new javax.swing.JTextField();
        botonAñadirPropietario = new javax.swing.JButton();
        botonPruebaPropietarios = new javax.swing.JButton();
        botonModificarPropietario = new javax.swing.JButton();
        jComboBoxViviendasPropietarios = new javax.swing.JComboBox();
        panelCuotas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCuotas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jT_Vivienda = new javax.swing.JTextField();
        jT_Agua = new javax.swing.JTextField();
        jT_Luz = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jT_Basura = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jT_ZonasComunes = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jT_Otros = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jT_Total = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jT_Fecha = new javax.swing.JTextField();
        jCheckBoxPagado = new javax.swing.JCheckBox();
        botonGuardarCuota = new javax.swing.JButton();
        botonPruebaCuotas = new javax.swing.JButton();
        botonModificarCuota = new javax.swing.JButton();
        botonCobrarCuota = new javax.swing.JButton();
        jComboBoxViviendasCuotas = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaViviendas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tablaViviendas);

        jLabel5.setText("Número:");

        jLabel6.setText("Propietario:");

        jLabel17.setText("Número de cuenta:");

        botonAñadirVivienda.setText("Añadir Vivienda");
        botonAñadirVivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAñadirViviendaActionPerformed(evt);
            }
        });

        botonPruebaVIviendas.setText("jButton1");
        botonPruebaVIviendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPruebaVIviendasActionPerformed(evt);
            }
        });

        botonModificarVivienda.setText("Modificar Vivienda");
        botonModificarVivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarViviendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelViviendasLayout = new javax.swing.GroupLayout(panelViviendas);
        panelViviendas.setLayout(panelViviendasLayout);
        panelViviendasLayout.setHorizontalGroup(
            panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViviendasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelViviendasLayout.createSequentialGroup()
                        .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonAñadirVivienda)
                            .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(panelViviendasLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jT_Propietario))
                                .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelViviendasLayout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addComponent(jT_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelViviendasLayout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jT_numCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(botonPruebaVIviendas))
                        .addContainerGap(132, Short.MAX_VALUE))
                    .addGroup(panelViviendasLayout.createSequentialGroup()
                        .addComponent(botonModificarVivienda)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelViviendasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelViviendasLayout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(0, 377, Short.MAX_VALUE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        panelViviendasLayout.setVerticalGroup(
            panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelViviendasLayout.createSequentialGroup()
                .addContainerGap(209, Short.MAX_VALUE)
                .addComponent(jT_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jT_Propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jT_numCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonAñadirVivienda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificarVivienda)
                .addGap(27, 27, 27)
                .addComponent(botonPruebaVIviendas)
                .addGap(24, 24, 24))
            .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelViviendasLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39)
                    .addComponent(jLabel5)
                    .addContainerGap(197, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Viviendas", panelViviendas);

        tablaPropietarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaPropietarios);

        jLabel3.setText("Apellidos:");

        jLabel4.setText("Nombre:");

        jLabel16.setText("Vivienda:");

        jLabel18.setText("E-Mail:");

        jLabel19.setText("Teléfono:");

        botonAñadirPropietario.setText("Añadir Propietario");
        botonAñadirPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAñadirPropietarioActionPerformed(evt);
            }
        });

        botonPruebaPropietarios.setText("jButton1");
        botonPruebaPropietarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPruebaPropietariosActionPerformed(evt);
            }
        });

        botonModificarPropietario.setText("Modificar Propietario");
        botonModificarPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarPropietarioActionPerformed(evt);
            }
        });

        jComboBoxViviendasPropietarios.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelPropietariosLayout = new javax.swing.GroupLayout(panelPropietarios);
        panelPropietarios.setLayout(panelPropietariosLayout);
        panelPropietariosLayout.setHorizontalGroup(
            panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropietariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPropietariosLayout.createSequentialGroup()
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                            .addGroup(panelPropietariosLayout.createSequentialGroup()
                                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPropietariosLayout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jT_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(botonPruebaPropietarios))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(panelPropietariosLayout.createSequentialGroup()
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelPropietariosLayout.createSequentialGroup()
                                .addComponent(botonAñadirPropietario)
                                .addGap(18, 18, 18)
                                .addComponent(botonModificarPropietario))
                            .addGroup(panelPropietariosLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jT_Prop_Vivienda))
                            .addGroup(panelPropietariosLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(jT_Email))
                            .addGroup(panelPropietariosLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jT_Nombre))
                            .addGroup(panelPropietariosLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jT_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxViviendasPropietarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
        );
        panelPropietariosLayout.setVerticalGroup(
            panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropietariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jT_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxViviendasPropietarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jT_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jT_Prop_Vivienda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jT_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jT_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAñadirPropietario)
                    .addComponent(botonModificarPropietario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonPruebaPropietarios)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Propietarios", panelPropietarios);

        tablaCuotas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaCuotas);

        jLabel1.setText("Vivienda:");

        jLabel2.setText("Agua:");

        jLabel8.setText("Luz:");

        jLabel9.setText("Basura:");

        jLabel10.setText("Zonas Comunes:");

        jLabel11.setText("Otros:");

        jLabel12.setText("Total:");

        jT_Total.setEditable(false);

        jLabel13.setText("Fecha:");

        jCheckBoxPagado.setText("Pagado");

        botonGuardarCuota.setText("Guardar Cuota");
        botonGuardarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarCuotaActionPerformed(evt);
            }
        });

        botonPruebaCuotas.setText("jButton1");
        botonPruebaCuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPruebaCuotasActionPerformed(evt);
            }
        });

        botonModificarCuota.setText("Modificar Cuota");
        botonModificarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarCuotaActionPerformed(evt);
            }
        });

        botonCobrarCuota.setText("Cobrar Cuota");

        jComboBoxViviendasCuotas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelCuotasLayout = new javax.swing.GroupLayout(panelCuotas);
        panelCuotas.setLayout(panelCuotasLayout);
        panelCuotasLayout.setHorizontalGroup(
            panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCuotasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCuotasLayout.createSequentialGroup()
                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jT_Luz, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jT_Agua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jT_Basura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonPruebaCuotas)
                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelCuotasLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jT_Vivienda, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jT_Otros, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                            .addComponent(jT_ZonasComunes, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                            .addComponent(jT_Total, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                            .addComponent(jT_Fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                                    .addGroup(panelCuotasLayout.createSequentialGroup()
                                        .addComponent(botonGuardarCuota)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(botonModificarCuota)
                                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(jCheckBoxPagado, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(botonCobrarCuota)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxViviendasCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 10, Short.MAX_VALUE))))
        );
        panelCuotasLayout.setVerticalGroup(
            panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCuotasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jT_Vivienda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jT_ZonasComunes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jT_Agua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jT_Otros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jT_Luz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jT_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jT_Basura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jT_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxPagado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCuotasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxViviendasCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGuardarCuota)
                    .addComponent(botonModificarCuota)
                    .addComponent(botonCobrarCuota))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonPruebaCuotas)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cuotas", panelCuotas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAñadirViviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAñadirViviendaActionPerformed
        //vivienda = new Vivienda(jT_Numero.getText(), jT_Propietario.getText(), jT_numCuenta.getText());
        vivienda = new Vivienda("1-B", "Sebastián", "1234");
        viviendas.setVivienda(vivienda);
        tablaViviendas.setModel(new ViviendasTableModel());


    }//GEN-LAST:event_botonAñadirViviendaActionPerformed

    private void botonPruebaVIviendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPruebaVIviendasActionPerformed
        System.out.println(viviendas.toString());
    }//GEN-LAST:event_botonPruebaVIviendasActionPerformed

    private void botonAñadirPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAñadirPropietarioActionPerformed

        // vivienda.setNumero(jT_Prop_Vivienda.getText()) ?? 
//        propietario = new Propietario(jT_Apellidos.getText(), jT_Nombre.getText(),
//                new Vivienda(jT_Prop_Vivienda.getText(), jT_Nombre.getText() + " " +jT_Apellidos.getText(),
//                        null), jT_Email.getText(), jT_Telefono.getText());
        propietario = new Propietario("Perez Moreno", "Seba", vivienda, "seba@ie.es", "651726512");
        propietarios.setPropietario(propietario);
        tablaPropietarios.setModel(new PropietariosTableModel());
    }//GEN-LAST:event_botonAñadirPropietarioActionPerformed

    private void botonPruebaPropietariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPruebaPropietariosActionPerformed
        System.out.println(propietarios.toString());
    }//GEN-LAST:event_botonPruebaPropietariosActionPerformed

    private void botonGuardarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarCuotaActionPerformed
        cuota = new Cuota(vivienda, 15.20, 13.02, 5.05, 10.25, 0.0, Cuotas.calcularTotal(15.20, 
                13.02, 5.05, 10.25, 0.0) ,new Date(2015, 05, 12), false);
        cuotas.setCuota(cuota);
        tablaCuotas.setModel(new CuotasTableModel());

        tablaCuotas.getColumnModel().getColumn(1).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(2).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(3).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(4).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(5).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(6).setCellRenderer(new PrecioRenderer());

        tablaCuotas.getColumnModel().getColumn(7).setCellRenderer(new FechaRenderer());
        tablaCuotas.getColumnModel().getColumn(8).setCellRenderer(new PagadoRenderer());

    }//GEN-LAST:event_botonGuardarCuotaActionPerformed

    private void botonPruebaCuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPruebaCuotasActionPerformed
        System.out.println(cuotas.toString());
    }//GEN-LAST:event_botonPruebaCuotasActionPerformed

    private void botonModificarViviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarViviendaActionPerformed
        int filaSeleccionada = tablaViviendas.getSelectedRow();

        Vivienda viviendaSeleccionada = Viviendas.getVivienda(filaSeleccionada);

        viviendaSeleccionada.setNumero(jT_Numero.getText());
        viviendaSeleccionada.setPropietario(jT_Propietario.getText());
        viviendaSeleccionada.setNumCuenta(jT_numCuenta.getText());
    }//GEN-LAST:event_botonModificarViviendaActionPerformed

    private void botonModificarPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarPropietarioActionPerformed
        int filaSeleccionada = tablaPropietarios.getSelectedRow();

        Propietario propietarioSeleccionado = Propietarios.getPropietario(filaSeleccionada);

        propietarioSeleccionado.setApellidos(jT_Apellidos.getText());
        propietarioSeleccionado.setNombre(jT_Nombre.getText());
        propietarioSeleccionado.setVivienda((Vivienda)jComboBoxViviendasPropietarios.getSelectedItem());
        propietarioSeleccionado.setEmail(jT_Email.getText());
        propietarioSeleccionado.setTelefono(jT_Telefono.getText());
    }//GEN-LAST:event_botonModificarPropietarioActionPerformed

    private void botonModificarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarCuotaActionPerformed
        int filaSeleccionada = tablaCuotas.getSelectedRow();
        
        Cuota cuotaSeleccionada = Cuotas.getCuota(filaSeleccionada);

        cuotaSeleccionada.setVivienda((Vivienda)jComboBoxViviendasCuotas.getSelectedItem());
        cuotaSeleccionada.setAgua(Double.valueOf(jT_Agua.getText()));
        cuotaSeleccionada.setLuz(Double.valueOf(jT_Agua.getText()));
        cuotaSeleccionada.setBasura(Double.valueOf(jT_Agua.getText()));
        cuotaSeleccionada.setZonasComunes(Double.valueOf(jT_Agua.getText()));
        cuotaSeleccionada.setOtros(Double.valueOf(jT_Agua.getText()));
        cuotaSeleccionada.setAgua(Double.valueOf(jT_Agua.getText()));
        cuotaSeleccionada.setTotal(Cuotas.calcularTotal(cuotaSeleccionada.getAgua(), cuotaSeleccionada.getLuz(), cuotaSeleccionada.getBasura(), cuotaSeleccionada.getZonasComunes(), cuotaSeleccionada.getOtros()));
        // setTotal ( Hacer suma en en el metodo de la clase Cuota y hacer campo de texto no editable?
    }//GEN-LAST:event_botonModificarCuotaActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAñadirPropietario;
    private javax.swing.JButton botonAñadirVivienda;
    private javax.swing.JButton botonCobrarCuota;
    private javax.swing.JButton botonGuardarCuota;
    private javax.swing.JButton botonModificarCuota;
    private javax.swing.JButton botonModificarPropietario;
    private javax.swing.JButton botonModificarVivienda;
    private javax.swing.JButton botonPruebaCuotas;
    private javax.swing.JButton botonPruebaPropietarios;
    private javax.swing.JButton botonPruebaVIviendas;
    private javax.swing.JCheckBox jCheckBoxPagado;
    private javax.swing.JComboBox jComboBoxViviendasCuotas;
    private javax.swing.JComboBox jComboBoxViviendasPropietarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jT_Agua;
    private javax.swing.JTextField jT_Apellidos;
    private javax.swing.JTextField jT_Basura;
    private javax.swing.JTextField jT_Email;
    private javax.swing.JTextField jT_Fecha;
    private javax.swing.JTextField jT_Luz;
    private javax.swing.JTextField jT_Nombre;
    private javax.swing.JTextField jT_Numero;
    private javax.swing.JTextField jT_Otros;
    private javax.swing.JTextField jT_Prop_Vivienda;
    private javax.swing.JTextField jT_Propietario;
    private javax.swing.JTextField jT_Telefono;
    private javax.swing.JTextField jT_Total;
    private javax.swing.JTextField jT_Vivienda;
    private javax.swing.JTextField jT_ZonasComunes;
    private javax.swing.JTextField jT_numCuenta;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelCuotas;
    private javax.swing.JPanel panelPropietarios;
    private javax.swing.JPanel panelViviendas;
    private javax.swing.JTable tablaCuotas;
    private javax.swing.JTable tablaPropietarios;
    private javax.swing.JTable tablaViviendas;
    // End of variables declaration//GEN-END:variables
}
