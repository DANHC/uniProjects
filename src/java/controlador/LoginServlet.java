
package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Entrenador;
import modelo.Usuario;
import modelo.dao.EntrenadorDAO;
import util.AccountVerifier;


public class LoginServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String message;
        AccountVerifier verificador = new AccountVerifier();
        Usuario user;
        HttpSession sesion = request.getSession();
        
        
        if( request.getParameter("verificarUser") != null) {
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contraUsuario = request.getParameter("contraUsuario");
            
            user = verificador.getUser(nombreUsuario, contraUsuario);
            
            if(user != null) {
                sesion.setAttribute("usuario", user);
                this.redirectUser(user, response, sesion);
            } else {
                message = "Credenciales incorrectas o usuario inexistente";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/paginas/login.jsp").forward(request, response);
            }
            
        }
        
        if(request.getParameter("cerrar") != null) {
            sesion = request.getSession(false);
            sesion.invalidate();
            response.sendRedirect("index.jsp");
        }
        
        
    }
    
    
    public void redirectUser(Usuario user, HttpServletResponse response, HttpSession sesion) 
            throws ServletException, IOException {
        
        Entrenador entre;
        EntrenadorDAO entreDao = new EntrenadorDAO();
        
        
        if(user.getTipoUsuario().equals("Administrador")) {
            response.sendRedirect("EntrenadorController");
        } else {
            entre = entreDao.getEntrenadorByIdUser(user.getId());
            sesion.setAttribute("entrenador", entre);
            response.sendRedirect("EntrenadorJugadores");
        }
        
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
