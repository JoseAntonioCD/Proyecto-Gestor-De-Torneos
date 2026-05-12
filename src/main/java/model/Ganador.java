package model;

import java.time.LocalDate;

public class Ganador {

    private Participante participante;
    private Premio premio;
    private int posicion;
    private LocalDate fechaAsignacion;

    public Ganador() {}

    public Ganador(Participante participante, Premio premio, int posicion, LocalDate fechaAsignacion) {
        this.participante = participante;
        this.premio = premio;
        this.posicion = posicion;
        this.fechaAsignacion = fechaAsignacion;
    }

    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }

    public Premio getPremio() { return premio; }
    public void setPremio(Premio premio) { this.premio = premio; }

    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

    @Override
    public String toString() {
        return participante.getNombre() + " ganó " + premio.getNombre() + " (posición " + posicion + ")";
    }
}
