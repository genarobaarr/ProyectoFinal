/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class EvaluacionOVAfirmacion {
    private int idAfirmacionEvaluacionOV;
    private String descripcion;
    private int idCategoriaEvaluacionOV;

    public EvaluacionOVAfirmacion() {
    }

    public EvaluacionOVAfirmacion(int idAfirmacionEvaluacionOV, String descripcion, int idCategoriaEvaluacionOV) {
        this.idAfirmacionEvaluacionOV = idAfirmacionEvaluacionOV;
        this.descripcion = descripcion;
        this.idCategoriaEvaluacionOV = idCategoriaEvaluacionOV;
    }

    public int getIdAfirmacionEvaluacionOV() {
        return idAfirmacionEvaluacionOV;
    }

    public void setIdAfirmacionEvaluacionOV(int idAfirmacionEvaluacionOV) {
        this.idAfirmacionEvaluacionOV = idAfirmacionEvaluacionOV;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCategoriaEvaluacionOV() {
        return idCategoriaEvaluacionOV;
    }

    public void setIdCategoriaEvaluacionOV(int idCategoriaEvaluacionOV) {
        this.idCategoriaEvaluacionOV = idCategoriaEvaluacionOV;
    }
}