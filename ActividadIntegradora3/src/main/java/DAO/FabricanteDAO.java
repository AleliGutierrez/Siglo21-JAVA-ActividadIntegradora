
package DAO;

// DATOS
import java.sql.ResultSet;
import java.util.List;

public abstract class FabricanteDAO<T> extends DAO<T> {
    
    // MÉTODOS ABSTRACTOS: PERSONALIZADOS
    abstract T convertirFabricante(ResultSet result);
    
    abstract List<T> convertirFabricantes(ResultSet result);
    
}
