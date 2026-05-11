package mediateca;

public class Usuario {
    private int id;
    private String nombre;
    private String user;
    private String password;
    private String rol;

    public Usuario(int id, String nombre, String user, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.user = user;
        this.password = password;
        this.rol = rol;
    }

    public String getRol() { return rol; }
    public String getNombre() { return nombre; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
}
