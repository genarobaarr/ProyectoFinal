/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.modelo.pojo;

public class EvaluacionExposicion {
    private int idEvaluacionExposicion;
    private String comentarios;
    private double puntajeFinal;
    private int idExpediente;
    private int idAcademicoEvaluador;

    public EvaluacionExposicion() {
    }

    public EvaluacionExposicion(int idEvaluacionExposicion, String comentarios, double puntajeFinal, int idExpediente, int idAcademicoEvaluador) {
        this.idEvaluacionExposicion = idEvaluacionExposicion;
        this.comentarios = comentarios;
        this.puntajeFinal = puntajeFinal;
        this.idExpediente = idExpediente;
        this.idAcademicoEvaluador = idAcademicoEvaluador;
    }

    public int getIdEvaluacionExposicion() {
        return idEvaluacionExposicion;
    }

    public void setIdEvaluacionExposicion(int idEvaluacionExposicion) {
        this.idEvaluacionExposicion = idEvaluacionExposicion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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

    public int getIdAcademicoEvaluador() {
        return idAcademicoEvaluador;
    }

    public void setIdAcademicoEvaluador(int idAcademicoEvaluador) {
        this.idAcademicoEvaluador = idAcademicoEvaluador;
    }
}