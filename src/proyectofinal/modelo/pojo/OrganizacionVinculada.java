/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class OrganizacionVinculada {
    private int idOrganizacionVinculada;
    private String nombre;
    private String telefono;

    public OrganizacionVinculada() {
    }

    public OrganizacionVinculada(int idOrganizacionVinculada, String nombre, String telefono) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getIdOrganizacionVinculada() {
        return idOrganizacionVinculada;
    }

    public void setIdOrganizacionVinculada(int idOrganizacionVinculada) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
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
}