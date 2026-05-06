package model;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

public class Evento {

    private int idEvento;
    private LocalDate fechaEvento;
    private EntidadPromotora entidad;
    private Localizacion localizacion;
    private List<Participacion> participaciones = new ArrayList<>();
    private List<Premio> premios = new ArrayList<>();

    public Evento() {}

    public List<Participacion> getParticipaciones() {
        return participaciones;
    }

    public void addParticipacion(Participacion p) {
        participaciones.add(p);
    }

    public List<Premio> getPremios() {
        return premios;
    }

    public void addPremio(Premio p) {
        premios.add(p);
    }

    public Evento(int idEvento, LocalDate fechaEvento, EntidadPromotora entidad, Localizacion localizacion) {
        this.idEvento = idEvento;
        this.fechaEvento = fechaEvento;
        this.entidad = entidad;
        this.localizacion = localizacion;
    }

    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int idEvento) { this.idEvento = idEvento; }

    public LocalDate getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(LocalDate fechaEvento) { this.fechaEvento = fechaEvento; }

    public EntidadPromotora getEntidad() { return entidad; }
    public void setEntidad(EntidadPromotora entidad) { this.entidad = entidad; }

    public Localizacion getLocalizacion() { return localizacion; }
    public void setLocalizacion(Localizacion localizacion) { this.localizacion = localizacion; }

    @Override
    public String toString() {
        return "Evento " + idEvento + " - " + fechaEvento;
    }
}
