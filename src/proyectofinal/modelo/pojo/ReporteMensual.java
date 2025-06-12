/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.modelo.pojo;

public class ReporteMensual {
    private int idReporteMensual;
    private int numeroReporte;
    private int numeroHoras;
    private String observaciones;
    private String nombreArchivo;
    private String extensionArchivo;
    private byte[] archivo;
    private int idExpediente;
    private String estatus;

    public ReporteMensual() {
    }

    public ReporteMensual(int idReporteMensual, int numeroReporte, int numeroHoras, String observaciones, String nombreArchivo, String extensionArchivo, byte[] archivo, int idExpediente, String estatus) {
        this.idReporteMensual = idReporteMensual;
        this.numeroReporte = numeroReporte;
        this.numeroHoras = numeroHoras;
        this.observaciones = observaciones;
        this.nombreArchivo = nombreArchivo;
        this.extensionArchivo = extensionArchivo;
        this.archivo = archivo;
        this.idExpediente = idExpediente;
        this.estatus = estatus;
    }

    public int getIdReporteMensual() {
        return idReporteMensual;
    }

    public void setIdReporteMensual(int idReporteMensual) {
        this.idReporteMensual = idReporteMensual;
    }

    public int getNumeroReporte() {
        return numeroReporte;
    }

    public void setNumeroReporte(int numeroReporte) {
        this.numeroReporte = numeroReporte;
    }

    public int getNumeroHoras() {
        return numeroHoras;
    }

    public void setNumeroHoras(int numeroHoras) {
        this.numeroHoras = numeroHoras;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getExtensionArchivo() {
        return extensionArchivo;
    }

    public void setExtensionArchivo(String extensionArchivo) {
        this.extensionArchivo = extensionArchivo;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    

    @Override
    public String toString() {
        return "ReporteMensual{" + "nombreArchivo=" + nombreArchivo + ", extensionArchivo=" + extensionArchivo + '}';
    }
}