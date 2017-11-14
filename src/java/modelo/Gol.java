
package modelo;

import modelo.dao.Jugador;


public class Gol {
    private int id;
    private Partido partido;
    private Jugador jugador;
    
    public Gol() {}

    public Gol(int id, Partido partido, Jugador jugador) {
        this.id = id;
        this.partido = partido;
        this.jugador = jugador;
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
    
    
}
