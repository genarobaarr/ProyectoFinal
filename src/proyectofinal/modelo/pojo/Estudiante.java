/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class Estudiante extends Usuario {
    private String fechaNacimiento;
    private String matricula;
    private int idExperienciaEducativa;

    public Estudiante() {
    }

    public Estudiante(String fechaNacimiento, String matricula, int idExperienciaEducativa, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String username) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, email, username);
        this.fechaNacimiento = fechaNacimiento;
        this.matricula = matricula;
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }
}