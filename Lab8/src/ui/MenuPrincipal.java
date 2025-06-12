
package ui;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Empresa - CRUDs");
        setSize(300,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,1,5,5));
        JButton b1 = new JButton("Departamentos");
        JButton b2 = new JButton("Ingenieros");
        JButton b3 = new JButton("Proyectos");
        JButton b4 = new JButton("Asignaciones");
        add(b1); add(b2); add(b3); add(b4);
        b1.addActionListener(e -> new FrmDepartamento().setVisible(true));
        b2.addActionListener(e -> new FrmIngeniero().setVisible(true));
        b3.addActionListener(e -> new FrmProyecto().setVisible(true));
        b4.addActionListener(e -> new FrmAsignaciones().setVisible(true));
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true)); }
}
