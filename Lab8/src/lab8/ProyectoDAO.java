
package lab8;


import model.Proyecto;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ProyectoDAO implements CrudRepository<Proyecto,Integer> {
    private static final String INSERT =
        "INSERT INTO proyecto(nombre, fec_inicio, fec_termino) VALUES (?,?,?)";
    private static final String UPDATE =
        "UPDATE proyecto SET nombre=?, fec_inicio=?, fec_termino=? WHERE id_proy=?";
    private static final String DELETE =
        "DELETE FROM proyecto WHERE id_proy=?";
    private static final String SELECT_ALL =
        "SELECT id_proy, nombre, fec_inicio, fec_termino FROM proyecto";

    @Override public void insert(Proyecto p) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT)) {
            ps.setString(1, p.nombre());
            ps.setDate(2, java.sql.Date.valueOf(p.fechaInicio()));
            if (p.fechaTermino() != null)
                ps.setDate(3, java.sql.Date.valueOf(p.fechaTermino()));
            else ps.setNull(3, Types.DATE);
            ps.executeUpdate();
        }
    }
    @Override public void update(Proyecto p) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setString(1, p.nombre());
            ps.setDate(2, java.sql.Date.valueOf(p.fechaInicio()));
            if (p.fechaTermino()!=null)
                ps.setDate(3, java.sql.Date.valueOf(p.fechaTermino()));
            else ps.setNull(3,Types.DATE);
            ps.setInt(4, p.id());
            ps.executeUpdate();
        }
    }
    @Override public void delete(Integer id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    @Override public List<Proyecto> findAll() throws SQLException {
        List<Proyecto> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL)) {
            while (rs.next()) {
                java.sql.Date fin = rs.getDate("fec_termino");
                list.add(new Proyecto(
                    rs.getInt("id_proy"),
                    rs.getString("nombre"),
                    rs.getDate("fec_inicio").toLocalDate(),
                    fin==null? null : fin.toLocalDate()));
            }
        }
        return list;
    }
}
