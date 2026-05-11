package mediateca;

public class Revista {
    private String codigo, titulo, editorial, periodicidad, fecha, ubicacion;

    public Revista(String codigo, String titulo, String editorial, String periodicidad, String fecha, String ubicacion) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getEditorial() { return editorial; }
    public String getPeriodicidad() { return periodicidad; }
    public String getFecha() { return fecha; }
    public String getUbicacion() { return ubicacion; }
}