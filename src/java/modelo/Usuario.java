
package modelo;


public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contra;
    private String tipoUsuario;
    
    public Usuario() {}

    public Usuario(int id, String nombreUsuario, String contra, String tipoUsuario) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contra = contra;
        this.tipoUsuario = tipoUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    
    
}
