/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class Coordinador extends Usuario {
    private String telefono;

    public Coordinador() {
    }

    public Coordinador(String telefono, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String username) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, email, username);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}