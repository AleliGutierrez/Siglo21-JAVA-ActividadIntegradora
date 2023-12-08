
package Vista;

// DATOS
import Controlador.ControladorBiblioteca;
import Modelo.Genero;
import Modelo.Libro;

// EXCEPCIONES
import java.util.InputMismatchException;

// UTILIDADES
import javax.swing.JOptionPane;



public class InterfazBiblioteca {
    private final ControladorBiblioteca controlador;

    // CONSTRUCTOR
    public InterfazBiblioteca(ControladorBiblioteca controlador) {
        this.controlador = controlador;
        userFrame();
    }
    
    
    
    // VENTANA NEW USUARIO
    private void userFrame(){
        try{
            String nombre = JOptionPane.showInputDialog(null, "¡Bienvenido/a! Ingresa tu nombre para continuar: ");
            this.controlador.esNulo(nombre);
            this.controlador.crearUsuario(nombre);
            menuFrame();
        } catch (InputMismatchException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            userFrame();
        } catch (NullPointerException e){
            cancelUserFrame();
        }
    }
    
    // VENTANA OPERACIONES
    private void menuFrame() {
        String[] opciones = {"Mirar biblioteca", "Buscar", "Alquilar", "Devolver", "Prestar", "Salir"};
        
        Integer opcionSeleccionada = JOptionPane.showOptionDialog(null, 
                "¡Bienvenido/a a la biblioteca, " + this.controlador.getUsuario() + "!\nEscoge una opción: ",
                "Biblioteca", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]);
        
        String mensaje;
        
        /* Al hacer a la opción Integer se puede determinar si el usuario ha apretado la x (= null)
        y por tanto permitirle salir, cosa que con datos primitivos no es posible..*/
        if (opcionSeleccionada == null){
            return;
        }
        
