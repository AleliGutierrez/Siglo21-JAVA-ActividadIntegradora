package Modelo;

// DATOS
import java.util.Date;


public class Persona {
    
     // ATRIBUTOS
    private String nombre, apellido, dni;
    private Date fecha_nacimiento;

     // CONSTRUCTOR
    public Persona(String nombre, String apellido, String dni, Date fecha_nacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
    }

     // GETTERS / SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    
     // MÉTODOS
    @Override
    public String toString() {
        return "Nombre: " + this.nombre + ". Apellido: " + this.apellido + ". DNI: " + this.dni + ". Fecha de nacimiento: " + this.fecha_nacimiento.toString() + ".\n";
    }
    
    
}
