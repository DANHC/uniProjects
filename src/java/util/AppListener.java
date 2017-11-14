
package util;

import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import modelo.Torneo;
import modelo.dao.TorneoDAO;

public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TorneoDAO tornDao = new TorneoDAO();
        Torneo tor;
        
        try {
            tor = tornDao.getUltimoTorneo();
            sce.getServletContext().setAttribute("torneo", tor);
            System.out.println(tor.getNombreTorneo());
            System.out.println(tor.getProgramacionFinalizada());
        } catch(SQLException  ex) {
            System.out.println(ex.toString());
        }
 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Torneo tor = (Torneo) sce.getServletContext().getAttribute("torneo");
        
        try {
            sce.getServletContext().removeAttribute("torneo");
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
    }

    
   
}
