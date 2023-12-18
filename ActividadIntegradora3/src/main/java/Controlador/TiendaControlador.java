
package Controlador;

// DATOS
import DAO.FabricanteDAOImpl;
import DAO.ProductoDAOImpl;
import Modelo.Fabricante;
import Modelo.Producto;
import Vista.InterfazTienda;
import java.util.List;


public class TiendaControlador {
    private static final ProductoDAOImpl prodDAO = new ProductoDAOImpl();
    private static final FabricanteDAOImpl fabDAO = new FabricanteDAOImpl();
    
    public TiendaControlador() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazTienda().setVisible(true);
            }
        });
    }
    
    // LISTAR
    public static List<Producto> listarProductos(String parametroBusqueda){
        return prodDAO.getAll(parametroBusqueda);
    }
    
    public static List<Fabricante> listarFabricantes(String parametroBusqueda){
        return fabDAO.getAll(parametroBusqueda);
    }
    
    
    // BUSCAR
    public static Producto buscarProducto(String nombre){
        return prodDAO.getOne(nombre);
    }
    
    public static Producto buscarProducto(Integer id){
        return prodDAO.getOne(id);
    }
    
    public static Fabricante buscarFabricante(String nombre){
        return fabDAO.getOne(nombre);
    }
    
    public static Fabricante buscarFabricante(Integer id){
        return fabDAO.getOne(id);
    }
    
    // CREAR
    public static String crearProducto(String nombre, Double precio, Object f) {
        if (buscarProducto(nombre) != null) {
            return "El producto ya existe.";
        }

        Fabricante fabricante = (Fabricante) f;
        prodDAO.create(new Producto(nombre, precio, fabricante.getCodigo()));
        return "¡Se ha creado éxitosamente el producto!";
    }
    
    public static String crearFabricante(String nombre) {
        if (buscarFabricante(nombre) != null) {
            return "El fabricante ya existe.";
        }

        fabDAO.create(new Fabricante(nombre));
        return "¡Se ha creado exitosamente el fabricante!";
    }
   
    
    // ACTUALIZAR
    public static String actualizarFabricante(String nombre, Integer codigo){
        fabDAO.update(new Fabricante(codigo, nombre));
        return "¡Se ha actualizado exitosamente el fabricante!";
    }
    
    public static String actualizarProducto(String nombre, Double precio,Object f, Integer codigo){
        Fabricante fabricante = (Fabricante) f;
        prodDAO.update(new Producto(codigo, nombre, precio, fabricante.getCodigo()));
        return "¡Se ha actualizado exitosamente el producto!";
    }
    
    
    // ELIMINAR
    public static String eliminarProducto(Producto producto){
        prodDAO.delete(producto);
        return "¡Se ha eliminado exitosamente el producto!";
    }
    
    public static String eliminarFabricante(Fabricante fabricante){
        fabDAO.delete(fabricante);
        return "¡Se ha eliminado exitosamente el fabricante!";
    }
}

