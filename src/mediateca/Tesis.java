package mediateca;

public class Tesis {
    private String codigo, titulo, autor, carrera, anio, ubicacion;

    public Tesis(String codigo, String titulo, String autor, String carrera, String anio, String ubicacion) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.carrera = carrera;
        this.anio = anio;
        this.ubicacion = ubicacion;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getCarrera() { return carrera; }
    public String getAnio() { return anio; }
    public String getUbicacion() { return ubicacion; }
}