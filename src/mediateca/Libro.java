package mediateca;

public class Libro {
    private String titulo, autor, editorial, isbn, ubicacion;
    private int paginas, cantidad;

    public Libro(String titulo, String autor, int paginas, String editorial, String isbn, String ubicacion, int cantidad) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.editorial = editorial;
        this.isbn = isbn;
        this.ubicacion = ubicacion;
        this.cantidad = cantidad;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getPaginas() { return paginas; }
    public String getEditorial() { return editorial; }
    public String getIsbn() { return isbn; }
    public String getUbicacion() { return ubicacion; }
    public int getCantidad() { return cantidad; }
}