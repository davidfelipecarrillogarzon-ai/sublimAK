import java.io.*;

public class Datos {

static final String CARPETA = System.getProperty("user.home") + "/SublimAK/datos/";
static final String RUTA_INVENTARIO = CARPETA + "inventario.txt";
static final String RUTA_CUENTA = CARPETA + "cuenta.txt";
static final String RUTA_HISTORIAL = CARPETA + "historial.txt";

    public static void guardarInventario(Inventario inv) {
        try {
            new File(CARPETA).mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(RUTA_INVENTARIO));
            for (int i = 0; i < inv.u; i++) {
                pw.println(inv.nombres[i] + ";" + inv.precios[i] + ";" + inv.stocks[i] + ";" + inv.codigos[i]);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error guardando inventario: " + e);
        }
    }

    public static void guardarCuenta(CuentaEmpresa cuenta) {
        try {
            new File(CARPETA).mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(RUTA_CUENTA));
            pw.println(cuenta.dinero + ";" + cuenta.dineroDigital);
            pw.close();
        } catch (IOException e) {
            System.out.println("Error guardando cuenta: " + e);
        }
    }

    public static void guardarHistorial(CuentaEmpresa cuenta) {
        try {
            new File(CARPETA).mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(RUTA_HISTORIAL));
            for (CuentaEmpresa.Movimiento m : cuenta.historial) {
                pw.println(m.id + "|" + m.tipo + "|" + m.monto + "|" + m.fecha);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error guardando historial: " + e);
        }
    }
    public static void cargarInventario(Inventario inv) {
    try {
        File archivo = new File(RUTA_INVENTARIO);
        if (!archivo.exists()) return; // primera vez, no hay nada que cargar

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(";");
            inv.nombres[inv.u] = partes[0];
            inv.precios[inv.u] = Double.parseDouble(partes[1]);
            inv.stocks[inv.u] = Integer.parseInt(partes[2]);
            inv.codigos[inv.u] = Integer.parseInt(partes[3]);
            inv.u++;
        }
        br.close();
    } catch (IOException e) {
        System.out.println("Error cargando inventario: " + e);
    }
}

public static void cargarCuenta(CuentaEmpresa cuenta) {
    try {
        File archivo = new File(RUTA_CUENTA);
        if (!archivo.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea = br.readLine();
        if (linea != null) {
            String[] partes = linea.split(";");
            cuenta.dinero = Float.parseFloat(partes[0]);
            cuenta.dineroDigital = Float.parseFloat(partes[1]);
        }
        br.close();
    } catch (IOException e) {
        System.out.println("Error cargando cuenta: " + e);
    }
}

public static void cargarHistorial(CuentaEmpresa cuenta) {
    try {
        File archivo = new File(RUTA_HISTORIAL);
        if (!archivo.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("\\|");
            int id = Integer.parseInt(partes[0]);
            String tipo = partes[1];
            float monto = Float.parseFloat(partes[2]);
            String fecha = partes[3];

            CuentaEmpresa.Movimiento m = new CuentaEmpresa.Movimiento(id, tipo, monto);
            m.fecha = fecha; // sobrescribe la fecha con la guardada
            cuenta.historial.add(m);
            cuenta.contadorMovimientos = id + 1; // continúa el contador desde donde quedó
        }
        br.close();
    } catch (IOException e) {
        System.out.println("Error cargando historial: " + e);
    }
}
}