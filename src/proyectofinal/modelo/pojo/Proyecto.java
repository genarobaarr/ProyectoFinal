/*
 * Genaro Alejandro Barradas Sánchez
 * 29-05-2025
 */
package proyectofinal.modelo.pojo;

public class Proyecto {
    private int idProyecto;
    private String nombre;
    private String descripcion;
    private String objetivos; 
    private int cupo; 
    private int horasTotales; 
    private String fechaInicio;
    private String fechaFin;
    private int idOrganizacionVinculada;
    private int idAcademico;
    private int idCoordinador;
    private int IdResponsableDeProyecto;
    private String estatus; 
    private String nombreOrganizacion;
    private String nombreResponsable;
    private String departamentoResponsable;
    private String cargoResponsable;
    
    public Proyecto() {
    }

    public Proyecto(int idProyecto, String nombre, String descripcion, String objetivos, int cupo, int horasTotales,
                    String fechaInicio, String fechaFin, int idOrganizacionVinculada, int idAcademico, String estatus) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.objetivos = objetivos;
        this.cupo = cupo;
        this.horasTotales = horasTotales;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idOrganizacionVinculada = idOrganizacionVinculada;
        this.idAcademico = idAcademico;
        this.estatus = estatus;
    }

    public Proyecto(int idProyecto, String nombre, String descripcion, String objetivos, int cupo, int horasTotales, String fechaInicio, String fechaFin, int idOrganizacionVinculada, int idAcademico, int idCoordinador, int IdResponsableDeProyecto, String estatus) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.objetivos = objetivos;
        this.cupo = cupo;
        this.horasTotales = horasTotales;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idOrganizacionVinculada = idOrganizacionVinculada;
        this.idAcademico = idAcademico;
        this.idCoordinador = idCoordinador;
        this.IdResponsableDeProyecto = IdResponsableDeProyecto;
        this.estatus = estatus;
    }

    public Proyecto(int idProyecto, String nombre, String descripcion, String objetivos, int cupo, int horasTotales, String fechaInicio, String fechaFin, int idOrganizacionVinculada, int idAcademico, int idCoordinador, int IdResponsableDeProyecto, String estatus, String nombreOrganizacion, String nombreResponsable, String departamentoResponsable, String cargoResponsable) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.objetivos = objetivos;
        this.cupo = cupo;
        this.horasTotales = horasTotales;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idOrganizacionVinculada = idOrganizacionVinculada;
        this.idAcademico = idAcademico;
        this.idCoordinador = idCoordinador;
        this.IdResponsableDeProyecto = IdResponsableDeProyecto;
        this.estatus = estatus;
        this.nombreOrganizacion = nombreOrganizacion;
        this.nombreResponsable = nombreResponsable;
        this.departamentoResponsable = departamentoResponsable;
        this.cargoResponsable = cargoResponsable;
    }
    
    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivo) { 
        this.objetivos = objetivo;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getHorasTotales() { 
        return horasTotales;
    }

    public void setHorasTotales(int horasTotales) {
        this.horasTotales = horasTotales;
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

    public int getIdOrganizacionVinculada() { 
        return idOrganizacionVinculada;
    }

    public void setIdOrganizacionVinculada(int idOrganizacionVinculada) { 
        this.idOrganizacionVinculada = idOrganizacionVinculada;
    }

    public int getIdAcademico() { 
        return idAcademico;
    }

    public void setIdAcademico(int idAcademico) { 
        this.idAcademico = idAcademico;
    }

    public String getEstatus() { 
        return estatus;
    }

    public void setEstatus(String estatus) { 
        this.estatus = estatus;
    }

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public int getIdResponsableDeProyecto() {
        return IdResponsableDeProyecto;
    }

    public void setIdResponsableDeProyecto(int IdResponsableDeProyecto) {
        this.IdResponsableDeProyecto = IdResponsableDeProyecto;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getDepartamentoResponsable() {
        return departamentoResponsable;
    }

    public void setDepartamentoResponsable(String departamentoResponsable) {
        this.departamentoResponsable = departamentoResponsable;
    }

    public String getCargoResponsable() {
        return cargoResponsable;
    }

    public void setCargoResponsable(String cargoResponsable) {
        this.cargoResponsable = cargoResponsable;
    }
}