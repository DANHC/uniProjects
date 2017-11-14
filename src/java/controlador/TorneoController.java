
package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Torneo;
import modelo.dao.TorneoDAO;


public class TorneoController extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        TorneoDAO torDao = new TorneoDAO();
        Torneo tor;
        Torneo torneoNuevo;
                
        if(request.getParameter("insertar") != null) {
            
            Torneo torneoActual = (Torneo) request.getServletContext().getAttribute("torneo");
        
            if(torneoActual.getFinalizado() == 0) {
                request.setAttribute("msjTorni", "Error: El torneo actual aún no ha finalizado");
            } else {
                tor = new Torneo(
                        0,
                        request.getParameter("torneoNombre"),
                        0,
                        0
                );
                torDao.registrarTorneo(tor);
                try {
                    torneoNuevo = torDao.getUltimoTorneo();
                    request.getServletContext().removeAttribute("torneo");
                    request.getServletContext().setAttribute("torneo", torneoNuevo);
                } catch (SQLException ex) {
                    System.out.println("TorneoController: "+ex.toString());
                }
                
            }
            
            
        }
        ArrayList<Torneo> torneos = torDao.getAllTorneos();
        request.setAttribute("torneos", torneos);
        request.getRequestDispatcher("paginas/torneos.jsp").forward(request, response);
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
