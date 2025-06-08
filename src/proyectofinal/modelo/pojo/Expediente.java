/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class Expediente {
    private int idExpediente;
    private String estatus;
    private int horasAcumuladas;
    private int idProyecto;
    private int idPeriodo;
    private int idEstudiante;

    public Expediente() {
    }

    public Expediente(int idExpediente, String estatus, int horasAcumuladas, int idProyecto, int idPeriodo, int idEstudiante) {
        this.idExpediente = idExpediente;
        this.estatus = estatus;
        this.horasAcumuladas = horasAcumuladas;
        this.idProyecto = idProyecto;
        this.idPeriodo = idPeriodo;
        this.idEstudiante = idEstudiante;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setHorasAcumuladas(int horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
}