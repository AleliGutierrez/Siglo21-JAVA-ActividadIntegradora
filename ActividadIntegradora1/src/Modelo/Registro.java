package Modelo;

// DATOS
import java.util.LinkedList;


public class Registro {
    
     // ATRIBUTOS
    private LinkedList<Persona> personas_registradas = new LinkedList<>();

     // CONSTRUCTOR
    public Registro() {
    }

     // GETTERS
    public LinkedList<Persona> getPersonasRegistradas() {
        return personas_registradas;
    }

     // MÉTODOS
    public void agregarPersona(Persona p){
        this.personas_registradas.add(p);
    }
   
}
