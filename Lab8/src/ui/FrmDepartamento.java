
package ui;

import lab8.DepartamentoDAO;
import model.Departamento;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class FrmDepartamento extends CrudFrame {
    private final DepartamentoDAO dao = new DepartamentoDAO();
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtTel    = new JTextField(15);
    private final JTextField txtFax    = new JTextField(15);

    public FrmDepartamento() {
        super("Departamentos", new String[]{"ID","Nombre","Teléfono","Fax"});
        buildForm();
        loadData();
    }

    private void buildForm() {
        JPanel form = new JPanel(new GridLayout(3,2,5,5));
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Teléfono:")); form.add(txtTel);
        form.add(new JLabel("Fax:")); form.add(txtFax);

        JPanel actions = new JPanel();
        JButton btnAdd = new JButton("Insertar");
        JButton btnUpd = new JButton("Actualizar");
        JButton btnDel = new JButton("Eliminar");
        actions.add(btnAdd); actions.add(btnUpd); actions.add(btnDel);

        btnAdd.addActionListener(e -> insert());
        btnUpd.addActionListener(e -> update());
        btnDel.addActionListener(e -> delete());

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(actions, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);
    }

    private void loadData() {
        clearTable();
        try {
            for (Departamento d : dao.findAll()) {
                model.addRow(new Object[]{d.id(), d.nombre(), d.telefono(), d.fax()});
            }
        } catch (SQLException ex) { showError(ex); }
    }

    private void insert() {
        try {
            dao.insert(new Departamento(0, txtNombre.getText(), txtTel.getText(), txtFax.getText()));
            loadData();
            clearFields();
        } catch (SQLException ex) { showError(ex); }
    }

    private void update() {
        int row = table.getSelectedRow();
        if (row==-1) { JOptionPane.showMessageDialog(this,"Selecciona una fila"); return; }
        int id = (int) model.getValueAt(row,0);
        try {
            dao.update(new Departamento(id, txtNombre.getText(), txtTel.getText(), txtFax.getText()));
            loadData();
        } catch (SQLException ex) { showError(ex); }
    }

    private void delete() {
        int row = table.getSelectedRow();
        if (row==-1) { JOptionPane.showMessageDialog(this,"Selecciona una fila"); return; }
        int id = (int) model.getValueAt(row,0);
        int ok = JOptionPane.showConfirmDialog(this,
                "¿Eliminar ID "+id+"?","Confirmar",JOptionPane.YES_NO_OPTION);
        if (ok==JOptionPane.YES_OPTION) {
            try { dao.delete(id); loadData(); } catch (SQLException ex) { showError(ex);} }
    }

    private void clearFields() { txtNombre.setText(""); txtTel.setText(""); txtFax.setText(""); }
    private void showError(Exception ex){ JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new FrmDepartamento().setVisible(true)); }
}
