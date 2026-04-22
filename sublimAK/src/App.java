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
    ventana.setVisible(true);
    ventana.getContentPane().setBackground(Color.DARK_GRAY);//PARA PONER EL FRAME EN MODO OSCURO(SOLO APLICA PARA LA VENTANA)//PARA PONER EL FRAME EN MODO OSCURO(SOLO APLICA PARA LA VENTANA)
    java.net.URL url = getClass().getResource("iconoSK.png");;//ICONO
    setVisible(true);
}

    ArrayList<Movimiento> historial = new ArrayList<>();
    int contadorMovimientos = 1;

    static int i = 0;

    //Variables de cuenta
    static float dinero = 0;
    static float dineroDigital= 0;
    static float dineroARetirar = 0;

    //Variables de inventario
    static String[] nombres = new String[100];
    static double [] precios = new double [100];
    static int [] stocks = new int[100];
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
        String[] botonesMenuInventario = {"Ver Productos Agregados", "Modificar Inventario", "Agregar Stock", "Menú Principal", "Salir"};
        int opcionMenuInventario = JOptionPane.showOptionDialog(ventana, "Productos E Inventario", "Menu Inventario", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMenuInventario, botonesMenuInventario[0]);
        switch (opcionMenuInventario) {
            case 0:
                if (i == 0){JOptionPane.showMessageDialog(ventana, "No Hay Productos Agregados Aun.");
                    inventario();}else{verProductos();}
                break;
            case 1:
                agregarProductos();
                inventario();
                break;
            default:
                JOptionPane.showMessageDialog(ventana, "Salió Del Programa");
                break;
        }
    }
    public void verProductos(){//Función para ver los prouctos del inventario
        if(nombres[0].isEmpty()){
            JOptionPane.showMessageDialog(ventana, "El Inventario Esta Vacio Aun...");
        }
        String mensaje = "Nombre \t  Precio \t  Stock \t  Codigo\n";
        for(i = 0; i < nombres.length; i++){
            mensaje += nombres[i] + "\t  " + precios[i] + "\t  " + stocks[i] + "\t       " + codigos[i] + "\n"; 
        }
        JOptionPane.showMessageDialog(ventana, mensaje);
    }
    public void agregarProductos(){//Función para agregar productos en el inventario usando arrays
        nombres[i] = JOptionPane.showInputDialog("Escriba El Nombre Del Producto Número " + (i + 1));
        precios[i] = Double.parseDouble(JOptionPane.showInputDialog("Escriba El Precio De " + nombres[i]));
        stocks[i] = Integer.parseInt(JOptionPane.showInputDialog("Escriba El Stock Disponible De " + nombres[i]));
        codigos[i] = Integer.parseInt(JOptionPane.showInputDialog("Escriba EL COdigo De" + nombres[i]));
        i++;
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
