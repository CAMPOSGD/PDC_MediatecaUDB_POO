package mediateca;

public class CD {
    private String codigo, titulo, artista, genero, duracion, ubicacion;
    private int canciones;

    public CD(String codigo, String titulo, String artista, String genero, String duracion, int canciones, String ubicacion) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
        this.canciones = canciones;
        this.ubicacion = ubicacion;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public String getGenero() { return genero; }
    public String getDuracion() { return duracion; }
    public int getCanciones() { return canciones; }
    public String getUbicacion() { return ubicacion; }
}
