
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Partido;
import modelo.ProgramacionPartido;


public class PartidoDAO extends DAO {
    
    public int getLastIdPartido() {
        String sql = "select * from vw_partido_id";
        ResultSet rs;
        int id = 0;
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            if(rs.next()) {
                id = rs.getInt("IDPART");
            } else {
                throw new SQLException("ID DE PARTIDO NO RECUPERADO");
            }
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return id;
    }
    
    
    
    
    public boolean insertar(Partido part) {
        
        boolean inserted = false;
        
        String sql = "INSERT INTO partido(idProgramacionPartido, totalGoles, totalFaltas, totalCorner, totalPenalti, golesLocal, golesVisitante) "
                    + "VALUES(?,?,?,?,?,?,?)";
                
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, part.getProg().getId());
            this.pst.setInt(2, part.getTotalGoles());
            this.pst.setInt(3, part.getTotalFaltas());
            this.pst.setInt(4, part.getTotalCorner());
            this.pst.setInt(5, part.getTotalPenalti());
            this.pst.setInt(6, part.getGolesLocal());
            this.pst.setInt(7, part.getGolesVisitante());
            
            inserted = this.pst.executeUpdate() > 0;
            this.pst.close();  
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex);
        }
        
        
        return inserted;
    }
    
    public ArrayList<Partido> getPartidos(int torneoid) {
        
        String sql = "SELECT * FROM vw_partidosView WHERE IDTORNEO = ?";
        ProgramacionPartidoDAO progDao = new ProgramacionPartidoDAO();
        ArrayList<Partido> partidos = new ArrayList<>();
        ResultSet rs;
        ProgramacionPartido prog;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, torneoid);
            rs = this.pst.executeQuery();
            
            while(rs.next()) {
                prog = progDao.getProgramacionPartidoById(rs.getInt("IDPROGRAMACIONPARTIDO"));
                partidos.add(
                        new Partido(
                                rs.getInt("ID"),
                                prog,
                                rs.getInt("TOTALGOLES"),
                                rs.getInt("TOTALFALTAS"),
                                rs.getInt("TOTALCORNER"),
                                rs.getInt("TOTALPENALTI"),
                                rs.getInt("GOLESLOCAL"),
                                rs.getInt("GOLESVISITANTE")
                        )
                );
            }
            
            rs.close();
            this.pst.close();
            this.con.close();
            
        } catch(SQLException ex) {
            System.out.println("PartidoDAO : "+ex.toString());
        }
        
        return partidos;
        
    }
    
    
    
    
}
