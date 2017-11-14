
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Arbitro;
import modelo.dao.ArbitroDAO;


public class ArbitroController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        ArbitroDAO arDao = new ArbitroDAO();
        ArrayList<Arbitro> arbitros;
        Arbitro arbitro = new Arbitro();
        String msj = "";
        
        if(request.getParameter("insertar") != null) {
            
            arbitro = new Arbitro(
                    0,
                    request.getParameter("arbitroNombre"),
                    request.getParameter("arbitroApellido"),
                    Integer.parseInt(request.getParameter("arbitroEdad")),
                    request.getParameter("arbitroSexo"),
                    request.getParameter("arbitroNacionalidad"),
                    0
            );
            
            boolean inserted = arDao.insertar(arbitro);
            
            if(!inserted) {
                msj = "Arbitro No Insertado";
            } else {
                msj = "INSERTADO";
            }
            
        }
        
        if(request.getParameter("modificar") != null) {
            arbitro = new Arbitro(
                    Integer.parseInt(request.getParameter("arbitroId")),
                    request.getParameter("arbitroNombre"),
                    request.getParameter("arbitroApellido"),
                    Integer.parseInt(request.getParameter("arbitroEdad")),
                    request.getParameter("arbitroSexo"),
                    request.getParameter("arbitroNacionalidad"),
                    Integer.parseInt(request.getParameter("arbitroEstado"))
            );
            
            boolean inserted = arDao.modificar(arbitro);
            
            if(!inserted) {
                msj = "Arbitro No Insertado";
            } else {
                msj = "MODIFICADO";
            }
        }
       
        arbitros = arDao.getArbitros();
        request.setAttribute("arbitros", arbitros);
        request.getRequestDispatcher("paginas/mantenimientoArbitros.jsp").forward(request, response);
        
        
        
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
