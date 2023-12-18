
package Modelo;

import java.util.Objects;

public class Producto {
    private Integer codigo;
    private String nombre;
    private Double precio;
    private Integer codigo_fabricante;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.codigo);
        hash = 29 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj == null && getClass() != obj.getClass())
            return false;

        final Producto other = (Producto) obj;
        return this.nombre == other.nombre && this.codigo == other.codigo;
    }
    
    public Producto() {}

    public Producto(Integer codigo, String nombre, Double precio, Integer codigo_fabricante) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.codigo_fabricante = codigo_fabricante;
    }

    public Producto(String nombre, Double precio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Producto{" + "codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + ", codigo_fabricante=" + codigo_fabricante + '}';
    }

    public Producto(String nombre, Double precio, Integer codigo_fabricante) {
        this.nombre = nombre;
        this.precio = precio;
        this.codigo_fabricante = codigo_fabricante;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getCodigo_fabricante() {
        return codigo_fabricante;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setCodigo_fabricante(Integer codigo_fabricante) {
        this.codigo_fabricante = codigo_fabricante;
    }
    
    
}
