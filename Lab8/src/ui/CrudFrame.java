
package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * Superclase común con utilidades genéricas para CRUD frames.
 */
public abstract class CrudFrame extends JFrame {
    protected final DefaultTableModel model;
    protected final JTable table;

    protected CrudFrame(String titulo, String[] columnas) {
        super(titulo);
        model = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        setLayout(new BorderLayout(5,5));
        add(new JScrollPane(table), BorderLayout.CENTER);
        setSize(600,400);
        setLocationRelativeTo(null);
    }

    protected void clearTable() { model.setRowCount(0); }
}

