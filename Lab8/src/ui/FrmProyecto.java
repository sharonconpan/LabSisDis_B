
package ui;

import lab8.ProyectoDAO;
import model.Proyecto;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FrmProyecto extends CrudFrame {
    private final ProyectoDAO dao = new ProyectoDAO();
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtInicio = new JTextField(10); // yyyy-mm-dd
    private final JTextField txtFin    = new JTextField(10);
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FrmProyecto() {
        super("Proyectos", new String[]{"ID","Nombre","Inicio","Fin"});
        buildForm();
        loadData();
    }

    private void buildForm() {
        JPanel form = new JPanel(new GridLayout(3,2,5,5));
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Fecha inicio (yyyy-MM-dd):")); form.add(txtInicio);
        form.add(new JLabel("Fecha fin (opcional):")); form.add(txtFin);

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
            for (Proyecto p : dao.findAll()) {
                model.addRow(new Object[]{p.id(), p.nombre(), p.fechaInicio(), p.fechaTermino()});
            }
        } catch (SQLException ex) { showError(ex); }
    }

    private void insert() {
        try {
            LocalDate ini = LocalDate.parse(txtInicio.getText(), fmt);
            LocalDate fin = txtFin.getText().isBlank()? null : LocalDate.parse(txtFin.getText(), fmt);
            dao.insert(new Proyecto(0, txtNombre.getText(), ini, fin));
            loadData(); clearFields();
        } catch (Exception ex) { showError(ex); }
    }
    private void update() {
        int row = table.getSelectedRow();
        if (row==-1) { JOptionPane.showMessageDialog(this,"Selecciona una fila"); return; }
        int id = (int) model.getValueAt(row,0);
        try {
            LocalDate ini = LocalDate.parse(txtInicio.getText(), fmt);
            LocalDate fin = txtFin.getText().isBlank()? null : LocalDate.parse(txtFin.getText(), fmt);
            dao.update(new Proyecto(id, txtNombre.getText(), ini, fin));
            loadData();
        } catch (Exception ex) { showError(ex); }
    }
    private void delete() {
        int row = table.getSelectedRow();
        if (row==-1) { JOptionPane.showMessageDialog(this,"Selecciona una fila"); return; }
        int id = (int) model.getValueAt(row,0);
        if (JOptionPane.showConfirmDialog(this,"¿Eliminar ID "+id+"?", "Confirmar", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            try { dao.delete(id); loadData(); } catch (SQLException ex) { showError(ex); }
        }
    }
    private void clearFields() { txtNombre.setText(""); txtInicio.setText(""); txtFin.setText(""); }
    private void showError(Exception ex){ JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new FrmProyecto().setVisible(true)); }
}

