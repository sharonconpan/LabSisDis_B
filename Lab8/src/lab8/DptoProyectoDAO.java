
package lab8;


import model.DptoProyecto;
import java.sql.*;
import java.util.*;

public class DptoProyectoDAO implements CrudRepository<DptoProyecto, DptoProyecto> {
    private static final String INSERT = "INSERT INTO depto_proyecto VALUES (?,?)";
    private static final String DELETE = "DELETE FROM depto_proyecto WHERE id_dpto=? AND id_proy=?";
    private static final String SELECT_ALL = "SELECT id_dpto, id_proy FROM depto_proyecto";

    @Override public void insert(DptoProyecto dp) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT)) {
            ps.setInt(1, dp.idDpto());
            ps.setInt(2, dp.idProy());
            ps.executeUpdate();
        }
    }
    @Override public void update(DptoProyecto dp) { /* no aplica (PK=PK) */ }
    @Override public void delete(DptoProyecto key) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, key.idDpto());
            ps.setInt(2, key.idProy());
            ps.executeUpdate();
        }
    }
    @Override public List<DptoProyecto> findAll() throws SQLException {
        List<DptoProyecto> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {
            while (rs.next()) {
                list.add(new DptoProyecto(rs.getInt(1), rs.getInt(2)));
            }
        }
        return list;
    }
}