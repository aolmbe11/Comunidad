package comunidad;

import entity_class.Comunidad;
import entity_class.Cuota;
import entity_class.Iva;
import entity_class.Propietario;
import entity_class.Vivienda;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import models.PropietariosTableModel;
import models.CuotasTableModel;
import models.ViviendasTableModel;
import renders.PagadoRenderer;
import renders.FechaRenderer;
import renders.PrecioRenderer;
import java.text.NumberFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import renders.ListaPropietariosRenderer;
import renders.ListaViviendasRenderer;
import renders.MaxLengthDocument;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Main extends javax.swing.JFrame {

    public static final int COMUNIDAD = 1;
    public static final int PROPIETARIO = 3;
    public static final int CUOTA = 4;

    private Comunidad comunidad;

    private Cuotas cuotas = new Cuotas();
    private Propietarios propietarios = new Propietarios();
    private Viviendas viviendas = new Viviendas();

    private PropietariosTableModel propietariosTableModel = new PropietariosTableModel(propietarios);
    private CuotasTableModel cuotasTableModel = new CuotasTableModel(cuotas);
    private ViviendasTableModel viviendasTableModel = new ViviendasTableModel(viviendas);

    private JComboBox comboBoxCellPropietario;

    private NumberFormat formato = NumberFormat.getCurrencyInstance();

    private JDialogInicio jDialogInicio;
    private JDialogIVA jDialogIVA;

    public static EntityManager entityManager;

    private Connection conexion;

    private Query consultaPropietarios;
    private Query consultaViviendas;
    private Query consultaCuotas;

    private Iva iva;
    private BigDecimal importeIVA;

    public Main() {
        initComponents();

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/house.png")));

        this.setLocationRelativeTo(null);

        this.camposViviendasEditable(false);

        try{
            entityManager = Persistence.createEntityManagerFactory("ComunidadPU").createEntityManager();
        } catch(PersistenceException ex){
            JOptionPane.showMessageDialog(this, "No se han podido cargar los datos. No hay conexión con la base de datos.");
        }
        

        jDialogInicio = new JDialogInicio(this, true);
        jDialogIVA = new JDialogIVA(this, true);
        jDialogInicio.setVisible(true);

        comunidad = jDialogInicio.getComunidadSelected();
        etiquetaNombreComunidad.setText(comunidad.getNombre());

        consultaPropietarios = entityManager.createNamedQuery("Propietario.findAll");
        consultaViviendas = entityManager.createQuery("SELECT v FROM Vivienda v WHERE v.comunidad = :comunidad");
        consultaCuotas = entityManager.createQuery("SELECT v FROM Cuota v WHERE v.vivienda.comunidad = :comunidad");
        consultaViviendas.setParameter("comunidad", comunidad);
        consultaCuotas.setParameter("comunidad", comunidad);

        propietarios.setListaPropietarios(consultaPropietarios.getResultList());
        viviendas.setListaViviendas(consultaViviendas.getResultList());
        cuotas.setListaCuotas(consultaCuotas.getResultList());

        jT_Apellidos.setDocument(new MaxLengthDocument(Values.TAM_APELLIDOS_PROP));
        jT_Nombre.setDocument(new MaxLengthDocument(Values.TAM_NOMBRE_PROP));
        jT_Email.setDocument(new MaxLengthDocument(Values.TAM_EMAIL_PROP));
        jT_Telefono.setDocument(new MaxLengthDocument(Values.TAM_TELEF_PROP));
        jT_DNI.setDocument(new MaxLengthDocument(Values.TAM_DNI_PROP));

        tablaViviendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

        tablaViviendas.setModel(viviendasTableModel);

        jComboBoxViviendasCuotas.setModel(new DefaultComboBoxModel(viviendas.getListaViviendas().toArray()));
        jComboBoxViviendasCuotas.setRenderer(new ListaViviendasRenderer());

        jListViviendas.setCellRenderer(new ListaViviendasRenderer());

        tablaPropietarios.setModel(propietariosTableModel);

        tablaCuotas.setModel(cuotasTableModel);

        this.comboPropietarioCellEditor(tablaViviendas, 1);
        Main.texFieldinCellEditor(tablaViviendas, 0, Values.TAM_NUM_VIVIENDA);
        Main.texFieldinCellEditor(tablaViviendas, 2, Values.TAM_NUM_CUENTA_VIVIENDA);

        tablaCuotas.getColumnModel().getColumn(1).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(2).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(3).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(4).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(5).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(6).setCellRenderer(new PrecioRenderer());
        tablaCuotas.getColumnModel().getColumn(7).setCellRenderer(new FechaRenderer());
        tablaCuotas.getColumnModel().getColumn(8).setCellRenderer(new PagadoRenderer());

        botonModificarPropietario.setEnabled(false);
        botonCancelarPropietario.setEnabled(false);
        botonEliminarPropietario.setEnabled(false);

        botonModificarCuota.setEnabled(false);
        botonCancelarCuota.setEnabled(false);
        botonEliminarCuota.setEnabled(false);

        iva = jDialogIVA.getIva();
        if (iva == null) {
            label_IVA_agua.setText("Sin IVA");
            label_IVA_luz.setText("Sin IVA");
            label_IVA_basura.setText("Sin IVA");
            label_IVA_zonas.setText("Sin IVA");
            label_IVA_otros.setText("Sin IVA");
        } else {
            label_IVA_agua.setText("IVA: " + iva.getIvaAgua().multiply(BigDecimal.valueOf(100)).intValue() + "%");
            label_IVA_luz.setText("IVA: " + iva.getIvaLuz().multiply(BigDecimal.valueOf(100)).intValue() + "%");
            label_IVA_basura.setText("IVA: " + iva.getIvaBasura().multiply(BigDecimal.valueOf(100)).intValue() + "%");
            label_IVA_zonas.setText("IVA: " + iva.getIvaZonasComunes().multiply(BigDecimal.valueOf(100)).intValue() + "%");
            label_IVA_otros.setText("IVA: " + iva.getIvaOtros().multiply(BigDecimal.valueOf(100)).intValue() + "%");
        }
    }

    private void camposViviendasEditable(boolean editable) {
        if (editable) {
            viviendasTableModel.setEditable(true);
            botonAñadirVivienda.setEnabled(true);
            botonGuardarCambios.setEnabled(true);
            botonCancelarCambios.setEnabled(true);
            botonVolver.setEnabled(false);
        } else {
            viviendasTableModel.setEditable(false);
            botonAñadirVivienda.setEnabled(false);
            botonGuardarCambios.setEnabled(false);
            botonCancelarCambios.setEnabled(false);
            botonVolver.setEnabled(true);
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
            jT_DNI.setText("");
            botonModificarPropietario.setEnabled(false);
            botonCancelarPropietario.setEnabled(false);
            botonEliminarPropietario.setEnabled(false);
        } else {
            botonModificarPropietario.setEnabled(true);
            botonCancelarPropietario.setEnabled(true);
            botonEliminarPropietario.setEnabled(true);
            botonAñadirPropietario.setEnabled(false);
            jT_Nombre.setText(propietarios.getPropietario(filaSeleccionada).getNombre());
            jT_Apellidos.setText(propietarios.getPropietario(filaSeleccionada).getApellidos());
            jListViviendas.setListData(propietarios.getPropietario(filaSeleccionada).getViviendaCollection().toArray());
            jT_Email.setText(propietarios.getPropietario(filaSeleccionada).getEmail());
            jT_Telefono.setText(propietarios.getPropietario(filaSeleccionada).getTelefono());
            jT_DNI.setText(propietarios.getPropietario(filaSeleccionada).getDni());
        }
    }

    private void mostrarDetallesCuotas() {
        int filaSeleccionada = tablaCuotas.getSelectedRow();

        if (filaSeleccionada < 0) {
            jT_Vivienda.setText("");
            jT_Agua.setText("");
            jT_Luz.setText("");
            jT_Basura.setText("");
            jT_ZonasComunes.setText("");
            jT_Otros.setText("");
            jT_Total.setText("");
            jDateChooser1.setDate(null);
            jCheckBoxPagado.setSelected(false);
            botonModificarCuota.setEnabled(false);
            botonEliminarCuota.setEnabled(false);
            botonCancelarCuota.setEnabled(false);
        } else {
            botonModificarCuota.setEnabled(true);
            botonEliminarCuota.setEnabled(true);
            botonCancelarCuota.setEnabled(true);
            botonGuardarCuota.setEnabled(false);

            jT_Vivienda.setText(cuotas.getCuota(filaSeleccionada).getVivienda().getNumero());
            jT_Agua.setText(String.valueOf(cuotas.getCuota(filaSeleccionada).getAgua()));
            jT_Luz.setText(String.valueOf(cuotas.getCuota(filaSeleccionada).getLuz()));
            jT_Basura.setText(String.valueOf(cuotas.getCuota(filaSeleccionada).getBasura()));
            jT_ZonasComunes.setText(String.valueOf(cuotas.getCuota(filaSeleccionada).getZonasComunes()));
            jT_Otros.setText(String.valueOf(cuotas.getCuota(filaSeleccionada).getOtros()));
            jT_Total.setText(formato.format(cuotas.getCuota(filaSeleccionada).getTotal()));
            jDateChooser1.setDate((cuotas.getCuota(filaSeleccionada).getFecha()));
            if (cuotas.getCuota(filaSeleccionada).getPagado()) {
                jCheckBoxPagado.setSelected(true);
            } else {
                jCheckBoxPagado.setSelected(false);
            }
        }
    }

    private void controlDecimalCuotas(JTextField jTextField, int precision, int scale) {
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

    public static void texFieldinCellEditor(JTable jtable, int columna, int tamaño) {
        TableColumn column = jtable.getColumnModel().getColumn(columna);
        JTextField jTextField = new JTextField();

        Border borde = BorderFactory.createLineBorder(Color.BLACK);
        jTextField.setBorder(borde);
        jTextField.setMargin(new Insets(0, 0, 0, 0));
        jTextField.setDocument(new MaxLengthDocument(tamaño));
        column.setCellEditor(new DefaultCellEditor(jTextField));
    }

    private void comboPropietarioCellEditor(JTable jtable, int columna) {
        TableColumn propietarioColumn = jtable.getColumnModel().getColumn(columna);
        comboBoxCellPropietario = new JComboBox();
        comboBoxCellPropietario.setModel(new DefaultComboBoxModel(propietarios.getListaPropietarios().toArray()));
        comboBoxCellPropietario.setRenderer(new ListaPropietariosRenderer());
        propietarioColumn.setCellEditor(new DefaultCellEditor(comboBoxCellPropietario));

        jtable.getColumnModel().getColumn(columna).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value != null) {
                    Propietario propietario = (Propietario) value;
                    setText(propietario.getApellidos() + ", " + propietario.getNombre() + " (" + propietario.getDni() + ")");
                } else {
                    setText("");
                }
            }
        });
    }

    private void insertVivienda(Vivienda vivienda) {
        entityManager.persist(vivienda);
        viviendas.setVivienda(vivienda);
    }

    private void insertObjet(Object o, int tipoObjeto) {
        entityManager.persist(o);
        entityManager.getTransaction().commit();
        switch (tipoObjeto) {
            case PROPIETARIO:
                propietarios.setPropietario((Propietario) o);
                break;
            case CUOTA:
                cuotas.setCuota((Cuota) o);
                break;
            default:

        }
    }

    private void updateObjet(Object o) {
        entityManager.getTransaction().begin();
        entityManager.merge(o);
        entityManager.getTransaction().commit();
    }

    private void removeObject(Object o, int tipoObjeto) {
        entityManager.getTransaction().begin();
        entityManager.remove(o);
        entityManager.getTransaction().commit();
        switch (tipoObjeto) {
            case PROPIETARIO:
                propietarios.getListaPropietarios().remove((Propietario) o);
                break;
            case CUOTA:
                cuotas.getListaCuotas().remove((Cuota) o);
                break;
            default:

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelViviendas = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaViviendas = new javax.swing.JTable();
        etiquetaNombreComunidad = new javax.swing.JLabel();
        botonAñadirVivienda = new javax.swing.JButton();
        botonGuardarCambios = new javax.swing.JButton();
        botonCancelarCambios = new javax.swing.JButton();
        botonEditar = new javax.swing.JButton();
        botonVolver = new javax.swing.JButton();
        panelPropietarios = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPropietarios = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jT_Apellidos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jT_Nombre = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jT_Email = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jT_Telefono = new javax.swing.JTextField();
        botonAñadirPropietario = new javax.swing.JButton();
        botonModificarPropietario = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListViviendas = new javax.swing.JList();
        jLabel20 = new javax.swing.JLabel();
        jT_DNI = new javax.swing.JTextField();
        botonCancelarPropietario = new javax.swing.JButton();
        botonEliminarPropietario = new javax.swing.JButton();
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
        jCheckBoxPagado = new javax.swing.JCheckBox();
        botonGuardarCuota = new javax.swing.JButton();
        botonModificarCuota = new javax.swing.JButton();
        botonCobrarCuota = new javax.swing.JButton();
        jComboBoxViviendasCuotas = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        botonEliminarCuota = new javax.swing.JButton();
        botonCancelarCuota = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        botonModificarIVA = new javax.swing.JButton();
        label_IVA_agua = new javax.swing.JLabel();
        label_IVA_luz = new javax.swing.JLabel();
        label_IVA_basura = new javax.swing.JLabel();
        label_IVA_zonas = new javax.swing.JLabel();
        label_IVA_otros = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

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

        etiquetaNombreComunidad.setText("Nombre Comunidad....");

        botonAñadirVivienda.setText("Añadir Vivienda");
        botonAñadirVivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAñadirViviendaActionPerformed(evt);
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

        botonEditar.setText("Editar");
        botonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarActionPerformed(evt);
            }
        });

        botonVolver.setText("Volver a Comunidades");
        botonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelViviendasLayout = new javax.swing.GroupLayout(panelViviendas);
        panelViviendas.setLayout(panelViviendasLayout);
        panelViviendasLayout.setHorizontalGroup(
            panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViviendasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                    .addGroup(panelViviendasLayout.createSequentialGroup()
                        .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etiquetaNombreComunidad)
                            .addGroup(panelViviendasLayout.createSequentialGroup()
                                .addComponent(botonAñadirVivienda)
                                .addGap(18, 18, 18)
                                .addComponent(botonGuardarCambios)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonCancelarCambios)
                                .addGap(18, 18, 18)
                                .addComponent(botonEditar))
                            .addComponent(botonVolver))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelViviendasLayout.setVerticalGroup(
            panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelViviendasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetaNombreComunidad)
                .addGap(18, 18, 18)
                .addGroup(panelViviendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAñadirVivienda)
                    .addComponent(botonGuardarCambios)
                    .addComponent(botonCancelarCambios)
                    .addComponent(botonEditar))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(botonVolver)
                .addContainerGap(125, Short.MAX_VALUE))
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

        jLabel16.setText("Viviendas del propietario:");

        jLabel18.setText("E-Mail:");

        jLabel19.setText("Teléfono:");

        botonAñadirPropietario.setText("Añadir Propietario");
        botonAñadirPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAñadirPropietarioActionPerformed(evt);
            }
        });

        botonModificarPropietario.setText("Modificar Propietario");
        botonModificarPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarPropietarioActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(jListViviendas);

        jLabel20.setText("DNI:");

        botonCancelarPropietario.setText("Cancelar");
        botonCancelarPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarPropietarioActionPerformed(evt);
            }
        });

        botonEliminarPropietario.setText("Eliminar Propietario");
        botonEliminarPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarPropietarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPropietariosLayout = new javax.swing.GroupLayout(panelPropietarios);
        panelPropietarios.setLayout(panelPropietariosLayout);
        panelPropietariosLayout.setHorizontalGroup(
            panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropietariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPropietariosLayout.createSequentialGroup()
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPropietariosLayout.createSequentialGroup()
                                .addComponent(botonAñadirPropietario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonEliminarPropietario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonModificarPropietario)
                                .addGap(18, 18, 18)
                                .addComponent(botonCancelarPropietario)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(panelPropietariosLayout.createSequentialGroup()
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panelPropietariosLayout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addGap(18, 18, 18)
                                    .addComponent(jT_Email))
                                .addGroup(panelPropietariosLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jT_Nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                                .addGroup(panelPropietariosLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jT_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPropietariosLayout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jT_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPropietariosLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jT_DNI, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(112, 112, 112))))
        );
        panelPropietariosLayout.setVerticalGroup(
            panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropietariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPropietariosLayout.createSequentialGroup()
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jT_Apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jT_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jT_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jT_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jT_DNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelPropietariosLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(panelPropietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAñadirPropietario)
                    .addComponent(botonModificarPropietario)
                    .addComponent(botonCancelarPropietario)
                    .addComponent(botonEliminarPropietario))
                .addGap(23, 23, 23))
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

        jT_Vivienda.setEditable(false);

        jT_Agua.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_AguaFocusLost(evt);
            }
        });

        jT_Luz.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_LuzFocusLost(evt);
            }
        });

        jLabel8.setText("Luz:");

        jLabel9.setText("Basura:");

        jT_Basura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_BasuraFocusLost(evt);
            }
        });

        jLabel10.setText("Zonas Comunes:");

        jT_ZonasComunes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_ZonasComunesFocusLost(evt);
            }
        });

        jLabel11.setText("Otros:");

        jT_Otros.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jT_OtrosFocusLost(evt);
            }
        });

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

        botonModificarCuota.setText("Modificar Cuota");
        botonModificarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarCuotaActionPerformed(evt);
            }
        });

        botonCobrarCuota.setText("Generar Informe de Impagos");
        botonCobrarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCobrarCuotaActionPerformed(evt);
            }
        });

        jComboBoxViviendasCuotas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel21.setText("Viviendas de la comunidad:");

        botonEliminarCuota.setText("Eliminar Cuota");
        botonEliminarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarCuotaActionPerformed(evt);
            }
        });

        botonCancelarCuota.setText("Cancelar");
        botonCancelarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarCuotaActionPerformed(evt);
            }
        });

        botonModificarIVA.setText("Modificar IVA");
        botonModificarIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarIVAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCuotasLayout = new javax.swing.GroupLayout(panelCuotas);
        panelCuotas.setLayout(panelCuotasLayout);
        panelCuotasLayout.setHorizontalGroup(
            panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCuotasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCuotasLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jT_Vivienda, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelCuotasLayout.createSequentialGroup()
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
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label_IVA_agua, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                            .addComponent(label_IVA_luz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(label_IVA_basura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCuotasLayout.createSequentialGroup()
                                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel12)
                                                    .addComponent(jLabel11))
                                                .addGap(68, 68, 68))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCuotasLayout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(18, 18, 18)))
                                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jT_Otros)
                                            .addComponent(jT_ZonasComunes)
                                            .addComponent(jT_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label_IVA_zonas, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                            .addComponent(label_IVA_otros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(62, 62, 62)
                                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addComponent(jComboBoxViviendasCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelCuotasLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(66, 66, 66)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addComponent(jCheckBoxPagado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonCobrarCuota)
                        .addGap(29, 29, 29))
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addComponent(botonGuardarCuota)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonModificarCuota)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonEliminarCuota)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonCancelarCuota)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonModificarIVA)
                        .addGap(37, 37, 37))))
        );
        panelCuotasLayout.setVerticalGroup(
            panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCuotasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelCuotasLayout.createSequentialGroup()
                                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jT_ZonasComunes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10))
                                    .addComponent(label_IVA_zonas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jT_Otros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addComponent(label_IVA_otros, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jT_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxViviendasCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(label_IVA_luz, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelCuotasLayout.createSequentialGroup()
                            .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jT_Vivienda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jT_Agua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_IVA_agua, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jT_Luz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCuotasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCuotasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_IVA_basura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jT_Basura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxPagado)
                    .addComponent(botonCobrarCuota))
                .addGap(18, 18, 18)
                .addGroup(panelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGuardarCuota)
                    .addComponent(botonModificarCuota)
                    .addComponent(botonEliminarCuota)
                    .addComponent(botonCancelarCuota)
                    .addComponent(botonModificarIVA))
                .addGap(45, 45, 45))
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

    private void botonAñadirPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAñadirPropietarioActionPerformed
        entityManager.getTransaction().begin();

        Propietario propietario = new Propietario(null, jT_Apellidos.getText(), jT_Nombre.getText(), jT_Email.getText(), jT_DNI.getText(), jT_Telefono.getText());
        this.insertObjet(propietario, PROPIETARIO);
        propietariosTableModel.fireTableRowsInserted(propietarios.getListaPropietarios().size() - 1, propietarios.getListaPropietarios().size() - 1);

        propietarios.setListaPropietarios(consultaPropietarios.getResultList());
        comboBoxCellPropietario.setModel(new DefaultComboBoxModel(propietarios.getListaPropietarios().toArray()));

    }//GEN-LAST:event_botonAñadirPropietarioActionPerformed

    private void botonGuardarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarCuotaActionPerformed
        entityManager.getTransaction().begin();

        BigDecimal[] cuotasConIva = this.cuotasConIVA(jT_Agua.getText(), jT_Luz.getText(),
                jT_Basura.getText(), jT_ZonasComunes.getText(), jT_Otros.getText());
        BigDecimal agua = cuotasConIva[0];
        BigDecimal luz = cuotasConIva[1];
        BigDecimal basura = cuotasConIva[2];
        BigDecimal zonasComunes = cuotasConIva[3];
        BigDecimal otros = cuotasConIva[4];

        BigDecimal total = Cuotas.calcularTotal(agua, luz, basura, zonasComunes, otros);

        Date fecha = jDateChooser1.getDate();
        Cuota cuota = new Cuota(1, agua, luz, basura, zonasComunes, otros, total, fecha, jCheckBoxPagado.isSelected());
        cuota.setVivienda((Vivienda) jComboBoxViviendasCuotas.getSelectedItem());
        this.insertObjet(cuota, CUOTA);
        cuotasTableModel.fireTableRowsInserted(cuotas.getListaCuotas().size() - 1, cuotas.getListaCuotas().size() - 1);
        this.mostrarDetallesCuotas();
    }//GEN-LAST:event_botonGuardarCuotaActionPerformed

    private void botonModificarPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarPropietarioActionPerformed
        int filaSeleccionada = tablaPropietarios.getSelectedRow();

        Propietario propietarioSeleccionado = propietarios.getPropietario(filaSeleccionada);

        propietarioSeleccionado.setApellidos(jT_Apellidos.getText());
        propietarioSeleccionado.setNombre(jT_Nombre.getText());
        propietarioSeleccionado.setEmail(jT_Email.getText());
        propietarioSeleccionado.setTelefono(jT_Telefono.getText());
        propietarioSeleccionado.setDni(jT_DNI.getText());
        this.updateObjet(propietarioSeleccionado);
        propietariosTableModel.fireTableRowsUpdated(filaSeleccionada, filaSeleccionada);
        propietarios.setListaPropietarios(consultaPropietarios.getResultList());
        comboBoxCellPropietario.setModel(new DefaultComboBoxModel(propietarios.getListaPropietarios().toArray()));
    }//GEN-LAST:event_botonModificarPropietarioActionPerformed

    private void botonModificarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarCuotaActionPerformed
        int filaSeleccionada = tablaCuotas.getSelectedRow();

        Cuota cuotaSeleccionada = cuotas.getCuota(filaSeleccionada);

        BigDecimal[] cuotasConIva = this.cuotasConIVA(jT_Agua.getText(), jT_Luz.getText(),
                jT_Basura.getText(), jT_ZonasComunes.getText(), jT_Otros.getText());
        BigDecimal agua = cuotasConIva[0];
        BigDecimal luz = cuotasConIva[1];
        BigDecimal basura = cuotasConIva[2];
        BigDecimal zonasComunes = cuotasConIva[3];
        BigDecimal otros = cuotasConIva[4];

        cuotaSeleccionada.setVivienda((Vivienda) jComboBoxViviendasCuotas.getSelectedItem());
        cuotaSeleccionada.setAgua(agua);
        cuotaSeleccionada.setLuz(luz);
        cuotaSeleccionada.setBasura(basura);
        cuotaSeleccionada.setZonasComunes(zonasComunes);
        cuotaSeleccionada.setOtros(otros);
        cuotaSeleccionada.setFecha(jDateChooser1.getDate());
        cuotaSeleccionada.setPagado(jCheckBoxPagado.isSelected());

        cuotaSeleccionada.setTotal(Cuotas.calcularTotal(cuotaSeleccionada.getAgua(),
                cuotaSeleccionada.getLuz(), cuotaSeleccionada.getBasura(),
                cuotaSeleccionada.getZonasComunes(), cuotaSeleccionada.getOtros()));

        jT_Vivienda.setText(cuotaSeleccionada.getVivienda().getNumero());
        jT_Total.setText(formato.format(cuotaSeleccionada.getTotal()));
        this.updateObjet(cuotaSeleccionada);
        cuotasTableModel.fireTableRowsUpdated(filaSeleccionada, filaSeleccionada);
        this.mostrarDetallesCuotas();

    }//GEN-LAST:event_botonModificarCuotaActionPerformed

    private void jT_AguaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_AguaFocusLost
        this.controlDecimalCuotas(jT_Agua, 6, 2);
    }//GEN-LAST:event_jT_AguaFocusLost

    private void jT_LuzFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_LuzFocusLost
        this.controlDecimalCuotas(jT_Luz, 6, 2);
    }//GEN-LAST:event_jT_LuzFocusLost

    private void jT_BasuraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_BasuraFocusLost
        this.controlDecimalCuotas(jT_Basura, 6, 2);
    }//GEN-LAST:event_jT_BasuraFocusLost

    private void jT_ZonasComunesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_ZonasComunesFocusLost
        this.controlDecimalCuotas(jT_ZonasComunes, 6, 2);
    }//GEN-LAST:event_jT_ZonasComunesFocusLost

    private void jT_OtrosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jT_OtrosFocusLost
        this.controlDecimalCuotas(jT_Otros, 6, 2);
    }//GEN-LAST:event_jT_OtrosFocusLost

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        entityManager.close();
    }//GEN-LAST:event_formWindowClosing

    private void botonAñadirViviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAñadirViviendaActionPerformed
        Vivienda vivienda = new Vivienda();
        vivienda.setComunidad(comunidad);
        this.insertVivienda(vivienda);
        viviendasTableModel.fireTableRowsInserted(viviendas.getListaViviendas().size() - 1, viviendas.getListaViviendas().size() - 1);
        tablaViviendas.setRowSelectionInterval(viviendas.getListaViviendas().size() - 1, viviendas.getListaViviendas().size() - 1);

    }//GEN-LAST:event_botonAñadirViviendaActionPerformed

    private void botonGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarCambiosActionPerformed

        entityManager.getTransaction().commit();
        jComboBoxViviendasCuotas.setModel(new DefaultComboBoxModel(viviendas.getListaViviendas().toArray()));
        // Cargar la lista de viviendas de cada propietario.
        //jListViviendas.setListData(propietarios.getPropietario(filaSeleccionada).getViviendaCollection().toArray());
        this.camposViviendasEditable(false);
    }//GEN-LAST:event_botonGuardarCambiosActionPerformed

    private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarActionPerformed
        entityManager.getTransaction().begin();
        this.camposViviendasEditable(true);
    }//GEN-LAST:event_botonEditarActionPerformed


    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed

        jDialogInicio.setClearSelection(true);
        jDialogInicio.setVisible(true);
        comunidad = jDialogInicio.getComunidadSelected();
        etiquetaNombreComunidad.setText(comunidad.getNombre());
        viviendasTableModel.fireTableDataChanged();
        propietariosTableModel.fireTableDataChanged();
        cuotasTableModel.fireTableDataChanged();
        this.camposViviendasEditable(false);
        this.limpiarCamposCuota();
        this.limpiarCamposPropietario();

        consultaPropietarios = entityManager.createNamedQuery("Propietario.findAll");
        consultaViviendas = entityManager.createQuery("SELECT v FROM Vivienda v WHERE v.comunidad = :comunidad");
        consultaCuotas = entityManager.createQuery("SELECT v FROM Cuota v WHERE v.vivienda.comunidad = :comunidad");
        consultaViviendas.setParameter("comunidad", comunidad);
        consultaCuotas.setParameter("comunidad", comunidad);

        propietarios.setListaPropietarios(consultaPropietarios.getResultList());
        viviendas.setListaViviendas(consultaViviendas.getResultList());
        cuotas.setListaCuotas(consultaCuotas.getResultList());

        jComboBoxViviendasCuotas.setModel(new DefaultComboBoxModel(viviendas.getListaViviendas().toArray()));


    }//GEN-LAST:event_botonVolverActionPerformed

    private void botonCancelarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarCambiosActionPerformed
        entityManager.getTransaction().rollback();
        viviendas.setListaViviendas(consultaViviendas.getResultList());
        viviendasTableModel.fireTableDataChanged();
        tablaViviendas.clearSelection();
        this.camposViviendasEditable(false);
    }//GEN-LAST:event_botonCancelarCambiosActionPerformed

    private void botonCancelarPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarPropietarioActionPerformed
        this.limpiarCamposPropietario();
        tablaPropietarios.clearSelection();
        botonModificarPropietario.setEnabled(false);
    }//GEN-LAST:event_botonCancelarPropietarioActionPerformed

    private void botonCobrarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCobrarCuotaActionPerformed

        String prueba = comunidad.getNombre();

        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/comunidad", "root", "");
            Statement stmt = conexion.createStatement();

            ResultSet cuotaImpagada = stmt.executeQuery("SELECT cu.id as id_cuota, cu.agua, cu.luz,"
                    + " cu.basura,cu.zonas_comunes,cu.otros,cu.total,cu.fecha,cu.pagado,cu.vivienda,"
                    + " v.id as id_vivienda,v.numero,v.numero_cuenta,v.propietario,v.comunidad,c.id as id_comunidad,"
                    + " c.nombre as nombre_comunidad,c.direccion,p.id as id_propietario,p.apellidos,"
                    + " p.nombre as nombre_propietario,p.email,p.dni,p.telefono"
                    + " FROM cuota as cu"
                    + " JOIN vivienda as v ON v.id = cu.vivienda"
                    + " JOIN comunidad as c ON v.comunidad = c.id"
                    + " JOIN propietario as p ON v.propietario = p.id WHERE cu.pagado = 0"
                    + " and c.nombre = " + "'" + prueba + "'");

            if (!cuotaImpagada.next()) {
                JOptionPane.showMessageDialog(this, "No hay impagos en esta comunidad");
            } else {
                try {
                    Map parameters = new HashMap();
                    parameters.put("comunidad", prueba);
                    parameters.put("numero", cuotaImpagada.getString("numero"));
                    if (iva != null) {
                        parameters.put("iva_agua", "IVA: " + iva.getIvaAgua().multiply(BigDecimal.valueOf(100)).intValue() + " %");
                        parameters.put("iva_luz", "IVA: " + iva.getIvaLuz().multiply(BigDecimal.valueOf(100)).intValue() + "%");
                        parameters.put("iva_basura", "IVA: " + iva.getIvaBasura().multiply(BigDecimal.valueOf(100)).intValue() + "%");
                        parameters.put("iva_zonas", "IVA: " + iva.getIvaZonasComunes().multiply(BigDecimal.valueOf(100)).intValue() + "%");
                        parameters.put("iva_otros","IVA: " + iva.getIvaOtros().multiply(BigDecimal.valueOf(100)).intValue() + "%");
                        parameters.put("iva_incluido", "(IVA incluido)");
                        parameters.put("importeIVA", importeIVA);
                    } else {
                        parameters.put("iva_agua", "Sin IVA");
                        parameters.put("iva_luz", "Sin IVA");
                        parameters.put("iva_basura", "Sin IVA");
                        parameters.put("iva_zonas", "Sin IVA");
                        parameters.put("iva_otros", "Sin IVA");
                        parameters.put("iva_incluido", "(IVA no incluido)");
                        parameters.put("importeIVA", importeIVA);
                    }
                    JasperReport jasperReport
                            = JasperCompileManager.compileReport(
                                    "comunidad/Simple_Blue_Table_Based.jrxml");
                    cuotaImpagada.beforeFirst();
                    JasperPrint jasperPrint = JasperFillManager.fillReport(
                            jasperReport, parameters, new JRResultSetDataSource(cuotaImpagada));
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (JRException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No hay conexión con la base de datos");
            ex.printStackTrace();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonCobrarCuotaActionPerformed

    private void botonEliminarPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarPropietarioActionPerformed
        int filaSeleccionada = tablaPropietarios.getSelectedRow();
        if (filaSeleccionada < 0) {
            botonEliminarPropietario.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Debe seleccionar un propietario");
        } else {
            Propietario propietarioSeleccionado = propietarios.getPropietario(filaSeleccionada);
            int mensaje = JOptionPane.showConfirmDialog(this, "¿Seguro que quiere eliminar el propietario "
                    + propietarioSeleccionado.getApellidos() + " ," + propietarioSeleccionado.getNombre() + "?", "Eliminar Propietario", JOptionPane.YES_NO_OPTION);
            if (mensaje == JOptionPane.YES_OPTION) {
                removeObject(propietarioSeleccionado, PROPIETARIO);
                propietariosTableModel.fireTableRowsDeleted(tablaPropietarios.getSelectedRow(), tablaPropietarios.getSelectedRow());
            }
        }
        botonCancelarPropietario.setEnabled(false);
        botonAñadirPropietario.setEnabled(true);
        propietarios.setListaPropietarios(consultaPropietarios.getResultList());
        comboBoxCellPropietario.setModel(new DefaultComboBoxModel(propietarios.getListaPropietarios().toArray()));
        // Al borrar propietario, mostrar en la lista de viviendas la vivienda sin propietario. Avisar al usuario.
        
    }//GEN-LAST:event_botonEliminarPropietarioActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged

        if (jTabbedPane1.getSelectedIndex() > 0) {
            if (entityManager.getTransaction().isActive()) {
                jTabbedPane1.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Debe terminar la edición de la vivienda");
            } else {
                tablaViviendas.clearSelection();
                tablaPropietarios.clearSelection();
                tablaCuotas.clearSelection();

                botonAñadirPropietario.setEnabled(true);
                botonGuardarCuota.setEnabled(true);
            }
        }


    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void botonEliminarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarCuotaActionPerformed
        int filaSeleccionada = tablaCuotas.getSelectedRow();
        if (filaSeleccionada < 0) {
            botonEliminarCuota.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Debe seleccionar una cuota");
        } else {
            Cuota cuotaSeleccionada = cuotas.getCuota(filaSeleccionada);
            int mensaje = JOptionPane.showConfirmDialog(this, "¿Seguro que quiere eliminar la cuota de la vivienda "
                    + cuotaSeleccionada.getVivienda().getNumero() + "?", "Eliminar Cuota", JOptionPane.YES_NO_OPTION);
            if (mensaje == JOptionPane.YES_OPTION) {
                removeObject(cuotaSeleccionada, CUOTA);
                cuotasTableModel.fireTableRowsDeleted(tablaCuotas.getSelectedRow(), tablaCuotas.getSelectedRow());
            }
        }
        botonCancelarCuota.setEnabled(false);
        botonGuardarCuota.setEnabled(true);
    }//GEN-LAST:event_botonEliminarCuotaActionPerformed

    private void botonCancelarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarCuotaActionPerformed
        this.limpiarCamposCuota();
        tablaCuotas.clearSelection();
        botonModificarCuota.setEnabled(false);
    }//GEN-LAST:event_botonCancelarCuotaActionPerformed

    private void botonModificarIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarIVAActionPerformed
        jDialogIVA.setVisible(true);
    }//GEN-LAST:event_botonModificarIVAActionPerformed

    private void limpiarCamposPropietario() {
        jT_Apellidos.setText(null);
        jT_Nombre.setText(null);
        jT_Email.setText(null);
        jT_Telefono.setText(null);
        jListViviendas.setListData(new Vivienda[0]);
    }

    private void limpiarCamposCuota() {
        jT_Vivienda.setText(null);
        jT_Agua.setText(null);
        jT_Luz.setText(null);
        jT_Basura.setText(null);
        jT_Otros.setText(null);
        jT_ZonasComunes.setText(null);
        jT_Total.setText(null);
        jDateChooser1.setDate(null);
        jCheckBoxPagado.setSelected(false);
    }

    private BigDecimal[] cuotasConIVA(String jtAguaGetText, String jtLuzGetText,
            String jtBasuraGetText, String jtZonasGetText, String jtOtrosGetText) {

        BigDecimal agua = Values.toBigDecimal(jtAguaGetText);
        BigDecimal luz = Values.toBigDecimal(jtLuzGetText);
        BigDecimal basura = Values.toBigDecimal(jtBasuraGetText);
        BigDecimal zonasComunes = Values.toBigDecimal(jtZonasGetText);
        BigDecimal otros = Values.toBigDecimal(jtOtrosGetText);

        iva = jDialogIVA.getIva();

        if (iva == null) {
            //JOptionPane.showMessageDialog(this, "Atención: Importes sin IVA");
            label_IVA_agua.setText("Sin IVA");
            label_IVA_luz.setText("Sin IVA");
            label_IVA_basura.setText("Sin IVA");
            label_IVA_zonas.setText("Sin IVA");
            label_IVA_otros.setText("Sin IVA");
        } else {
            // Hay que darle un redondeo a la multiplicacion con new MathContext
            BigDecimal aguaIVA = agua.multiply(iva.getIvaAgua());
            BigDecimal luzIVA = luz.multiply(iva.getIvaLuz());
            BigDecimal basuraIVA = basura.multiply(iva.getIvaBasura());
            BigDecimal zonasIVA = zonasComunes.multiply(iva.getIvaZonasComunes());
            BigDecimal otrosIVA = otros.multiply(iva.getIvaOtros());
            
            importeIVA = aguaIVA.add(luzIVA).add(basuraIVA).add(zonasIVA).add(otrosIVA);

            agua = agua.add(aguaIVA);
            luz = luz.add(luzIVA);
            basura = basura.add(basuraIVA);
            zonasComunes = zonasComunes.add(zonasIVA);
            otros = otros.add(otrosIVA);

            label_IVA_agua.setText("IVA: " + iva.getIvaAgua().multiply(BigDecimal.valueOf(100)).intValue() + " %");
            label_IVA_luz.setText("IVA: " + iva.getIvaLuz().multiply(BigDecimal.valueOf(100)).intValue() + "%");
            label_IVA_basura.setText("IVA: " + iva.getIvaBasura().multiply(BigDecimal.valueOf(100)).intValue() + "%");
            label_IVA_zonas.setText("IVA: " + iva.getIvaZonasComunes().multiply(BigDecimal.valueOf(100)).intValue() + "%");
            label_IVA_otros.setText("IVA: " + iva.getIvaOtros().multiply(BigDecimal.valueOf(100)).intValue() + "%");
        }
        BigDecimal[] cuotasConIva = {agua, luz, basura, zonasComunes, otros};
        return cuotasConIva;
    }

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
    private javax.swing.JButton botonCancelarCambios;
    private javax.swing.JButton botonCancelarCuota;
    private javax.swing.JButton botonCancelarPropietario;
    private javax.swing.JButton botonCobrarCuota;
    private javax.swing.JButton botonEditar;
    private javax.swing.JButton botonEliminarCuota;
    private javax.swing.JButton botonEliminarPropietario;
    private javax.swing.JButton botonGuardarCambios;
    private javax.swing.JButton botonGuardarCuota;
    private javax.swing.JButton botonModificarCuota;
    private javax.swing.JButton botonModificarIVA;
    private javax.swing.JButton botonModificarPropietario;
    private javax.swing.JButton botonVolver;
    private javax.swing.JLabel etiquetaNombreComunidad;
    private javax.swing.JCheckBox jCheckBoxPagado;
    private javax.swing.JComboBox jComboBoxViviendasCuotas;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jListViviendas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jT_Agua;
    private javax.swing.JTextField jT_Apellidos;
    private javax.swing.JTextField jT_Basura;
    private javax.swing.JTextField jT_DNI;
    private javax.swing.JTextField jT_Email;
    private javax.swing.JTextField jT_Luz;
    private javax.swing.JTextField jT_Nombre;
    private javax.swing.JTextField jT_Otros;
    private javax.swing.JTextField jT_Telefono;
    private javax.swing.JTextField jT_Total;
    private javax.swing.JTextField jT_Vivienda;
    private javax.swing.JTextField jT_ZonasComunes;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel label_IVA_agua;
    private javax.swing.JLabel label_IVA_basura;
    private javax.swing.JLabel label_IVA_luz;
    private javax.swing.JLabel label_IVA_otros;
    private javax.swing.JLabel label_IVA_zonas;
    private javax.swing.JPanel panelCuotas;
    private javax.swing.JPanel panelPropietarios;
    private javax.swing.JPanel panelViviendas;
    private javax.swing.JTable tablaCuotas;
    private javax.swing.JTable tablaPropietarios;
    private javax.swing.JTable tablaViviendas;
    // End of variables declaration//GEN-END:variables
}
