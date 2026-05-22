package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    private int idEvento;

    private String nombre;

    private String descripcion;

    private String estado;

    private LocalDate fechaEvento;

    private EntidadPromotora entidad;

    private Localizacion localizacion;

    private List<Participacion> participaciones =
            new ArrayList<>();

    private List<Premio> premios =
            new ArrayList<>();

    public Evento() {

    }

    public Evento(
            int idEvento,
            String nombre,
            String descripcion,
            String estado,
            LocalDate fechaEvento
    ) {

        this.idEvento = idEvento;

        this.nombre = nombre;

        this.descripcion = descripcion;

        this.estado = estado;

        this.fechaEvento = fechaEvento;
    }

    public int getIdEvento() {

        return idEvento;
    }

    public void setIdEvento(int idEvento) {

        this.idEvento = idEvento;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getDescripcion() {

        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        this.descripcion = descripcion;
    }

    public String getEstado() {

        return estado;
    }

    public void setEstado(String estado) {

        this.estado = estado;
    }

    public LocalDate getFechaEvento() {

        return fechaEvento;
    }

    public void setFechaEvento(
            LocalDate fechaEvento
    ) {

        this.fechaEvento = fechaEvento;
    }

    public EntidadPromotora getEntidad() {

        return entidad;
    }

    public void setEntidad(
            EntidadPromotora entidad
    ) {

        this.entidad = entidad;
    }

    public Localizacion getLocalizacion() {

        return localizacion;
    }

    public void setLocalizacion(
            Localizacion localizacion
    ) {

        this.localizacion = localizacion;
    }

    public List<Participacion> getParticipaciones() {

        return participaciones;
    }

    public void addParticipacion(
            Participacion p
    ) {

        participaciones.add(p);
    }

    public List<Premio> getPremios() {

        return premios;
    }

    public void addPremio(
            Premio p
    ) {

        premios.add(p);
    }

    @Override
    public String toString() {

        return nombre +
                " - " +
                fechaEvento;
    }
}