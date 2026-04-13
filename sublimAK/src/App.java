import javax.swing.*;
import java.awt.*;

public class App {
    static int i = 0;

    //Variables de cuenta
    static float dinero = 0;
    static float dineroDigital= 0;
    static float dineroARetirar = 0;
    static int billeteraDeRetiro;

    //Variables de inventario
    static String[] nombres = new String[100];
    static double [] precios = new double [100];
    static int [] stocks = new int[100];
    static int[] codigos = new int[100];

    //Funcion de personalizacion de interfaz para JOptionPane creada por ChatGPT:
public static void aplicarTema() {
        Color fondo = new Color(18, 18, 18);
        Color texto = new Color(230, 230, 230);
        Color boton = new Color(40, 40, 40);

        Font fuente = new Font("Segoe UI", Font.PLAIN, 16);

        UIManager.put("Panel.background", fondo);
        UIManager.put("OptionPane.background", fondo);

        UIManager.put("OptionPane.messageForeground", texto);

        UIManager.put("Button.background", boton);
        UIManager.put("Button.foreground", texto);

        UIManager.put("OptionPane.messageFont", fuente);
        UIManager.put("Button.font", fuente);
    }




    public static void sumarDinero(){
        //Suma de Dinero
                        float dineroASumar = 0;
                        String[] billeteraAMover= {"Efectivo", "Nequi"};
                        int billeteraDeRetiro = JOptionPane.showOptionDialog(null, "¿Va A Realizar El Movimiento En Efectivo O Nequi?", "Menu Dinero", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, billeteraAMover, billeteraAMover[0]);
                        if(billeteraDeRetiro == 1){
                            dineroASumar = Float.parseFloat(JOptionPane.showInputDialog("Escriba el dinero que va a sumar."));
                        while (dineroASumar <1000) {
                            dineroASumar = Float.parseFloat(JOptionPane.showInputDialog("El monto que desea añadir a la cuenta es incorrecto no puede sumar menos de 1000$"));
                        }
                        dinero += dineroASumar;
                        JOptionPane.showMessageDialog(null, "El dinero en efectivo ahorrado es: " + dinero);
                        }else{
                            dineroASumar = Float.parseFloat(JOptionPane.showInputDialog("Escriba la suma del dinero que va a añadir(Dinero Digital)."));
                        while (dineroASumar <1000) {
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("El monto que desea retirar es incorrecto no puede retirar menos de 1000 ni mas de " + dinero + "$"));
                        }
                        dineroDigital += dineroASumar;
                        }
    }
    public static void restarDinero(){
        //Resta de dinero
                        String[] billeteraAMover= {"Efectivo", "Nequi"};
                        int billeteraDeRetiro = JOptionPane.showOptionDialog(null, "¿Va A Realizar El Movimiento En Efectivo O Nequi?", "Menu Dinero", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, billeteraAMover, billeteraAMover[0]);
                        if(billeteraDeRetiro == 1){
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("Escriba el dinero que va a retirar."));
                        while (dineroARetirar <1000 || dineroARetirar > dinero) {
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("El monto que desea retirar es incorrecto no puede retirar menos de 1000 ni mas de " + dinero + "$"));
                        }
                        dinero -= dineroARetirar;
                        }else{
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("Escriba el dinero que va a retirar(Dinero Digital)."));
                        while (dineroARetirar <1000 || dineroARetirar > dinero) {
                            dineroARetirar = Float.parseFloat(JOptionPane.showInputDialog("El monto que desea retirar es incorrecto no puede retirar menos de 1000 ni mas de " + dinero + "$"));
                        }
                        dineroDigital -= dineroARetirar;
                        }
    }
    public static void menuCuentas(){
        String[] botonesDinero = {"Ver total", "Restar Dinero A La Cuenta", "Sumar Dinero A La Cuenta", "Retroceder Al Menu Principal", "Salir"};
        int opcionMenuCuentas = JOptionPane.showOptionDialog(null, "¿Que desea hacer?", "Menu Dinero", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesDinero, botonesDinero[0]);
        switch (opcionMenuCuentas) {
            case 0:
                JOptionPane.showMessageDialog(null, "Su Dinero TotaL Es: " + (dinero + dineroDigital) + "$\nDinero en efectivo: " + dinero + "$" + "\nDinero en Nequi: " + dineroDigital + "$");
                menuCuentas();
                break;
            case 1:
                restarDinero();
                break;
            case 2:
                sumarDinero();
                break;
            case 3:
                menu();
                break;
            case 4:
                System.exit(0);
            default:
                break;
        }

    }
    public static void inventario(){
        String[] botonesMenuInventario = {"Ver Productos Agregados", "Modificar Inventario", "Agregar Stock", "Menu Principal", "Salir"};
        int opcionMenuInventario = JOptionPane.showOptionDialog(null, "Productos E Inventario", "Menu Inventario", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMenuInventario, botonesMenuInventario[0]);
        switch (opcionMenuInventario) {
            case 0:
                if (i == 0){JOptionPane.showMessageDialog(null, "No Hay Productos Agregados Aun.");
                    inventario();}else{verProductos();}
                break;
            case 1:
                agregarProductos();
                inventario();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Salió Del Programa");
                break;
        }
    }
    public static void verProductos(){
        String mensaje = "Nombre\tPrecio\tStock\n";
        for(i = 0; i < nombres.length; i++){
            mensaje += nombres[i] + "\t" + precios[i] + "\t" +stocks[i] + "\t" + codigos[i] + "\n"; 
        }
        JOptionPane.showMessageDialog(null, mensaje);
    }
    public static void agregarProductos(){
        nombres[i] = JOptionPane.showInputDialog("Escriba El Nombre Del Producto Número " + (i + 1));
        precios[i] = Double.parseDouble(JOptionPane.showInputDialog("Escriba El Precio De " + nombres[i]));
        stocks[i] = Integer.parseInt(JOptionPane.showInputDialog("Escriba El Stock Disponible De " + nombres[i]));
        codigos[i] = Integer.parseInt(JOptionPane.showInputDialog("Escriba EL COdigo De" + nombres[i]));
        i++;
    }
    public static void menu(){
        String[] botonesMenuPrincipal = {"Ver Dinero", "Productos E Inventario", "Salir"};
        int opcionMenuPrincipal = JOptionPane.showOptionDialog(null, "¿Que quiere hacer hoy?", "Menu principal", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMenuPrincipal, botonesMenuPrincipal[0]);
            switch (opcionMenuPrincipal){
                case 0:
                    menuCuentas();
                    break;
                case 1:
                    inventario();
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "La opcion elegida es incorrecta....");
                    break;
            }

        }
    public static void main(String[] args) throws Exception{
        aplicarTema();
        menu();
    }
}
