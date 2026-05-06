package model;

public class Suscripcion {

    private int idSuscripcion;
    private String nombreTier;
    private int nivel;
    private double precio;

    public Suscripcion() {}

    public Suscripcion(int idSuscripcion, String nombreTier, int nivel, double precio) {
        this.idSuscripcion = idSuscripcion;
        this.nombreTier = nombreTier;
        this.nivel = nivel;
        this.precio = precio;
    }

    public int getIdSuscripcion() { return idSuscripcion; }
    public void setIdSuscripcion(int idSuscripcion) { this.idSuscripcion = idSuscripcion; }

    public String getNombreTier() { return nombreTier; }
    public void setNombreTier(String nombreTier) { this.nombreTier = nombreTier; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return nombreTier + " (€" + precio + ")";
    }
}