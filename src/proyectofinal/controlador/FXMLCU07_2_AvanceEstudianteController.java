/*
 * Omar Morales Garcia
 * 10-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.modelo.dao.ReporteMensualDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.EvaluacionExposicion;
import proyectofinal.modelo.pojo.EvaluacionOV;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.utilidades.Utilidad; 

public class FXMLCU07_2_AvanceEstudianteController implements Initializable {

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaReportesMensuales();
        configurarTablaEvaluacionesOV();
        configurarTablaEvaluacionesExposicion();
    }
    
    @FXML
    private void clicBotonAceptar(ActionEvent event) {
        Utilidad.getEscenario(tvReportes).close();
    }
    
    public void inicializarInformacion(Estudiante estudianteSeleccionado) {
        this.estudianteActual = estudianteSeleccionado;
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
            lbNombreEstudiante.setText("Nombre del estudiante: " + estudianteActual.getNombre() + " " +
                                       estudianteActual.getApellidoPaterno() + " " +
                                       estudianteActual.getApellidoMaterno());
            lbMatricula.setText("Matrícula: " + estudianteActual.getMatricula());
            lbHorasAcumuladas.setText(String.valueOf("Horas acumuladas: " + estudianteActual.getHorasAcumuladas()));

            try {
                List<ReporteMensual> listaReportes = ReporteMensualDAO.obtenerReportesMensualesEstudiante(estudianteActual.getIdUsuario());
                reportesMensuales = FXCollections.observableArrayList(listaReportes);
                tvReportes.setItems(reportesMensuales);

                List<EvaluacionOV> listaEvaluacionesOV = EstudianteDAO.obtenerEvaluacionesOVEstudiante(estudianteActual.getIdUsuario());
                evaluacionesOV = FXCollections.observableArrayList(listaEvaluacionesOV);
                tvEvaluacionesOV.setItems(evaluacionesOV);

                List<EvaluacionExposicion> listaEvaluacionesExposicion = EstudianteDAO.obtenerEvaluacionesExposicionEstudiante(estudianteActual.getIdUsuario());
                evaluacionesExposicion = FXCollections.observableArrayList(listaEvaluacionesExposicion);
                tvEvaluacionesExposicion.setItems(evaluacionesExposicion);

                if (listaReportes.isEmpty() && listaEvaluacionesOV.isEmpty() && listaEvaluacionesExposicion.isEmpty()) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                            "Sin Datos", "El estudiante seleccionado no tiene reportes ni evaluaciones registradas.");
                }
            } catch (SQLException e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error de Base de Datos", "Hubo un problema al intentar recuperar los datos del estudiante");
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Estudiante No Seleccionado", "No se ha seleccionado un estudiante para mostrar su avance.");
        }
    }
}