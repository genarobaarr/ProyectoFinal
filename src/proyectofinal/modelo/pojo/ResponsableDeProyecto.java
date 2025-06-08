/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class ResponsableDeProyecto {
    private int idResponsableDeProyecto;
    private String email;
    private String nombre;
    private String telefono;
    private int idOrganizacionVinculada;

    public ResponsableDeProyecto() {
    }

    public ResponsableDeProyecto(int idResponsableDeProyecto, String email, String nombre, String telefono, int idOrganizacionVinculada) {
        this.idResponsableDeProyecto = idResponsableDeProyecto;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.idOrganizacionVinculada = idOrganizacionVinculada;
    }

    public int getIdResponsableDeProyecto() {
        return idResponsableDeProyecto;
    }

    public void setIdResponsableDeProyecto(int idResponsableDeProyecto) {
        this.idResponsableDeProyecto = idResponsableDeProyecto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdOrganizacionVinculada() {
        return idOrganizacionVinculada;
    }

    public void setIdOrganizacionVinculada(int idOrganizacionVinculada) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
    }
}