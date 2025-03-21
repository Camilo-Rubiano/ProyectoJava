package utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import modelo.Producto;
import modelo.Vendedor;

/**
* Clase de utilidades para manejo de archivos.
*/
public class UtilsFile {
 
 /**
  * Lee un archivo de productos y retorna una lista de objetos Producto.
  * 
  * @param filePath Ruta del archivo de productos
  * @return Mapa de productos con el ID como clave
  * @throws IOException Si ocurre un error de lectura
  */
 public static Map<String, Producto> leerProductos(String filePath) throws IOException {
     Map<String, Producto> productos = new HashMap<>();
     
     try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
         String line;
         while ((line = reader.readLine()) != null) {
             String[] parts = line.split(";");
             if (parts.length >= 3) {
                 String id = parts[0];
                 String nombre = parts[1];
                 double precio = Double.parseDouble(parts[2]);
                 productos.put(id, new Producto(id, nombre, precio));
             }
         }
     }
     
     return productos;
 }
 
 /**
  * Lee un archivo de vendedores y retorna una lista de objetos Vendedor.
  * 
  * @param filePath Ruta del archivo de vendedores
  * @return Mapa de vendedores con el número de documento como clave
  * @throws IOException Si ocurre un error de lectura
  */
 public static Map<String, Vendedor> leerVendedores(String filePath) throws IOException {
     Map<String, Vendedor> vendedores = new HashMap<>();
     
     try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
         String line;
         while ((line = reader.readLine()) != null) {
             String[] parts = line.split(";");
             if (parts.length >= 4) {
                 String tipoDoc = parts[0];
                 String numDoc = parts[1];
                 String nombres = parts[2];
                 String apellidos = parts[3];
                 vendedores.put(numDoc, new Vendedor(tipoDoc, numDoc, nombres, apellidos));
             }
         }
     }
     
     return vendedores;
 }
 
 /**
  * Procesa los archivos de ventas de vendedores.
  * 
  * @param carpeta Carpeta donde se encuentran los archivos
  * @param vendedores Mapa de vendedores
  * @param productos Mapa de productos
  * @throws IOException Si ocurre un error de lectura
  */
 public static void procesarArchivosVentas(String carpeta, Map<String, Vendedor> vendedores, 
                                           Map<String, Producto> productos) throws IOException {
     File folder = new File(carpeta);
     File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt") && 
                                    !name.equals("vendedores.txt") && 
                                    !name.equals("productos.txt"));
     
     if (files == null || files.length == 0) {
         System.out.println("No se encontraron archivos de ventas.");
         return;
     }
     
     for (File file : files) {
         procesarArchivoVentas(file.getPath(), vendedores, productos);
     }
 }
 
 /**
  * Procesa un archivo de ventas individual.
  * 
  * @param filePath Ruta del archivo de ventas
  * @param vendedores Mapa de vendedores
  * @param productos Mapa de productos
  * @throws IOException Si ocurre un error de lectura
  */
 private static void procesarArchivoVentas(String filePath, Map<String, Vendedor> vendedores, 
                                          Map<String, Producto> productos) throws IOException {
     try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
         String primeraLinea = reader.readLine();
         if (primeraLinea == null) {
             return;
         }
         
         String[] vendedorInfo = primeraLinea.split(";");
         if (vendedorInfo.length < 2) {
             System.out.println("Formato incorrecto en archivo: " + filePath);
             return;
         }
         
         String numDoc = vendedorInfo[1];
         Vendedor vendedor = vendedores.get(numDoc);
         
         if (vendedor == null) {
             System.out.println("Vendedor no encontrado: " + numDoc + " en archivo: " + filePath);
             return;
         }
         
         String line;
         while ((line = reader.readLine()) != null) {
             String[] parts = line.split(";");
             if (parts.length >= 2) {
                 String idProducto = parts[0];
                 int cantidad = Integer.parseInt(parts[1]);
                 
                 Producto producto = productos.get(idProducto);
                 if (producto != null) {
                     producto.incrementarCantidadVendida(cantidad);
                     vendedor.incrementarVentas(cantidad * producto.getPrecioUnitario());
                 } else {
                     System.out.println("Producto no encontrado: " + idProducto + " en archivo: " + filePath);
                 }
             }
         }
     }
 }
}