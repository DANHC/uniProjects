
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Entrenador;
import modelo.Equipo;


public class EquipoDAO extends DAO{
    
    public boolean insertar(Equipo equipo) {
        
        String sql = "INSERT INTO equipo(nombre, rutaLogo, idEntrenador, presidente, estadio, ubicacion, detalles, descalificado) "
                    + "VALUES(?,?,?,?,?,?,?,0)";
        
        String sql2 = "INSERT INTO estadisticasEquipo(idequipo, partidosEmpatados, partidosganados, partidosperdidos, partidosjugados, golesafavor, golesencontra, puntos) values(equipo_seq.CURRVAL, 0, 0, 0, 0, 0, 0, 0)";
        
        boolean saved = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setString(1, equipo.getNombre());
            this.pst.setString(2, equipo.getRutaLogo());
            this.pst.setInt(3, equipo.getEntrenador().getId());
            this.pst.setString(4, equipo.getPresidente());
            this.pst.setString(5, equipo.getEstadio());
            this.pst.setString(6, equipo.getUbicacion());
            this.pst.setString(7, equipo.getDetalles());
            
            saved = this.pst.executeUpdate() > 0;
            
            this.pst.close();
            
            if(saved) {
                this.pst = this.getConnection().prepareStatement(sql2);
                saved = this.pst.executeUpdate() > 0;
            }
            
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
     
        return saved;
        
    }
    
    public boolean modificar(Equipo equipo) {
        
        String sql = "UPDATE equipo SET nombre = ?, "
                    + "idEntrenador = ?, "
                    + "presidente = ?, "
                    + "estadio = ?, "
                    + "ubicacion = ?, "
                    + "detalles = ?, "
                    + "descalificado = ? "
                + "WHERE id = ?";
        
        boolean saved = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setString(1, equipo.getNombre());
            this.pst.setInt(2, equipo.getEntrenador().getId());
            this.pst.setString(3, equipo.getPresidente());
            this.pst.setString(4, equipo.getEstadio());
            this.pst.setString(5, equipo.getUbicacion());
            this.pst.setString(6, equipo.getDetalles());
            this.pst.setInt(7, equipo.getDescalificado());
            this.pst.setInt(8, equipo.getId());
            
            saved = this.pst.executeUpdate() > 0;
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return saved;
    }
    
    public ArrayList<Equipo> getEquipos() {
        Equipo equipo;
        ArrayList<Equipo> equipos = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM vw_equipo";
        EntrenadorDAO entDao = new EntrenadorDAO();
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            while(rs.next()) {
                Entrenador ent = entDao.getEntrenadorById(rs.getInt("idEntrenador"));
                equipo = new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rutaLogo"),
                        ent,
                        rs.getString("presidente"),
                        rs.getString("estadio"),
                        rs.getString("ubicacion"),
                        rs.getString("detalles"),
                        rs.getInt("descalificado")
                );
                equipos.add(equipo);
                
 
            }
            
            rs.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return equipos;
        
    }
    
    
    public ArrayList<Equipo> getEquiposProg() {
        Equipo equipo;
        ArrayList<Equipo> equipos = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM vw_equipo";
        EntrenadorDAO entDao = new EntrenadorDAO();
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            while(rs.next()) {
                
                if( this.equipoTieneJugadores(rs.getInt("id")) ) {
                    Entrenador ent = entDao.getEntrenadorById(rs.getInt("idEntrenador"));
                    equipo = new Equipo(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("rutaLogo"),
                            ent,
                            rs.getString("presidente"),
                            rs.getString("estadio"),
                            rs.getString("ubicacion"),
                            rs.getString("detalles"),
                            rs.getInt("descalificado")
                    );
                    equipos.add(equipo);
                } 
 
            }
            
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return equipos;
        
    }
    
    
    public Equipo getEquipoById(int id) {
        String sql = "SELECT * FROM vw_equipo WHERE id = ?";
        ResultSet rs;
        Equipo equipo = null;
        Entrenador ent;
        EntrenadorDAO entDAO = new EntrenadorDAO();
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, id);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                ent = entDAO.getEntrenadorById(rs.getInt("idEntrenador"));
                equipo = new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rutaLogo"),
                        ent,
                        rs.getString("presidente"),
                        rs.getString("estadio"),
                        rs.getString("ubicacion"),
                        rs.getString("detalles"),
                        rs.getInt("descalificado")
                );
            }
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return equipo;
        
    }
    
    
    public boolean equipoTieneJugadores(int idEquipo) {
        
        String sql = "SELECT COUNT(*) AS numeroJugadores FROM jugador WHERE idEquipo = ?";
        ResultSet rs;
        int numJugadores = 0;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, idEquipo);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                numJugadores = rs.getInt("numeroJugadores");
            }
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        } 
        
        return numJugadores >= 11;
        
    }
    
    
    public Equipo getEquipoByIdEntrenador(int idEntrenador) {
        String sql = "SELECT * FROM vw_equipo WHERE IDENTRENADOR = ?";
        ResultSet rs;
        Equipo equipo = null;
        Entrenador ent;
        EntrenadorDAO entDAO = new EntrenadorDAO();
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, idEntrenador);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                ent = entDAO.getEntrenadorById(rs.getInt("idEntrenador"));
                equipo = new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rutaLogo"),
                        ent,
                        rs.getString("presidente"),
                        rs.getString("estadio"),
                        rs.getString("ubicacion"),
                        rs.getString("detalles"),
                        rs.getInt("descalificado")
                );
            }
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return equipo;
    }
    
    
}
