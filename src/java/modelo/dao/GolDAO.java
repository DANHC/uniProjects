
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Gol;
import modelo.Goleador;


public class GolDAO extends DAO{
    

    public boolean insertarGoles(ArrayList<Gol> goles, int partId) {
    
        
        boolean inserted = false;
        String sql = "INSERT INTO gol(idPartido, idJugador) VALUES("+partId+", ?)";

        try {
            this.pst = this.getConnection().prepareStatement(sql);
            for(Gol gol : goles) {
                this.pst.clearParameters();
                this.pst.setInt(1, gol.getJugador().getId());
                inserted = this.pst.executeUpdate() > 0;
            }
            this.pst.close();
            this.con.close();
        } catch(SQLException  ex) {
            System.out.println(ex.toString());
        }
        
        return inserted;
        
    }
    
    public Goleador getMaximoGoleador() {
        Jugador jugador = new Jugador();
        Goleador gole = new Goleador();
        ResultSet rs;
        String sql = "SELECT * FROM vw_goleadores WHERE ROWNUM = 1";
        JugadorDAO jugDao = new JugadorDAO();
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            if(rs.next()) {
                jugador = jugDao.getJugadorById(rs.getInt("IDJUGADOR"));
                gole.setNumGoles(rs.getInt("GOLES"));
            }
            
            gole.setId(jugador.getId());
            gole.setEquipo(jugador.getEquipo());
            gole.setNombres(jugador.getNombres());
            gole.setApelllidos(jugador.getApelllidos());
            gole.setEdad(jugador.getEdad());
            gole.setEstatura(jugador.getEstatura());
            gole.setNacionalidad(jugador.getNacionalidad());
            gole.setNumeroJugador(jugador.getNumeroJugador());
            gole.setPosicion(jugador.getPosicion());
            gole.setRutaFoto(jugador.getRutaFoto());
            gole.setDescalificado(jugador.getDescalificado());
            
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return gole;
    }
    
    
    
    
}
