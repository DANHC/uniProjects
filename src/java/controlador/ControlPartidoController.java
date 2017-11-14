
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Equipo;
import modelo.EstadisticasEquipo;
import modelo.FaltasPartido;
import modelo.Gol;
import modelo.Partido;
import modelo.ProgramacionPartido;
import modelo.Torneo;
import modelo.dao.EquipoDAO;
import modelo.dao.EstadisticasEquipoDAO;
import modelo.dao.FaltasPartidoDAO;
import modelo.dao.GolDAO;
import modelo.dao.Jugador;
import modelo.dao.JugadorDAO;
import modelo.dao.PartidoDAO;
import modelo.dao.ProgramacionPartidoDAO;
import modelo.dao.TorneoDAO;


public class ControlPartidoController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        JugadorDAO jugDao = new JugadorDAO();
        ProgramacionPartidoDAO progDao = new ProgramacionPartidoDAO();
        PartidoDAO partidoDao = new PartidoDAO();
        TorneoDAO torDao = new TorneoDAO();
        Torneo tor = (Torneo) request.getServletContext().getAttribute("torneo");
        
        if(request.getParameter("partidoTerminado") != null) {
            ArrayList<Gol> goles = new ArrayList<>();
            ArrayList<FaltasPartido> faltas = new ArrayList<>();
            FaltasPartidoDAO faltasDao = new FaltasPartidoDAO();
            GolDAO golesDao = new GolDAO();
            Partido partido;
 
            String[] golesLocal = request.getParameterValues("idGoleadoresLocal");
            String[] golesVisitante = request.getParameterValues("idGoleadoresVisitante");
            String[] faltasId = request.getParameterValues("idJugadorFalta");
            String[] faltasTipo = request.getParameterValues("tipoFalta");
            
            int totalGolesLocal = Integer.parseInt(request.getParameter("totalGolesLocal"));
            int totalGolesVisitante = Integer.parseInt(request.getParameter("totalGolesVisitante"));
            
            int totalCorners = Integer.parseInt(request.getParameter("corners"));
            int totalPenaltis = Integer.parseInt(request.getParameter("penaltis"));
            
            int totalFaltas = (faltasId != null) ? faltasId.length : 0;
            ProgramacionPartido parti = new ProgramacionPartido();
            parti.setId(Integer.parseInt(request.getParameter("idProgramacionPartido")));
            
            partido = new Partido(
                    0,
                    parti,
                    totalGolesLocal + totalGolesVisitante,
                    totalFaltas,
                    totalCorners,
                    totalPenaltis,
                    totalGolesLocal,
                    totalGolesVisitante
            );
            
            boolean inserted = partidoDao.insertar(partido);
            
            
            if(inserted) {
                if(golesLocal != null) {
                    
                    for(int i = 0; i < golesLocal.length; i++) {
                        Jugador jug = new Jugador();
                        jug.setId(Integer.parseInt(golesLocal[i]));
                        goles.add(
                                new Gol(
                                        0,
                                        partido,
                                        jug
                                )
                        );
                    }
                    
                    
                    
                }
                
                if(golesVisitante != null) {
                    
                        for(int i = 0; i < golesVisitante.length; i++) {
                            Jugador jug = new Jugador();
                            jug.setId(Integer.parseInt(golesVisitante[i]));
                            goles.add(
                                    new Gol(
                                            0,
                                            partido,
                                            jug
                                    )
                            );
                        }
                        
                }
                
                int partId = partidoDao.getLastIdPartido();
                
                golesDao.insertarGoles(goles, partId);
                
                if(faltasId != null) {
                    for(int i = 0; i < faltasId.length; i++) {
                        Jugador jug = new Jugador();
                        jug.setId(Integer.parseInt(faltasId[i]));
                        faltas.add(
                                new FaltasPartido(
                                        0,
                                        partido,
                                        jug,
                                        faltasTipo[i]
                                )
                        );
                    }
                    
                    faltasDao.insertarFaltas(faltas, partId);
                }
                
            }
            
            this.procesarEstadisticas(request);
            
            progDao.finalizarPartido(Integer.parseInt(request.getParameter("idProgramacionPartido")));
            
        }
        

        ProgramacionPartido prog = progDao.getUltimoPartidoProgramado();
        
        if(prog == null && tor.getProgramacionFinalizada() == 1) {
            tor.setFinalizado(1);
            torDao.finalizarTorneo(tor.getId());
        } else if(prog == null) {
            request.setAttribute("noProgramados", "No Programados");
        } else {
            ArrayList<Jugador> jugadoresLocal = jugDao.getJugadoresByIdEquipo(prog.getEquipoLocal().getId());
            ArrayList<Jugador> jugadoresVisitante = jugDao.getJugadoresByIdEquipo(prog.getEquipoVisitante().getId());
            request.setAttribute("jugadoresLocal", jugadoresLocal);
            request.setAttribute("jugadoresVisitante", jugadoresVisitante);
            request.setAttribute("prog", prog);
        }
        
        
        request.getRequestDispatcher("paginas/controlPartido.jsp").forward(request, response);
        
    }

    public void procesarEstadisticas(HttpServletRequest request) {
        
        EstadisticasEquipo estadisticasLocal;
        EstadisticasEquipo estadisticasVisitante;
        EstadisticasEquipoDAO estadisticsDao = new EstadisticasEquipoDAO();
        
        EquipoDAO equipoDao = new EquipoDAO();
        
        Equipo equipoLocal = equipoDao.getEquipoById(Integer.parseInt(request.getParameter("equipoLocalId")));
        Equipo equipoVisitante = equipoDao.getEquipoById(Integer.parseInt(request.getParameter("equipoVisitanteId")));
        
        estadisticasLocal = estadisticsDao.getEstadisticaEquipo(equipoLocal.getId());
        estadisticasVisitante = estadisticsDao.getEstadisticaEquipo(equipoVisitante.getId());
        
        String[] golesLocal = request.getParameterValues("idGoleadoresLocal");
        String[] golesVisitante = request.getParameterValues("idGoleadoresVisitante");
        
        estadisticasLocal.setPartidosJugados(estadisticasLocal.getPartidosJugados()+1);
        estadisticasVisitante.setPartidosJugados(estadisticasVisitante.getPartidosJugados()+1);
        
        if(golesLocal == null) {
            golesLocal = new String[0];
        }
        if(golesVisitante == null) {
            golesVisitante = new String[0];
        }
        
        estadisticasLocal.setGolesAfavor(estadisticasLocal.getGolesAfavor()+golesLocal.length);
        estadisticasLocal.setGolesEnContra(estadisticasLocal.getGolesEnContra()+golesVisitante.length);
        estadisticasVisitante.setGolesAfavor(estadisticasVisitante.getGolesAfavor()+golesVisitante.length);
        estadisticasVisitante.setGolesEnContra(estadisticasVisitante.getGolesEnContra()+golesLocal.length);
        
        if( golesLocal.length == golesVisitante.length ) {
            estadisticasLocal.setPartidosEmpatados(estadisticasLocal.getPartidosEmpatados()+1);
            estadisticasVisitante.setPartidosEmpatados(estadisticasVisitante.getPartidosEmpatados()+1);
            estadisticasLocal.setPuntos(estadisticasLocal.getPuntos()+1);
            estadisticasVisitante.setPuntos(estadisticasVisitante.getPuntos()+1);
            
        } else if( (golesLocal.length > golesVisitante.length) ) {
            estadisticasLocal.setPartidosGanados(estadisticasLocal.getPartidosGanados()+1);
            estadisticasLocal.setPuntos(estadisticasLocal.getPuntos()+3);
            estadisticasVisitante.setPartidosPerdidos(estadisticasVisitante.getPartidosPerdidos()+1);
            
        } else {
            estadisticasVisitante.setPartidosGanados(estadisticasVisitante.getPartidosGanados()+1);
            estadisticasVisitante.setPuntos(estadisticasVisitante.getPuntos()+3);
            estadisticasLocal.setPartidosPerdidos(estadisticasLocal.getPartidosPerdidos()+1);
        }
        
        estadisticsDao.actualizarEstadisticas(estadisticasLocal);
        estadisticsDao.actualizarEstadisticas(estadisticasVisitante);
        
        
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
