package model;
import java.util.ArrayList;
import java.util.List;

public class Participante extends Usuario {

    private String telefono;

    private List<Participacion> participaciones = new ArrayList<>();
    private List<Ganador> premiosGanados = new ArrayList<>();

    public Participante() {}

    public Participante(int id, String nombre, String email, String password, String telefono) {
        super(id, nombre, email, password);
        this.telefono = telefono;
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Participacion> getParticipaciones() {
        return participaciones;
    }

    public void addParticipacion(Participacion p) {
        participaciones.add(p);
    }

    public List<Ganador> getPremiosGanados() {
        return premiosGanados;
    }

    public void addPremioGanado(Ganador g) {
        premiosGanados.add(g);
    }

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }
}