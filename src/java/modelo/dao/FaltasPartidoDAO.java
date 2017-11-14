
package modelo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import modelo.FaltasPartido;


public class FaltasPartidoDAO extends DAO {
    
    public boolean insertarFaltas(ArrayList<FaltasPartido> faltas, int partId) {
        
        boolean inserted = false;
        String sql = "INSERT INTO faltasPartido(idPartido, idJugador, tipoFalta) VALUES("+partId+",?,?)";
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            for(FaltasPartido falta : faltas) {
                this.pst.clearParameters();
                this.pst.setInt(1, falta.getJugador().getId());
                this.pst.setString(2, falta.getTipoFalta());
                inserted = this.pst.executeUpdate() > 0;
            }
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return inserted;
    }
    
    
    
}
