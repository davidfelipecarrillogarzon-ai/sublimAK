import javax.swing.*;
import java.awt.*;

public class App extends JFrame{
    CuentaEmpresa cuenta;
    Inventario inventario;

    public App(){
    cuenta = new CuentaEmpresa(this);
    inventario = new Inventario(this);

    // ← cargar datos guardados
    Datos.cargarCuenta(cuenta);
    Datos.cargarHistorial(cuenta);
    Datos.cargarInventario(inventario);

    this.setTitle("SublimAK");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);//PARA PANTALLA COMPLETA AJUSTADA A MONITOR
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
    this.getContentPane().setBackground(Color.DARK_GRAY);//PARA PONER EL FRAME EN MODO OSCURO(SOLO APLICA PARA LA VENTANA)//PARA PONER EL FRAME EN MODO OSCURO(SOLO APLICA PARA LA VENTANA)
    this.setVisible(true);
    }
    public void menu(){//Función del menu principal
           while(true){
            String[] botonesMenuPrincipal = {"Ver Dinero", "Productos E Inventario", "Registrar venta","Salir"};
        int opcionMenuPrincipal = JOptionPane.showOptionDialog(this, "¿Qué Quiere Hacer Hoy?", "Menú Principal", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMenuPrincipal, botonesMenuPrincipal[0]);
            switch (opcionMenuPrincipal){
                case -1:
                    System.exit(0);
                case 0:
                    cuenta.menuCuentas();
                    break;
                case 1:
                    inventario.inventario();
                    break;
                case 2:
                    inventario.registrarventa();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "La Opción Elegida Es Incorrecta....");
                    break;
            }
   } 
        
        }
    public static void main(String[] args) throws Exception{
        
        App app = new App();
        app.menu();
    }
}
