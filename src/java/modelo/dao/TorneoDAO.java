
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Torneo;


public class TorneoDAO extends DAO{
    
    public Torneo getUltimoTorneo() throws SQLException{
        // Obtiene solo una fila
        String sql = "select * from vw_torneo";
        ResultSet rs;
        Torneo tor = new Torneo();
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                tor.setId(rs.getInt("ID"));
                tor.setNombreTorneo(rs.getString("NOMBRETORNEO"));
                tor.setFinalizado(rs.getInt("FINALIZADO"));
                tor.setProgramacionFinalizada(rs.getInt("PROGRAMACIONFINALIZADA"));
            } else {
                throw new SQLException("NULL RETURNED");
            }
            rs.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return tor;
    }
    
    public boolean finalizarProgramacionTorneo(Torneo tor) {
        String sql = "UPDATE torneo SET programacionFinalizada = 1 WHERE id = ?";
        boolean r = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, tor.getId());
            this.pst.executeUpdate();
            this.pst.close();
            this.con.close();
            r = true;
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return r;
    }
    
    public Torneo getTorneoById(int id) {
        String sql = "SELECT * FROM vw_torneo WHERE ID = ?";
        ResultSet rs;
        Torneo tor = null;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, id);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                tor = new Torneo(
                        rs.getInt("ID"),
                        rs.getString("NOMBRETORNEO"),
                        rs.getInt("FINALIZADO"),
                        rs.getInt("PROGRAMACIONFINALIZADA")
                );
            }
            rs.close();
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return tor;
        
    }
    
    public boolean registrarTorneo(Torneo tor) {
        
        String sql = "INSERT INTO torneo(nombreTorneo) VALUES(?)";
        boolean inserted = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setString(1, tor.getNombreTorneo());
            inserted = this.pst.executeUpdate() > 0;
            
            this.pst.close();
            this.con.close();
            
        } catch(SQLException ex) {
            System.out.println("torneoDAO: "+ex.toString());
        }
        
        return inserted;
        
    }
    
    public ArrayList<Torneo> getAllTorneos() {
        
        String sql = "SELECT * FROM torneo";
        ArrayList<Torneo> torneos = new ArrayList<>();
        ResultSet rs;
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            while(rs.next()) {
                torneos.add(
                        new Torneo(
                                rs.getInt("id"),
                                rs.getString("nombreTorneo"),
                                rs.getInt("finalizado"),
                                rs.getInt("programacionFinalizada")
                        )
                );
            }
            rs.close();
            this.st.close();
            this.con.close();
            
        } catch(SQLException ex) {
            System.out.println("TorneoDAO: "+ex.toString());
            return null;
        }
        
        return torneos;
    }
    
    public boolean finalizarTorneo(int torneoId) {
        
        String sql = "UPDATE torneo SET finalizado = 1 WHERE id = ?";
        boolean updated = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, torneoId);
            updated = this.pst.executeUpdate() > 0;
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println("TorneoDAO: "+ex.toString());
        }
        
        return updated;
    }
    
    
}
