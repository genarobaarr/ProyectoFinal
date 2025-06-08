/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class Proyecto {
    private int idProyecto;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private String nombre;
    private int idResponsableDeProyecto; //idUsuario
    private int idCoordinador; //idUsuario

    public Proyecto() {
    }

    public Proyecto(int idProyecto, String descripcion, String fechaInicio, String fechaFin, String nombre, int idResponsableDeProyecto, int idCoordinador) {
        this.idProyecto = idProyecto;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombre = nombre;
        this.idResponsableDeProyecto = idResponsableDeProyecto;
        this.idCoordinador = idCoordinador;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdResponsableDeProyecto() {
        return idResponsableDeProyecto;
    }

    public void setIdResponsableDeProyecto(int idResponsableDeProyecto) {
        this.idResponsableDeProyecto = idResponsableDeProyecto;
    }

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }
}