/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.modelo.pojo;

public class DocumentoInicio {
    private int idDocumentoInicio;
    private String fechaEntregado;
    private String nombreArchivo;
    private String extensionArchivo;
    private byte[] archivo;
    private int idExpediente;

    public DocumentoInicio() {
    }

    public DocumentoInicio(int idDocumentoInicio, String fechaEntregado, String nombreArchivo, String extensionArchivo, byte[] archivo, int idExpediente) {
        this.idDocumentoInicio = idDocumentoInicio;
        this.fechaEntregado = fechaEntregado;
        this.nombreArchivo = nombreArchivo;
        this.extensionArchivo = extensionArchivo;
        this.archivo = archivo;
        this.idExpediente = idExpediente;
    }

    public int getIdDocumentoInicio() {
        return idDocumentoInicio;
    }

    public void setIdDocumentoInicio(int idDocumentoInicio) {
        this.idDocumentoInicio = idDocumentoInicio;
    }

    public String getFechaEntregado() {
        return fechaEntregado;
    }

    public void setFechaEntregado(String fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
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
}