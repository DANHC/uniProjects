
package modelo;

import modelo.dao.Jugador;


public class FaltasPartido {
    private int id;
    private Partido partido;
    private Jugador jugador;
    private String tipoFalta;
    
    public FaltasPartido() {}

    public FaltasPartido(int id, Partido partido, Jugador jugador, String tipoFalta) {
        this.id = id;
        this.partido = partido;
        this.jugador = jugador;
        this.tipoFalta = tipoFalta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public String getTipoFalta() {
        return tipoFalta;
    }

    public void setTipoFalta(String tipoFalta) {
        this.tipoFalta = tipoFalta;
    }
    
    
    
}
