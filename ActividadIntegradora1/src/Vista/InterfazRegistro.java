package Vista;

// COMPONENTES
import javax.swing.*;
import java.awt.*;

// EVENTOS
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// DATOS
import java.util.ArrayList;
import java.util.Arrays;
import Controlador.ControladorRegistro;
import Modelo.Persona;
import java.util.LinkedList;

public class InterfazRegistro extends JFrame {

     // ATRIBUTOS
    public JTextField nombreField, apellidoField, dniField;
    public JSpinner fechaNacimientoField;
    public JButton btnRegistrar;
    public JButton btnVerListado;
    private ControladorRegistro controladorRegistro;

     // CONSTRUCTOR
    public InterfazRegistro(ControladorRegistro controladorRegistro) {

        this.controladorRegistro = controladorRegistro;

        // VENTANA
        setTitle("Registro de Personas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // COMPONENTES
        add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        add(nombreField);

        add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        add(apellidoField);

        add(new JLabel("DNI:"));
        dniField = new JTextField();
        add(dniField);

        add(new JLabel("Fecha de Nacimiento:"));
        fechaNacimientoField = new JSpinner(new SpinnerDateModel());
        fechaNacimientoField.setEditor(new JSpinner.DateEditor(fechaNacimientoField, "dd/MM/yyyy"));
        add(fechaNacimientoField);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnRegistrar) {

                    if (validacionFormulario()) {
                        controladorRegistro.procesarFormulario();
                    }
                }
            }
        });
        add(btnRegistrar);

        btnVerListado = new JButton("Ver Listado");
        btnVerListado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarListado();
            }
        });
        add(btnVerListado);
        
        setVisible(true);
    }

    
    // VALIDACIONES -> Formulario
    public boolean validacionFormulario() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String dni = dniField.getText().trim();

        ArrayList<String> valoresCampos = new ArrayList<>(Arrays.asList(nombre, apellido, dni));
        ArrayList<JTextField> camposTexto = new ArrayList<>(Arrays.asList(nombreField, apellidoField, dniField));

        // Validando que los datos no estén vacíos, sean un espacio o incluyan números en el caso del nombre o apellido::
        for (int i = 0; i < camposTexto.size(); i++) {

            if (!validacionDato(valoresCampos.get(i))) {
                JOptionPane.showMessageDialog(this, "No se admiten campos vacíos.\nPor favor, ingrese un dato válido.", "Error", JOptionPane.ERROR_MESSAGE);
                camposTexto.get(i).setText("");
                return false;
            }

            if (i < 2 && !validacionDatoTexto(valoresCampos.get(i))) {
                JOptionPane.showMessageDialog(this, "Este campo no admite números.\nPor favor, ingrese un dato válido.", "Error", JOptionPane.ERROR_MESSAGE);
                camposTexto.get(i).setText("");
                return false;
            }
        }

        // Validando la longitud del dni:
        if (!validacionDNI(dni)) {
            JOptionPane.showMessageDialog(this, "Este campo no admite letras, cáracteres especiales y debe tener una longitud de 8 números.\nPor favor, ingrese un dato válido.", "Error", JOptionPane.ERROR_MESSAGE);
            dniField.setText("");
            return false;
        }
        return true;
    }

    
    // VALIDACIONES -> Campo
    public boolean validacionDato(String dato) {
        ArrayList<String> caracteres_invalidos = new ArrayList<>(Arrays.asList("\\!", "\\@", "\\#", "\\$",
                "\\%", "\\^", "\\&", "\\*", "\\(", "\\)", "\\-", "\\_", "\\+", "\\=", "\\[", "\\]", "\\{", "\\}", "\\;", "\\:", "\\,", "\\.", "\\<", "\\>", "\\?", "\\/", "\\|", "\\\\", "\\~", "\\`"));

        for (String caracter : caracteres_invalidos) {
            if (dato.contains(caracter)) {
                return false;
            }
        }
        return !dato.isEmpty() && !dato.equals(" ");
    }

    public boolean validacionDatoTexto(String dato) {
        ArrayList<String> caracteres_invalidos = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"));

        for (String caracter : caracteres_invalidos) {
            if (dato.contains(caracter)) {
                return false;
            }
        }
        return true;
    }

    public boolean validacionDNI(String dni) {
        ArrayList<String> caracteres_invalidos = new ArrayList<>(Arrays.asList("a", "á", "b", "c", "d", "e", "é", "f",
                "g", "h", "i", "í", "j", "k", "l", "m", "n", "ñ", "o", "ó", "p", "q", "r", "s", "t", "u", "ú", "ü", "v", "w", "x", "y", "z"
        ));

        dni = dni.toLowerCase();

        for (String caracter : caracteres_invalidos) {
            if (dni.contains(caracter)) {
                return false;
            }
        }
        return dni.length() == 8;
    }
    
    // LISTADO DE PERSONAS
    private void mostrarListado() {
        LinkedList<Persona> personas = controladorRegistro.getRegistro();
        mostrarVentanaListado(personas);
    }
    
    private void mostrarVentanaListado(LinkedList<Persona> personas) {
        JFrame listadoVentana = new JFrame("Listado de Personas");
        listadoVentana.setSize(400, 300);
        listadoVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea areaListado = new JTextArea();
        areaListado.setEditable(false);

        // Construir el contenido del JTextArea con la información de las personas
        StringBuilder listado = new StringBuilder("Listado de Personas:\n");
        if (!personas.isEmpty()){
            for (Persona persona : personas) {
                listado.append(persona.toString()).append("\n");
            }
        } else {
            listado.append("No se han registrado personas hasta el momento.");
        }
        
        areaListado.setText(listado.toString());

        listadoVentana.add(new JScrollPane(areaListado), BorderLayout.CENTER);

        // Hacer visible la ventana de listado
        listadoVentana.setVisible(true);
    }
}
