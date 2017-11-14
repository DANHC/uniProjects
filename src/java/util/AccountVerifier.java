
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;
import modelo.dao.DAO;


public class AccountVerifier extends DAO{
    
    
    public Usuario getUser(String user, String pass) {
        String sql = "SELECT * FROM usuario WHERE nombreUsuario = ? AND contra = ?";
        ResultSet rs;
        Usuario usuario = new Usuario();
        
        try {
            this.pst = this.con.prepareStatement(sql);
            this.pst.setString(1, user);
            this.pst.setString(2, pass);
            rs = this.pst.executeQuery();
            
            if( rs.next() ) {
                usuario.setId( rs.getInt("id") );
                usuario.setNombreUsuario( rs.getString("nombreUsuario") );
                usuario.setTipoUsuario( rs.getString("tipoUsuario") );
            } else {
                throw new SQLException("Usuario no encontrado");
            }
            rs.close();
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        } 
        
        
        return usuario;
    }
    
    
}
