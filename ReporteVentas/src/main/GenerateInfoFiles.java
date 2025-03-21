package main;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
* Clase principal para generar archivos de prueba del sistema.
*/
public class GenerateInfoFiles {
 // Listas de nombres y apellidos para generación aleatoria
 private static final String[] NOMBRES = {
     "Juan", "María", "Pedro", "Ana", "Luis", "Sofía", "Carlos", "Laura",
     "José", "Gabriela", "Andrés", "Paula", "Diego", "Valeria", "Fernando",
     "Daniela", "Jorge", "Camila", "Miguel", "Natalia"
 };
 
 private static final String[] APELLIDOS = {
     "García", "Rodríguez", "Martínez", "López", "González", "Pérez",
     "Sánchez", "Ramírez", "Torres", "Flores", "Rivera", "Gómez",
     "Díaz", "Reyes", "Cruz", "Morales", "Ortiz", "Jiménez", "Vargas", "Romero"
 };
 
 private static final String[] TIPOS_DOCUMENTO = {
     "CC", "NIT"
 };
 
 private static final String[] NOMBRES_PRODUCTOS = {
     "Laptop", "Smartphone", "Tablet", "Monitor", "Teclado", "Mouse", "Impresora",
     "Cámara", "Auriculares", "Parlantes", "Disco Duro", "SSD", "RAM", "Procesador",
     "Tarjeta Gráfica", "Motherboard", "Fuente de Poder", "Case", "Router", "Switch",
     "Smartwatch", "Cargador", "Batería", "Cable USB", "Cable HDMI", "Adaptador",
     "Webcam", "Micrófono", "Escáner", "UPS"
 };
 
 private static final Random random = new Random();
 
 /**
  * Método principal para generar archivos de prueba.
  */
 public static void main(String[] args) {
     try {
         // Crear directorio de archivos si no existe
         Path directory = Paths.get("archivos");
         if (!Files.exists(directory)) {
             Files.createDirectory(directory);
         }
         
         // Generar archivos de prueba
         int numVendedores = 10;
         int numProductos = 30;
         
         // Generar archivo de productos
         String productosFile = "archivos/productos.txt";
         createProductsFile(numProductos, productosFile);
         System.out.println("Archivo de productos generado: " + productosFile);
         
         // Generar archivo de vendedores
         String vendedoresFile = "archivos/vendedores.txt";
         List<String[]> vendedoresInfo = createSalesManInfoFile(numVendedores, vendedoresFile);
         System.out.println("Archivo de vendedores generado: " + vendedoresFile);
         
         // Generar archivo de ventas para cada vendedor
         for (String[] vendedor : vendedoresInfo) {
             int randomSalesCount = random.nextInt(15) + 5; // Entre 5 y 20 ventas
             String tipoDoc = vendedor[0];
             String numDoc = vendedor[1];
             String nombre = vendedor[2];
             createSalesMenFile(randomSalesCount, nombre, tipoDoc, numDoc, productosFile);
         }
         
         System.out.println("Generación de archivos completada exitosamente.");
         
     } catch (Exception e) {
         System.err.println("Error durante la generación de archivos: " + e.getMessage());
         e.printStackTrace();
     }
 }
 
