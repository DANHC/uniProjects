
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Arbitro;
import modelo.Equipo;
import modelo.ProgramacionPartido;
import modelo.Torneo;
import modelo.dao.ArbitroDAO;
import modelo.dao.EquipoDAO;
import modelo.dao.ProgramacionPartidoDAO;
import modelo.dao.TorneoDAO;


public class ProgramacionPartidoController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        EquipoDAO equipoDAO = new EquipoDAO();
        ArrayList<Equipo> equipos;
        ArbitroDAO arbitDao = new ArbitroDAO();
        ArrayList<Arbitro> arbitros;
        ProgramacionPartidoDAO progDao = new ProgramacionPartidoDAO();
        ProgramacionPartido part;
        Torneo tor = (Torneo) request.getServletContext().getAttribute("torneo");
        Equipo equipoLocal;
        Equipo equipoVisitante;
        Arbitro arb;
        String msjTor = "";
        TorneoDAO torDao = new TorneoDAO();
        
        
        if(request.getParameter("submited") != null) {
            String[] localesId = request.getParameterValues("localesId");
            String[] visitantesId = request.getParameterValues("visitantesId");
            String[] fechas = request.getParameterValues("fechas");
            String[] arbitrosId = request.getParameterValues("arbitros");
            
            boolean inserted = false;
            
            for(int i = 0; i < localesId.length; i++) {
                equipoLocal = new Equipo();
                equipoLocal.setId(Integer.parseInt(localesId[i]));
                equipoVisitante = new Equipo();
                equipoVisitante.setId(Integer.parseInt(visitantesId[i]));
                arb = new Arbitro();
                arb.setId(Integer.parseInt(arbitrosId[i]));
                
                part = new ProgramacionPartido(
                        0,
                        tor,
                        equipoLocal,
                        equipoVisitante,
                        arb,
                        fechas[i],
                        0
                );
                
               inserted =  progDao.insertarPartido(part);
               
                
            }
            
            
            if(inserted) {
                msjTor = "La Programación de Partidos para el torneo: "+tor.getNombreTorneo()+" finalizó.";
                tor.setProgramacionFinalizada(1);
                torDao.finalizarProgramacionTorneo(tor);
                request.getServletContext().setAttribute("msj", msjTor);
            }
            
            
        }
        
        
        arbitros = arbitDao.getArbitros();
        equipos = equipoDAO.getEquiposProg();
        request.setAttribute("equipos", equipos);
        request.setAttribute("arbitros", arbitros);
        request.getRequestDispatcher("paginas/programacionPartidos.jsp").forward(request, response);
        
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
