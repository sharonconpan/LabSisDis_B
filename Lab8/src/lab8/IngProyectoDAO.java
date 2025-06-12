package lab8;
import java.sql.*;import java.util.*;import model.IngProyecto;
public class IngProyectoDAO {
    private static final String INSERT =
        "INSERT INTO ing_proyecto (id_ing, id_proy, horas, rol) VALUES (?,?,?,?)";
    private static final String UPDATE =
        "UPDATE ing_proyecto SET horas=?, rol=? WHERE id_ing=? AND id_proy=?";
    private static final String DELETE =
        "DELETE FROM ing_proyecto WHERE id_ing=? AND id_proy=?";
    // Incluimos nombres para visualización en la GUI
    private static final String SELECT_VIEW =
        """
        SELECT i.id_ing, i.nombre AS ing_nombre,
               p.id_proy, p.nombre AS proy_nombre,
               ip.horas, ip.rol
        FROM   ing_proyecto ip
        JOIN   ingeniero i ON i.id_ing = ip.id_ing
        JOIN   proyecto  p ON p.id_proy = ip.id_proy
        """;

    public void insert(IngProyecto ip) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT)) {
            ps.setInt(1, ip.idIng());
            ps.setInt(2, ip.idProy());
            ps.setInt(3, ip.horas());
            ps.setString(4, ip.rol());
            ps.executeUpdate();
        }
    }
    public void update(IngProyecto ip) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {
            ps.setInt(1, ip.horas());
            ps.setString(2, ip.rol());
            ps.setInt(3, ip.idIng());
            ps.setInt(4, ip.idProy());
            ps.executeUpdate();
        }
    }
    public void delete(int idIng, int idProy) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {
            ps.setInt(1, idIng);
            ps.setInt(2, idProy);
            ps.executeUpdate();
        }
    }
    // Devuelve filas ya listas para la tabla (Object[])
    public List<Object[]> viewAll() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT_VIEW)) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("id_ing"),  rs.getString("ing_nombre"),
                    rs.getInt("id_proy"), rs.getString("proy_nombre"),
                    rs.getInt("horas"),   rs.getString("rol")
                });
            }
        }
        return list;
    }
}
