/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class EvaluacionOVCriterio {
    private int idCriterioEvaluacionOV;
    private String nombre;
    private double valor;

    public EvaluacionOVCriterio() {
    }

    public EvaluacionOVCriterio(int idCriterioEvaluacionOV, String nombre, double valor) {
        this.idCriterioEvaluacionOV = idCriterioEvaluacionOV;
        this.nombre = nombre;
        this.valor = valor;
    }

    public int getIdCriterioEvaluacionOV() {
        return idCriterioEvaluacionOV;
    }

    public void setIdCriterioEvaluacionOV(int idCriterioEvaluacionOV) {
        this.idCriterioEvaluacionOV = idCriterioEvaluacionOV;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}