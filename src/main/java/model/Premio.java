package model;
import java.util.ArrayList;
import java.util.List;

public class Premio {

    private int idPremio;
    private String nombre;
    private int nivelMinimo;
    private List<Ganador> ganadores = new ArrayList<>();

    public Premio() {}


    public List<Ganador> getGanadores() {
        return ganadores;
    }

    public void addGanador(Ganador g) {
        ganadores.add(g);
    }

    public Premio(int idPremio, String nombre, int nivelMinimo) {
        this.idPremio = idPremio;
        this.nombre = nombre;
        this.nivelMinimo = nivelMinimo;
    }

    public int getIdPremio() { return idPremio; }
    public void setIdPremio(int idPremio) { this.idPremio = idPremio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNivelMinimo() { return nivelMinimo; }
    public void setNivelMinimo(int nivelMinimo) { this.nivelMinimo = nivelMinimo; }

    @Override
    public String toString() {
        return nombre;
    }
}