        switch (opcionSeleccionada){
            case 0:
                // MIRAR BIBLIOTECA
                Libro[] listado = this.controlador.librosBiblioteca();
                Libro libro = listFrame(listado, "Seleccione el libro a inspeccionar: ");
                if (libro != null){
                    bookFrame(libro);
                    menuFrame();
                    break;
                } 
                return; 
                
             case 1:
                 // BUSCAR LIBRO
                Libro[] coincidencias = searchFrame();
                if (coincidencias != null){
                    listFrame(coincidencias, "Coincidencias encontradas:");
                    cancelFrame();
                    break;
                } 
                return;  
                
            case 2:
                // ALQUILAR LIBRO
                Libro[] alquiler = this.controlador.librosDisponiblesAlquiler();
                if (this.controlador.alquiler(listFrame(alquiler, "Seleccione el libro a alquilar: "))) {
                    JOptionPane.showMessageDialog(null, "¡Se ha alquilado el libro!");
                    menuFrame();
                    break;
                }
                return;
                
            case 3: 
                // DEVOLVER LIBRO
                Libro[] devolver = this.controlador.librosDisponiblesDevolver();
                if (this.controlador.devolver(listFrame(devolver, "Seleccione el libro a devolver: "))) {
                    JOptionPane.showMessageDialog(null, "¡Se ha devuelto el libro!");
                    menuFrame();
                    break;
                }
                return;
                
            case 4:
                // CREAR LIBRO
                mensaje = createBookFrame();
                if (mensaje != null) {
                    JOptionPane.showMessageDialog(null, mensaje);
                    menuFrame();
                    break;
                }
                return;
                    
            default:
                // SALIR 
                userFrame();
        }
    }
    
    // VENTANA LISTADO -> General, Alquiler, Devolver
    private Libro listFrame(Libro[] listado, String mensaje) {
        // Se enseña el listado:
        if (listado.length > 0) {
            Object seleccion = JOptionPane.showInputDialog(null, mensaje, "Lista de Libros", 
                    JOptionPane.QUESTION_MESSAGE, null, listado, listado[0]);
            // Se comprueba que no se haya seleccionado la opción cancelar o x:
            if (seleccion == null){
                //De ser así, se le ofrece regresar al menú:
                cancelFrame();
                return null;
            } else {
                // Si no se retorna el libro:
                return (Libro) seleccion;
            }
        } else {
            cancelFrame("No hay ejemplares disponibles");
            return null;
        }
    }

    // VENTANA NEW LIBRO
    private String createBookFrame() {
        String titulo = "", autor = "", cantEjemplaresTexto = "";
        Genero genero = null;
        
        int cantEjemplares = 0;
        
        try {
            titulo = JOptionPane.showInputDialog("Ingrese el título del libro:");
            this.controlador.esNulo(titulo);
            this.controlador.esValido(titulo);
            
            autor = JOptionPane.showInputDialog("Ingrese el autor del libro:");
            this.controlador.esNulo(autor);
            this.controlador.esValido(autor);
            
            genero = (Genero) JOptionPane.showInputDialog(null, "Seleccione el género del libro:", "Género",
                JOptionPane.QUESTION_MESSAGE, null, Genero.values(), Genero.values()[0]);
            this.controlador.esNulo(genero);
            
            cantEjemplaresTexto = JOptionPane.showInputDialog("Ingrese la cantidad de ejemplares a entregar:");
            this.controlador.esNulo(cantEjemplaresTexto);
            this.controlador.esValido(cantEjemplaresTexto);
            cantEjemplares = Integer.parseInt(cantEjemplaresTexto);
           
        } catch (InputMismatchException | NumberFormatException e) {
            
            // verificarDato -> Arroja InputMismatchException si el campo está vacío.
            // NumberFormatException -> Se arroja si el campo integer recibe un valor inválido.
            
            String mensaje = (e instanceof NumberFormatException) ? "Debe ingresar un número." : e.getMessage();
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return createBookFrame();
            
        } catch (NullPointerException e){
            // NullPointerException -> Se arroja si el usuario decide cancelar en algún punto la operación.
            cancelFrame(e.getMessage());
            return null;
        }
        
        return this.controlador.crearLibro(titulo, autor, genero, cantEjemplares);
    }
    
    
     // VENTANA INSPECCIONAR LIBRO
    private void bookFrame(Libro libro){
        String mensaje = "Título: " + libro.titulo + "\n"
            + "Autor: " + libro.autor + "\n"
            + "Género: " + libro.genero + "\n"
            + "Ejemplares disponibles: " + libro.cantEjemplares;

    JOptionPane.showMessageDialog(null, mensaje, "Detalles del Libro", JOptionPane.INFORMATION_MESSAGE);
}
    
    
    // VENTANA BÚSQUEDA
    private Libro[] searchFrame(){
        
        String titulo = "", autor = "";
        Genero genero = null;
        
        try {
            titulo = JOptionPane.showInputDialog("Ingrese el título del libro:");
            this.controlador.esNulo(titulo);
            
            autor = JOptionPane.showInputDialog("Ingrese el autor del libro:");
            this.controlador.esNulo(autor);
            
            genero = (Genero) JOptionPane.showInputDialog(null, "Seleccione el género del libro:", "Género",
                    JOptionPane.QUESTION_MESSAGE, null, Genero.values(), null);
            
        } catch (NullPointerException e) {
            // esNulo -> Arroja NullPointerException si el campo está vacío.
            cancelFrame(e.getMessage());
            return null;
        }
        return this.controlador.buscar(titulo, autor, genero);
    }
    
    
     // OPCIÓN CANCELAR (APLICABLE A TODOS LOS CAMPOS)
    private void cancelFrame(String title) {
        String[] opciones = {"Menú", "Salir"};
        Integer opcion = JOptionPane.showOptionDialog(null, "¿Volver al menú?", title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (opcion == 0) {
            menuFrame();
        } else {
            return;
        }
    }
    
    private void cancelFrame() {
        String[] opciones = {"Menú", "Salir"};
        int opcion = JOptionPane.showOptionDialog(null, "¿Volver al menú?", "Cancelar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (opcion == 0) {
            menuFrame();
        } else {
            return;
        }
    }
    
    // OPCIÓN CANCELAR (APLICABLE AL CAMPO USUARIO)
    private void cancelUserFrame() {
        String[] opciones = {"Sí", "No"};
        int opcion = JOptionPane.showOptionDialog(null, "¿Deseas salir de la aplicación?", "Salir",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (opcion == 0) {
            return;
        } else {
            userFrame();
        }
    }
}
