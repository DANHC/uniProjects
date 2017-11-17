
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
    
    
    
    
}
