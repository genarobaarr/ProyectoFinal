/*
 * Alejandro Martínez Ramírez
 * 30-05-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import proyectofinal.modelo.dao.DocumentoInicioDAO;
import proyectofinal.modelo.pojo.DocumentoInicio;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import proyectofinal.modelo.dao.DocumentoFinalDAO;
import proyectofinal.modelo.dao.ExpedienteDAO;
import proyectofinal.modelo.pojo.DocumentoFinal;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;


public class FXMLCU03_ActualizarExpedienteController implements Initializable {

    @FXML
    private TableView<String> tvExpediente;
    @FXML
    private TableColumn<String, String> colDocumentos;
    @FXML
    private Button btnCargar;
    @FXML
    private Button btnSubir;
    
    private ObservableList<String> documentos;
    private File archivoSeleccionado;
    private Usuario estudiante;
    private int idExpediente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonCargar(ActionEvent event) {
        archivoSeleccionado = seleccionarArchivo();
        if (archivoSeleccionado != null) {
            btnSubir.setDisable(false);
            btnCargar.setDisable(true);
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Archivo seleccionado",
                "Has seleccionado: " + archivoSeleccionado.getName());
        }
    }

    @FXML
    private void clicBotonSubir(ActionEvent event) {
        try {
            if (validarArchivo(archivoSeleccionado)) {
                if (DocumentoInicioDAO.contarDocumentosInicioPorEstudiante(estudiante.getIdUsuario()) < 4) {
                    DocumentoInicio documentoInicio = obtenerNuevoDocumentoInicio(archivoSeleccionado, idExpediente);
                    subirArchivoDocumentoInicio(documentoInicio);
                } else {
                    if (DocumentoFinalDAO.contarDocumentosFinalPorEstudiante(estudiante.getIdUsuario()) < 2) {
                        DocumentoFinal documentoFinal = obtenerNuevoDocumentoFinal(archivoSeleccionado, idExpediente);
                        subirArchivoDocumentoFinal(documentoFinal);
                    } else {
                        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", 
                            "Ya no puedes introducir documentos nuevos.");
                        Utilidad.getEscenario(tvExpediente).close();
                    }
                }
                archivoSeleccionado = null;
                btnSubir.setDisable(true);
                btnCargar.setDisable(false);
                cargarInformacionTabla();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error de carga", 
                        "El tamaño del archivo excede lo permitido. Por favor, intenta nuevamente.");
            }
        } catch (SQLException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                        "Error de conexion con base de datos, intentalo más tarde");
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error con el archivo", 
                        "Error al leer el archivo, intentalo más tarde");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir?")) {
            Utilidad.getEscenario(tvExpediente).close();
        }
    }
    
    public void inicializarInformacion (Usuario estudiante) {
        try {
            this.estudiante = estudiante;
            this.idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(estudiante.getIdUsuario());
            btnSubir.setDisable(true);
            btnCargar.setDisable(false);
            configurarTabla();
            cargarInformacionTabla();
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                        "Error de conexion con base de datos, intentalo más tarde");
        }
    }
    
    private void configurarTabla() {
        colDocumentos.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
    }
    
    private void cargarInformacionTabla() {
        try {
            documentos = FXCollections.observableArrayList();

            ArrayList<DocumentoInicio> documentosInicio = DocumentoInicioDAO.obtenerDocumentoIniciosPorEstudiante(estudiante.getIdUsuario());
            for (DocumentoInicio documentoInicio : documentosInicio) {
                documentos.add(documentoInicio.getNombreArchivo());
            }

            ArrayList<DocumentoFinal> documentosFinales = DocumentoFinalDAO.obtenerDocumentosFinalesPorEstudiante(estudiante.getIdUsuario());
            for (DocumentoFinal documentoFinal : documentosFinales) {
                documentos.add(documentoFinal.getNombreArchivo());
            }

            tvExpediente.setItems(documentos);
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexion con base de datos, intentalo más tarde");
            Utilidad.getEscenario(tvExpediente).close();
        }
    }
    
    private File seleccionarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        return fileChooser.showOpenDialog(null);
    }
    
    public boolean validarArchivo(File archivoSeleccionado) {
        if (archivoSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,  
                    "Archivo no seleccionado", "Por favor, selecciona un archivo antes de subirlo");
            return false;
        }

        double tamanioEnMB = archivoSeleccionado.length() / (1024.0 * 1024.0);
        if (tamanioEnMB > 20) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error de carga", 
                    "El tamaño del archivo seleccionado excede lo permitido (20MB). Por favor, intente nuevamente.");
            return false;
        }
        return true;
    }
    
    private void subirArchivoDocumentoInicio(DocumentoInicio documento) {
        try {
            ResultadoOperacion resultadoSubir = DocumentoInicioDAO.registrarDocumentoInicio(documento);
            if (!resultadoSubir.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "Documento cargado exitosamente");
                Utilidad.getEscenario(tvExpediente).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al registrar documento", resultadoSubir.getMensaje());
                Utilidad.getEscenario(tvExpediente).close();
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tvExpediente).close();
        }
    }
    
    private DocumentoInicio obtenerNuevoDocumentoInicio(File archivo, int idExpediente) throws IOException {
        DocumentoInicio documento = new DocumentoInicio();
        
        byte[] contenido = Files.readAllBytes(archivo.toPath());

        documento.setFechaEntregado(LocalDate.now().toString());
        documento.setNombreArchivo(archivo.getName());
        documento.setExtensionArchivo("pdf");
        documento.setArchivo(contenido);
        documento.setIdExpediente(idExpediente);
        
        return documento;
    }
    
    private void subirArchivoDocumentoFinal(DocumentoFinal documento) {
        try {
            ResultadoOperacion resultadoSubir = DocumentoFinalDAO.registrarDocumentoFinal(documento);
            if (!resultadoSubir.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "Documento cargado exitosamente");
                Utilidad.getEscenario(tvExpediente).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al registrar documento", resultadoSubir.getMensaje());
                Utilidad.getEscenario(tvExpediente).close();
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tvExpediente).close();
        }
    }
    
    private DocumentoFinal obtenerNuevoDocumentoFinal(File archivo, int idExpediente) throws IOException {
        DocumentoFinal documento = new DocumentoFinal();
        
        byte[] contenido = Files.readAllBytes(archivo.toPath());

        documento.setFechaEntregado(LocalDate.now().toString());
        documento.setNombreArchivo(archivo.getName());
        documento.setExtensionArchivo("pdf");
        documento.setArchivo(contenido);
        documento.setIdExpediente(idExpediente);
        
        return documento;
    }
}