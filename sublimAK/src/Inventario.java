import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class Inventario {
    App app;
    JFrame ventana;

    public Inventario(App app){
        this.app = app;
        this.ventana = app;
        
    }
    int totalProductos = 0;//IMPORTANTE...Es el número de productos que hay

    
    //Variables de inventario para verificacion - nombres
    String[] nombres = new String[100];
    String[] preciosstr = new String[100];
    String[] stocksstr = new String[100];
    String[] codigosstr = new String[100];

    //Variables despues de verificacion de null y espacios vacios
    double[] precios = new double[100];
    int[] stocks = new int[100];
    int[] codigos = new int[100];
public void verProductos() {
    String mensaje = String.format("%-20s %-10s %-8s %-10s%n", "Nombre", "Precio", "Stock", "Codigo");
    for (int i = 0; i < totalProductos; i++) {
        mensaje += String.format("%-20s %-10.2f %-8d %-10d%n", nombres[i], precios[i], stocks[i], codigos[i]);
    }
    JTextArea area = new JTextArea(mensaje);
    area.setFont(new Font("Monospaced", Font.PLAIN, 14));
    area.setEditable(false);
    JOptionPane.showMessageDialog(ventana, area);
}
    

    public boolean verificadorEntradasDeDatos(String []variableAVerificar, String tipoInventario){
        while (true) {
            
        variableAVerificar[totalProductos] = JOptionPane.showInputDialog("Escriba El " + tipoInventario + " Del Producto " + (totalProductos + 1));
        if(variableAVerificar[totalProductos] == null){
                JOptionPane.showMessageDialog(ventana, "Cancelaste agregar el producto " + (totalProductos + 1));
                return false;
        }//Aquí es para si el usuario da cancelar la variable no quede con el String "null"
        if(variableAVerificar[totalProductos].trim().isEmpty()){
                JOptionPane.showMessageDialog(ventana, "No Puede Dejar El Campo Vacío"); 
                continue;
        }//Si el usuario deja el input vacío
            if(tipoInventario.equals("Nombre")){
         variableAVerificar [totalProductos] = variableAVerificar[totalProductos];  
         break; 
        }else if(tipoInventario.equals("Precio")){
            try{
            precios [totalProductos] = Double.parseDouble(preciosstr[totalProductos]);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
            continue;//Hace que se repita el bucle while(true){....}
        }
            if(precios[totalProductos] <= 100){
                JOptionPane.showMessageDialog(ventana, "El Producto No Puede Valer Menos De 100$");
                continue;
            }
            break;
        
        }else if(tipoInventario.equals("Stock")){
            try{
            stocks [totalProductos] = Integer.parseInt(stocksstr[totalProductos]);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
            continue;//Hace que se repita el bucle while(true){....}
        }
            if(stocks[totalProductos] <= 0){
                JOptionPane.showMessageDialog(ventana, "No Puede Poner Stock Menor Ni igual A Cero");
                continue;
            }
            break;
        }else if(tipoInventario.equals("Codigo")){
                        if(codigosstr[totalProductos].length() <= 3){
                JOptionPane.showMessageDialog(ventana, "El Codigo Debe Tener Mas De Tres Digitos");
                continue;
            }

            try{
            codigos [totalProductos] = Integer.parseInt(codigosstr[totalProductos]);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
            continue;//Hace que se repita el bucle while(true){....}
        }
        break;
        }else{
            System.out.println("Parametros de la función incorrectos");
        }
    }
    return true;



}
    public void agregarProductos(){//Función para agregar productos en el inventario usando arrays
        if(!verificadorEntradasDeDatos(nombres, "Nombre")) return;
        if(!verificadorEntradasDeDatos(preciosstr, "Precio")) return;
        if(!verificadorEntradasDeDatos(stocksstr, "Stock"))return;
        if(!verificadorEntradasDeDatos(codigosstr, "Codigo"))return;
        totalProductos++;
        Datos.guardarInventario(this);
    }
public void buscarProductoXNombre() {
    int stockAnterior = 0;
    int iterador = 0;
    boolean encontrado = false;

    String productoBuscado = JOptionPane.showInputDialog(ventana, "Escriba El Nombre Exacto Del Producto A Modificar Stock");

    if (productoBuscado == null) return;
    if (productoBuscado.trim().isEmpty()) {
        JOptionPane.showMessageDialog(ventana, "El Campo No Puede Estar Vacío");
        return;
    }

    for (int i = 0; i < totalProductos; i++) {
        if (productoBuscado.equals(nombres[i])) {
            iterador = i;
            stockAnterior = stocks[iterador];
            encontrado = true;
            break;
        }
    }
        if(!encontrado){
            JOptionPane.showMessageDialog(ventana, productoBuscado + "No Esta Registrado Aún");
            agregarProductos();
            return;
        }
        while (true) {
            stocksstr[iterador] = JOptionPane.showInputDialog("Escriba El Stock Que Va A Añadir A " + nombres[iterador]);
            if(stocksstr[iterador] == null){JOptionPane.showMessageDialog(ventana, "Canceló Agregar Stock Al Producto " + nombres[iterador]);
            return;}
            if(stocksstr[iterador].trim().isEmpty()){
                JOptionPane.showMessageDialog(ventana, "No Puede Dejar El Stock Que Va A Agregar Vacío");
                continue;
            }
            try{
            stocks [iterador] = Integer.parseInt(stocksstr[iterador]);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
            continue;//Hace que se repita el bucle while(true){....}
        }
            if(stocks[iterador] <= 0){
                JOptionPane.showMessageDialog(ventana, "No Puede Agregar Stock Menor Ni igual A Cero");
                continue;
            }
            break;

        }
        stocks[iterador] += stockAnterior;
        Datos.guardarInventario(this);
    }
public void buscarProductoXCodigo() {
    int productoBuscado = 0;
    int iterador = -1;
    int stockAnterior = 0;
    boolean encontrado = false;
    String productoBuscadostr;

    while (true) {
        productoBuscadostr = JOptionPane.showInputDialog("Escriba El Codigo Exacto Del Producto A Modificar Stock"); // ✅ dentro del bucle

        if (productoBuscadostr == null) return; //cancela limpiamente

        if (productoBuscadostr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "No Puede Dejar Este Campo Vacío");
            continue;
        }
        if (productoBuscadostr.length() < 4) {
            JOptionPane.showMessageDialog(ventana, "Los Codigos Son De 4 Digitos O Más");
            continue;
        }
        try {
            productoBuscado = Integer.parseInt(productoBuscadostr);
            break; //input válido, salir del bucle
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
        }
    }

    // Búsqueda en el array (igual que antes)
    for (int i = 0; i < totalProductos; i++) {
        if (productoBuscado == codigos[i]) {
            iterador = i;
            encontrado = true;
            break;
        }
    }

    if (!encontrado) {
        JOptionPane.showMessageDialog(ventana, "El Producto No Está Registrado");
        return;
    }

    stockAnterior = stocks[iterador];
        while (true) {
            stocksstr[iterador] = JOptionPane.showInputDialog("Escriba El Stock Que Va A Añadir A " + nombres[iterador]);
            if(stocksstr[iterador] == null){JOptionPane.showMessageDialog(ventana, "Canceló Agregar Stock Al Producto " + nombres[iterador]);
            return;}
            if(stocksstr[iterador].trim().isEmpty()){
                JOptionPane.showMessageDialog(ventana, "No Puede Dejar El Stock Que Va A Agregar Vacío");
                continue;
            }
            try{
            stocks [iterador] = Integer.parseInt(stocksstr[iterador]);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
            continue;//Hace que se repita el bucle while(true){....}
        }
            if(stocks[iterador] <= 0){
                JOptionPane.showMessageDialog(ventana, "No Puede Agregar Stock Menor Ni Igual A Cero");
                continue;
            }
            break;

        }
        stocks[iterador] += stockAnterior;
        Datos.guardarInventario(this); 
    }
    public void agregarStock(){
        String [] botonesAgregarStock = {"Buscar Producto Por Nombre", "Buscar Producto Por Codigo", "Volver"};
        int OpcionAgregarStock = JOptionPane.showOptionDialog(ventana, "¿Que Metodo De Busqueda Quiere Realizar?", "Agregar Stock", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesAgregarStock, botonesAgregarStock[0]);
        switch (OpcionAgregarStock) {
            case -1:
                return;
            case 0:
                buscarProductoXNombre();
                break;
            case 1:
                buscarProductoXCodigo();
                break;
            case 2:
                return;
            default:
                break;
        }
    }
    public void editarProducto(){
        String productoAModificar = "";
        while (true) {
        productoAModificar = JOptionPane.showInputDialog("Escriba El Nombre Del Producto A Modificar");
        if(productoAModificar == null){return;}
        if(productoAModificar.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío");continue;}  
        break;
        }
        int productoAModificarEncontrado = app.buscador.buscarProductoXNombre(productoAModificar);
        if(productoAModificarEncontrado ==  -1){JOptionPane.showMessageDialog(app, "Producto No Encontrado En El Inventario");return;}

        String[] botonesModificarInventario = {"Nombre", "Precio", "Stock", "Codigo", "Volver"};
        int opcionModificarInventario = JOptionPane.showOptionDialog(ventana, "¿Qué Desea Modificar?", "Menu Inventario", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesModificarInventario, botonesModificarInventario[0]);
        
        switch (opcionModificarInventario) {
            case -1,4:
                return;
            case 0:
                while (true) {
                    String nuevoNombre = JOptionPane.showInputDialog("Escriba El Nuevo Nombre Para El Producto " + nombres[productoAModificarEncontrado]);
                    if(nuevoNombre == null){return;}
                    if(nuevoNombre.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacio"); continue;}

                    nombres[productoAModificarEncontrado] = nuevoNombre;
                    break;
                }
                Datos.guardarInventario(this);
                break;
            case 1:
                while (true) {
                String nuevoPreciostr = JOptionPane.showInputDialog("Escriba El Nuevo Precio Del Producto " + nombres[productoAModificarEncontrado]);
                if(nuevoPreciostr == null){return;}
                if(nuevoPreciostr.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacio"); continue;} 
                try {
                    double nuevoPrecio = Double.parseDouble(nuevoPreciostr);
                    precios[productoAModificarEncontrado] = nuevoPrecio;
                    Datos.guardarInventario(this);
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(app, "Escriba Solo Números");
                }  
                }
                break;
            case 2:
                while (true) {
                String nuevoStockstr = JOptionPane.showInputDialog("Escriba El Nuevo Stock Del Producto " + nombres[productoAModificarEncontrado]);
                if(nuevoStockstr == null){return;}
                if(nuevoStockstr.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacio"); continue;} 
                try {
                    int nuevoStock = Integer.parseInt(nuevoStockstr);
                    stocks[productoAModificarEncontrado] = nuevoStock;
                    Datos.guardarInventario(this);
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(app, "Escriba Solo Números");
                }  
                }
                break;
            case 3:
                while (true) {
                String nuevoCodigostr = JOptionPane.showInputDialog("Escriba El Nuevo Codigo Del Producto " + nombres[productoAModificarEncontrado]);
                if(nuevoCodigostr == null){return;}
                if(nuevoCodigostr.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacio"); continue;} 
                try {
                    int nuevoCodigo = Integer.parseInt(nuevoCodigostr);
                    codigos[productoAModificarEncontrado] = nuevoCodigo;
                    Datos.guardarInventario(this);
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(app, "Escriba Solo Números");
                }  
                }
                break;
        }

    }

    public void eliminarProducto(){
        if(totalProductos == 0){JOptionPane.showMessageDialog(ventana, "No Hay Productos Registrados En El Inventario");return;}
        String productoAEliminar = "";
        while (true) {
        productoAEliminar = JOptionPane.showInputDialog("Escriba El Nombre Del Producto A Modificar");
        if(productoAEliminar == null){return;}
        if(productoAEliminar.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío");continue;}  
        break;
        }
        int productoAModificarEncontrado = app.buscador.buscarProductoXNombre(productoAEliminar);
        if(productoAModificarEncontrado ==  -1){JOptionPane.showMessageDialog(app, "Producto No Encontrado En El Inventario");return;}  

        int confirmacionEliminarProducto = JOptionPane.showConfirmDialog(ventana, "¿Esta Seguro Que Desea Eliminar " + nombres[productoAModificarEncontrado] + "? Del Inventario, Esta Opcion No Se Puede Deshacer", "¿Confirme La ELiminación?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(confirmacionEliminarProducto != JOptionPane.YES_OPTION){return;}

        // Desplazar todos los arreglos una posición hacia la izquierda
    for(int i = productoAModificarEncontrado; i < totalProductos - 1; i++){
        nombres[i]     = nombres[i + 1];
        preciosstr[i]  = preciosstr[i + 1];
        stocksstr[i]   = stocksstr[i + 1];
        codigosstr[i]  = codigosstr[i + 1];
        precios[i]     = precios[i + 1];
        stocks[i]      = stocks[i + 1];
        codigos[i]     = codigos[i + 1];
    }

    // Limpiar el último hueco
    totalProductos--;
    nombres[totalProductos]    = null;
    preciosstr[totalProductos] = null;
    stocksstr[totalProductos]  = null;
    codigosstr[totalProductos] = null;
    precios[totalProductos]    = 0;
    stocks[totalProductos]     = 0;
    codigos[totalProductos]    = 0;

    Datos.guardarInventario(this);
    JOptionPane.showMessageDialog(app, "Producto Eliminado Correctamente");
    }

    public void menuModificarInventario(){
        String[] botonesModificarInventario = {"Editar Producto", "Eliminar Producto", "Volver"};
        int opcionModificarInventario = JOptionPane.showOptionDialog(ventana, "¿Que Desea?", "Menu Modificar Inventario", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesModificarInventario, botonesModificarInventario[0]);

        switch (opcionModificarInventario) {
            case -1, 2:
                return;
            case 0:
                editarProducto();
                break;
            case 1:
                eliminarProducto();
        }
    }
public void inventario(){//funcion menu inventario
        String[] botonesMenuInventario = {"Ver Inventario", "Agregar Producto", "Agregar Stock", "Modificar Inventario","Menú Principal", "Salir"};
        int opcionMenuInventario = JOptionPane.showOptionDialog(ventana, "Productos E Inventario", "Menu Inventario", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMenuInventario, botonesMenuInventario[0]);
        try {
            
            switch (opcionMenuInventario) {
            case -1:
                return;
            case 0:
                if(totalProductos == 0){
                JOptionPane.showMessageDialog(ventana, "Sin Productos Registrados Aún"); 
                return;
            }else{verProductos();}
                break;
            case 1:
                agregarProductos();
                return;
            case 2:
                agregarStock();
                break;
            case 3:
                menuModificarInventario();
                break;
            case 4:
                return;
            case 5:
                System.exit(0);
        }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}