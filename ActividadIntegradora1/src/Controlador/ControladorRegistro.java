
package Controlador;

// DATOS
import Modelo.Persona;
import Modelo.Registro;
import Vista.InterfazRegistro;
import java.util.Date;
import java.util.LinkedList;

// UTILIDADES
import javax.swing.SwingUtilities;

public class ControladorRegistro {
    private InterfazRegistro interfaz;
    private Registro registro;
    
    // CONSTRUCTOR
    public ControladorRegistro(){
        SwingUtilities.invokeLater(() -> {
            this.interfaz = new InterfazRegistro(this);
            this.registro = new Registro();
        });
    }
    
    // GETTERS
     public LinkedList<Persona> getRegistro(){
         return this.registro.getPersonasRegistradas();
     }
    
     // MÉTODOS
    public void procesarFormulario() {
        String nombre = interfaz.nombreField.getText();
        String apellido = interfaz.apellidoField.getText();
        String dni = interfaz.dniField.getText().trim();
        Date fechaNacimiento = (Date) interfaz.fechaNacimientoField.getValue();
            
        registro.agregarPersona(new Persona(nombre, apellido, dni, fechaNacimiento));;
    }
 
    
}
