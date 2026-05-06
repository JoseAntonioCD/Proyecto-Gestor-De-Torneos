package model;

public abstract class Usuario {

    protected int id;
    protected String nombre;
    protected String email;
    protected String password;

    public Usuario() {}

    public Usuario(int id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Métodos comunes
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("Sesión cerrada");
    }
}
