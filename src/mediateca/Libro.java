package mediateca;

public class Libro {
    private String codigo, titulo, autor, editorial, isbn, ubicacion;
    private int paginas;

    public Libro(String codigo, String titulo, String autor, int paginas, String editorial, String isbn, String ubicacion) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.editorial = editorial;
        this.isbn = isbn;
        this.ubicacion = ubicacion;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getPaginas() { return paginas; }
    public String getEditorial() { return editorial; }
    public String getIsbn() { return isbn; }
    public String getUbicacion() { return ubicacion; }
}