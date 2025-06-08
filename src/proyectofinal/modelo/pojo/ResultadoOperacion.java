/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.modelo.pojo;

public class ResultadoOperacion {
    
    private boolean error;
    private String mensaje;

    public ResultadoOperacion() {
    }

    public ResultadoOperacion(boolean error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
