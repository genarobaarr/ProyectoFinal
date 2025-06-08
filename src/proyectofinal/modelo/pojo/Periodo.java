/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class Periodo {
    private int idPeriodo;
    private String fechaInicio;
    private String fechaFin;
    private String nombre;

    public Periodo() {
    }

    public Periodo(int idPeriodo, String fechaInicio, String fechaFin, String nombre) {
        this.idPeriodo = idPeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombre = nombre;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}