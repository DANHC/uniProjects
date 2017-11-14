
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Equipo;
import modelo.EstadisticasEquipo;


public class EstadisticasEquipoDAO extends DAO{
    
    public boolean actualizarEstadisticas(EstadisticasEquipo estadisticasEquipo) {
        
        boolean updated = false;
        String sql = "UPDATE estadisticasEquipo SET "
                        + "partidosEmpatados = ?, "
                        + "partidosGanados = ?, "
                        + "partidosPerdidos = ?, "
                        + "partidosJugados = ?, "
                        + "golesAFavor = ?, "
                        + "golesEnContra = ?, "
                        + "puntos = ? "
                    + "WHERE idEquipo = ?";
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, estadisticasEquipo.getPartidosEmpatados());
            this.pst.setInt(2, estadisticasEquipo.getPartidosGanados());
            this.pst.setInt(3, estadisticasEquipo.getPartidosPerdidos());
            this.pst.setInt(4, estadisticasEquipo.getPartidosJugados());
            this.pst.setInt(5, estadisticasEquipo.getGolesAfavor());
            this.pst.setInt(6, estadisticasEquipo.getGolesEnContra());
            this.pst.setInt(7, estadisticasEquipo.getPuntos());
            this.pst.setInt(8, estadisticasEquipo.getEquipo().getId());
            
            updated = this.pst.executeUpdate() > 0;
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return updated;
        
    }
    
    
    public EstadisticasEquipo getEstadisticaEquipo(int equipoId) {
        
        String sql = "SELECT * FROM estadisticasEquipo WHERE idEquipo = ?";
        ResultSet rs;
        EstadisticasEquipo statistics = null;
        Equipo equipo;
        EquipoDAO eqDao = new EquipoDAO();
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, equipoId);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                equipo = eqDao.getEquipoById(rs.getInt("idEquipo"));
                statistics = new EstadisticasEquipo(
                        rs.getInt("id"),
                        equipo,
                        rs.getInt("partidosEmpatados"),
                        rs.getInt("partidosGanados"),
                        rs.getInt("partidosPerdidos"),
                        rs.getInt("partidosJugados"),
                        rs.getInt("golesAFavor"),
                        rs.getInt("golesEnContra"),
                        rs.getInt("puntos")
                );
            }
            rs.close();
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return statistics;
        
    }
    
    public ArrayList<EstadisticasEquipo> getAllEstadisticas() {
        
        ArrayList<EstadisticasEquipo> estadisticas = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM vw_equiposEstadisticas";
        EquipoDAO equipDao = new EquipoDAO();
        
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            while(rs.next()) {
                Equipo eq = equipDao.getEquipoById(rs.getInt("ID"));
                estadisticas.add(
                        new EstadisticasEquipo(
                              rs.getInt("POS"),
                              eq,
                              rs.getInt("EM"),
                              rs.getInt("PG"),
                              rs.getInt("PER"),
                              rs.getInt("PJUG"),
                              rs.getInt("GLFAV"),
                              rs.getInt("GLCON"),
                              rs.getInt("PNTS")
                        )
                );
            }
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return estadisticas;
    }
    
    
}
