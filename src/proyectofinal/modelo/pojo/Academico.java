/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class Academico extends Usuario {
    private int noPersonal;

    public Academico() {
    }

    public Academico(int noPersonal, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String username) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, email, username);
        this.noPersonal = noPersonal;
    }

    public int getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(int noPersonal) {
        this.noPersonal = noPersonal;
    }
}