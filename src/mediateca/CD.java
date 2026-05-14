package mediateca;

public class CD {
    private String titulo, artista, genero, duracion, ubicacion;
    private int canciones, cantidad;

    public CD(String titulo, String artista, String genero, String duracion, int canciones, String ubicacion, int cantidad) {
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
        this.canciones = canciones;
        this.ubicacion = ubicacion;
        this.cantidad = cantidad;
    }

    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public String getGenero() { return genero; }
    public String getDuracion() { return duracion; }
    public int getCanciones() { return canciones; }
    public String getUbicacion() { return ubicacion; }
    public int getCantidad() { return cantidad; }
}