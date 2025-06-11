/*
 * Omar Morales Garcia
 * 10-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.EvaluacionExposicion;
import proyectofinal.modelo.pojo.EvaluacionOV;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.utilidades.Utilidad; 

public class FXMLCU07_AvanceEstudianteController implements Initializable {

    @FXML
    private Label lbNombreEstudiante;
    @FXML
    private Label lbMatricula;
    @FXML
    private Label lbHorasAcumuladas;
    @FXML
    private TableView<ReporteMensual> tvReportes;
    @FXML
    private TableColumn<ReporteMensual, Integer> tcNoReporte; 
    @FXML
    private TableColumn<ReporteMensual, Integer> tcNumeroHorasReporte; 
    @FXML
    private TableColumn<ReporteMensual, String> tcObservacionesReporte;
    @FXML
    private TableColumn<ReporteMensual, String> tcNombreArchivo; 
    @FXML
    private TableColumn<ReporteMensual, String> tcExtensionArchivo;
    @FXML
    private TableView<EvaluacionOV> tvEvaluacionesOV;
    @FXML
    private TableColumn<EvaluacionOV, String> tcFechaEvaluacionOV;
    @FXML
    private TableColumn<EvaluacionOV, Double> tcPuntajeOV; 
    @FXML
    private TableColumn<EvaluacionOV, String> tcComentariosOV;
    @FXML
    private TableView<EvaluacionExposicion> tvEvaluacionesExposicion;
    @FXML
    private TableColumn<EvaluacionExposicion, Double> tcPuntajeExposicion; 
    @FXML
    private TableColumn<EvaluacionExposicion, String> tcComentariosExposicion; 
    
    private ObservableList<ReporteMensual> reportesMensuales;
    private ObservableList<EvaluacionOV> evaluacionesOV;
    private ObservableList<EvaluacionExposicion> evaluacionesExposicion;

    private Estudiante estudianteActual; 
    private EstudianteDAO estudianteDAO;
    @FXML
    private Button botonAceptar; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaReportesMensuales();
        configurarTablaEvaluacionesOV();
        configurarTablaEvaluacionesExposicion();
    }    
    
    public void inicializarInformacion(Estudiante estudianteSeleccionado, EstudianteDAO estudianteDAO) {
        this.estudianteActual = estudianteSeleccionado; 
        this.estudianteDAO = estudianteDAO; 
        cargarInformacionAvance();
    }

    private void configurarTablaReportesMensuales() {
        tcNoReporte.setCellValueFactory(new PropertyValueFactory<>("numeroReporte"));
        tcNumeroHorasReporte.setCellValueFactory(new PropertyValueFactory<>("numeroHoras"));
        tcObservacionesReporte.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        tcNombreArchivo.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo")); 
        tcExtensionArchivo.setCellValueFactory(new PropertyValueFactory<>("extensionArchivo")); 
    }

    private void configurarTablaEvaluacionesOV() {
        tcComentariosOV.setCellValueFactory(new PropertyValueFactory<>("comentarios"));
        tcFechaEvaluacionOV.setCellValueFactory(new PropertyValueFactory<>("fecha")); 
        tcPuntajeOV.setCellValueFactory(new PropertyValueFactory<>("puntajeFinal")); 
    }

    private void configurarTablaEvaluacionesExposicion() {
        tcComentariosExposicion.setCellValueFactory(new PropertyValueFactory<>("comentarios"));
        tcPuntajeExposicion.setCellValueFactory(new PropertyValueFactory<>("puntajeFinal")); 
    }

    private void cargarInformacionAvance() {
        if (estudianteActual != null) {
            lbNombreEstudiante.setText(estudianteActual.getNombre() + " " +
                                       estudianteActual.getApellidoPaterno() + " " +
                                       estudianteActual.getApellidoMaterno());
            lbMatricula.setText(estudianteActual.getMatricula());
            lbHorasAcumuladas.setText(String.valueOf(estudianteActual.getHorasAcumuladas()));

            if (estudianteDAO == null) {
                System.err.println("Error: EstudianteDAO no ha sido inyectado correctamente.");
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error interno de la aplicación: DAO no disponible.");
                return;
            }

            try {
                // Cargar reportes mensuales
                List<ReporteMensual> listaReportes = estudianteDAO.obtenerReportesMensualesEstudiante(estudianteActual.getIdUsuario());
                reportesMensuales = FXCollections.observableArrayList(listaReportes);
                tvReportes.setItems(reportesMensuales);

                // Cargar evaluaciones de Organización Vinculada
                List<EvaluacionOV> listaEvaluacionesOV = estudianteDAO.obtenerEvaluacionesOVEstudiante(estudianteActual.getIdUsuario());
                evaluacionesOV = FXCollections.observableArrayList(listaEvaluacionesOV);
                tvEvaluacionesOV.setItems(evaluacionesOV);

                // Cargar evaluaciones de Exposición
                List<EvaluacionExposicion> listaEvaluacionesExposicion = estudianteDAO.obtenerEvaluacionesExposicionEstudiante(estudianteActual.getIdUsuario());
                evaluacionesExposicion = FXCollections.observableArrayList(listaEvaluacionesExposicion);
                tvEvaluacionesExposicion.setItems(evaluacionesExposicion);

                if (listaReportes.isEmpty() && listaEvaluacionesOV.isEmpty() && listaEvaluacionesExposicion.isEmpty()) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Sin Datos", "El estudiante seleccionado no tiene reportes ni evaluaciones registradas.");
                }

            } catch (RuntimeException e) {
                System.err.println("Error al cargar la información de avance: " + e.getMessage());
                e.printStackTrace();
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Base de Datos", "Hubo un problema al intentar recuperar los datos del estudiante");
            }
        } else {
            System.err.println("Estudiante seleccionado es nulo. No se puede cargar el avance.");
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Estudiante No Seleccionado", "No se ha seleccionado un estudiante para mostrar su avance.");
        }
    }
    
    @FXML
    private void clicBotonAceptar(ActionEvent event) {
        Stage stage = (Stage) botonAceptar.getScene().getWindow();
        stage.close();
    }
}