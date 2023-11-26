package Modelo;

 // DATOS
import java.util.LinkedList;
import java.util.List;


public class Biblioteca implements Busqueda{
    public LinkedList<Libro> inventario = new LinkedList<>();
    public LinkedList<Usuario> usuarios = new LinkedList<>();
    
    // CONSTRUCTOR
    public Biblioteca() {
    }
    
     // GETTERS
    @Override
    public List<Libro> getInventario() {
        return this.inventario;
    }
    
    // MÉTODOS -> Libros
    public void agregarLibroNuevo(Libro libro){
        //Se añade un nuevo libro a la biblioteca.
        this.inventario.add(libro);
    }
    
    public void prestarLibro(Libro libro){
        libro.prestarEjemplar();
    }
    
    // MÉTODOS -> Usuarios
    public void agregarUsuario(Usuario user){
        this.usuarios.add(user);
    }
   
    
}
