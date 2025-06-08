/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class ExperienciaEducativa {
    private int idExperienciaEducativa;
    private int bloque;
    private int seccion;
    private int nrc;
    private int idPeriodo;
    private int idAcademico; //idUsuario

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(int idExperienciaEducativa, int bloque, int seccion, int nrc, int idPeriodo, int idAcademico) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.bloque = bloque;
        this.seccion = seccion;
        this.nrc = nrc;
        this.idPeriodo = idPeriodo;
        this.idAcademico = idAcademico;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public int getBloque() {
        return bloque;
    }

    public void setBloque(int bloque) {
        this.bloque = bloque;
    }

    public int getSeccion() {
        return seccion;
    }

    public void setSeccion(int seccion) {
        this.seccion = seccion;
    }

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdAcademico() {
        return idAcademico;
    }

    public void setIdAcademico(int idAcademico) {
        this.idAcademico = idAcademico;
    }
}