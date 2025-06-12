package ui;

import lab8.IngenieroDAO;
import model.Ingeniero;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class FrmIngeniero extends JFrame {

    private final IngenieroDAO dao = new IngenieroDAO();

    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtEsp    = new JTextField(20);
    private final JTextField txtCargo  = new JTextField(20);

    private final DefaultTableModel model =
        new DefaultTableModel(new String[]{"ID","Nombre","Especialidad","Cargo"}, 0);
    private final JTable table = new JTable(model);

    public FrmIngeniero() {
        super("Ingenieros");

        //-------------------------------
        // 1) DISEÑO DEL CONTENT PANE
        //-------------------------------
        getContentPane().setLayout(new BorderLayout(10,10));

        // -------- Formulario (NORTH) --------
        JPanel pnlForm = new JPanel(new GridLayout(3,2,5,5));
        pnlForm.add(new JLabel("Nombre:"));       pnlForm.add(txtNombre);
        pnlForm.add(new JLabel("Especialidad:")); pnlForm.add(txtEsp);
        pnlForm.add(new JLabel("Cargo:"));        pnlForm.add(txtCargo);
        add(pnlForm, BorderLayout.NORTH);

        // -------- Tabla (CENTER) ------------
        add(new JScrollPane(table), BorderLayout.CENTER);

        // -------- Botones (SOUTH) -----------
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnAdd = new JButton("Insertar");
        JButton btnUpd = new JButton("Actualizar");
        JButton btnDel = new JButton("Eliminar");
        pnlBtns.add(btnAdd); pnlBtns.add(btnUpd); pnlBtns.add(btnDel);
        add(pnlBtns, BorderLayout.SOUTH);

        //------------------------------------
        // 2) ACCIONES
        //------------------------------------
        btnAdd.addActionListener(e -> insertar());
        btnUpd.addActionListener(e -> actualizar());
        btnDel.addActionListener(e -> eliminar());

        //------------------------------------
        // 3) CONFIG BÁSICA DE VENTANA
        //------------------------------------
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        loadData();                 // llena la tabla antes de empaquetar
        pack();                     // ajusta al tamaño preferido de componentes
        setLocationRelativeTo(null);// centra la ventana
    }

    /* ----------  CRUD LÓGICO  ---------- */
    private void insertar() {
        try {
            dao.insert(new Ingeniero(0,
                    txtNombre.getText(),
                    txtEsp.getText(),
                    txtCargo.getText()));
            clearFields();
            loadData();
        } catch (SQLException ex) { showError(ex); }
    }

    private void actualizar() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Selecciona una fila"); return; }
        int id = (int) model.getValueAt(row, 0);
        try {
            dao.update(new Ingeniero(id,
                    txtNombre.getText(),
                    txtEsp.getText(),
                    txtCargo.getText()));
            loadData();
        } catch (SQLException ex) { showError(ex); }
    }

    private void eliminar() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Selecciona una fila"); return; }
        int id = (int) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this,
                "¿Eliminar ID " + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try { dao.delete(id); loadData(); }
            catch (SQLException ex) { showError(ex); }
        }
    }

    /* ----------  UTILIDADES  ---------- */
    private void loadData() {
        model.setRowCount(0);
        try {
            for (Ingeniero i : dao.findAll()) {
                model.addRow(new Object[]{
                        i.id(), i.nombre(), i.especialidad(), i.cargo()});
            }
        } catch (SQLException ex) { showError(ex); }
    }

    private void clearFields() {
        txtNombre.setText(""); txtEsp.setText(""); txtCargo.setText("");
    }
    private void warn(String msg){ JOptionPane.showMessageDialog(this,msg); }
    private void showError(Exception ex){
        JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    /* ----------  MAIN  ---------- */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmIngeniero().setVisible(true));
    }
}
