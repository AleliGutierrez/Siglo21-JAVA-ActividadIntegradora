
package Vista;

// DATOS
import Controlador.TiendaControlador;
import Modelo.Fabricante;
import Modelo.Producto;
import java.util.List;

// EXCEPCIONES
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

// EVENTOS
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// COMPONENTES
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class InterfazTienda extends JFrame {
    // COMPONENTES                    
    private static JMenuItem MenuBorrarItem = new JMenuItem("Eliminar");
    private static JMenuItem MenuBuscarItem = new JMenuItem("Buscar");
    private static JMenuItem MenuCrearItem = new JMenuItem("Crear");
    private static JMenuItem MenuModificarItem = new JMenuItem("Modificar");
    private static JTable TablaFabricantes = new JTable();
    private static JTable TablaProductos = new JTable();
    private static JLabel fabricantesLabel = new JLabel();
    private static JMenuBar Menu = new JMenuBar();
    private static JScrollPane jScrollPane1 = new JScrollPane();
    private static JScrollPane jScrollPane2 = new JScrollPane();
    private static JSeparator  jSeparator1 = new JSeparator();
    private static JLabel productosLabel = new JLabel();
       
    // CONSTRUCTOR
    public InterfazTienda() {
        initComponents();
        setLocationRelativeTo(null);
    }

       
    // MÉTODOS -> INICIALIZADOR
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        refrescarTablas();
        
        //TablaProductos.setModel(setModeloTablaProductos(TiendaControlador.listarProductos(null)));
        jScrollPane1.setViewportView(TablaProductos);

        //TablaFabricantes.setModel(setModeloTablaFabricantes(TiendaControlador.listarFabricantes(null)));       
        jScrollPane2.setViewportView(TablaFabricantes);

        productosLabel.setText("PRODUCTOS");

        fabricantesLabel.setText("FABRICANTES");

        Menu.add(MenuCrearItem);
        MenuCrearItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear();
                refrescarTablas();
            }
        });
       
        Menu.add(MenuBorrarItem);
        MenuBorrarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
                refrescarTablas();
            }
        });

        Menu.add(MenuModificarItem);
        MenuModificarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
                refrescarTablas();
            }
        });

        Menu.add(MenuBuscarItem);
        MenuBuscarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar();
                refrescarTablas();
            }
        });

        setJMenuBar(Menu);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(productosLabel)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                                        .addComponent(jSeparator1, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                                        .addComponent(fabricantesLabel)
                                        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(productosLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fabricantesLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();

        TablaProductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        TablaFabricantes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    }                    

    // MÉTODOS -> ACTUALIZACIÓN TABLAS
    private void refrescarTablas() {
        // Listados con todos los fabricantes y productos:
        List<Fabricante> fabricantes = TiendaControlador.listarFabricantes(null);
        List<Producto> productos = TiendaControlador.listarProductos(null);

        // Creación de los modelos:
        DefaultTableModel modeloProductos = setModeloTablaProductos(productos);
        DefaultTableModel modeloFabricantes = setModeloTablaFabricantes(fabricantes);

        // Asignación de los modelos a las tablas:
        TablaProductos.setModel(modeloProductos);
        TablaFabricantes.setModel(modeloFabricantes);
    }
    
    private DefaultTableModel setModeloTablaProductos(List<Producto> listado){
        DefaultTableModel modeloTablaProductos = new DefaultTableModel();
        modeloTablaProductos.setRowCount(0);
        modeloTablaProductos.setColumnCount(0);

        // Columnas:
        for (Object columna : new Object[]{"Código", "Nombre", "Precio", "Fabricante"}) {
            modeloTablaProductos.addColumn(columna);
        }

        // Filas:
        for (Producto producto : listado) {
            Object[] fila = {producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getCodigo_fabricante()};
            modeloTablaProductos.addRow(fila);
        }
        
        return modeloTablaProductos;
    }
    
    private DefaultTableModel setModeloTablaFabricantes(List<Fabricante> listado){
        DefaultTableModel modeloTablaFabricantes = new DefaultTableModel();
        modeloTablaFabricantes.setRowCount(0);
        modeloTablaFabricantes.setColumnCount(0);

        // Columnas:
        for (Object columna : new Object[]{"Código", "Fabricante"}) {
            modeloTablaFabricantes.addColumn(columna);
        }

        // Filas:
        for (Fabricante producto : listado) {
            Object[] fila = {producto.getCodigo(), producto.getNombre()};
            modeloTablaFabricantes.addRow(fila);
        }
        
        return modeloTablaFabricantes;
    }
    
    
    // MÉTODOS -> OPERACIONES 
    /* Nota: Averiguar cómo hacer una operación global que realice el mismo procedimiento para cada una de las operaciones CRUD.
    y que reciba como parámetros otras funciones.*/
    
    // CREAR
    private void crear(){
        Integer opcionSeleccionada = seleccionarOpcion(new String[]{"Crear producto", "Crear fabricante"}, "Crear");
        
        String mensaje = null;
        
        /* Al hacer a la opción Integer se puede determinar si el usuario ha apretado la x (= null)
        y por tanto permitirle salir, cosa que con datos primitivos no es posible.*/
        if (opcionSeleccionada == null){
            return;
        }
        
        switch (opcionSeleccionada){
            
            case 0:
                mensaje = crearProducto(mensaje);
                if (mensaje != null)
                    JOptionPane.showMessageDialog(null, mensaje);
                break;
            case 1:
                mensaje =  crearFabricante(mensaje);
                if (mensaje != null)
                    JOptionPane.showMessageDialog(null, mensaje);
                break;  
        }
                
    }
    
    private String crearProducto(String mensaje) {
        try {
            
            String nombre = ingresarNombre("Ingrese el nombre del producto: ");
            Double precio = ingresarPrecio("Ingrese el precio del producto: ");
            Object fabricante = ingresarFabricante();
            
            mensaje = TiendaControlador.crearProducto(nombre, precio, fabricante);

        } catch (NumberFormatException e) {
            mensaje = "Se ha ingresado un precio inválido";
        } catch (InputMismatchException e){
            mensaje = e.getMessage();
        } catch (NullPointerException e) {
            cancel();
        } finally {
            return mensaje;
        }   
    }
    
    private String crearFabricante(String mensaje) {
        try {
            
            String nombre = ingresarNombre("Ingrese el nombre del fabricante: ");
            mensaje = TiendaControlador.crearFabricante(nombre);

        } catch (InputMismatchException e){
            mensaje = e.getMessage();
        } catch (NullPointerException e) {
            cancel();
        } finally {
            return mensaje;
        }   
    }
    
    
    // ACTUALIZAR
    private void actualizar(){
        Integer opcionSeleccionada = seleccionarOpcion(new String[]{"Actualizar producto", "Actualizar fabricante"}, "Actualizar");
        
        String mensaje = null;
        
        /* Al hacer a la opción Integer se puede determinar si el usuario ha apretado la x (= null)
        y por tanto permitirle salir, cosa que con datos primitivos no es posible.*/
        if (opcionSeleccionada == null)
            return;

        switch (opcionSeleccionada){
            
            case 0:
                mensaje = actualizarProducto(mensaje);
                if (mensaje != null)
                    JOptionPane.showMessageDialog(null, mensaje);
                break;
                
            case 1:
                mensaje = actualizarFabricante(mensaje);
                if (mensaje != null)
                    JOptionPane.showMessageDialog(null, mensaje);
                break;
                
        }
    }
    
    private String actualizarProducto(String mensaje) {
        Producto producto;
        try {
            // Búsqueda del producto proporcionado:
            String nombre = ingresarNombre("Ingrese el nombre del producto a modificar: ");
            producto = TiendaControlador.buscarProducto(nombre);
            
            if (producto == null)
                throw new NoSuchElementException("No se ha encontrado el producto proporcionado.");
            
            nombre = ingresarNombre("Ingrese el nombre del producto: ", producto.getNombre());
            
            Producto productoExistente = TiendaControlador.buscarProducto(nombre);
            
            if (productoExistente != null && productoExistente.equals(producto))
                // Se comprueba que el nuevo nombre proporcionado no sea igual al de otro producto, se ignora si es igual al original:
                throw new InputMismatchException("Ya existe un producto con el nombre proporcionado");
            
            Double precio = ingresarPrecio("Ingrese el precio del producto: ", producto.getPrecio());
            Object fabricante = ingresarFabricante((Object) TiendaControlador.buscarFabricante(producto.getCodigo_fabricante()));

            // Actualzación del producto encontrado:
            mensaje = TiendaControlador.actualizarProducto(nombre, precio, fabricante, producto.getCodigo());
            
        } catch (NumberFormatException e) {
            mensaje = "Se ha ingresado un precio inválido";
        } catch (InputMismatchException e) {
            mensaje = e.getMessage();
        } catch (NoSuchElementException e) {
            mensaje = e.getMessage();
        } catch (NullPointerException e) {
            cancel();
        } finally {
            return mensaje;
        }
    }
    
    private String actualizarFabricante(String mensaje) {
        Fabricante fabricante;
        try {
            // Búsqueda del fabricante proporcionado:
            String nombre = ingresarNombre("Ingrese el nombre del fabricante a modificar: ");
            fabricante = TiendaControlador.buscarFabricante(nombre);
            
            if (fabricante == null)
                throw new NoSuchElementException("No se ha encontrado el fabricante proporcionado.");
            
            nombre = ingresarNombre("Ingrese el nombre del fabricante: ", fabricante.getNombre());
            Producto fabricanteExistente = TiendaControlador.buscarProducto(nombre);
            
            if (fabricanteExistente != null && fabricanteExistente.equals(fabricante))
                // Se comprueba que el nuevo nombre proporcionado no sea igual al de otro fabricante, se ignora si es igual al original:
                throw new InputMismatchException("Ya existe un  fabricante con el nombre proporcionado");
            
             // Actualzación del fabricante encontrado:
            mensaje = TiendaControlador.actualizarFabricante(nombre, fabricante.getCodigo());
            
        } catch (InputMismatchException e) {
            mensaje = e.getMessage();
        } catch (NoSuchElementException e) {
            mensaje = e.getMessage();
        } catch (NullPointerException e) {
            cancel();
        } finally {
            return mensaje;
        }
    }
    
    
    // ELIMINAR
    private void eliminar(){
        Integer opcionSeleccionada = seleccionarOpcion(new String[]{"Eliminar producto", "Eliminar fabricante"}, "Eliminar");
       
        String mensaje = null;
        
        /* Al hacer a la opción Integer se puede determinar si el usuario ha apretado la x (= null)
        y por tanto permitirle salir, cosa que con datos primitivos no es posible.*/
        if (opcionSeleccionada == null)
            return;

        switch (opcionSeleccionada){
            
            case 0:
                mensaje = eliminarProducto(mensaje);
                if (mensaje != null)
                    JOptionPane.showMessageDialog(null, mensaje);
                break;
                
            case 1:
                mensaje = eliminarFabricante(mensaje);
                if (mensaje != null)
                    JOptionPane.showMessageDialog(null, mensaje);
                break;
                
        }
    }
    
    
    private String eliminarProducto(String mensaje) {
        Producto producto;
        try {
            String nombre = ingresarNombre("Ingrese el nombre del producto a eliminar: ");
            producto = TiendaControlador.buscarProducto(nombre);
            
            if (producto == null)
                throw new NoSuchElementException("No se ha encontrado el producto proporcionado.");
            
            mensaje = TiendaControlador.eliminarProducto(producto);
            
        } catch (InputMismatchException e) {
            mensaje = e.getMessage();
        } catch (NoSuchElementException e) {
            mensaje = e.getMessage();
        } catch (NullPointerException e) {
            cancel();
        } finally {
            return mensaje;
        }
    }
    
    private String eliminarFabricante(String mensaje) {
        Fabricante fabricante;
        try {
            String nombre = ingresarNombre("Ingrese el nombre del fabricante a eliminar: ");
            fabricante = TiendaControlador.buscarFabricante(nombre);
            
            if (fabricante == null)
                throw new NoSuchElementException("No se ha encontrado el fabricante proporcionado.");
            
            mensaje = TiendaControlador.eliminarFabricante(fabricante);
            
        } catch (InputMismatchException e) {
            mensaje = e.getMessage();
        } catch (NoSuchElementException e) {
            mensaje = e.getMessage();
        } catch (NullPointerException e) {
            cancel();
        } finally {
            return mensaje;
        }
    }
    
    
    // BUSCAR
    private void buscar(){
        Integer opcionSeleccionada = seleccionarOpcion(new String[]{"Buscar producto", "Buscar fabricante"}, "Buscar");
       
        String mensaje = null;
        
        /* Al hacer a la opción Integer se puede determinar si el usuario ha apretado la x (= null)
        y por tanto permitirle salir, cosa que con datos primitivos no es posible.*/
        if (opcionSeleccionada == null)
            return;

        switch (opcionSeleccionada){
            
            case 0:
                mensaje = buscarProducto(mensaje);
                if (mensaje != null)
                    JOptionPane.showMessageDialog(null, mensaje);
                break;
                
            case 1:
                mensaje = buscarFabricante(mensaje);
                if (mensaje != null)
                    JOptionPane.showMessageDialog(null, mensaje);
                break;
                
        }
    }
    
    private String buscarFabricante(String mensaje) {
        List<Fabricante> fabricantes = null;
        try {
            String nombre = ingresarNombre("Ingrese el nombre del fabricante a buscar: ");
            fabricantes = TiendaControlador.listarFabricantes(nombre);
            
            if (fabricantes == null) {
                throw new NoSuchElementException("No se han encontrado coincidencias.");
            }

            StringBuilder builder = new StringBuilder();
            for (Fabricante fabricante : fabricantes) {
                builder.append(fabricante.toString()).append("\n");
            }

            // Muestra el resultado en un JOptionPane
            JOptionPane.showMessageDialog(null, builder.toString(), "Listado de Fabricantes", JOptionPane.INFORMATION_MESSAGE);

        } catch (InputMismatchException e) {
            mensaje = e.getMessage();
        } catch (NoSuchElementException e) {
            mensaje = e.getMessage();
        } catch (NullPointerException e) {
            cancel();
        } finally {
            return mensaje;
        }
    }
    
    private String buscarProducto(String mensaje) {
        List<Producto> productos;
        try {
            String nombre = ingresarNombre("Ingrese el nombre del producto a buscar: ");
            productos = TiendaControlador.listarProductos(nombre);
            
            
            if (productos == null) {
                throw new NoSuchElementException("No se han encontrado coincidencias.");
            }

            StringBuilder builder = new StringBuilder();
            for (Producto producto : productos) {
                builder.append(producto.toString()).append("\n");
            }

            JOptionPane.showMessageDialog(null, builder.toString(), "Listado de Productos", JOptionPane.INFORMATION_MESSAGE);

        } catch (InputMismatchException e) {
            mensaje = e.getMessage();
        } catch (NoSuchElementException e) {
            mensaje = e.getMessage();
        } catch (NullPointerException e) {
            cancel();
        } finally {
            return mensaje;
        }
    }
    
    
    // CANCELAR
    private void cancel() {
        String[] opciones = {"Menú", "Salir"};
        Integer opcion = JOptionPane.showOptionDialog(null, "¿Volver al menú?", "Operación cancelada",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        
        if (opcion == 0 || opcion == -1)
            return;
        else 
            this.dispose();
    }
    
    
    
    // MÉTODOS: OPERACIONES COMPLEMENTARIAS (INGRESO DE DATOS Y OPCIONES)
    private Integer seleccionarOpcion(String[] opciones, String title){
        Integer opcionSeleccionada = JOptionPane.showOptionDialog(null, 
                "Escoge una opción: ",  title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opciones, null);
        
        return opcionSeleccionada;
    }
    
    private String ingresarNombre(String mensaje, String nombreDefecto){
        String nombre = (String) JOptionPane.showInputDialog(null, mensaje, "", 
                JOptionPane.PLAIN_MESSAGE, null, null, nombreDefecto);
        nombre = nombre.trim();
        if (nombre == null) 
                throw new NullPointerException();
        if (nombre.isEmpty()) 
            throw new InputMismatchException("Se han ingresado datos inválidos.");
        return nombre;
    }
    
    private String ingresarNombre(String mensaje){
        String nombre = JOptionPane.showInputDialog(null, mensaje).trim();
        if (nombre == null) 
                throw new NullPointerException();
        if (nombre.isEmpty()) 
            throw new InputMismatchException("Se han ingresado datos inválidos.");
        return nombre;
    }
    
    private Double ingresarPrecio(String mensaje){
        Double precio = Double.parseDouble(JOptionPane.showInputDialog(null, mensaje));
        return precio;
    }
    
    private Double ingresarPrecio(String mensaje, Double precioDefecto){
        Double precio = Double.parseDouble( (String) JOptionPane.showInputDialog(null, mensaje, "", 
                JOptionPane.PLAIN_MESSAGE, null, null, precioDefecto));
        return precio;
    }
    
    private Object ingresarFabricante(){
        Object[] fabricantes = TiendaControlador.listarFabricantes(null).toArray();
        
        Object fabricante = JOptionPane.showInputDialog(null, null, "Seleccione un fabricante: ",
                    JOptionPane.QUESTION_MESSAGE, null, fabricantes, fabricantes[0]);
        
        if (fabricante == null) 
                throw new NullPointerException();
        return fabricante;
    }
    
    private Object ingresarFabricante(Object fabricanteDefecto){
        Object[] fabricantes = TiendaControlador.listarFabricantes(null).toArray();
        
        Object fabricante = JOptionPane.showInputDialog(null, null, "Seleccione un fabricante: ",
                    JOptionPane.QUESTION_MESSAGE, null, fabricantes, fabricanteDefecto);
        
        if (fabricante == null) 
                throw new NullPointerException();
        return fabricante;
    }
}