 /**
  * Crea un archivo de ventas para un vendedor específico.
  * 
  * @param randomSalesCount Cantidad de ventas aleatorias
  * @param name Nombre del vendedor
  * @param tipoDoc Tipo de documento
  * @param id Número de documento
  * @param productosFilePath Ruta del archivo de productos
  * @throws IOException Si ocurre un error de escritura
  */
 private static void createSalesMenFile(int randomSalesCount, String name, String tipoDoc, 
                                       String id, String productosFilePath) throws IOException {
     // Leer IDs de productos
     List<String> productIds = readProductIds(productosFilePath);
     if (productIds.isEmpty()) {
         throw new IOException("No se pudieron leer los IDs de productos desde " + productosFilePath);
     }
     
     // Generar nombre de archivo basado en el nombre del vendedor
     String fileName = "archivos/" + name.toLowerCase().replace(" ", "_") + "_" + id + ".txt";
     
     try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
         writer.write(tipoDoc + ";" + id + "\n");
         
         // Generar ventas aleatorias
         Set<String> usedProducts = new HashSet<>();
         for (int i = 0; i < randomSalesCount; i++) {
             // Seleccionar un producto aleatorio (asegurándose de no repetir)
             String productId;
             do {
                 productId = productIds.get(random.nextInt(productIds.size()));
             } while (usedProducts.contains(productId) && usedProducts.size() < productIds.size());
             
             usedProducts.add(productId);
             
             // Generar cantidad vendida (entre 1 y 10)
             int cantidad = random.nextInt(10) + 1;
             
             // Escribir línea de venta
             writer.write(productId + ";" + cantidad + ";\n");
         }
     }
     
     System.out.println("Archivo de ventas generado: " + fileName);
 }
 
 /**
  * Lee los IDs de productos desde un archivo.
  * 
  * @param filePath Ruta del archivo de productos
  * @return Lista de IDs de productos
  * @throws IOException Si ocurre un error de lectura
  */
 private static List<String> readProductIds(String filePath) throws IOException {
     List<String> productIds = new ArrayList<>();
     
     try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
         String line;
         while ((line = reader.readLine()) != null) {
             String[] parts = line.split(";");
             if (parts.length > 0) {
                 productIds.add(parts[0]);
             }
         }
     }
     
     return productIds;
 }
 
 /**
  * Crea un archivo con información de productos.
  * 
  * @param productsCount Cantidad de productos a generar
  * @param filePath Ruta del archivo a crear
  * @throws IOException Si ocurre un error de escritura
  */
 private static void createProductsFile(int productsCount, String filePath) throws IOException {
     try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
         Set<String> usedNames = new HashSet<>();
         
         for (int i = 0; i < productsCount; i++) {
             String id = "P" + String.format("%03d", i + 1);
             
             // Seleccionar un nombre de producto único
             String nombre;
             do {
                 nombre = NOMBRES_PRODUCTOS[random.nextInt(NOMBRES_PRODUCTOS.length)];
                 if (usedNames.size() >= NOMBRES_PRODUCTOS.length) {
                     nombre += " " + (random.nextInt(100) + 1);
                 }
             } while (usedNames.contains(nombre));
             
             usedNames.add(nombre);
             
             // Generar precio (entre 10,000 y 1,000,000)
             double precio = 10000 + random.nextInt(990000);
             
             // Escribir línea de producto
             writer.write(id + ";" + nombre + ";" + precio + "\n");
         }
     }
 }
 
 /**
  * Crea un archivo con información de vendedores.
  * 
  * @param salesmanCount Cantidad de vendedores a generar
  * @param filePath Ruta del archivo a crear
  * @return Lista de información de vendedores generados
  * @throws IOException Si ocurre un error de escritura
  */
 private static List<String[]> createSalesManInfoFile(int salesmanCount, String filePath) throws IOException {
     List<String[]> vendedoresInfo = new ArrayList<>();
     Set<String> usedIDs = new HashSet<>();
     
     try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
         for (int i = 0; i < salesmanCount; i++) {
             // Generar tipo de documento aleatorio
             String tipoDoc = TIPOS_DOCUMENTO[random.nextInt(TIPOS_DOCUMENTO.length)];
             
             // Generar número de documento único
             String numDoc;
             do {
                 numDoc = String.valueOf(1000000000L + random.nextInt(900000000));
             } while (usedIDs.contains(numDoc));
             
             usedIDs.add(numDoc);
             
             // Generar nombre y apellido aleatorios
             String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
             String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
             
             // Escribir línea de vendedor
             writer.write(tipoDoc + ";" + numDoc + ";" + nombre + ";" + apellido + "\n");
             
             // Guardar información del vendedor para uso posterior
             vendedoresInfo.add(new String[]{tipoDoc, numDoc, nombre});
         }
     }
     
     return vendedoresInfo;
 }
}