/*
 * Omar Morales García
 * 10-06-2025
 */
package proyectofinal.modelo.pojo;

public class ProyectoConEstudiante {
    private Proyecto proyecto;
    private Estudiante estudiante;
    private int idExpediente;

    public ProyectoConEstudiante() {
    }

    public ProyectoConEstudiante(Proyecto proyecto, Estudiante estudiante, int idExpediente) {
        this.proyecto = proyecto;
        this.estudiante = estudiante;
        this.idExpediente = idExpediente;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public String getNombreCompletoEstudiante() {
        if (estudiante != null) {
            return estudiante.getNombre() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno();
        }
        return "N/A";
    }

    public String getNombreProyecto() {
        if (proyecto != null) {
            return proyecto.getNombre();
        }
        return "N/A";
    }
}