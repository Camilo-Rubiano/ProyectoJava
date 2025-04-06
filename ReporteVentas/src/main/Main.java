package main;

import java.io.IOException;
import java.util.Map;

import modelo.Producto;
import modelo.Vendedor;
import utils.UtilsFile;

/**
* Clase principal para procesar los archivos y generar reportes.
*/
public class Main {
public static void main(String[] args) {
   try {
       // Rutas de los archivos
       String productosPath = "archivos/productos.txt";
       String vendedoresPath = "archivos/vendedores.txt";
       String carpetaArchivos = "archivos";
       
       // Rutas de los archivos de salida
       String reporteVendedoresPath = "reporte_vendedores.csv";
       String reporteProductosPath = "reporte_productos.csv";
       
       // Leer archivos de productos y vendedores
       Map<String, Producto> productos =  UtilsFile.leerProductos(productosPath);
       Map<String, Vendedor> vendedores =  UtilsFile.leerVendedores(vendedoresPath);
       
       // Procesar archivos de ventas
       UtilsFile.procesarArchivosVentas(carpetaArchivos, vendedores, productos);
       
       // Generar reportes
       UtilsFile.generarReporteVendedores(vendedores.values(), reporteVendedoresPath);
       UtilsFile.generarReporteProductos(productos.values(), reporteProductosPath);
       
       System.out.println("Procesamiento completado exitosamente.");
       System.out.println("Reporte de vendedores generado: " + reporteVendedoresPath);
       System.out.println("Reporte de productos generado: " + reporteProductosPath);
       
   } catch (IOException e) {
       System.err.println("Error durante el procesamiento de archivos: " + e.getMessage());
       e.printStackTrace();
   }
}
}