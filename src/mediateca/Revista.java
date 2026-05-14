package mediateca;

public class Revista {
    private String titulo, editorial, periodicidad, fecha, ubicacion;
    private int cantidad;

    public Revista(String titulo, String editorial, String periodicidad, String fecha, String ubicacion, int cantidad) {
        this.titulo = titulo;
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.cantidad = cantidad;
    }

    public String getTitulo() { return titulo; }
    public String getEditorial() { return editorial; }
    public String getPeriodicidad() { return periodicidad; }
    public String getFecha() { return fecha; }
    public String getUbicacion() { return ubicacion; }
    public int getCantidad() { return cantidad; }
}