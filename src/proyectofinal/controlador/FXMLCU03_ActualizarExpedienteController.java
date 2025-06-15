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
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import proyectofinal.modelo.dao.ExpedienteDAO;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;


public class FXMLCU03_ActualizarExpedienteController implements Initializable {

    @FXML
    private TableView<DocumentoInicio> tvExpediente;
    @FXML
    private Label lblNombreEstudiante;
    
    
    private ObservableList<DocumentoInicio> documentos;
    private File archivoSeleccionado;
    private Usuario estudiante;
    @FXML
    private TableColumn colDocumentos;
    
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
                DocumentoInicioDAO.subirArchivo(archivoSeleccionado, obtenerIdExpediente(estudiante.getIdUsuario()));
                cargarInformacionTabla();
                archivoSeleccionado = null;
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
        colDocumentos.setCellValueFactory(new PropertyValueFactory("nombreArchivo"));
    }
    
    private void cargarInformacionTabla() {
        try {
            documentos = FXCollections.observableArrayList();
            ArrayList<DocumentoInicio> documentosDAO = DocumentoInicioDAO.obtenerDocumentoIniciosPorEstudiante(estudiante.getIdUsuario());
            documentos.addAll(documentosDAO);
            tvExpediente.setItems(documentos);
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar", 
                    "Lo sentimos, por el momento no se puede mostrar la información "
                            + "de los expedientes, por favor, "
                            + "inténtelo de nuevo más tarde.");
            Utilidad.getEscenario(tvExpediente).close();
        }
    }
    
    private File seleccionarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        return fileChooser.showOpenDialog(null);
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