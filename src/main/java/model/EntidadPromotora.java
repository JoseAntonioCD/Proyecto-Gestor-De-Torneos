package model;

public class EntidadPromotora extends Usuario {

    private Suscripcion suscripcion;

    public EntidadPromotora() {}

    public EntidadPromotora(int id, String nombre, String email, String password, Suscripcion suscripcion) {
        super(id, nombre, email, password);
        this.suscripcion = suscripcion;
    }

    public Suscripcion getSuscripcion() { return suscripcion; }
    public void setSuscripcion(Suscripcion suscripcion) { this.suscripcion = suscripcion; }

    @Override
    public String toString() {
        return nombre + " - " + suscripcion.getNombreTier();
    }
}