/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class EvaluacionOV {
    private int idEvaluacionOV;
    private String comentarios;
    private String fecha;
    private double puntajeFinal;
    private int idExpediente;

    public EvaluacionOV() {
    }

    public EvaluacionOV(int idEvaluacionOV, String comentarios, String fecha, double puntajeFinal, int idExpediente) {
        this.idEvaluacionOV = idEvaluacionOV;
        this.comentarios = comentarios;
        this.fecha = fecha;
        this.puntajeFinal = puntajeFinal;
        this.idExpediente = idExpediente;
    }

    public int getIdEvaluacionOV() {
        return idEvaluacionOV;
    }

    public void setIdEvaluacionOV(int idEvaluacionOV) {
        this.idEvaluacionOV = idEvaluacionOV;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPuntajeFinal() {
        return puntajeFinal;
    }

    public void setPuntajeFinal(double puntajeFinal) {
        this.puntajeFinal = puntajeFinal;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }
}