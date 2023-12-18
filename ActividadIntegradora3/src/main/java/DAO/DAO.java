
package DAO;

// DATOS
import java.sql.*;
import java.util.List;

public abstract class DAO<T> {

    // ATRIBUTOS
    protected static Connection conexion = null;
    protected static ResultSet result = null;
    protected static Statement query = null;
    protected static PreparedStatement queryPrepared = null;
    
    // MÉTODOS CONCRETOS: CONEXIÓN
    protected void conectar() {
        try{
            DAO.conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tienda", "root", "root");
        } catch (SQLException e) {
            System.out.println("No se pudo establecer la conexión." + e);
        }
    }
    
    protected void desconectar(){
        if(DAO.conexion != null){
            try {
                DAO.conexion.close();
            } catch (SQLException e) {
                System.out.println("No se pudo cerrar la conexión." + e);
            }
        }
    }
    
   
    

    // MÉTODOS ABSTRACTOS: CRUD
    abstract void create(T object);
    
    abstract List<T> getAll(String parametroBusqueda);
    
    abstract T getOne(String name);
    
    abstract void update(T object);
    
    abstract void delete(T object);
}

