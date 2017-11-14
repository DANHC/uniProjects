
package util;

import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexion {
    
    private static DataSource ds;
    
    static {
        try {
            ds = (DataSource) new InitialContext().lookup("jdbc/futbolCon");
        } catch (NamingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    /*
    private Connection con;
    
    public Conexion() throws ClassNotFoundException, SQLException {
        this.connect();
    }
    
    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        this.con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "root", "root");
    }
    
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if( this.con.isClosed() ) {
            this.connect();
        }
        
        return this.con;
    }
    
    public void disconnect() throws SQLException {
        this.con.close();
    }
    */
    
    
}
