/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class EvaluacionOVResultado {
    private int idResultadoEvaluacionOV;
    private int idCriterioEvaluacionOV;
    private int idAfirmacionEvaluacionOV;
    private int idEvaluacionOV;

    public EvaluacionOVResultado() {
    }

    public EvaluacionOVResultado(int idResultadoEvaluacionOV, int idCriterioEvaluacionOV, int idAfirmacionEvaluacionOV, int idEvaluacionOV) {
        this.idResultadoEvaluacionOV = idResultadoEvaluacionOV;
        this.idCriterioEvaluacionOV = idCriterioEvaluacionOV;
        this.idAfirmacionEvaluacionOV = idAfirmacionEvaluacionOV;
        this.idEvaluacionOV = idEvaluacionOV;
    }

    public int getIdResultadoEvaluacionOV() {
        return idResultadoEvaluacionOV;
    }

    public void setIdResultadoEvaluacionOV(int idResultadoEvaluacionOV) {
        this.idResultadoEvaluacionOV = idResultadoEvaluacionOV;
    }

    public int getIdCriterioEvaluacionOV() {
        return idCriterioEvaluacionOV;
    }

    public void setIdCriterioEvaluacionOV(int idCriterioEvaluacionOV) {
        this.idCriterioEvaluacionOV = idCriterioEvaluacionOV;
    }

    public int getIdAfirmacionEvaluacionOV() {
        return idAfirmacionEvaluacionOV;
    }

    public void setIdAfirmacionEvaluacionOV(int idAfirmacionEvaluacionOV) {
        this.idAfirmacionEvaluacionOV = idAfirmacionEvaluacionOV;
    }

    public int getIdEvaluacionOV() {
        return idEvaluacionOV;
    }

    public void setIdEvaluacionOV(int idEvaluacionOV) {
        this.idEvaluacionOV = idEvaluacionOV;
    }
}