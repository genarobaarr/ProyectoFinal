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
import javafx.scene.control.Label;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Label lblNombreEstudiante;
    
    
    private ObservableList<String> documentos;
    private File archivoSeleccionado;
    private Usuario estudiante;
    @FXML
    private TableColumn<String, String> colDocumentos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonCargar(ActionEvent event) {
        archivoSeleccionado = seleccionarArchivo();
        if (archivoSeleccionado != null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Archivo seleccionado",
                "Has seleccionado: " + archivoSeleccionado.getName());
        }
    }

    @FXML
    private void clicBotonSubir(ActionEvent event) {
        try {
            if(validarArchivo(archivoSeleccionado)) {
                if(DocumentoInicioDAO.contarDocumentosInicioPorEstudiante(estudiante.getIdUsuario()) < 5){
                subirArchivoDocumentoInicio(archivoSeleccionado, obtenerIdExpediente(estudiante.getIdUsuario()));
                cargarInformacionTabla();
                archivoSeleccionado = null;
                } else {
                subirArchivoDocumentoFinal(archivoSeleccionado, obtenerIdExpediente(estudiante.getIdUsuario()));
                cargarInformacionTabla();
                archivoSeleccionado = null;
                }
            }
        } catch (SQLException e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", "Error de conexion con base de datos, intentalo más tarde");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir?")) {
            Utilidad.getEscenario(tvExpediente).close();
        }
    }
    
    public void inicializarInformacion (Usuario estudiante) {
        this.estudiante = estudiante;
        lblNombreEstudiante.setText(estudiante.toString());
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla() {
    colDocumentos.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
    }

    
  private void cargarInformacionTabla() {
    try {
        ObservableList<String> nombresArchivos = FXCollections.observableArrayList();

        ArrayList<DocumentoInicio> docsInicio = DocumentoInicioDAO.obtenerDocumentoIniciosPorEstudiante(estudiante.getIdUsuario());
        for (DocumentoInicio doc : docsInicio) {
            nombresArchivos.add(doc.getNombreArchivo());
        }

        ArrayList<DocumentoFinal> docsFinal = DocumentoFinalDAO.obtenerDocumentosFinalesPorEstudiante(estudiante.getIdUsuario());
        for (DocumentoFinal doc : docsFinal) {
            nombresArchivos.add(doc.getNombreArchivo());
        }

        tvExpediente.setItems(nombresArchivos);
    } catch (SQLException e) {
        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los nombres de los documentos.");
    }
}

    
    private File seleccionarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        return fileChooser.showOpenDialog(null);
    }
    
    public static ResultadoOperacion subirArchivoDocumentoInicio(File archivo, int idExpediente) {
        ResultadoOperacion resultado = new ResultadoOperacion();

        try {
            byte[] contenido = Files.readAllBytes(archivo.toPath());

            DocumentoInicio documento = new DocumentoInicio();
            documento.setFechaEntregado(LocalDate.now().toString());
            documento.setNombreArchivo(archivo.getName());
            documento.setExtensionArchivo("pdf");
            documento.setArchivo(contenido);
            documento.setIdExpediente(idExpediente);

            resultado = DocumentoInicioDAO.registrarDocumentoInicio(documento);

        } catch (IOException e) {
            resultado.setError(true);
            resultado.setMensaje("Error al leer el archivo.");
        } catch (SQLException e) {
            resultado.setError(true);
            resultado.setMensaje("Error al guardar en la base de datos.");
        }

        return resultado;
    }
    
        public static ResultadoOperacion subirArchivoDocumentoFinal(File archivo, int idExpediente) {
        ResultadoOperacion resultado = new ResultadoOperacion();

        try {
            byte[] contenido = Files.readAllBytes(archivo.toPath());

            DocumentoFinal documento = new DocumentoFinal();
            documento.setFechaEntregado(LocalDate.now().toString());
            documento.setNombreArchivo(archivo.getName());
            documento.setExtensionArchivo("pdf");
            documento.setArchivo(contenido);
            documento.setIdExpediente(idExpediente);

            resultado = DocumentoFinalDAO.registrarDocumentoFinal(documento);

        } catch (IOException e) {
            resultado.setError(true);
            resultado.setMensaje("Error al leer el archivo.");
        } catch (SQLException e) {
            resultado.setError(true);
            resultado.setMensaje("Error al guardar en la base de datos.");
        }

        return resultado;
    }

    public boolean validarArchivo(File archivoSeleccionado){
        if (archivoSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,  "Archivo no seleccionado", "Por favor, selecciona un archivo antes de subirlo");
            return false;
        }

        double tamanioEnMB = archivoSeleccionado.length() / (1024.0 * 1024.0);
        if (tamanioEnMB > 20) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error de carga", "El tamaño del archivo seleccionado excede lo permitido. Por favor, intenta nuevamente");
            return false;
        }

        return true;
    }

    private int obtenerIdExpediente(int idEstudiante) throws SQLException {
        int idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(idEstudiante);
        return idExpediente;
    }
    
    
}