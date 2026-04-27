import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RegistrarVentas {
    App app;
    JFrame ventana;

    public RegistrarVentas(App app){
        this.app = app;
        this.ventana = app;
    }

    public void registrarventa() {
    String buscarProducto = JOptionPane.showInputDialog("Escriba El Nombre Del Producto");

    if (buscarProducto == null) return; // usuario canceló

    if (app.inventario.u == 0) {
        JOptionPane.showMessageDialog(ventana, "No Hay Productos Registrados En El Inventario");
        return;
    }

    for (int i = 0; i < app.inventario.u; i++) {
        if (app.inventario.nombres[i] != null && app.inventario.nombres[i].equals(buscarProducto)) {

            while (true) {
                try {
                    String input = JOptionPane.showInputDialog("Escriba La Cantidad De " + app.inventario.nombres[i] + " Vendida");
                    if (input == null) return; // canceló

                    int cantidadVendida = Integer.parseInt(input);

                    if (cantidadVendida <= 0) {
                        JOptionPane.showMessageDialog(ventana, "La Cantidad Debe Ser Mayor A Cero");
                        continue;
                    }

                    if (cantidadVendida > app.inventario.stocks[i]) {
                        JOptionPane.showMessageDialog(ventana, "Stock disponible: " + app.inventario.stocks[i] + ". No hay suficiente.");
                        continue;
                    }

                    // Actualizar stock
                    app.inventario.stocks[i] -= cantidadVendida;

                    // Calcular total y sumar a la cuenta
                    double totalVenta = app.inventario.precios[i] * cantidadVendida;
                    app.cuenta.dinero += (float) totalVenta;
                    app.cuenta.historial.add(new CuentaEmpresa.Movimiento(
                        app.cuenta.contadorMovimientos++,
                        "Venta - " + app.inventario.nombres[i],
                        (float) totalVenta
                    ));

                    JOptionPane.showMessageDialog(ventana,
                        "Venta Registrada\nProducto: " + app.inventario.nombres[i] +
                        "\nCantidad: " + cantidadVendida +
                        "\nTotal: $" + totalVenta);
                    Datos.guardarInventario(app.inventario);        
                    Datos.guardarCuenta(app.cuenta);     
                    Datos.guardarHistorial(app.cuenta); 
                    return;

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(ventana, "Ingrese Solo Números");
                }
            }
        }
    }

    JOptionPane.showMessageDialog(ventana, "Producto No Encontrado En El Inventario");
}
}
