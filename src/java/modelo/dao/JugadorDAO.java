
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Equipo;


public class JugadorDAO extends DAO{
    
    
    public boolean insertar(Jugador jugador) {
        String sql = "INSERT INTO jugador(idEquipo, nombre, apellido, edad, estatura, nacionalidad, numeroJugador, posicion, rutaFoto, descalificado) "
                + "VALUES(?,?,?,?,?,?,?,?,?, 0)";
        
        boolean inserted = false;
        
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, jugador.getEquipo().getId());
            this.pst.setString(2, jugador.getNombres());
            this.pst.setString(3, jugador.getApelllidos());
            this.pst.setInt(4, jugador.getEdad());
            this.pst.setDouble(5, jugador.getEstatura());
            this.pst.setString(6, jugador.getNacionalidad());
            this.pst.setInt(7, jugador.getNumeroJugador());
            this.pst.setString(8, jugador.getPosicion());
            this.pst.setString(9, jugador.getRutaFoto());
            
            inserted = this.pst.executeUpdate() > 0;
            this.pst.close();
            this.con.close();
            if(!inserted) {
                throw new SQLException("Jugador No Insertado");
            }
            
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return inserted;
        
    }
    
    
    public boolean modificar(Jugador jugador) {
        
        String sql =  "UPDATE jugador SET idEquipo = ?, "
                    + "nombre = ?, "
                    + "apellido = ?, "
                    + "edad = ?, "
                    + "estatura = ?, "
                    + "nacionalidad = ?,"
                    + "numeroJugador = ?, "
                    + "posicion = ?, "
                    + "descalificado = ?"
                + " WHERE id = ?";
        
        boolean updated = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, jugador.getEquipo().getId());
            this.pst.setString(2, jugador.getNombres());
            this.pst.setString(3, jugador.getApelllidos());
            this.pst.setInt(4, jugador.getEdad());
            this.pst.setDouble(5, jugador.getEstatura());
            this.pst.setString(6, jugador.getNacionalidad());
            this.pst.setInt(7, jugador.getNumeroJugador());
            this.pst.setString(8, jugador.getPosicion());
            this.pst.setInt(9, jugador.getDescalificado());
            this.pst.setInt(10, jugador.getId());
            
            updated = this.pst.executeUpdate() > 0;
            this.pst.close();
            this.con.close();
            if(!updated) {
                throw new SQLException("Jugador No Modificado");
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return updated;
        
    }
    
    public ArrayList<Jugador> getJugadores() {
        String sql = "SELECT * FROM vw_jugador";
        ResultSet rs;
        ArrayList<Jugador> jugadores = new ArrayList<>();
        Equipo equipo;
        EquipoDAO eqDAO = new EquipoDAO();
        Jugador jug;
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            while(rs.next()) {
                equipo = eqDAO.getEquipoById(rs.getInt("idEquipo"));
                jug = new Jugador(
                        rs.getInt("idJugador"),
                        equipo,
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getDouble("estatura"),
                        rs.getString("nacionalidad"),
                        rs.getInt("numeroJugador"),
                        rs.getString("posicion"),
                        rs.getString("foto"),
                        rs.getInt("descalificado")
                );
                
                jugadores.add(jug);
            }
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return jugadores;
        
    }
    
    public Jugador getJugadorById(int id) {
        String sql = "SELECT * FROM vw_jugador WHERE idJugador = ?";
        ResultSet rs;
        Jugador jug = null;
        Equipo equipo;
        EquipoDAO eqDAO = new EquipoDAO();
        
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, id);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                equipo = eqDAO.getEquipoById(rs.getInt("idEquipo"));
                jug = new Jugador(
                        rs.getInt("idJugador"),
                        equipo,
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getDouble("estatura"),
                        rs.getString("nacionalidad"),
                        rs.getInt("numeroJugador"),
                        rs.getString("posicion"),
                        rs.getString("foto"),
                        rs.getInt("descalificado")
                );
            }
            rs.close();
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return jug;
        
    }
    
    public ArrayList<Jugador> getJugadoresByIdEquipo(int id) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM vw_jugador WHERE IDEQUIPO = ?";
        EquipoDAO eqDao = new EquipoDAO();
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, id);
            rs = this.pst.executeQuery();
            
            while(rs.next()) {
                Equipo eq = eqDao.getEquipoById(rs.getInt("IDEQUIPO"));
                Jugador jug = new Jugador(
                        rs.getInt("idJugador"),
                        eq,
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getDouble("estatura"),
                        rs.getString("nacionalidad"),
                        rs.getInt("numeroJugador"),
                        rs.getString("posicion"),
                        rs.getString("foto"),
                        rs.getInt("descalificado")
                );
                jugadores.add(jug);
            }
            rs.close();
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return jugadores;
        
    }
    
    public ArrayList<Jugador> getJugadorByNombreOrEquipo(String search) {
        
        search = search
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        
        search = search.toUpperCase();
        
        String sql = "SELECT * FROM vw_jugador WHERE upper(nombre) LIKE ? OR upper(apellido) LIKE ? OR                                          upper(equipo) LIKE ?";
        
        ResultSet rs;
        ArrayList<Jugador> jugadores = new ArrayList<>();
        EquipoDAO eqDao = new EquipoDAO();
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setString(1, "%"+search+"%");
            this.pst.setString(2, "%"+search+"%");
            this.pst.setString(3, "%"+search+"%");
            rs = this.pst.executeQuery();
            
            while(rs.next()) {
                Equipo eq = eqDao.getEquipoById(rs.getInt("IDEQUIPO"));
                Jugador jug = new Jugador(
                        rs.getInt("idJugador"),
                        eq,
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getDouble("estatura"),
                        rs.getString("nacionalidad"),
                        rs.getInt("numeroJugador"),
                        rs.getString("posicion"),
                        rs.getString("foto"),
                        rs.getInt("descalificado")
                );
                jugadores.add(jug);
            }
            rs.close();
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println("JugadorDAO: "+ex.toString());
        }
        
        return jugadores;
        
        
    }
    

    
}
