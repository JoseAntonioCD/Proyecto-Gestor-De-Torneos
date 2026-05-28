package model;

public abstract class Usuario {

    private int id;

    private String nombre;

    private String email;

    private String password;

    private String tipo;

    /*
     * CONSTRUCTOR VACÍO
     */

    public Usuario() {

    }

    /*
     * CONSTRUCTOR COMPLETO
     */

    public Usuario(
            int id,
            String nombre,
            String email,
            String password,
            String tipo
    ) {

        this.id = id;

        this.nombre = nombre;

        this.email = email;

        this.password = password;

        this.tipo = tipo;
    }

    /*
     * GETTERS Y SETTERS
     */

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getTipo() {

        return tipo;
    }

    public void setTipo(String tipo) {

        this.tipo = tipo;
    }

    @Override
    public String toString() {

        return nombre;
    }
}