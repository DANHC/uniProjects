
package modelo;


public class Equipo {
    private int id;
    private String nombre;
    private String rutaLogo;
    private Entrenador entrenador;
    private String presidente;
    private String estadio;
    private String ubicacion;
    private String detalles;
    private int descalificado;

    public Equipo() {
    }

    public Equipo(int id, String nombre, String rutaLogo, Entrenador entrenador, String presidente, String estadio, String ubicacion, String detalles, int descalificado) {
        this.id = id;
        this.nombre = nombre;
        this.rutaLogo = rutaLogo;
        this.entrenador = entrenador;
        this.presidente = presidente;
        this.estadio = estadio;
        this.ubicacion = ubicacion;
        this.detalles = detalles;
        this.descalificado = descalificado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getDescalificado() {
        return descalificado;
    }

    public void setDescalificado(int descalificado) {
        this.descalificado = descalificado;
    }
    
    
    
    
}
