
package DAO;

// DATOS
import Modelo.Producto;
import java.util.ArrayList;
import java.util.List;

// UTILIDADES
import java.sql.*;

public class ProductoDAOImpl extends ProductoDAO<Producto> {

    @Override
    public void create(Producto objeto) {
        try {
            // Conexión a la base de datos
            conectar();
            
            // Consulta
            queryPrepared =  conexion.prepareStatement("INSERT INTO producto(nombre, precio, codigo_fabricante) VALUES (?, ?, ?)");
            queryPrepared.setString(1, objeto.getNombre());
            queryPrepared.setDouble(2, objeto.getPrecio());
            queryPrepared.setInt(3, objeto.getCodigo_fabricante());
            queryPrepared.execute();
            
        } catch(SQLException e){
            System.out.println("No se pudo crear el producto."  + e.getMessage());
        } finally {
             // Cierre de conexión y recursos.
            desconectar();
        }
    }
    
    @Override
    public void update(Producto objeto) {
        try {
            // Conexión a la base de datos
            conectar();
            
            // Consulta
            queryPrepared =  conexion.prepareStatement("UPDATE producto SET nombre = ?, precio = ?, codigo_fabricante = ? WHERE codigo = ?");
            queryPrepared.setString(1, objeto.getNombre());
            queryPrepared.setDouble(2, objeto.getPrecio());
            queryPrepared.setInt(3, objeto.getCodigo_fabricante());
            queryPrepared.setInt(4, objeto.getCodigo());
            
            queryPrepared.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("No se pudo actualizar el producto."  + e.getMessage());
        } finally {
             // Cierre de conexión y recursos.'
            desconectar();
        }
    }   
      
    @Override
    public void delete(Producto objeto) {
       try {
            // Conexión a la base de datos
            conectar();
            
            // Consulta
            queryPrepared =  conexion.prepareStatement("DELETE FROM producto WHERE codigo = ?");
            queryPrepared.setInt(1, objeto.getCodigo());
            
            queryPrepared.execute();
            
        } catch(SQLException e){
            System.out.println("No se pudo eliminar el producto."  + e.getMessage());
        } finally {
             // Cierre de conexión y recursos.
            desconectar();
        }
    }

    @Override
    public List<Producto> getAll(String parametroBusqueda) {
        try {
            
            // Conexión 
            conectar();
            
            if (parametroBusqueda == null){
                // Consulta
                query = conexion.createStatement();
                // Resultado
                result = query.executeQuery("SELECT * FROM producto");
            } else {
                // Consulta
                queryPrepared = conexion.prepareStatement("SELECT * FROM producto WHERE nombre LIKE ?");
                queryPrepared.setString(1, "%" + parametroBusqueda + "%");
                
                queryPrepared.execute();
                
                // Resultado
                result = queryPrepared.getResultSet();
            }
            
            
        } catch(SQLException e) {
            System.out.println("No se pudo mostrar el listado."  + e.getMessage());

        } finally {
            List<Producto> productos = convertirProductos(result);
            desconectar();
            return productos;
        }
    }
    
    @Override
    public Producto getOne(String name) {
        Producto producto = null;
        try {
            
            // Conexión 
            conectar();
            // Consulta
            queryPrepared =  conexion.prepareStatement("SELECT * FROM producto WHERE nombre = ?");
            queryPrepared.setString(1, name);
            queryPrepared.execute();
            // Resultado
            result = queryPrepared.getResultSet();
            if (result.next()) 
                producto = convertirProducto(result);
            
        } catch(SQLException e) {
            System.out.println("No se pudo encontrar el producto proporcionado."  + e.getMessage());
        } finally {
            desconectar();
            return producto;
        }
    }

    
    public Producto getOne(Integer id) {
        Producto producto = null;
        try {
            
            // Conexión 
            conectar();
            // Consulta
            queryPrepared =  conexion.prepareStatement("SELECT * FROM producto WHERE codigo = ?");
            queryPrepared.setInt(1, id);
            queryPrepared.execute();
            // Resultado
            result = queryPrepared.getResultSet();
            if (result.next())
                producto = convertirProducto(result);
            
        } catch(SQLException e) {
            System.out.println("No se pudo encontrar el producto proporcionado."  + e.getMessage());
        } finally {
            desconectar();
            return producto;
        }
    }
    
    // MÉTODOS PERSONALIZADOS:
    public Producto convertirProducto(ResultSet result){
        Producto producto = new Producto();
        try {
            
            producto.setCodigo(result.getInt("codigo"));
            producto.setNombre(result.getString("nombre"));
            producto.setPrecio(result.getDouble("precio")); 
            producto.setCodigo_fabricante(result.getInt("codigo_fabricante"));
            
        } catch(SQLException e) {
            System.out.println("No se pudo convertir el producto."  + e.getMessage());
        } finally {
            return producto;
        }
    }
    
    public List<Producto> convertirProductos(ResultSet result){
        List<Producto> productos = new ArrayList<>();
        try {
            while(result.next()){
                productos.add(convertirProducto(result));
            }
        } catch(SQLException e) {
            System.out.println("No se pudo convertir los productos."  + e.getMessage());
        } finally {
            return (productos.size() > 0) ? productos : null;
        }
    }
}
