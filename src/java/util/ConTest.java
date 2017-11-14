
package util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class ConTest {
    
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
