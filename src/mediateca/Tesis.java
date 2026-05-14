package mediateca;

public class Tesis {
    private String titulo, autor, carrera, anio, ubicacion;
    private int cantidad;

    public Tesis(String titulo, String autor, String carrera, String anio, String ubicacion, int cantidad) {
        this.titulo = titulo;
        this.autor = autor;
        this.carrera = carrera;
        this.anio = anio;
        this.ubicacion = ubicacion;
        this.cantidad = cantidad;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getCarrera() { return carrera; }
    public String getAnio() { return anio; }
    public String getUbicacion() { return ubicacion; }
    public int getCantidad() { return cantidad; }
}