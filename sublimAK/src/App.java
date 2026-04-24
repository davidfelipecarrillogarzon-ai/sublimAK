import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App extends JFrame{

    JFrame ventana;

    public App(){
    ventana = new JFrame("SublimAK");
    ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);//PARA PANTALLA COMPLETA AJUSTADA A MONITOR
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
    ventana.getContentPane().setBackground(Color.DARK_GRAY);//PARA PONER EL FRAME EN MODO OSCURO(SOLO APLICA PARA LA VENTANA)//PARA PONER EL FRAME EN MODO OSCURO(SOLO APLICA PARA LA VENTANA)
}

    ArrayList<Movimiento> historial = new ArrayList<>();
    int contadorMovimientos = 1;

    static int u = 0;

    //Variables de cuenta
    static float dinero = 0;
    static float dineroDigital= 0;
    static float dineroARetirar = 0;

    //Variables de inventario para verificacion - nombres
    static String[] nombres = new String[100];
    static String[] preciosstr = new String[100];
    static String[] stocksstr = new String[100];
    static String[] codigosstr = new String[100];

    //Variables despues de verificacion de null y espacios vacios
    static double[] precios = new double[100];
    static int[] stocks = new int[100];
    static int[] codigos = new int[100];


    class Movimiento{
        int id;
        String tipo;
        float monto;
        String fecha;

        public Movimiento(int id, String tipo, float monto){
            this.id = id;
            this.tipo = tipo;
            this.monto = monto;

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            this.fecha = LocalDateTime.now().format(formatoFecha);
        }

        //Lo siguiente es un CONSTRUCTOR no una funcion
        public String toString(){
            return "ID: " + id + " | " + tipo + " | $" + monto + " | " + fecha;
        }
    }

    public void mostrarHistorial(){
        if(contadorMovimientos == 1){
        JOptionPane.showMessageDialog(ventana, "No Hay Movimientos Registrados");
        menuCuentas();
        };
        String texto = "";
        for(Movimiento m : historial){

            texto += m.toString() + "\n";
        }
            JOptionPane.showMessageDialog(ventana, texto);
            menuCuentas();
    }

    public void sumarDinero(){//Suma de Dinero
                        int billeteraDeRetiro = 0;
                        float dineroASumar = 0;
                        String[] billeteraAMover= {"Efectivo", "Nequi", "Volver"};
                        try{
                        billeteraDeRetiro = JOptionPane.showOptionDialog(ventana, "¿Va A Realizar El Movimiento En Efectivo O Nequi?", "Menú Dinero", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, billeteraAMover, billeteraAMover[0]);
                        if(billeteraDeRetiro == 0){
                            dineroASumar = Float.parseFloat(JOptionPane.showInputDialog("Escriba El Dinero Que Va A Agregar"));
                        while (dineroASumar <1000) {
                            dineroASumar = Float.parseFloat(JOptionPane.showInputDialog("El Monto Que Desea Añadir A La Cuenta Es Incorrecto No Puede Agregar Menos De 1000$"));
                        }
                        dinero += dineroASumar;
                        historial.add(new Movimiento(contadorMovimientos++, "Deposito Efectivo", dineroASumar));
                        JOptionPane.showMessageDialog(ventana, "El Dinero En Efectivo Ahorrado Es " + dinero + "$");
                        menuCuentas();
                        }else if(billeteraDeRetiro == 1){
                            dineroASumar = Float.parseFloat(JOptionPane.showInputDialog("Escriba El Dinero Que Va A Agregar(Dinero Digital)."));
                        while (dineroASumar <1000) {
                            dineroASumar = Float.parseFloat(JOptionPane.showInputDialog("El Monto Que Desea Agregar Es Incorrecto No Puede Agregar Menos De 1000"));
                        }
                        dineroDigital += dineroASumar;

                        //Guardar movimiento 
                        historial.add(new Movimiento(contadorMovimientos++, "Deposito Nequi", dineroASumar));
                        JOptionPane.showMessageDialog(ventana, "El Dinero En Nequi Ahorrado Es " + dineroDigital + "$");
                        menuCuentas();
                        }else if(billeteraDeRetiro == -1){
                            menu();
                        }else{
                            menuCuentas();
                        }
                    }catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(ventana, "Pusó valores incorrectos.(Solo números)");
                        sumarDinero();
                    }catch(NullPointerException e){
                        JOptionPane.showMessageDialog(ventana, "Canceló la operación");
                        menuCuentas();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(ventana, "ERROR Desconocido");
                        System.out.println("Error en funcion sumarDinero(); dinero: " + e);
                        sumarDinero();
                    }
                        
    }
    public void restarDinero(){//Resta de dinero
                        int billeteraDeRetiro = 0;
                        String[] billeteraAMover= {"Efectivo", "Nequi", "Volver"};
                        billeteraDeRetiro = JOptionPane.showOptionDialog(ventana, "¿Va A Realizar El Movimiento En Efectivo O Nequi?", "Menú Dinero", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, billeteraAMover, billeteraAMover[0]);
                        try{
                        if(billeteraDeRetiro == 0){
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("Escriba El Dinero Que Va A Retirar"));
                        while (dineroARetirar <1000) {
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("No Puede Retirar Menos De 1000$"));
                        }while (dineroARetirar > dinero) {
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("El Monto De Dinero que Piensa Retirar Es Mayor Al Dinero Disponible En Su Cuenta\nDinero Disponible En Su Cuenta: " + dinero + "$"));
                        }
                        dinero -= dineroARetirar;
                        historial.add(new Movimiento(contadorMovimientos++, "Retiro Efectivo", dineroARetirar));
                        menuCuentas();
                        }else if (billeteraDeRetiro == 1){
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("Escriba El Dinero Que Va A Retirar (Dinero Digital)."));
                        while (dineroARetirar <1000) {
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("El monto que desea retirar es incorrecto no puede retirar menos de 1000 ni mas de " + dinero + "$"));
                        }while (dineroARetirar > dineroDigital) {
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("El Monto De Dinero que Piensa Retirar Es Mayor Al Dinero Disponible En Su Cuenta\nDinero Disponible En Su Cuenta Nequi es: " + dineroDigital + "$"));
                        }
                        dineroDigital -= dineroARetirar;
                        historial.add(new Movimiento(contadorMovimientos++, "Retiro Nequi", dineroARetirar));
                        menuCuentas();
                        }else if(billeteraDeRetiro == -1){
                            menu();
                        }else if(billeteraDeRetiro == 2){
                            menuCuentas();
                        }
                    }catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(ventana, "Pusó valores incorrectos.(Solo números)");
                        restarDinero();
                    }catch(NullPointerException e){
                        JOptionPane.showMessageDialog(ventana, "Canceló la operación");
                        menuCuentas();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(ventana, "ERROR Desconocido");
                        System.out.println("Error en funcion restarDinero(); dinero: " + e);
                        restarDinero();
                    }
    }
    public void menuCuentas(){//Función para el menu de cuenta
        String[] botonesDinero = {"Ver total", "Restar Dinero A La Cuenta", "Sumar Dinero A La Cuenta", "Ver Movimientos", "Retroceder Al Menú Principal", "Salir"};
        int opcionMenuCuentas = JOptionPane.showOptionDialog(ventana, "¿Que desea hacer?", "Menú Dinero", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesDinero, botonesDinero[0]);
        switch (opcionMenuCuentas) {
            case -1:
                menu();
                break;
            case 0:
                JOptionPane.showMessageDialog(ventana, "Su Dinero Total Es: " + (dinero + dineroDigital) + "$\nDinero en efectivo: " + dinero + "$" + "\nDinero en Nequi: " + dineroDigital + "$");
                menuCuentas();
                break;
            case 1:
                restarDinero();
                break;
            case 2:
                sumarDinero();
                break;
            case 3:
                mostrarHistorial();
                break;
            case 4:
                menu();
                break;
            case 5:
                System.exit(0);
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
                menu();
                break;
            case 0:
                if(u == 0){JOptionPane.showMessageDialog(ventana, "Sin Productos Registrados Aún"); inventario();}else{verProductos();}
                break;
            case 1:
                agregarProductos();
                inventario();
                break;
            case 2:
                System.out.print("Aqui va agregar stock");
                break;
            case 3:
                menu();
                break;
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
    public void verProductos(){//Función para ver los prouctos del inventario
        String mensaje = "Nombre \t  Precio \t  Stock \t  Codigo\n";
        for(int i = 0; i < nombres.length; i++){
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
            while(codigosstr[u].trim().isEmpty()){
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
    }
    public void registrarventa(){//Función para registrar las ventas
        String buscarProducto = JOptionPane.showInputDialog("Escriba El Nombre Del Producto");
        if(nombres[0] == null){
          JOptionPane.showMessageDialog(ventana, "No Hay " + buscarProducto + " Registrado En El Inventario");
          menu();
        }
        for(int i = 0; i < nombres.length; i++){
            if (nombres[i].equals(buscarProducto)){
                int cantidadVendida = Integer.parseInt(JOptionPane.showInputDialog("Escriba La Cantidad De " + nombres[i] + " Vendida"));
                while(cantidadVendida > stocks[i]){
                    cantidadVendida = Integer.parseInt(JOptionPane.showInputDialog("El Stock Disponible Para " + nombres[i] + " Es De " + stocks[i] + ", Su Requerimiento Es De " + stocks[i] + " No hay Stock Suficiente"));
                }
                stocks[i] -= cantidadVendida;
            }
        }
    }
    public void menu(){//Función del menu principal
        String[] botonesMenuPrincipal = {"Ver Dinero", "Productos E Inventario", "Registrar venta","Salir"};
        int opcionMenuPrincipal = JOptionPane.showOptionDialog(ventana, "¿Qué Quiere Hacer Hoy?", "Menú Principal", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMenuPrincipal, botonesMenuPrincipal[0]);
            switch (opcionMenuPrincipal){
                case -1:
                    System.exit(0);
                case 0:
                    menuCuentas();
                    break;
                case 1:
                    inventario();
                    break;
                case 2:
                    registrarventa();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(ventana, "La Opción Elegida Es Incorrecta....");
                    menu();
                    break;
            }
        }
    public static void main(String[] args) throws Exception{
        App app = new App();
        app.menu();
    }
}
