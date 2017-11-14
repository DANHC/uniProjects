
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Arbitro;
import modelo.Equipo;
import modelo.ProgramacionPartido;
import modelo.Torneo;


public class ProgramacionPartidoDAO extends DAO{
    
    public boolean insertarPartido(ProgramacionPartido part) {
        String sql = "INSERT INTO programacionPartido(idTorneo, idLocal, idVisitante, idArbitro, fecha)"
                    + " VALUES(?,?,?,?,?)";
        boolean inserted = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, part.getTorneo().getId());
            this.pst.setInt(2, part.getEquipoLocal().getId());
            this.pst.setInt(3, part.getEquipoVisitante().getId());
            this.pst.setInt(4, part.getArbitro().getId());
            this.pst.setString(5, part.getFecha());
            
            inserted = this.pst.executeUpdate() > 0;
            this.pst.close();
            this.con.close();
            if(!inserted) {
                throw new SQLException("NINGUNA FILA INSERTADA DE PARTIDO");
            }
            
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return inserted;
    }
    
    
    public boolean finalizarPartido(int partidoId) {
        String sql = "UPDATE programacionPartido SET finalizado = 1 WHERE id = ?";
        boolean updated = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, partidoId);
            this.pst.executeUpdate();
            this.pst.close();
            updated = true;
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return updated;
        
    }
    
    public ProgramacionPartido getUltimoPartidoProgramado() {
        
        String sql = "SELECT * FROM vw_progPartido WHERE ROWNUM = 1";
        ProgramacionPartido prog = null;
        Torneo tor;
        Equipo local;
        Equipo visitante;
        Arbitro ar;
        ArbitroDAO arDao = new ArbitroDAO();
        EquipoDAO eqDao = new EquipoDAO();
        TorneoDAO torDao = new TorneoDAO();
        
        ResultSet rs;
        
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            if(rs.next()) {
                tor = torDao.getTorneoById(rs.getInt("TORNEOID"));
                local = eqDao.getEquipoById(rs.getInt("LOCALID"));
                visitante = eqDao.getEquipoById(rs.getInt("VISITANTEID"));
                ar = arDao.getArbitroById(rs.getInt("ARBITROID"));
                prog = new ProgramacionPartido(
                        rs.getInt("ID"),
                        tor,
                        local,
                        visitante,
                        ar,
                        rs.getString("FECHA"),
                        rs.getInt("FINALIZADO")
                );
            }
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return prog;
        
    }
    
    
    public ArrayList<ProgramacionPartido> getPartidosProgramados(int torneoId) {
        
        ArrayList<ProgramacionPartido> programaciones = new ArrayList<>();
        ResultSet rs;
        Torneo tor;
        Equipo local;
        Equipo visitante;
        Arbitro ar;
        ArbitroDAO arDao = new ArbitroDAO();
        EquipoDAO eqDao = new EquipoDAO();
        TorneoDAO torDao = new TorneoDAO();
        String sql = "SELECT * FROM vw_progPartidoTodos WHERE TORNEOID = ?";
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, torneoId);
            rs = this.pst.executeQuery();
            
            while(rs.next()) {
                tor = torDao.getTorneoById(rs.getInt("TORNEOID"));
                local = eqDao.getEquipoById(rs.getInt("LOCALID"));
                visitante = eqDao.getEquipoById(rs.getInt("VISITANTEID"));
                ar = arDao.getArbitroById(rs.getInt("ARBITROID"));
                
                programaciones.add(
                        new ProgramacionPartido(
                                rs.getInt("ID"),
                                tor,
                                local,
                                visitante,
                                ar,
                                rs.getString("FECHA"),
                                rs.getInt("FINALIZADO")
                        )
                );
            }
            rs.close();
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return programaciones;
    }
    
    public ProgramacionPartido getProgramacionPartidoById(int id) {
        
        String sql = "SELECT * FROM vw_progPartidoTodos WHERE ID = ?";
        ResultSet rs;
        ProgramacionPartido prog = null;
        Torneo tor;
        Equipo local;
        Equipo visitante;
        Arbitro ar;
        ArbitroDAO arDao = new ArbitroDAO();
        EquipoDAO eqDao = new EquipoDAO();
        TorneoDAO torDao = new TorneoDAO();
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, id);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                tor = torDao.getTorneoById(rs.getInt("TORNEOID"));
                local = eqDao.getEquipoById(rs.getInt("LOCALID"));
                visitante = eqDao.getEquipoById(rs.getInt("VISITANTEID"));
                ar = arDao.getArbitroById(rs.getInt("ARBITROID"));
                prog = new ProgramacionPartido(
                         rs.getInt("ID"),
                         tor,
                         local,
                         visitante,
                         ar,
                         rs.getString("FECHA"),
                         rs.getInt("FINALIZADO")
                );
            }
            rs.close();
            this.pst.close();
            this.con.close();
            
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return prog;
        
    }
    
    
}
