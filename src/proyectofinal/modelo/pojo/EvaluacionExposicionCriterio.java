/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.modelo.pojo;

public class EvaluacionExposicionCriterio {
    private int idCriterioEvvaluacionExposicion;
    private String criterio; //-- Dominio del tema, Formalidad de la presentación, Organización del equipo
    private String nivel;    //Excelente, Satisfactorio, Puede mejorar, No cumple con lo requerido
    private double valor;    //   1.0,         0.85,           0.7,                0.5
    private int idEvaluacionExposicion;

    public EvaluacionExposicionCriterio() {
    }

    public EvaluacionExposicionCriterio(int idCriterioEvvaluacionExposicion, String criterio, String nivel, double valor, int idEvaluacionExposicion) {
        this.idCriterioEvvaluacionExposicion = idCriterioEvvaluacionExposicion;
        this.criterio = criterio;
        this.nivel = nivel;
        this.valor = valor;
        this.idEvaluacionExposicion = idEvaluacionExposicion;
    }

    public int getIdCriterioEvvaluacionExposicion() {
        return idCriterioEvvaluacionExposicion;
    }

    public void setIdCriterioEvvaluacionExposicion(int idCriterioEvvaluacionExposicion) {
        this.idCriterioEvvaluacionExposicion = idCriterioEvvaluacionExposicion;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdEvaluacionExposicion() {
        return idEvaluacionExposicion;
    }

    public void setIdEvaluacionExposicion(int idEvaluacionExposicion) {
        this.idEvaluacionExposicion = idEvaluacionExposicion;
    }
}