import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BuscadorProductos {
    App app;
    JFrame ventana;

    public BuscadorProductos(App app){ 
        this.app = app;
        this.ventana = app;
    }

    int busquedaXCodigo;
    int encontradoXCodigo;
    int encontradoXNombre;


    public int buscarProductoXNombre(String nombre){
        for (int i = 0; i < app.inventario.totalProductos; i++){
        if (app.inventario.nombres[i] != null && app.inventario.nombres[i].equalsIgnoreCase(nombre)) {
            return i;
        }
    }
    return -1;
    }
    public int buscarProductoXCodigo(int codigo){
        for (int i = 0; i < app.inventario.totalProductos; i++){
        if (app.inventario.codigos[i] == codigo) {
            return i;
        }
    }
    return -1;
    }

    public void tipoBusqueda(){
        String[] botonesTipoBusqueda = {"Por Nombre", "Por Codigo", "Cancelar"};
        int opcionBotonesBusqueda = JOptionPane.showOptionDialog(ventana, "Busqueda De Productos", "¿Como Desea Ralizar La Busqueda Del Producto?", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botonesTipoBusqueda, botonesTipoBusqueda[0]);
            switch (opcionBotonesBusqueda) {
            case -1:
                return;
            case 0:
                while (true) {
            String nombreProducto = JOptionPane.showInputDialog("Escriba El Nombre Del Producto");
            if (nombreProducto == null) return; // usuario canceló

            if (app.inventario.totalProductos == 0) {
            JOptionPane.showMessageDialog(ventana, "No Hay Productos Registrados En El Inventario");
            return;
            }
        
            if (encontradoXNombre == -1) {
                JOptionPane.showMessageDialog(ventana, "Producto No Encontrado En El Inventario");
                return;
            }
            encontradoXNombre = buscarProductoXNombre(nombreProducto);
            return;
                }
            case 1:
            String busquedaXCodigostr = "";
            while (true) {
                busquedaXCodigostr = JOptionPane.showInputDialog(app, "Escribe El Codigo Del Producto");
                if(busquedaXCodigostr == null){return;}//Usuario canceló
                if(busquedaXCodigostr.trim().isEmpty()){
                JOptionPane.showMessageDialog(app, "No Deje El Campo Vacío");
                continue;
            }
            try {
                 busquedaXCodigo = Integer.parseInt(busquedaXCodigostr);
                 encontradoXCodigo = buscarProductoXCodigo(busquedaXCodigo);
                 break;
                } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(app, "Solo Puede Usar Números");
                continue;
                            }
        }
        }
}

}
