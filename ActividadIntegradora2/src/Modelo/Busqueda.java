package Modelo;

// DATOS
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public interface Busqueda {
    
    // MÉTODOS ABSTRACTOS
    List<Libro> getInventario();
    
    // MÉTODOS COMPUESTOS
    default Set buscarTitulo(String titulo){
        Set<Libro> coincidencias  = this.getInventario().stream()
                .filter( (libro) -> libro.titulo.contains(titulo))
                .collect(Collectors.toSet());
        return coincidencias;
    }
    
    default Set buscarAutor(String autor){
        Set<Libro> coincidencias  = this.getInventario().stream()
                .filter( (libro) -> libro.autor.contains(autor))
                .collect(Collectors.toSet());
        return coincidencias;
    }
    
    default Set buscarGenero(Genero genero){
        Set<Libro> coincidencias  = this.getInventario().stream()
                .filter( (libro) -> libro.genero == genero)
                .collect(Collectors.toSet());
        return coincidencias;
    }
    
}
