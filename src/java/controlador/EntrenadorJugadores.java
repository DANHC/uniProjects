
package controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.Entrenador;
import modelo.Equipo;
import modelo.dao.EquipoDAO;
import modelo.dao.Jugador;
import modelo.dao.JugadorDAO;

@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
             maxFileSize=1024*1024*10,      // 10MB
             maxRequestSize=1024*1024*50)
public class EntrenadorJugadores extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        Equipo equipo;
        EquipoDAO eqDAO = new EquipoDAO();
        String msj = null;
        Jugador jug;
        JugadorDAO jugDao = new JugadorDAO();
        Equipo equipoEntre;
        ArrayList<Jugador> jugadores;
        Entrenador entrenador = (Entrenador) request.getSession().getAttribute("entrenador");
        
        
        if(request.getParameter("insertar") != null) {
            
            Part part = request.getPart("jugadorFoto");
            String fileName = this.extractFileName(part);
            
            String savePath = "C:\\Users\\Daniel\\Documents\\NetBeansProjects\\ProyectoFutbol\\web\\img\\";
            
            File file = new File(savePath, fileName);
            
            try (InputStream input = part.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            
            equipo = eqDAO.getEquipoById(Integer.parseInt(request.getParameter("jugadorEquipo")));
            
            jug = new Jugador(
                    0,
                    equipo,
                    request.getParameter("jugadorNombre"),
                    request.getParameter("jugadorApellido"),
                    Integer.parseInt(request.getParameter("jugadorEdad")),
                    Double.parseDouble(request.getParameter("jugadorEstatura")),
                    request.getParameter("jugadorNacionalidad"),
                    Integer.parseInt(request.getParameter("jugadorNumero")),
                    request.getParameter("jugadorPosicion"),
                    fileName,
                    0
                    
            );
            
            boolean inserted = jugDao.insertar(jug);
            
            if(!inserted) {
                msj = "Jugador No Insertado";
            } else {
                msj = "INSERTADI";
            }
            
        }
        
        if(request.getParameter("modificar") != null) {
            equipo = eqDAO.getEquipoById(Integer.parseInt(request.getParameter("jugadorEquipo")));
            
            jug = new Jugador(
                    Integer.parseInt(request.getParameter("jugadorId")),
                    equipo,
                    request.getParameter("jugadorNombre"),
                    request.getParameter("jugadorApellido"),
                    Integer.parseInt(request.getParameter("jugadorEdad")),
                    Double.parseDouble(request.getParameter("jugadorEstatura")),
                    request.getParameter("jugadorNacionalidad"),
                    Integer.parseInt(request.getParameter("jugadorNumero")),
                    request.getParameter("jugadorPosicion"),
                    "",
                    Integer.parseInt(request.getParameter("jugadorDescalificado"))
            );
            
            boolean inserted = jugDao.modificar(jug);
            
            if(!inserted) {
                msj = "Jugador No Modificado";
            } else {
                msj = "MODIFICADO EXITOSO";
            }
        }
        
        equipoEntre = eqDAO.getEquipoByIdEntrenador(entrenador.getId());
        jugadores = jugDao.getJugadoresByIdEquipo(equipoEntre.getId());
        request.setAttribute("equipo", equipoEntre);
        request.setAttribute("jugadores", jugadores);
        request.setAttribute("msj", msj);
        request.getRequestDispatcher("paginas/jugadoresEntrenador.jsp").forward(request, response);
        
    }
    
    public String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        
        for(String s : items) {
            if(s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        
        return "";
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
