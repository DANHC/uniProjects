
package modelo.dao;

import modelo.Equipo;


public class Jugador {
    private int id;
    private Equipo equipo;
    private String nombres;
    private String apelllidos;
    private int edad;
    private double estatura;
    private String nacionalidad;
    private int numeroJugador;
    private String posicion;
    private String rutaFoto;
    private int descalificado;
    
    
    public Jugador() {
        this.equipo = new Equipo();
    }

    public Jugador(int id, Equipo equipo, String nombres, String apelllidos, int edad, double estatura, String nacionalidad, int numeroJugador, String posicion, String rutaFoto, int descalificado) {
        this.id = id;
        this.equipo = equipo;
        this.nombres = nombres;
        this.apelllidos = apelllidos;
        this.edad = edad;
        this.estatura = estatura;
        this.nacionalidad = nacionalidad;
        this.numeroJugador = numeroJugador;
        this.posicion = posicion;
        this.rutaFoto = rutaFoto;
        this.descalificado = descalificado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApelllidos() {
        return apelllidos;
    }

    public void setApelllidos(String apelllidos) {
        this.apelllidos = apelllidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getNumeroJugador() {
        return numeroJugador;
    }

    public void setNumeroJugador(int numeroJugador) {
        this.numeroJugador = numeroJugador;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public int getDescalificado() {
        return descalificado;
    }

    public void setDescalificado(int descalificado) {
        this.descalificado = descalificado;
    }
    
    
    
    
}
