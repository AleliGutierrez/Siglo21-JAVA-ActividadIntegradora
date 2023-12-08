package Modelo;

// DATOS
import java.util.LinkedList;
import java.util.Objects;


public class Usuario {
    public String nombre;
    public LinkedList<Libro> librosAlquilados = new LinkedList<>();
    
    // CONSTRUCTOR
    public Usuario(String nombre) {
        this.nombre = nombre;
    }
    
    // MÉTODOS

    public void alquilarLibro(Libro libro) {
        this.librosAlquilados.add(libro);
    }
    
    public boolean devolverLibro(Libro libro){
        return this.librosAlquilados.remove(libro);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final Usuario otroUsuario = (Usuario) obj;
        return Objects.equals(nombre, otroUsuario.nombre);
    }
}
