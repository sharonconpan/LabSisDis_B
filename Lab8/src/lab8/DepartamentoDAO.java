
package lab8;

import model.Departamento;
import java.sql.*;
import java.util.*;

public class DepartamentoDAO implements CrudRepository<Departamento,Integer> {
    private static final String INSERT =
        "INSERT INTO departamento(nombre, telefono, fax) VALUES (?,?,?)";
    private static final String UPDATE =
        "UPDATE departamento SET nombre=?, telefono=?, fax=? WHERE id_dpto=?";
    private static final String DELETE =
        "DELETE FROM departamento WHERE id_dpto=?";
    private static final String SELECT_ALL =
        "SELECT id_dpto, nombre, telefono, fax FROM departamento";

    @Override
    public void insert(Departamento d) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT)) {
            ps.setString(1, d.nombre());
            ps.setString(2, d.telefono());
            ps.setString(3, d.fax());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Departamento d) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, d.nombre());
            ps.setString(2, d.telefono());
            ps.setString(3, d.fax());
            ps.setInt(4, d.id());
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
    public List<Departamento> findAll() throws SQLException {
        List<Departamento> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {
            while (rs.next()) {
                list.add(new Departamento(
                    rs.getInt("id_dpto"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("fax")));
            }
        }
        return list;
    }
}
