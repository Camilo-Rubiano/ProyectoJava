package modelo;

/**
* Clase que representa un producto en el sistema.
*/
public class Producto {
private String id;
private String nombre;
private double precioUnitario;
private int cantidadVendida;

/**
* Constructor de la clase Producto.
* 
* @param id Identificador único del producto
* @param nombre Nombre descriptivo del producto
* @param precioUnitario Precio por unidad del producto
*/
public Producto(String id, String nombre, double precioUnitario) {
   this.id = id;
   this.nombre = nombre;
   this.precioUnitario = precioUnitario;
   this.cantidadVendida = 0;
}

/**
* Obtiene el ID del producto.
* 
* @return ID del producto
*/
public String getId() {
   return id;
}

/**
* Obtiene el nombre del producto.
* 
* @return Nombre del producto
*/
public String getNombre() {
   return nombre;
}

/**
* Obtiene el precio unitario del producto.
* 
* @return Precio unitario
*/
public double getPrecioUnitario() {
   return precioUnitario;
}

/**
* Obtiene la cantidad vendida del producto.
* 
* @return Cantidad vendida
*/
public int getCantidadVendida() {
   return cantidadVendida;
}

/**
* Incrementa la cantidad vendida del producto.
* 
* @param cantidad Cantidad a incrementar
*/
public void incrementarCantidadVendida(int cantidad) {
   if (cantidad > 0) {
       this.cantidadVendida += cantidad;
   }
}

/**
* Convierte el producto a formato CSV.
* 
* @return Representación del producto en formato CSV
*/
public String toCSV() {
   return id + ";" + nombre + ";" + precioUnitario;
}

/**
* Convierte el producto a formato CSV para reporte (nombre, precio y cantidad).
* 
* @return Representación del producto en formato CSV para reporte
*/
public String toReportCSV() {
   return nombre + ";" + precioUnitario + ";" + cantidadVendida;
}
}