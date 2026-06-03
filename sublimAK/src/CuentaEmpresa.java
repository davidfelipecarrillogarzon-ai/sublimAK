import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;

public class CuentaEmpresa {
    App app; // guardar referencia

    public CuentaEmpresa(App app) {
        this.app = app;
    }

    //Variables de cuenta
    float dinero = 0;
    float dineroDigital= 0;

    //Variables de historial de movimientos en cuenta
    ArrayList<Movimiento> historial = new ArrayList<>();
    int contadorMovimientos = 1;

    static class Movimiento{
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

        //Es un método sobrescrito, no un constructor
        @Override
        public String toString(){
            return "ID: " + id + " | " + tipo + " | $" + monto + " | " + fecha;
        }
    }

    public void mostrarHistorial(){
        if(historial.isEmpty()){
        JOptionPane.showMessageDialog(this.app, "No Hay Movimientos Registrados");
        return;
        }
        String texto = "";
        for(Movimiento m : historial){

            texto += m.toString() + "\n";
        }
// 1. Crear el JTextArea y asignarle el texto
JTextArea textArea = new JTextArea(texto);
textArea.setEditable(false); // Para que el usuario no pueda modificar el historial
textArea.setLineWrap(true);   // Ajusta el texto al ancho para que no se corte
textArea.setWrapStyleWord(true);

// 2. Definir un tamaño preferido para que la ventana no se abra gigante o muy pequeña
// (Filas, Columnas) -> Ajusta según prefieras
textArea.setRows(15);
textArea.setColumns(40);

// 3. Envolver el JTextArea en un JScrollPane
JScrollPane scrollPane = new JScrollPane(textArea);

// 4. Mostrar el JScrollPane en el JOptionPane
JOptionPane.showMessageDialog(this.app, scrollPane, "Historial de Movimientos", JOptionPane.INFORMATION_MESSAGE);
return;
    }

    public void sumarDinero(){

    String[] billeteraAMover = {"Efectivo", "Nequi", "Volver"};

    while(true){
        try{
            int opcion = JOptionPane.showOptionDialog(
                this.app,
                "¿Va A Realizar El Movimiento En Efectivo O Nequi?",
                "Menú Dinero",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                billeteraAMover,
                billeteraAMover[0]
            );

            if(opcion == -1 || opcion == 2){
                return;
            }

            String input = JOptionPane.showInputDialog(this.app, "Escriba El Dinero");

            if(input == null){
                return; // cancelar
            }

            float dineroASumar = Float.parseFloat(input);

            if(dineroASumar < 1000){
                JOptionPane.showMessageDialog(this.app, "No Puede Agregar Menos De 1000");
                continue;
            }

            if(opcion == 0){
                dinero += dineroASumar;
                historial.add(new Movimiento(contadorMovimientos++, "Deposito Efectivo", dineroASumar));
                JOptionPane.showMessageDialog(this.app, "Dinero En Efectivo: " + dinero);
                Datos.guardarCuenta(this);
                Datos.guardarHistorial(this);
            }

            if(opcion == 1){
                dineroDigital += dineroASumar;
                historial.add(new Movimiento(contadorMovimientos++, "Deposito Nequi", dineroASumar));
                JOptionPane.showMessageDialog(this.app, "Dinero En Nequi: " + dineroDigital);
                Datos.guardarCuenta(this);
                Datos.guardarHistorial(this);
            }
            return; //

        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this.app, "Ingrese Solo Números");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this.app, "Error Inesperado");
        }
    }
}
public void restarDinero(){

    String[] billeteraAMover = {"Efectivo", "Nequi", "Volver"};

    while(true){
        try{
            int opcion = JOptionPane.showOptionDialog(
                this.app,
                "¿Va A Realizar El Movimiento En Efectivo O Nequi?",
                "Menú Dinero",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                billeteraAMover,
                billeteraAMover[0]
            );

            if(opcion == -1 || opcion == 2){
                return;
            }

            String input = JOptionPane.showInputDialog(this.app, "Dinero A Retirar");

            if(input == null){
                return; // cancelar
            }

            float dineroARetirar = Float.parseFloat(input);

            if(dineroARetirar < 1000){
                JOptionPane.showMessageDialog(this.app, "No Puede Retirar Menos De 1000");
                continue;
            }

            if(opcion == 0){ // efectivo
                if(dineroARetirar > dinero){
                    JOptionPane.showMessageDialog(this.app, "No Tiene Suficiente Dinero En Efectivo");
                    continue;
                }

                dinero -= dineroARetirar;
                historial.add(new Movimiento(contadorMovimientos++, "Retiro Efectivo", dineroARetirar));
                JOptionPane.showMessageDialog(this.app, "Dinero Restante: " + dinero);
                Datos.guardarCuenta(this);
                Datos.guardarHistorial(this);
            }

            if(opcion == 1){ // nequi
                if(dineroARetirar > dineroDigital){
                    JOptionPane.showMessageDialog(this.app, "No Tiene Suficiente Dinero En Nequi");
                    continue;
                }

                dineroDigital -= dineroARetirar;
                historial.add(new Movimiento(contadorMovimientos++, "Retiro Nequi", dineroARetirar));
                JOptionPane.showMessageDialog(this.app, "Dinero Restante En Nequi: " + dineroDigital);
                Datos.guardarCuenta(this);
                Datos.guardarHistorial(this);
            }

            return; //termina correctamente

        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this.app, "Ingrese Solo Números");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this.app, "Error Inesperado");
        }
    }
}
    public void menuCuentas(){//Función para el menu de cuenta
        while(true){
        String[] botonesDinero = {"Ver total", "Sumar Dinero A La Cuenta", "Restar Dinero A La Cuenta", "Ver Movimientos", "Retroceder Al Menú Principal", "Salir"};
        int opcionMenuCuentas = JOptionPane.showOptionDialog(this.app, "¿Que Desea Hacer?", "Menú Dinero", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesDinero, botonesDinero[0]);
        switch (opcionMenuCuentas){
            case -1:
                return;
            case 0:
                JOptionPane.showMessageDialog(this.app, "Su Dinero Total Es: " + (dinero + dineroDigital) + "$\nDinero en efectivo: " + dinero + "$" + "\nDinero en Nequi: " + dineroDigital + "$");
                break;
            case 1:
                sumarDinero();
                break;
            case 2:
                restarDinero();
                break;
            case 3:
                mostrarHistorial();
                break;
            case 4:
                return;
            case 5:
                System.exit(0);
            default:
                break;
        }
    }
}
}
