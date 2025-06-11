/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class Estudiante extends Usuario {
    private String fechaNacimiento;
    private String matricula;
    private int idExperienciaEducativa;
    //Nuevos campos
    private int idExpediente;
    private int horasAcumuladas;
    
    public Estudiante() {
    }

    public Estudiante(String fechaNacimiento, String matricula, int idExperienciaEducativa, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String username) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, email, username);
        this.fechaNacimiento = fechaNacimiento;
        this.matricula = matricula;
        this.idExperienciaEducativa = idExperienciaEducativa;
    }
    
    public Estudiante(String fechaNacimiento, String matricula, int idExperiencia, int idUsuario, String nombre,
                      String apellidoPaterno, String apellidoMaterno, String email, String username,
                      int idExpediente, int horasAcumuladas) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, email, username);
        this.fechaNacimiento = fechaNacimiento;
        this.matricula = matricula;
        this.idExperienciaEducativa = idExperiencia;
        this.idExpediente = idExpediente;
        this.horasAcumuladas = horasAcumuladas;
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

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public int getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setHorasAcumuladas(int horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }
    
    
}