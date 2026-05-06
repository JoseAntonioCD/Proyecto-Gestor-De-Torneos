package model;


public class Localizacion {

    private int idLocalizacion;
    private String nombreLocalizacion;
    private int nivelMinimo;

    public Localizacion() {}

    public Localizacion(int idLocalizacion, String nombreLocalizacion, int nivelMinimo) {
        this.idLocalizacion = idLocalizacion;
        this.nombreLocalizacion = nombreLocalizacion;
        this.nivelMinimo = nivelMinimo;
    }

    public int getIdLocalizacion() { return idLocalizacion; }
    public void setIdLocalizacion(int idLocalizacion) { this.idLocalizacion = idLocalizacion; }

    public String getNombreLocalizacion() { return nombreLocalizacion; }
    public void setNombreLocalizacion(String nombreLocalizacion) { this.nombreLocalizacion = nombreLocalizacion; }

    public int getNivelMinimo() { return nivelMinimo; }
    public void setNivelMinimo(int nivelMinimo) { this.nivelMinimo = nivelMinimo; }

    @Override
    public String toString() {
        return nombreLocalizacion;
    }
}