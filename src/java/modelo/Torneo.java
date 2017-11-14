
package modelo;


public class Torneo {
    private int id;
    private String nombreTorneo;
    private int finalizado;
    private int programacionFinalizada;
    
    public Torneo() {}

    public Torneo(int id, String nombreTorneo, int finalizado, int programacionFinalizada) {
        this.id = id;
        this.nombreTorneo = nombreTorneo;
        this.finalizado = finalizado;
        this.programacionFinalizada = programacionFinalizada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public int getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(int finalizado) {
        this.finalizado = finalizado;
    }

    public int getProgramacionFinalizada() {
        return programacionFinalizada;
    }

    public void setProgramacionFinalizada(int programacionFinalizada) {
        this.programacionFinalizada = programacionFinalizada;
    }
    
    
    
    
}
