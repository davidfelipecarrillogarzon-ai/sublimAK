import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class Insumos {
    App app;
    JFrame ventana;

    public static ArrayList<Insumos> listaInsumos = new ArrayList<>();

    public Insumos(App app){
        this.app = app;
    }

    public String nombreInsumo;
    public int stockInsumos;
    public double precioInsumos;

    public Insumos(String nombreInsumos, int stockInsumos, double precioInsumos){
        this.nombreInsumo = nombreInsumos;
        this.stockInsumos = stockInsumos;
        this.precioInsumos = precioInsumos;
    }

    public void agregarDatosInsumos(){
        while (true) {
        nombreInsumo = JOptionPane.showInputDialog(app, "Escriba El Nombre Del Insumo");
        if(nombreInsumo == null){return;}   
        if(nombreInsumo.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío");continue;}
        break;
        }
        while (true) {
         String stockInsumosStr = JOptionPane.showInputDialog(app, "Escriba La Cantidad De Stock Del Insumo");
        if(stockInsumosStr == null){return;}   
        if(stockInsumosStr.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío");continue;}
        try {
            stockInsumos = Integer.parseInt(stockInsumosStr);
            break;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(app, "Escriba Solo Numeros");
        }
    }
        while (true) {
        String precioInsumosStr = JOptionPane.showInputDialog(app, "Escriba El Precio De Los Insumos");
        if(precioInsumosStr == null){return;}   
        if(precioInsumosStr.trim().isEmpty()){JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío");continue;}
        try {
          precioInsumos = Double.parseDouble(precioInsumosStr);
          break;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(app, "Escriba Solo Numeros");
        }
        }
        Insumos nuevoInsumo = new Insumos(nombreInsumo, stockInsumos, precioInsumos);
        añadirInsumo(nuevoInsumo);
    }
    public void comprarInsumo(){
    if(listaInsumos.isEmpty()){
        JOptionPane.showMessageDialog(app, "No Hay Insumos Registrados");
        return;
    }

    // Buscar el insumo
    String nombre = JOptionPane.showInputDialog(app, "Escriba El Nombre Del Insumo Que Compró");
    if(nombre == null) return;

    Insumos encontrado = null;
    for(Insumos ins : listaInsumos){
        if(ins.nombreInsumo.equalsIgnoreCase(nombre)){
            encontrado = ins;
            break;
        }
    }
    if(encontrado == null){
        JOptionPane.showMessageDialog(app, "Insumo No Encontrado");
        return;
    }

    // Cantidad comprada
    int cantidadComprada = 0;
    while(true){
        String cantidadStr = JOptionPane.showInputDialog(app, "¿Cuántas Unidades De " + encontrado.nombreInsumo + " Compró?");
        if(cantidadStr == null) return;
        if(cantidadStr.trim().isEmpty()){ JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío"); continue; }
        try{
            cantidadComprada = Integer.parseInt(cantidadStr);
            if(cantidadComprada <= 0){ JOptionPane.showMessageDialog(app, "La Cantidad Debe Ser Mayor A Cero"); continue; }
            break;
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(app, "Escriba Solo Números");
        }
    }

    // Precio total pagado
    double totalPagado = 0;
    while(true){
        String precioStr = JOptionPane.showInputDialog(app, "¿Cuánto Pagó En Total Por " + encontrado.nombreInsumo + "?");
        if(precioStr == null) return;
        if(precioStr.trim().isEmpty()){ JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío"); continue; }
        try{
            totalPagado = Double.parseDouble(precioStr);
            if(totalPagado <= 0){ JOptionPane.showMessageDialog(app, "El Precio Debe Ser Mayor A Cero"); continue; }
            break;
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(app, "Escriba Solo Números");
        }
    }

    // Medio de pago
    String[] medios = {"Efectivo", "Nequi"};
    int medioPagoOpcion = JOptionPane.showOptionDialog(app, "¿Con Qué Pagó?", "Medio De Pago",
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, medios, medios[0]);
    if(medioPagoOpcion == -1) return;

    // Verificar saldo suficiente
    if(medioPagoOpcion == 0 && totalPagado > app.cuenta.dinero){
        JOptionPane.showMessageDialog(app, "No Tiene Suficiente Dinero En Efectivo");
        return;
    }
    if(medioPagoOpcion == 1 && totalPagado > app.cuenta.dineroDigital){
        JOptionPane.showMessageDialog(app, "No Tiene Suficiente Dinero En Nequi");
        return;
    }

    // Descontar dinero y registrar movimiento
    String medioPago = (medioPagoOpcion == 0) ? "Efectivo" : "Nequi";
    if(medioPagoOpcion == 0){
        app.cuenta.dinero -= (float) totalPagado;
    } else {
        app.cuenta.dineroDigital -= (float) totalPagado;
    }

    app.cuenta.historial.add(new CuentaEmpresa.Movimiento(
        app.cuenta.contadorMovimientos++,
        "Compra Insumo " + medioPago + " - " + encontrado.nombreInsumo,
        (float) totalPagado));

    // Actualizar stock del insumo
    encontrado.stockInsumos += cantidadComprada;

    // Guardar todo
    Datos.guardarInsumos();
    Datos.guardarCuenta(app.cuenta);
    Datos.guardarHistorial(app.cuenta);

    JOptionPane.showMessageDialog(app,
        "Compra Registrada\nInsumo: " + encontrado.nombreInsumo +
        "\nCantidad Agregada: " + cantidadComprada +
        "\nTotal Pagado: $" + totalPagado +
        "\nMedio: " + medioPago +
        "\nNuevo Stock: " + encontrado.stockInsumos);
}

public void menuInsumos(){
    while(true){ // ← loop que le faltaba
        String[] botonesMenuInsumo = {"Agregar Insumo", "Ver Insumos", "Comprar Insumo", "Editar Insumos", "Borrar Insumo", "Volver Al Menu Principal"};
        int opcion = JOptionPane.showOptionDialog(app, "¿Qué Quiere Hacer?", "Menú Insumos",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesMenuInsumo, botonesMenuInsumo[0]);
        switch(opcion){
            case -1, 5:
                return;
            case 0:
                agregarDatosInsumos();
                break;
            case 1:
                mensajeInventarioInsumos();
                break;
            case 2:
                comprarInsumo(); // ← nuevo
                break;
            case 3:
                editarInsumo();
                break;
            case 4:
                borrarInsumo();
                break;
        }
    }
}

public void mensajeInventarioInsumos(){
    String mensaje = String.format("%-20s %-10s %-8s%n", "Nombre", "Precio", "Stock");
    for (int i = 0; i < listaInsumos.size(); i++) {
        Insumos ins = listaInsumos.get(i); // ← obtener el objeto
        mensaje += String.format("%-20s %-10.2f %-8d%n", ins.nombreInsumo, ins.precioInsumos, ins.stockInsumos);
    }
    JTextArea area = new JTextArea(mensaje);
    area.setFont(new Font("Monospaced", Font.PLAIN, 14));
    area.setEditable(false);
    JOptionPane.showMessageDialog(app, area); // app, no ventana (ventana no está inicializada)
}

public void editarInsumo(){
    if(listaInsumos.isEmpty()){
        JOptionPane.showMessageDialog(app, "No Hay Insumos Registrados");
        return;
    }
    String nombre = JOptionPane.showInputDialog(app, "Escriba El Nombre Del Insumo A Editar");
    if(nombre == null) return;

    Insumos encontrado = null;
    for(Insumos ins : listaInsumos){
        if(ins.nombreInsumo.equalsIgnoreCase(nombre)){
            encontrado = ins;
            break;
        }
    }
    if(encontrado == null){
        JOptionPane.showMessageDialog(app, "Insumo No Encontrado");
        return;
    }

    String[] camposEditar = {"Nombre", "Stock", "Precio", "Cancelar"};
    int opcion = JOptionPane.showOptionDialog(app, "¿Qué Desea Editar De " + encontrado.nombreInsumo + "?",
        "Editar Insumo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
        null, camposEditar, camposEditar[0]);

    switch(opcion){
        case -1, 3:
            return;
        case 0: // Editar nombre
            while(true){
                String nuevoNombre = JOptionPane.showInputDialog(app, "Nuevo Nombre");
                if(nuevoNombre == null) return;
                if(nuevoNombre.trim().isEmpty()){ JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío"); continue; }
                encontrado.nombreInsumo = nuevoNombre;
                break;
            }
            break;
        case 1: // Editar stock
            while(true){
                String nuevoStockStr = JOptionPane.showInputDialog(app, "Nuevo Stock");
                if(nuevoStockStr == null) return;
                if(nuevoStockStr.trim().isEmpty()){ JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío"); continue; }
                try{
                    encontrado.stockInsumos = Integer.parseInt(nuevoStockStr);
                    break;
                } catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(app, "Escriba Solo Números");
                }
            }
            break;
        case 2: // Editar precio
            while(true){
                String nuevoPrecioStr = JOptionPane.showInputDialog(app, "Nuevo Precio");
                if(nuevoPrecioStr == null) return;
                if(nuevoPrecioStr.trim().isEmpty()){ JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío"); continue; }
                try{
                    encontrado.precioInsumos = Double.parseDouble(nuevoPrecioStr);
                    break;
                } catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(app, "Escriba Solo Números");
                }
            }
            break;
    }
    Datos.guardarInsumos();
    JOptionPane.showMessageDialog(app, "Insumo Actualizado Correctamente");
}

public void borrarInsumo(){
    if(listaInsumos.isEmpty()){
        JOptionPane.showMessageDialog(app, "No Hay Insumos Registrados");
        return;
    }
    String nombre = JOptionPane.showInputDialog(app, "Escriba El Nombre Del Insumo A Borrar");
    if(nombre == null) return;

    Insumos encontrado = null;
    for(Insumos ins : listaInsumos){
        if(ins.nombreInsumo.equalsIgnoreCase(nombre)){
            encontrado = ins;
            break;
        }
    }
    if(encontrado == null){
        JOptionPane.showMessageDialog(app, "Insumo No Encontrado");
        return;
    }

    int confirmar = JOptionPane.showConfirmDialog(app,
        "¿Seguro Que Quiere Borrar " + encontrado.nombreInsumo + "?",
        "Confirmar", JOptionPane.YES_NO_OPTION);

    if(confirmar == JOptionPane.YES_OPTION){
        listaInsumos.remove(encontrado);
        Datos.guardarInsumos();
        JOptionPane.showMessageDialog(app, "Insumo Borrado Correctamente");
    }
}

    public void añadirInsumo(Insumos insumo){
        listaInsumos.add(insumo);
        Datos.guardarInsumos();
    }    
}
