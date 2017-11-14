
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Entrenador;
import modelo.Usuario;


public class EntrenadorDAO extends DAO{
    
    public boolean insertar(Entrenador entrenador) {
        
        boolean saved = false;
        
        String insert1 = "INSERT INTO usuario(nombreUsuario, contra, tipoUsuario) "
                        + "VALUES(?,?,'Entrenador')";
        
        String insert2 = "INSERT INTO entrenador(idUsuario, nombres, apellidos, edad, sexo, nacionalidad, eliminado) "
                        + "VALUES(usuario_seq.CURRVAL, ?, ?, ?, ?, ?, 0)";
        
        
        try {
            this.pst = this.getConnection().prepareStatement(insert1);
            this.pst.setString(1, entrenador.getUsuario().getNombreUsuario());
            this.pst.setString(2, entrenador.getUsuario().getContra());
            
            boolean inserted = this.pst.executeUpdate() > 0;
            
            if(inserted) {
                this.pst = this.getConnection().prepareStatement(insert2);
                this.pst.setString(1, entrenador.getNombres());
                this.pst.setString(2, entrenador.getApellidos());
                this.pst.setInt(3, entrenador.getEdad());
                this.pst.setString(4, entrenador.getSexo());
                this.pst.setString(5, entrenador.getNacionalidad());
                
                saved = this.pst.executeUpdate() > 0;
                this.pst.close();
                this.con.close();
                if(!saved) {
                    throw new SQLException("Entrenador No Insertado");
                }
            }
            
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        } 
        
        return saved;
    }
    
    public boolean modificar(Entrenador entrenador) {
        
        boolean modificado = false;
        
        String sql1 = "UPDATE entrenador SET nombres = ?, "
                        + "apellidos = ?, "
                        + "edad = ?, "
                        + "sexo = ?, "
                        + "nacionalidad = ?, "
                        + "eliminado = ? "
                    + "WHERE id = ?";
        
        String sql2 = "UPDATE usuario SET nombreUsuario = ?, "
                        + "contra = ?, "
                        + "eliminado = ? "
                      + "WHERE id = ?";
        
        try {
            this.pst = this.getConnection().prepareStatement(sql1);
            this.pst.setString(1, entrenador.getNombres());
            this.pst.setString(2, entrenador.getApellidos());
            this.pst.setInt(3, entrenador.getEdad());
            this.pst.setString(4, entrenador.getSexo());
            this.pst.setString(5, entrenador.getNacionalidad());
            this.pst.setInt(6, entrenador.getEliminado());
            this.pst.setInt(7, entrenador.getId());
            
            boolean updated = this.pst.executeUpdate() > 0;
            
            if(updated) {
                this.pst = this.getConnection().prepareStatement(sql2);
                this.pst.setString(1, entrenador.getUsuario().getNombreUsuario());
                this.pst.setString(2, entrenador.getUsuario().getContra());
                this.pst.setInt(3, entrenador.getEliminado());
                this.pst.setInt(4, entrenador.getUsuario().getId());
                modificado = this.pst.executeUpdate() > 0;
                this.pst.close();
                if(!modificado) {
                    throw new SQLException("Entrenador No Modificado");
                }
            } 
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return modificado;
    }
    
    public boolean eliminar(Entrenador entrenador) {
        String sql1 = "UPDATE entrenador SET eliminado = 1 WHERE id = ?";
        String sql2 = "UPDATE usuario SET eliminado = 1 WHERE id = ?";
        
        boolean eliminado = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql1);
            this.pst.setInt(1, entrenador.getId());
            
            boolean updated = this.pst.executeUpdate() > 0;
            
            if(updated) {
                this.pst = this.getConnection().prepareStatement(sql2);
                this.pst.setInt(1, entrenador.getUsuario().getId());
                eliminado = this.pst.executeUpdate() > 0;
                this.pst.close();
                if(!eliminado) {
                    throw new SQLException("Entrenador no eliminado");
                }
            }
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return eliminado;
    }
    
    public ArrayList<Entrenador> getEntrenadores() {
        String sql = "SELECT * FROM vw_entrenador";
        ResultSet rs;
        ArrayList<Entrenador> entrenadores = new ArrayList<>();
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            while(rs.next()) {
                Entrenador entr = new Entrenador(
                        rs.getInt("entrenadorId"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getInt("edad"),
                        rs.getString("sexo"),
                        rs.getString("nacionalidad"),
                        rs.getInt("eliminado"),
                        new Usuario(rs.getInt("usuarioId"), rs.getString("nombreUsuario"), rs.getString("contra"), rs.getString("tipoUsuario"))
                );
                
                entrenadores.add(entr);
            }
            rs.close(); this.st.close(); this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return entrenadores;
        
    }
    
    public Entrenador getEntrenadorById(int id) {
        Entrenador entrenador = null;
        String sql = "SELECT * FROM vw_entrenador WHERE entrenadorId = "+id;
        ResultSet rs;
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            if(rs.next()) {
                entrenador = new Entrenador(
                        rs.getInt("entrenadorId"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getInt("edad"),
                        rs.getString("sexo"),
                        rs.getString("nacionalidad"),
                        rs.getInt("eliminado"),
                        new Usuario(rs.getInt("usuarioId"), rs.getString("nombreUsuario"), rs.getString("contra"), rs.getString("tipoUsuario"))
                );
            } else {
                throw new SQLException("Entrenador No Encontrado");
            }
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return entrenador;
    }
    
    public Entrenador getEntrenadorByIdUser(int userId) {
        Entrenador entrenador = null;
        String sql = "SELECT * FROM vw_entrenador WHERE USUARIOID = "+userId;
        ResultSet rs;
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            if(rs.next()) {
                entrenador = new Entrenador(
                        rs.getInt("entrenadorId"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getInt("edad"),
                        rs.getString("sexo"),
                        rs.getString("nacionalidad"),
                        rs.getInt("eliminado"),
                        new Usuario(rs.getInt("usuarioId"), rs.getString("nombreUsuario"), rs.getString("contra"), rs.getString("tipoUsuario"))
                );
            } else {
                throw new SQLException("Entrenador No Encontrado");
            }
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return entrenador;
    }
    
    
    
}
