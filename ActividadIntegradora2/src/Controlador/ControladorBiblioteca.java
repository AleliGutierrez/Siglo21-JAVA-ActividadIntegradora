
package Controlador;

// DATOS
import Modelo.Biblioteca;
import Modelo.Genero;
import Modelo.Libro;
import Modelo.Usuario;
import Vista.InterfazBiblioteca;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// EXCEPCIONES
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

// UTILIDADES
import javax.swing.SwingUtilities;


public class ControladorBiblioteca {
    private final Biblioteca biblioteca = new Biblioteca();
    private InterfazBiblioteca interfaz;
    private Usuario user;

    // CONSTRUCTOR
    public ControladorBiblioteca() {
        SwingUtilities.invokeLater(() -> {
            this.interfaz = new InterfazBiblioteca(this);
            });
    }
    
    // GETTERS
    public String getUsuario(){
        return this.user.nombre;
    }
    
    
            
    // MÉTODOS -> Validaciones
    // Estas validaciones determinan si se ha cancelado una operacion:
    public void esNulo(String dato){
        if (dato == null) {
            throw new NullPointerException("Operación cancelada");
        }
    }
    
    public void esNulo(Genero genero) {
        if (genero == null) {
            throw new NullPointerException("Operación cancelada");
        }
    }
    
    // Validación global para campos de texto
    public void esValido(String dato) {
        if (dato.isEmpty() || dato.equals(" ")) {
            throw new InputMismatchException("No se ha ingresado ningún valor para el campo.");
        }
    }


    
    
    
    
    // MÉTODOS -> Operaciones
    public Libro[] librosBiblioteca() {
        return this.biblioteca.inventario.stream()
                .toArray(Libro[] :: new);
    }
    public Libro[] librosDisponiblesAlquiler() {
        return this.biblioteca.inventario.stream()
                .filter( (libro) -> libro.estaDisponible())
                .toArray(Libro[] :: new);
    }
      
    public Libro[] librosDisponiblesDevolver() {
        return this.user.librosAlquilados.stream()
                .toArray(Libro[] :: new);
    }
    
    public Libro[] librosCoincidencias(Set coincidencias) {
        return (Libro[]) coincidencias.stream()
                .toArray(Libro[] :: new);
    }
    
    public void crearUsuario(String username) {
        /* Si el username cumple con los requisitos (no puede estar vacío), se verifica que no sea nulo (de serlo
        se arroja una excepción), se busca coincidencias con otros usuarios y, de haberlas, se actualiza this.user
        al que coincida con el username enviado. 
        Si no se hayan coincidencias se crea un nuevo usuario, se lo añade a la lista de la biblioteca y se actualiza
        this.user.
         */
        try {
            esValido(username);
            if (username != null) {
                // Buscar si ya existe un usuario con el mismo nombre.
                Optional<Usuario> usuarioExistente = this.biblioteca.usuarios.stream()
                        .filter((Usuario u) -> u.nombre.equals(username))
                        .findFirst();

                // Verificar si se ha encontrado un usuario existente:
                if (usuarioExistente.isPresent()) {
                    // De hacerlo, tan solo actualizar this.user:
                    this.user = usuarioExistente.get();
                } else {
                    // SI no, crear uno nuevo y agregarlo a la lista:
                    Usuario nuevoUsuario = new Usuario(username);
                    this.biblioteca.agregarUsuario(nuevoUsuario);
                    this.user = nuevoUsuario;
                }
                
            } else {
                // De haberse enviado un campo nulo (producto de la cancelación del proceso) arrojar una excepción:
                throw new NullPointerException();
            }
            
        } catch (InputMismatchException | NullPointerException e) {
            throw e;
        }
    }
    
    
    public String crearLibro(String titulo, String autor, Genero genero, int cantEjemplares) {
        // Buscar un libro con el mismo título y autor:
        Optional<Libro> libroExistente = this.biblioteca.inventario.stream()
                .filter(libro -> libro.titulo.equals(titulo) && libro.autor.equals(autor))
                .findFirst();

        // Verificar si se encontró:
        if (libroExistente.isPresent()) {
            // Si existe, actualizar la cantidad de ejemplares en el libro existente
            Libro libroEnInventario = libroExistente.get();
            libroEnInventario.actualizarEjemplares(cantEjemplares);
            return "¡Se han agregado los ejemplares a la biblioteca!";
        } else {
            // Si la biblioteca no contiene el libro, se lo añade.
            this.biblioteca.inventario.add(new Libro(titulo, autor, genero, cantEjemplares));
            return "¡Se ha añadido el libro!";
        }
    }
            
    
    public boolean alquiler(Libro libro) {
        /* Si el libro no es nulo se resta un ejemplar de este en la biblioteca, se procede a actualizar la lista
        de libros alquilados por el usuario y se arroja un mensaje al usuario informandole del éxito en la operación.
        Si es nulo, se arroja otro mensaje dejandole saber al usuario que la operación ha sido cancelada.
        */
        if (libro != null) {
            this.biblioteca.prestarLibro(libro);
            this.user.alquilarLibro(libro);
            return true;
        } else {
            return false;
        }
    }

    public boolean devolver(Libro libro) {
        /* Si el libro no es nulo se resta un ejemplar de este en la lista de libros alquilados por el usuario, 
        se procede a actualizar la cantidad de ejemplares del libro y se arroja un mensaje al usuario informandole
        del éxito en la operación.
        Si es nulo, se arroja otro mensaje dejandole saber al usuario que la operación ha sido cancelada.
        */
        if (libro != null) {
            this.user.devolverLibro(libro);
           libro.agregarEjemplar();
            return true;
        } else {
            return false;
        }
    }

    public Libro[] buscar(String titulo, String autor, Genero genero) {
        /* Si el libro no es nulo se resta un ejemplar de este en la lista de libros alquilados por el usuario, 
        se procede a actualizar la cantidad de ejemplares del libro y se arroja un mensaje al usuario informandole
        del éxito en la operación.
        Si es nulo, se arroja otro mensaje dejandole saber al usuario que la operación ha sido cancelada.
        */
        Set<Libro> coincidencias = null;
        
        coincidencias = (titulo != null) ? this.biblioteca.buscarTitulo(titulo) : coincidencias;
        
        if (autor != null) {
            coincidencias.retainAll(this.biblioteca.buscarAutor(autor));
        }
        
        if (genero != null) {
            coincidencias.retainAll(this.biblioteca.buscarGenero(genero));
        }
        
        return librosCoincidencias(coincidencias);
    }
}
