package model;

public class Participacion {

    private Participante participante;
    private Evento evento;


    public Participacion() {}

    public Participacion(Participante participante, Evento evento, String tipoParticipacion) {
        this.participante = participante;
        this.evento = evento;

    }

    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }


    @Override
    public String toString() {
        return participante.getNombre() + " participa en " + evento.toString();
    }
}