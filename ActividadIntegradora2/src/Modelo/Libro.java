package Modelo;

import java.util.Objects;

public class Libro {
    public String titulo, autor;
    public Genero genero;
    public int cantEjemplares;
    
    // CONSTRUCTOR
    public Libro(String titulo, String autor, Genero genero, int cantEjemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.cantEjemplares = cantEjemplares;
    }

    // MÉTODOS
    public boolean estaDisponible(){
         return this.cantEjemplares > 0;
    }
    
    public void agregarEjemplar(){
        this.cantEjemplares++;
    }
    
    public Libro prestarEjemplar(){
        this.cantEjemplares--;
        return this;
    }
    
    public void actualizarEjemplares(int ejemplares){
        // El += no anda, es un mañoso.
        this.cantEjemplares = this.cantEjemplares + ejemplares;
    }
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.titulo);
        hash = 97 * hash + Objects.hashCode(this.autor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final Libro otroLibro = (Libro) obj;
        return Objects.equals(titulo, otroLibro.titulo) && Objects.equals(autor, otroLibro.autor);
    }

    @Override
    public String toString() {
        return titulo;
    }
}


