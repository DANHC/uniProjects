
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import util.Conexion;


public abstract class DAO {
    protected Connection con;
    protected PreparedStatement pst;
    protected Statement st;
    
    public DAO() {
        try {
            this.con = Conexion.getConnection();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public Connection getConnection() {
        try {
            if (this.con.isClosed()) {
                this.con = Conexion.getConnection();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return this.con;
    }
    
    
}
