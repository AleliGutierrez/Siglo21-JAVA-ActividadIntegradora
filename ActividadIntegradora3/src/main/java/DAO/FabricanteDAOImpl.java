
package DAO;

// DATOS
import Modelo.Fabricante;
import java.util.ArrayList;
import java.util.List;

// UTILIDADES
import java.sql.*;

public class FabricanteDAOImpl extends FabricanteDAO<Fabricante>{

    public void create(Fabricante objeto) {
        try {
            // Conexión a la base de datos
            conectar();
            
            // Consulta
            queryPrepared =  conexion.prepareStatement("INSERT INTO fabricante(nombre) VALUES (?)");
            queryPrepared.setString(1, objeto.getNombre());
            queryPrepared.execute();
            
        } catch(SQLException e){
            System.out.println("No se pudo crear el fabricante."  + e.getMessage());
        } finally {
             // Cierre de conexión y recursos.
            desconectar();
        }
    }
    
    @Override
    public void update(Fabricante objeto) {
        try {
            // Conexión a la base de datos
            conectar();
            
            // Consulta
            queryPrepared =  conexion.prepareStatement("UPDATE fabricante SET nombre = ? WHERE codigo = ?");
            queryPrepared.setString(1, objeto.getNombre());
            queryPrepared.setInt(2, objeto.getCodigo());
            queryPrepared.execute();
            
        } catch(SQLException e){
            System.out.println("No se pudo actualizar el fabricante."  + e.getMessage());
        } finally {
             // Cierre de conexión y recursos.
            desconectar();
        }
    }   
      
    @Override
    public void delete(Fabricante objeto) {
       try {
            // Conexión a la base de datos
            conectar();
            
            // Consulta
            queryPrepared =  conexion.prepareStatement("DELETE FROM fabricante WHERE codigo = ?");
            queryPrepared.setInt(1, objeto.getCodigo());
            
            queryPrepared.execute();
            
        } catch(SQLException e){
            System.out.println("No se pudo eliminar el fabricante."  + e.getMessage());
        } finally {
             // Cierre de conexión y recursos.
            desconectar();
        }
    }

    @Override
    public List<Fabricante> getAll(String parametroBusqueda) {
        try {
            
            // Conexión 
            conectar();
            
             if (parametroBusqueda == null) {
                // Consulta
                query = conexion.createStatement();
                // Resultado
                result = query.executeQuery("SELECT * FROM fabricante");
                
            } else {
                // Consulta
                queryPrepared = conexion.prepareStatement("SELECT * FROM fabricante WHERE nombre LIKE ?");
                queryPrepared.setString(1, "%" + parametroBusqueda + "%");

                queryPrepared.execute();

                // Resultado
                result = queryPrepared.getResultSet();
            }
           
        } catch(SQLException e) {
            System.out.println("No se pudo mostrar el listado."  + e.getMessage());

        } finally {
            List<Fabricante> productos = convertirFabricantes(result);
            desconectar();
            return productos;
        }
    }
    
    @Override
    public Fabricante getOne(String name) {
        Fabricante fabricante = null;
        try {
            
            // Conexión 
            conectar();
            // Consulta
            queryPrepared =  conexion.prepareStatement("SELECT * FROM fabricante WHERE nombre = ?");
            queryPrepared.setString(1, name);
            queryPrepared.execute();
            // Resultado
            result = queryPrepared.getResultSet();
            if (result.next()) 
                fabricante = convertirFabricante(result);
            
        } catch(SQLException e) {
            System.out.println("No se pudo encontrar el fabricante proporcionado."  + e.getMessage());
        } finally {
            desconectar();
            return fabricante;
        }
    }
    
    public Fabricante getOne(Integer id) {
        Fabricante fabricante = null;
        try {
            
            // Conexión 
            conectar();
            
            // Consulta
            queryPrepared =  conexion.prepareStatement("SELECT * FROM fabricante WHERE codigo = ?");
            queryPrepared.setInt(1, id);
            queryPrepared.execute();
            
            // Resultado
            result = queryPrepared.getResultSet();
            if (result.next()) 
                fabricante = convertirFabricante(result);
            
        } catch(SQLException e) {
            System.out.println("No se pudo encontrar el fabricante proporcionado."  + e.getMessage());
        } finally {
            desconectar();
            return fabricante;
        }
    }

    
    // MÉTODOS PERSONALIZADOS:
    @Override
    public Fabricante convertirFabricante(ResultSet result){
        Fabricante fabricante = new Fabricante();
        try {
            fabricante.setCodigo(result.getInt("codigo"));
            fabricante.setNombre(result.getString("nombre"));
        } catch(SQLException e) {
            System.out.println("No se pudo convertir el fabricante."  + e.getMessage());
        } finally {
            return fabricante;
        }
    }
    
    @Override
    public List<Fabricante> convertirFabricantes(ResultSet result){
        List<Fabricante> fabricantes = new ArrayList<>();
        try {
            while(result.next()){
                fabricantes.add(convertirFabricante(result));
            }
        } catch(SQLException e) {
            System.out.println("No se pudo convertir los fabricantes."  + e.getMessage());
        } finally {
            return (fabricantes.size() > 0) ? fabricantes : null;
        }
    }
}
