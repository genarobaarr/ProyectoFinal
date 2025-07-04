/*
 * Alejandro Martínez Ramírez
 * 02-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.dao.AsignacionReporteDAO;
import proyectofinal.modelo.dao.ReporteMensualDAO;
import proyectofinal.modelo.pojo.AsignacionReporte;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.utilidades.Utilidad;


public class FXMLCU04_1_EntregaReportesController implements Initializable {

    @FXML
    private TableView<ReporteMensual> tvReportesMensuales;
    @FXML
    private TableColumn colNombres;
    
    private ObservableList<ReporteMensual> reportes;
    private Estudiante estudiante;
    private AsignacionReporte asignacionReporte = new AsignacionReporte();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private void clicBotonNuevoReporte(ActionEvent event) {
        try {
            int numeroReportes = ReporteMensualDAO.obtenerNumeroTotalReportesEstudiante(estudiante.getIdUsuario());
            if (numeroReportes < 6) {
                if (asignacionReporte != null && asignacionReporte.getEstatus().equals("Habilitado")) {
                   irPantallaSiguiente();
                } else {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Acceso denegado", 
                        "La entrega de reportes está actualmente inhabilitada.");
                }
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                    "Acceso denegado", 
                    "Ya completaste la entrega de todos los reportes.");
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
            Utilidad.getEscenario(tvReportesMensuales).close();
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
            Utilidad.getEscenario(tvReportesMensuales).close();
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confiramción", "¿Deseas salir? No se guardarán los cambios")) {
            Utilidad.getEscenario(tvReportesMensuales).close();
        }
    }
    
    public void inicializarInformacion (Estudiante estudiante) {
        this.asignacionReporte = verificarAsignacionReporte();
        this.estudiante = estudiante;
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla() {
        colNombres.setCellValueFactory(new PropertyValueFactory("nombreArchivo"));
    }
    
    private void cargarInformacionTabla() {
        try {
            reportes = FXCollections.observableArrayList();
            ArrayList<ReporteMensual> reportesDAO = ReporteMensualDAO.obtenerReportesMensualesEstudiante(estudiante.getIdUsuario());
            reportes.addAll(reportesDAO);
            tvReportesMensuales.setItems(reportes);
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tvReportesMensuales).close();
        }
    }
    
    private AsignacionReporte verificarAsignacionReporte() {
        AsignacionReporte resultadoAsignacionReporte = new AsignacionReporte();
        try {
            resultadoAsignacionReporte = AsignacionReporteDAO.obtenerAsignacionActual();
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tvReportesMensuales).close();
        }
        return resultadoAsignacionReporte;
    }
    
    public void irPantallaSiguiente () throws IOException {
        Stage escenarioBase = (Stage) tvReportesMensuales.getScene().getWindow();
        FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource("vista/FXMLCU04_2_EntregaReportes.fxml"));
        Parent vista = cargador.load();

        FXMLCU04_2_EntregaReportesController controlador = cargador.getController();
        controlador.inicializarInformacion(estudiante);

        Scene escenaPrincipal = new Scene(vista);
        escenarioBase.setScene(escenaPrincipal);
        escenarioBase.setTitle("Entregar reporte");
        escenarioBase.show();
    }
}