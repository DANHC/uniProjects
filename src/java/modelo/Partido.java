
package modelo;


public class Partido {
    private int id;
    private ProgramacionPartido prog;
    private int totalGoles;
    private int totalFaltas;
    private int totalCorner;
    private int totalPenalti;
    private int golesLocal;
    private int golesVisitante;
    
    public Partido() {}

    public Partido(int id, ProgramacionPartido prog, int totalGoles, int totalFaltas, int totalCorner, int totalPenalti, int golesLocal, int golesVisitante) {
        this.id = id;
        this.prog = prog;
        this.totalGoles = totalGoles;
        this.totalFaltas = totalFaltas;
        this.totalCorner = totalCorner;
        this.totalPenalti = totalPenalti;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProgramacionPartido getProg() {
        return prog;
    }

    public void setProg(ProgramacionPartido prog) {
        this.prog = prog;
    }

    public int getTotalGoles() {
        return totalGoles;
    }

    public void setTotalGoles(int totalGoles) {
        this.totalGoles = totalGoles;
    }

    public int getTotalFaltas() {
        return totalFaltas;
    }

    public void setTotalFaltas(int totalFaltas) {
        this.totalFaltas = totalFaltas;
    }

    public int getTotalCorner() {
        return totalCorner;
    }

    public void setTotalCorner(int totalCorner) {
        this.totalCorner = totalCorner;
    }

    public int getTotalPenalti() {
        return totalPenalti;
    }

    public void setTotalPenalti(int totalPenalti) {
        this.totalPenalti = totalPenalti;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
    
    
    
}
