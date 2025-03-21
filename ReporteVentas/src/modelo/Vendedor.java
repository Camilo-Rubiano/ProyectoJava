package modelo;

/**
* Clase que representa un vendedor en el sistema.
*/
public class Vendedor {
private String tipoDocumento;
private String numeroDocumento;
private String nombres;
private String apellidos;
private double totalVentas;

/**
* Constructor de la clase Vendedor.
* 
* @param tipoDocumento Tipo de documento de identidad
* @param numeroDocumento Número de documento de identidad
* @param nombres Nombres del vendedor
* @param apellidos Apellidos del vendedor
*/
public Vendedor(String tipoDocumento, String numeroDocumento, String nombres, String apellidos) {
   this.tipoDocumento = tipoDocumento;
   this.numeroDocumento = numeroDocumento;
   this.nombres = nombres;
   this.apellidos = apellidos;
   this.totalVentas = 0.0;
}

/**
* Obtiene el tipo de documento del vendedor.
* 
* @return Tipo de documento
*/
public String getTipoDocumento() {
   return tipoDocumento;
}

/**
* Obtiene el número de documento del vendedor.
* 
* @return Número de documento
*/
public String getNumeroDocumento() {
   return numeroDocumento;
}

/**
* Obtiene los nombres del vendedor.
* 
* @return Nombres del vendedor
*/
public String getNombres() {
   return nombres;
}

/**
* Obtiene los apellidos del vendedor.
* 
* @return Apellidos del vendedor
*/
public String getApellidos() {
   return apellidos;
}

/**
* Obtiene el nombre completo del vendedor.
* 
* @return Nombre completo del vendedor
*/
public String getNombreCompleto() {
   return nombres + " " + apellidos;
}

/**
* Obtiene el total de ventas realizadas por el vendedor.
* 
* @return Total de ventas
*/
public double getTotalVentas() {
   return totalVentas;
}

/**
* Incrementa el total de ventas del vendedor.
* 
* @param monto Monto a incrementar
*/
public void incrementarVentas(double monto) {
   if (monto > 0) {
       this.totalVentas += monto;
   }
}

/**
* Convierte el vendedor a formato CSV.
* 
* @return Representación del vendedor en formato CSV
*/
public String toCSV() {
   return tipoDocumento + ";" + numeroDocumento + ";" + nombres + ";" + apellidos;
}

/**
* Convierte el vendedor a formato CSV para reporte (nombre completo y total ventas).
* 
* @return Representación del vendedor en formato CSV para reporte
*/
public String toReportCSV() {
   return nombres + " " + apellidos + ";" + String.format("%.2f", totalVentas);
}
}
