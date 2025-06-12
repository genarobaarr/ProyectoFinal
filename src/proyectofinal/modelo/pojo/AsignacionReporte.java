/*
 * Alejandro Martínez Ramírez
 * 11-06-2025
 */
package proyectofinal.modelo.pojo;

public class AsignacionReporte {
    private int idAsignacion;
    private String titulo;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private String estatus; // "Habilitado" o "Inhabilitado"

    public AsignacionReporte() {
    }
    
    public AsignacionReporte(int idAsignacion, String titulo, String descripcion, String fechaInicio, String fechaFin, String estatus) {
        this.idAsignacion = idAsignacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estatus = estatus;
    }
    
    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
