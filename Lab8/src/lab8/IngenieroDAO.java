package lab8;

import model.Ingeniero;
import java.sql.*;
import java.util.*;

public class IngenieroDAO implements CrudRepository<Ingeniero,Integer> {

    private static final String INSERT =
        "INSERT INTO ingeniero(nombre, especialidad, cargo) VALUES (?,?,?)";
    private static final String UPDATE =
        "UPDATE ingeniero SET nombre=?, especialidad=?, cargo=? WHERE id_ing=?";
    private static final String DELETE =
        "DELETE FROM ingeniero WHERE id_ing=?";
    private static final String SELECT_ALL =
        "SELECT id_ing, nombre, especialidad, cargo FROM ingeniero";

    @Override
    public void insert(Ingeniero i) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT)) {
            ps.setString(1, i.nombre());
            ps.setString(2, i.especialidad());
            ps.setString(3, i.cargo());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Ingeniero i) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, i.nombre());
            ps.setString(2, i.especialidad());
            ps.setString(3, i.cargo());
            ps.setInt   (4, i.id());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Ingeniero> findAll() throws SQLException {
        List<Ingeniero> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {
            while (rs.next()) {
                list.add(new Ingeniero(
                    rs.getInt   ("id_ing"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getString("cargo")
                ));
            }
        }
        return list;
    }
}
