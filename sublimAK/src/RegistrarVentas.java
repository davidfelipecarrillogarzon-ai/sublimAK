import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RegistrarVentas {
    App app;
    JFrame ventana;
    public RegistrarVentas(App app){
        this.app = app;
        this.ventana = app;
    }
    public int buscarProducto(String nombre){
        for (int i = 0; i < app.inventario.u; i++){
        if (app.inventario.nombres[i] != null && app.inventario.nombres[i].equalsIgnoreCase(nombre)) {
            return i;
        }
    }
    return -1;
}

    public void registrarventa() {
        String nombreProducto = JOptionPane.showInputDialog("Escriba El Nombre Del Producto");
            if (nombreProducto == null) return; // usuario canceló

             if (app.inventario.u == 0) {
            JOptionPane.showMessageDialog(ventana, "No Hay Productos Registrados En El Inventario");
            return;
            }
            int encontrado = buscarProducto(nombreProducto);
        
            if (encontrado == -1) {
                JOptionPane.showMessageDialog(ventana, "Producto No Encontrado En El Inventario");
                return;
            }
            while(true){//While importante para retornar a la funcion menu
                    int cantidadVendida = 0;
                    String cantidadVendidastr = JOptionPane.showInputDialog("Escriba La Cantidad De " + app.inventario.nombres[encontrado] + " Vendida");
                    if (cantidadVendidastr == null) return; // canceló
                    try {
                        cantidadVendida = Integer.parseInt(cantidadVendidastr);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(app, "Solo Puede Poner Números");
                        continue;
                    }
                    if (cantidadVendida <= 0) {
                        JOptionPane.showMessageDialog(ventana, "La Cantidad Debe Ser Mayor A Cero");
                        continue;
                    }

                    if (cantidadVendida > app.inventario.stocks[encontrado]) {
                        JOptionPane.showMessageDialog(ventana, "Stock disponible: " + app.inventario.stocks[encontrado] + ". No hay suficiente.");
                        continue;
                    }

                    String[] botonesRegistrarPrecio = {"Usar Precio De Stock", "Usar Precio Diferente","Cancelar Registro"};
                    int eleccionPrecioVenta = JOptionPane.showOptionDialog(app, "Elija Una Opción", "Menú De Registro De Ventas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesRegistrarPrecio, botonesRegistrarPrecio[0]);

                    switch (eleccionPrecioVenta) {
                        case -1:
                            return;
                        case 0:
                             // Calcular total y sumar a la cuenta
                            double totalVentaStock = app.inventario.precios[encontrado] * cantidadVendida;
                            finalizarventa(encontrado, cantidadVendida, totalVentaStock);//Actualizar datos
                             //Retornar A La Funciòn Menu
                            return;
                        case 1:
                            double precioTemporal;
                            String precioTemporalstr = JOptionPane.showInputDialog(app, "Escriba El Precio Temporal Para La Venta De " + app.inventario.nombres[encontrado]); 
                            if(precioTemporalstr == null){//El Usuario Canceló
                               return;
                            }
                            while(precioTemporalstr.trim().isEmpty()){
                               JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío");
                               precioTemporalstr = JOptionPane.showInputDialog(app, "Escriba El Precio Temporal Para La Venta De " + app.inventario.nombres[encontrado]); 
                               if(precioTemporalstr == null)return;
                            }
                            try {
                               precioTemporal = Double.parseDouble(precioTemporalstr);
                               if (precioTemporal < 1000) {
                               JOptionPane.showMessageDialog(app, "No Puede Poner Un Precio Menor A 1000");
                               continue;
                               }
                            } catch (NumberFormatException e) {
                               JOptionPane.showMessageDialog(app, "Solo Puede Usar Números");
                               continue;
                            }
                            double totalVentaTemp = precioTemporal * cantidadVendida;
                            finalizarventa(encontrado, cantidadVendida, totalVentaTemp);
                            return;
                        case 2:
                           return;
                    }
                }
            }

    private void finalizarventa(int indice, int cantidad, double totalVenta){
                    app.cuenta.dinero += (float) totalVenta;
                    app.cuenta.historial.add(new CuentaEmpresa.Movimiento(
                        app.cuenta.contadorMovimientos++,
                        "Venta - " + app.inventario.nombres[indice],
                        (float) totalVenta
                    ));
                    app.inventario.stocks[indice] -= cantidad;

                    //Mensaje de registro de ventas
                    JOptionPane.showMessageDialog(ventana,"Venta Registrada\nProducto: " + app.inventario.nombres[indice] + "\nCantidad: " + cantidad + "\nTotal: $" + totalVenta);

                    //Guardado de datos
                    Datos.guardarInventario(app.inventario);        
                    Datos.guardarCuenta(app.cuenta);     
                    Datos.guardarHistorial(app.cuenta); 
    }
    
     
}

