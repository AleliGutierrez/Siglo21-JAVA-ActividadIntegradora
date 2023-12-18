
package Modelo;

import java.util.Objects;

public class Fabricante {
    private int codigo;
    private String nombre;

    public Fabricante(){}
    
    public Fabricante(String nombre) {
        this.nombre = nombre;
    }

    public Fabricante(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.codigo;
        hash = 73 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj == null && getClass() != obj.getClass())
            return false;

        final Fabricante other = (Fabricante) obj;
        return this.nombre == other.nombre && this.codigo == other.codigo;
    }
        
    @Override
    public String toString() {
        return "Fabricante{" + "codigo=" + codigo + ", nombre=" + nombre + '}';
    }
    
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
