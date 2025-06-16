/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.modelo.pojo;

public class DocumentoFinal {
    private int idDocumentoFinal;
    private String fechaEntregado;
    private String nombreArchivo;
    private String extensionArchivo;
    private byte[] archivo;
    private int idExpediente;

    public DocumentoFinal() {
    }

    public DocumentoFinal(int idDocumentoFinal, String fechaEntregado, String nombreArchivo, String extensionArchivo, byte[] archivo, int idExpediente) {
        this.idDocumentoFinal = idDocumentoFinal;
        this.fechaEntregado = fechaEntregado;
        this.nombreArchivo = nombreArchivo;
        this.extensionArchivo = extensionArchivo;
        this.archivo = archivo;
        this.idExpediente = idExpediente;
    }

    public int getIdDocumentoFinal() {
        return idDocumentoFinal;
    }

    public void setIdDocumentoFinal(int idDocumentoFinal) {
        this.idDocumentoFinal = idDocumentoFinal;
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