/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class EvaluacionOVCategoria {
    private int idCategoriaEvaluacionOV;
    private String nombre;

    public EvaluacionOVCategoria() {
    }

    public EvaluacionOVCategoria(int idCategoriaEvaluacionOV, String nombre) {
        this.idCategoriaEvaluacionOV = idCategoriaEvaluacionOV;
        this.nombre = nombre;
    }

    public int getIdCategoriaEvaluacionOV() {
        return idCategoriaEvaluacionOV;
    }

    public void setIdCategoriaEvaluacionOV(int idCategoriaEvaluacionOV) {
        this.idCategoriaEvaluacionOV = idCategoriaEvaluacionOV;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}