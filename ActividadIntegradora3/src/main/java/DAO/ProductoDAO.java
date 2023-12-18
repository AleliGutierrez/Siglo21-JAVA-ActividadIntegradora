
package DAO;

// DATOS
import java.sql.ResultSet;
import java.util.List;

public abstract class ProductoDAO<T> extends DAO<T> {
    
    // MÉTODOS ABSTRACTOS: PERSONALIZADOS
    abstract T convertirProducto(ResultSet result);
    
    abstract List<T> convertirProductos(ResultSet result);
    
}