
package modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Arbitro;


public class ArbitroDAO extends DAO{
    
    public boolean insertar(Arbitro arbitro) {
        String sql = "INSERT INTO Arbitro(nombres, apellidos, edad, sexo, nacionalidad, eliminado) "
                + "VALUES(?,?,?,?,?,?)";
        
        boolean inserted = false;
        
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setString(1, arbitro.getNombres());
            this.pst.setString(2, arbitro.getApellidos());
            this.pst.setInt(3, arbitro.getEdad());
            this.pst.setString(4, arbitro.getSexo());
            this.pst.setString(5, arbitro.getNacionalidad());
            this.pst.setInt(6, arbitro.getEliminado());
            
            inserted = this.pst.executeUpdate() > 0;
            this.pst.close();
            this.con.close();
            if(!inserted) {
                throw new SQLException("Arbitro No Insertado");
            }
            
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return inserted;
        
    }
    
    
    public boolean modificar(Arbitro arbitro) {
        
        String sql =  "UPDATE arbitro SET "
                    + "nombres = ?, "
                    + "apellidos = ?, "
                    + "edad = ?, "
                    + "sexo = ?, "
                    + "nacionalidad = ?,"
                    + "eliminado = ?"
                + " WHERE id = ?";
        
        boolean updated = false;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setString(1, arbitro.getNombres());
            this.pst.setString(2, arbitro.getApellidos());
            this.pst.setInt(3, arbitro.getEdad());
            this.pst.setString(4, arbitro.getSexo());
            this.pst.setString(5, arbitro.getNacionalidad());
            this.pst.setInt(6, arbitro.getEliminado());
            this.pst.setInt(7, arbitro.getId());
            
            updated = this.pst.executeUpdate() > 0;
            this.pst.close();
            this.con.close();
            if(!updated) {
                throw new SQLException("Arbitro No Modificado");
            }
        } catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return updated;
        
    }
    
    public ArrayList<Arbitro> getArbitros() {
        String sql = "SELECT * FROM arbitro";
        ResultSet rs;
        ArrayList<Arbitro> arbitros = new ArrayList<>();
        Arbitro ar;
        
        try {
            this.st = this.getConnection().createStatement();
            rs = this.st.executeQuery(sql);
            
            while(rs.next()) {
                ar = new Arbitro(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getInt("edad"),
                        rs.getString("sexo"),
                        rs.getString("nacionalidad"),
                        rs.getInt("eliminado")
                );
                
                arbitros.add(ar);
            }
            rs.close();
            this.st.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return arbitros;
        
    }
    
    public Arbitro getArbitroById(int id) {
        String sql = "SELECT * FROM arbitro WHERE id = ?";
        Arbitro ar = null;
        ResultSet rs;
        
        try {
            this.pst = this.getConnection().prepareStatement(sql);
            this.pst.setInt(1, id);
            rs = this.pst.executeQuery();
            
            if(rs.next()) {
                ar = new Arbitro(
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getInt("edad"),
                    rs.getString("sexo"),
                    rs.getString("nacionalidad"),
                    rs.getInt("eliminado")  
                );
            }
            rs.close();
            this.pst.close();
            this.con.close();
        } catch(SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        return ar;
    }
    
    
}
