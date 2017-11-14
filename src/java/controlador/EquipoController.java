
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
import modelo.dao.EntrenadorDAO;
import modelo.dao.EquipoDAO;


@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
             maxFileSize=1024*1024*10,      // 10MB
             maxRequestSize=1024*1024*50)
public class EquipoController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        EquipoDAO dao = new EquipoDAO();
        EntrenadorDAO entDao = new EntrenadorDAO();
        Equipo equipo;
        Entrenador entrenador;
        ArrayList<Entrenador> entrenadores;
        ArrayList<Equipo> equipos;
        String msj = null;
        Equipo equipoConEntrenador;
        boolean inserted = false;
        
        if(request.getParameter("insertar") != null) {

            entrenador = entDao.getEntrenadorById(Integer.parseInt(request.getParameter("equipoEntrenador")));
            equipoConEntrenador = dao.getEquipoByIdEntrenador(entrenador.getId());
            
            
            if(equipoConEntrenador == null) {
                
                Part part = request.getPart("equipoLogo");
                String fileName = this.extractFileName(part);

                String savePath = "C:\\Users\\Daniel\\Documents\\NetBeansProjects\\ProyectoFutbol\\web\\img\\";

                File file = new File(savePath, fileName);

                try (InputStream input = part.getInputStream()) {
                    Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                equipo = new Equipo(
                        0,
                        request.getParameter("equipoNombre"),
                        fileName,
                        entrenador,
                        request.getParameter("equipoPresidente"),
                        request.getParameter("equipoEstadio"),
                        request.getParameter("equipoUbicacion"),
                        request.getParameter("equipoDescripcion"),
                        0
                ); 

                inserted = dao.insertar(equipo);
                
                if(!inserted) {
                    msj = "Equipo No Insertado";
                }
                
                
            } else {
                msj = "Equipo No Insertado, Cada equipo solo puede tener 1 entrenador.";
            }
            
            
            
        }
        
        if(request.getParameter("modificar") != null) {
            entrenador = entDao.getEntrenadorById(Integer.parseInt(request.getParameter("equipoEntrenador")));
            
            equipo = new Equipo(
                    Integer.parseInt(request.getParameter("equipoId")),
                    request.getParameter("equipoNombre"),
                    "",
                    entrenador,
                    request.getParameter("equipoPresidente"),
                    request.getParameter("equipoEstadio"),
                    request.getParameter("equipoUbicacion"),
                    request.getParameter("equipoDescripcion"),
                    Integer.parseInt(request.getParameter("equipoEstado"))
            ); 
            
            boolean inserted2 = dao.modificar(equipo);
            
            if(!inserted2) {
                msj = "Equipo No Modificado";
            }
        }
        
        
        entrenadores = entDao.getEntrenadores();
        equipos = dao.getEquipos();
        request.setAttribute("entrenadores", entrenadores);
        request.setAttribute("equipos", equipos);
        request.setAttribute("msj", msj);
        request.getRequestDispatcher("paginas/mantenimientoEquipos.jsp").forward(request, response);
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
