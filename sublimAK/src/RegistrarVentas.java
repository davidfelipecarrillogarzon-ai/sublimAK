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
        if (app.inventario.totalProductos == 0) {
            JOptionPane.showMessageDialog(ventana, "No Hay Productos Registrados En El Inventario");
            return;
            }
        int encontrado = -1;
        String [] codigoONombre = {"Registrar Por Nombre", "Registrar Por Codigo", "Cancelar"};
        int eleccionNombreCodigo = JOptionPane.showOptionDialog(app, "¿Registrar Venta Con Nombre O Codigo?", "Menú De Registro De Ventas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, codigoONombre, codigoONombre[0]);
        if(eleccionNombreCodigo == -1 || eleccionNombreCodigo == 2)return;
        if(eleccionNombreCodigo == 0){
            String nombreProducto = JOptionPane.showInputDialog("Escriba El Nombre Del Producto");
            if (nombreProducto == null) return; // usuario canceló

            encontrado = app.buscador.buscarProductoXNombre(nombreProducto);
        
            if (encontrado == -1) {
                JOptionPane.showMessageDialog(ventana, "Producto No Encontrado En El Inventario");
                return;
            }}
        
        if(eleccionNombreCodigo == 1){
            while (true) {
            String codigoBusquedastr = JOptionPane.showInputDialog("Escriba El Codigo Del Producto");
            if (codigoBusquedastr == null) return; // usuario canceló
            try{
                int codigoBusqueda = Integer.parseInt(codigoBusquedastr);
                encontrado = app.buscador.buscarProductoXCodigo(codigoBusqueda);
                break;
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(app, "Use Solo Números Al Escribir El Codigo");
            }

        }
    }
        if(encontrado == -1){
                JOptionPane.showMessageDialog(app, "Producto No Encontrado En El Inventario");return;
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
                        case -1, 2:
                            return;
                        case 0:
                             // Calcular total y sumar a la cuenta
                            double totalVentaStock = app.inventario.precios[encontrado] * cantidadVendida;
                            finalizarventa(encontrado, cantidadVendida, totalVentaStock);//Actualizar datos
                             //Retornar A La Funciòn Menu
                            return;
                        case 1:
                            double precioTemporal;
                            while (true) {
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
                        }
                    }
            }
        }
                

    private void finalizarventa(int indice, int cantidad, double totalVenta){
        String[] botonesMedioPago = {"Efectivo", "Nequi"};
        int eleccionMedioPago = JOptionPane.showOptionDialog(app, "¿Porque Medio Se Realizó El Pago?", "Medio De Pago", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMedioPago, botonesMedioPago[0]);
        if(eleccionMedioPago == 0) {
            app.cuenta.dinero += (float) totalVenta;
        }else if(eleccionMedioPago == 1){app.cuenta.dineroDigital += (float) totalVenta;}
        else{return;}
        String medioPago = (eleccionMedioPago == 0) ? "Efectivo" : "Nequi";
        app.cuenta.historial.add(new CuentaEmpresa.Movimiento(
        app.cuenta.contadorMovimientos++,
        "Venta " + medioPago + " - " + app.inventario.nombres[indice],
        (float) totalVenta));
        app.inventario.stocks[indice] -= cantidad;


        //Mensaje de registro de ventas
        JOptionPane.showMessageDialog(ventana,"Venta Registrada\nProducto: " + app.inventario.nombres[indice] + "\nCantidad: " + cantidad + "\nTotal: $" + totalVenta);

        //Guardado de datos
        Datos.guardarInventario(app.inventario);        
        Datos.guardarCuenta(app.cuenta);     
        Datos.guardarHistorial(app.cuenta); 
    }
}