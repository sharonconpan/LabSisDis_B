package ui;
import lab8.*;import model.*;import util.ComboItem;
import javax.swing.*;import javax.swing.table.DefaultTableModel;
import java.awt.*;import java.sql.*;import java.util.*;
public class FrmAsignaciones extends JFrame {
    private final IngProyectoDAO daoIngProy = new IngProyectoDAO();
    private final DptoProyectoDAO daoDptoProy = new DptoProyectoDAO();

    // ---------- Ingeniero-Proyecto widgets ----------
    private JComboBox<ComboItem> cboIng;
    private JComboBox<ComboItem> cboProy1;
    private JTextField txtHoras;
    private JTextField txtRol;
    private JTable tblIngProy;
    private DefaultTableModel mIngProy;

    // ---------- Departamento-Proyecto widgets ----------
    private JComboBox<ComboItem> cboDpto;
    private JComboBox<ComboItem> cboProy2;
    private JTable tblDptoProy;
    private DefaultTableModel mDptoProy;

    public FrmAsignaciones() {
        super("Asignaciones");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
        buildUI();
        refreshAllTables();
    }

    private void buildUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Ing ↔ Proy", buildIngProyPanel());
        tabs.addTab("Dpto ↔ Proy", buildDptoProyPanel());
        add(tabs);
    }

    // ======== Panel Ingeniero‑Proyecto ========
    private JPanel buildIngProyPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));

        cboIng   = new JComboBox<>(loadCombo("ingeniero", "id_ing", "nombre"));
        cboProy1 = new JComboBox<>(loadCombo("proyecto",  "id_proy", "nombre"));
        txtHoras = new JTextField();
        txtRol   = new JTextField();

        JPanel form = new JPanel(new GridLayout(4,2,6,6));
        form.add(new JLabel("Ingeniero:")); form.add(cboIng);
        form.add(new JLabel("Proyecto:"));  form.add(cboProy1);
        form.add(new JLabel("Horas:"));     form.add(txtHoras);
        form.add(new JLabel("Rol:"));       form.add(txtRol);
        panel.add(form, BorderLayout.NORTH);

        // ---- tabla ----
        mIngProy = new DefaultTableModel(new String[]{
            "IngID","Ingeniero","ProyID","Proyecto","Horas","Rol"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        tblIngProy = new JTable(mIngProy);
        panel.add(new JScrollPane(tblIngProy), BorderLayout.CENTER);

        // ---- botones ----
        JButton btnAdd = new JButton("Asignar");
        JButton btnUpd = new JButton("Actualizar");
        JButton btnDel = new JButton("Eliminar");
        btnAdd.addActionListener(e -> insertarIngProy());
        btnUpd.addActionListener(e -> actualizarIngProy());
        btnDel.addActionListener(e -> eliminarIngProy());
        JPanel btns = new JPanel(); btns.add(btnAdd); btns.add(btnUpd); btns.add(btnDel);
        panel.add(btns, BorderLayout.SOUTH);
        return panel;
    }

    // ======== Panel Departamento‑Proyecto ========
    private JPanel buildDptoProyPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));

        cboDpto  = new JComboBox<>(loadCombo("departamento", "id_dpto", "nombre"));
        cboProy2 = new JComboBox<>(loadCombo("proyecto",     "id_proy", "nombre"));

        JPanel form = new JPanel(new GridLayout(2,2,6,6));
        form.add(new JLabel("Departamento:")); form.add(cboDpto);
        form.add(new JLabel("Proyecto:"));     form.add(cboProy2);
        panel.add(form, BorderLayout.NORTH);

        mDptoProy = new DefaultTableModel(new String[]{
            "DptoID","Departamento","ProyID","Proyecto"},0){
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        tblDptoProy = new JTable(mDptoProy);
        panel.add(new JScrollPane(tblDptoProy), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Asignar");
        JButton btnDel = new JButton("Eliminar");
        btnAdd.addActionListener(e -> insertarDptoProy());
        btnDel.addActionListener(e -> eliminarDptoProy());
        JPanel btns = new JPanel(); btns.add(btnAdd); btns.add(btnDel);
        panel.add(btns, BorderLayout.SOUTH);
        return panel;
    }

    // ======== CRUD Ingeniero‑Proyecto ========
    private void insertarIngProy(){
        try {
            int idIng  = ((ComboItem)cboIng.getSelectedItem()).id();
            int idProy = ((ComboItem)cboProy1.getSelectedItem()).id();
            int horas  = Integer.parseInt(txtHoras.getText());
            String rol = txtRol.getText();
            daoIngProy.insert(new IngProyecto(idIng,idProy,horas,rol));
            refreshIngProy();
        } catch(Exception ex){ showError(ex);} }

    private void actualizarIngProy(){
        int row = tblIngProy.getSelectedRow();
        if(row<0){return;}
        try {
            int idIng  = (int) mIngProy.getValueAt(row,0);
            int idProy = (int) mIngProy.getValueAt(row,2);
            int horas  = Integer.parseInt(txtHoras.getText());
            String rol = txtRol.getText();
            daoIngProy.update(new IngProyecto(idIng,idProy,horas,rol));
            refreshIngProy();
        } catch(Exception ex){ showError(ex);} }

    private void eliminarIngProy(){
        int row = tblIngProy.getSelectedRow(); if(row<0) return;
        try {
            int idIng  = (int) mIngProy.getValueAt(row,0);
            int idProy = (int) mIngProy.getValueAt(row,2);
            daoIngProy.delete(idIng,idProy);
            refreshIngProy();
        } catch(Exception ex){ showError(ex);} }

    private void refreshIngProy(){
        mIngProy.setRowCount(0);
        try {
            for(Object[] r : daoIngProy.viewAll()){ mIngProy.addRow(r);} }
        catch(Exception ex){ showError(ex);} }

    // ======== CRUD Departamento‑Proyecto ========
    private void insertarDptoProy(){
        try {
            int idDpto = ((ComboItem)cboDpto.getSelectedItem()).id();
            int idProy = ((ComboItem)cboProy2.getSelectedItem()).id();
            daoDptoProy.insert(new DptoProyecto(idDpto,idProy));
            refreshDptoProy();
        } catch(Exception ex){ showError(ex);} }

    private void eliminarDptoProy(){
        int row = tblDptoProy.getSelectedRow(); if(row<0) return;
        try {
            int idDpto = (int) mDptoProy.getValueAt(row,0);
            int idProy = (int) mDptoProy.getValueAt(row,2);
            daoDptoProy.delete(new DptoProyecto(idDpto,idProy));
            refreshDptoProy();
        } catch(Exception ex){ showError(ex);} }

    private void refreshDptoProy(){
        mDptoProy.setRowCount(0);
        try {
            for(DptoProyecto dp : daoDptoProy.findAll()){
                mDptoProy.addRow(new Object[]{
                    dp.idDpto(), nombreById("departamento","id_dpto",dp.idDpto()),
                    dp.idProy(), nombreById("proyecto","id_proy",dp.idProy())
                });
            }
        } catch(Exception ex){ showError(ex);} }

    private void refreshAllTables(){ refreshIngProy(); refreshDptoProy(); }

    // ======== Utils ========
    private ComboItem[] loadCombo(String tabla, String idCol, String nameCol){
        java.util.List<ComboItem> list = new ArrayList<>();
        try(var c=DBConnection.getConnection();
            var st=c.createStatement();
            var rs=st.executeQuery("SELECT "+idCol+","+nameCol+" FROM "+tabla)){
            while(rs.next()) list.add(new ComboItem(rs.getInt(1), rs.getString(2)));
        } catch(SQLException ex){ showError(ex);} 
        return list.toArray(ComboItem[]::new);
    }

    private String nombreById(String tabla,String idCol,int id){
        try(var c=DBConnection.getConnection();
            var ps=c.prepareStatement("SELECT nombre FROM "+tabla+" WHERE "+idCol+"=?")){
            ps.setInt(1,id);var rs=ps.executeQuery(); if(rs.next()) return rs.getString(1);
        } catch(SQLException ignored){}
        return String.valueOf(id);
    }

    private void showError(Exception ex){ JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}    

    public static void main(String[] args){ SwingUtilities.invokeLater(() -> new FrmAsignaciones().setVisible(true)); }
}
