
package lab8;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T, K> {
    void insert(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(K id) throws SQLException;
    List<T> findAll() throws SQLException;
}