import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Inventario {
    App app;
    JFrame ventana;

    public Inventario(App app){
        this.app = app;
        this.ventana = app;
        
    }
    int u = 0;//IMPORTANTE...Es el número de productos que hay

    
    //Variables de inventario para verificacion - nombres
    String[] nombres = new String[100];
    String[] preciosstr = new String[100];
    String[] stocksstr = new String[100];
    String[] codigosstr = new String[100];

    //Variables despues de verificacion de null y espacios vacios
    double[] precios = new double[100];
    int[] stocks = new int[100];
    int[] codigos = new int[100];
    public void verProductos(){//Función para ver los prouctos del inventario
        String mensaje = "Nombre \t  Precio \t  Stock \t  Codigo\n";
        for(int i = 0; i < u; i++){
            mensaje += nombres[i] + "\t  " + precios[i] + "\t  " + stocks[i] + "\t       " + codigos[i] + "\n"; 
        }
        JOptionPane.showMessageDialog(ventana, mensaje);
    }
    public void agregarProductos(){//Función para agregar productos en el inventario usando arrays
        while (true) {
            nombres[u] = JOptionPane.showInputDialog("Escriba El Nombre Del Producto Número " + (u + 1));

            if(nombres[u] == null){
                JOptionPane.showMessageDialog(ventana, "Cancelaste agregar el producto " + (u + 1));
                return;
            }//Aquí es para si el usuario da cancelar la variable no quede con el String "null"
            if(nombres[u].trim().isEmpty()){JOptionPane.showMessageDialog(ventana, "No Puede Dejar El Nombre Vacío"); continue;}//Para cuando el usuario deje el nombre vacio

            break;
        }

        while (true) {
            preciosstr[u] = JOptionPane.showInputDialog("Escriba El Precio De " + nombres[u]);
            if(preciosstr[u] == null){JOptionPane.showMessageDialog(ventana, "Canceló Agregar Precio Al Producto " + nombres[u]);return;}
            if(preciosstr[u].trim().isEmpty()){
                JOptionPane.showMessageDialog(ventana, "No Puede Dejar El Precio Vacío");
                continue;
            }
            try{
            precios [u] = Double.parseDouble(preciosstr[u]);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
            continue;//Hace que se repita el bucle while(true){....}
        }
            if(precios[u] <= 100){
                JOptionPane.showMessageDialog(ventana, "El Producto No Puede Valer Menos De 100$");
                continue;
            }
            break;

        }
        while (true) {
            stocksstr[u] = JOptionPane.showInputDialog("Escriba El Stock De " + nombres[u]);
            if(stocksstr[u] == null){JOptionPane.showMessageDialog(ventana, "Canceló Agregar Stock Al Producto " + nombres[u]);return;}
            if(stocksstr[u].trim().isEmpty()){
                JOptionPane.showMessageDialog(ventana, "No Puede Dejar El Stock Vacío");
                continue;
            }
            try{
            stocks [u] = Integer.parseInt(stocksstr[u]);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
            continue;//Hace que se repita el bucle while(true){....}
        }
            if(stocks[u] <= 0){
                JOptionPane.showMessageDialog(ventana, "No Puede Poner Stock Menor Ni igual A Cero");
                continue;
            }
            break;

        }
        while (true) {
            codigosstr[u] = JOptionPane.showInputDialog("Escriba El Codigo De " + nombres[u] + "\nEsto Le Servira Para Encontrar Productos De Una Manera Más Eficiente Y Registrar Ventas Por Medio De Codigos");
            if(codigosstr[u] == null){JOptionPane.showMessageDialog(ventana, "Canceló Agregar Codigo Al Producto " + nombres[u]);return;}
            if(codigosstr[u].trim().isEmpty()){
                JOptionPane.showMessageDialog(ventana, "No Puede Dejar El Stock Vacío");
                continue;
            }
            if(codigosstr[u].length() <= 3){
                JOptionPane.showMessageDialog(ventana, "El Codigo Debe Tener Mas De Tres Digitos");
                continue;
            }

            try{
            codigos [u] = Integer.parseInt(codigosstr[u]);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
            continue;//Hace que se repita el bucle while(true){....}
        }
        break;

        }
        u++;
        Datos.guardarInventario(this);
    }
public void buscarProductoXNombre() {
    int stockAnterior = 0;
    int iterador = 0;
    boolean encontrado = false;

    String productoBuscado = JOptionPane.showInputDialog(ventana, "Escriba El Nombre Exacto Del Producto A Modificar Stock");

    if (productoBuscado == null) return; // ✅ usuario canceló
    if (productoBuscado.trim().isEmpty()) {
        JOptionPane.showMessageDialog(ventana, "El Campo No Puede Estar Vacío");
        return;
    }

    for (int i = 0; i < u; i++) {
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

        if (productoBuscadostr == null) return; // ✅ cancela limpiamente

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
            break; // ✅ input válido, salir del bucle
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
        }
    }

    // Búsqueda en el array (igual que antes)
    for (int i = 0; i < u; i++) {
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
    
public void inventario(){//funcion menu inventario
        String[] botonesMenuInventario = {"Ver Inventario", "Agregar Producto", "Agregar Stock", "Menú Principal", "Salir"};
        int opcionMenuInventario = JOptionPane.showOptionDialog(ventana, "Productos E Inventario", "Menu Inventario", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMenuInventario, botonesMenuInventario[0]);
        try {
            
            switch (opcionMenuInventario) {
            case -1:
                return;
            case 0:
                if(u == 0){
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
                return;
            case 4:
                System.exit(0);
            default:
                JOptionPane.showMessageDialog(ventana, "Opción no disponible");
                break;
        }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}