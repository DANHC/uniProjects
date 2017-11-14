
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Entrenador;
import modelo.Usuario;
import modelo.dao.EntrenadorDAO;


public class EntrenadorController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        Entrenador entrenador;
        Usuario user;
        EntrenadorDAO dao = new EntrenadorDAO();
        ArrayList<Entrenador> entrenadores;
        String msj = null;
        
        if(request.getParameter("insertar") != null) {
            entrenador = new Entrenador(
                    0,
                    request.getParameter("entrenadorNombre"),
                    request.getParameter("entrenadorApellido"),
                    Integer.parseInt(request.getParameter("entrenadorEdad")),
                    request.getParameter("entrenadorSexo"),
                    request.getParameter("entrenadorNacionalidad"),
                    0,
                    new Usuario(
                            0,
                            request.getParameter("entrenadorUsuario"),
                            request.getParameter("entrenadorContra"),
                            "Entrenador"
                    )
            );
            
            boolean inserted = dao.insertar(entrenador);
            
            if(!inserted) {
                msj = "Entrenador no insertado";
            }
            
        }
        
        if(request.getParameter("modificar") != null) {
            entrenador = new Entrenador(
                    Integer.parseInt(request.getParameter("entrenadorId")),
                    request.getParameter("entrenadorNombre"),
                    request.getParameter("entrenadorApellido"),
                    Integer.parseInt(request.getParameter("entrenadorEdad")),
                    request.getParameter("entrenadorSexo"),
                    request.getParameter("entrenadorNacionalidad"),
                    Integer.parseInt(request.getParameter("entrenadorEstado")),
                    new Usuario(
                            Integer.parseInt(request.getParameter("entrenadorUsuarioId")),
                            request.getParameter("entrenadorUsuario"),
                            request.getParameter("entrenadorContra"),
                            "Entrenador"
                    )
            );
            
            boolean modificado = dao.modificar(entrenador);
            
            if(!modificado) {
                msj = "Entrenador No Modificado";
            }
            
        }
        
        if(request.getParameter("eliminarEntrenador") != null) {
            entrenador = new Entrenador(
                    Integer.parseInt(request.getParameter("eliminarEntrenador")),
                    "",
                    "",
                    0,
                    "",
                    "",
                    1,
                    new Usuario(
                            Integer.parseInt(request.getParameter("usuarioId")),
                            "",
                            "",
                            ""
                    )
            );
            
            boolean eliminado = dao.eliminar(entrenador);
            
            if(!eliminado) {
                msj = "Entrenador No Eliminado";
            }
        }
        
        entrenadores = dao.getEntrenadores();
        request.setAttribute("entrenadores", entrenadores);
        request.setAttribute("msj", msj);
        request.getRequestDispatcher("paginas/administrador.jsp").forward(request, response);
        
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
