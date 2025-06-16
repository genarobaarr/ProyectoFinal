/*
 * Alejandro Martínez Ramírez
 * 07-06-2025
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
import proyectofinal.modelo.dao.ReporteMensualDAO;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU06_1_ValidarReportesController implements Initializable {

    @FXML
    private TableView<ReporteMensual> tvReportesMensuales;
    @FXML
    private TableColumn colNombres;
    
    private ObservableList<ReporteMensual> reportes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonValidar(ActionEvent event) {
        ReporteMensual reporteMensual = tvReportesMensuales.getSelectionModel().getSelectedItem();
        try {
            if (reporteMensual != null) {
                irPantallaSiguiente(reporteMensual);
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Selecciona un reporte", 
                    "Para validar un reporte mensual, selecciona alguno de la lista");
            }
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
            Utilidad.getEscenario(tvReportesMensuales).close();
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Desea salir?")) {
            Utilidad.getEscenario(tvReportesMensuales).close();
        }
    }
    
    public void inicializarInformacion() {
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void cargarInformacionTabla() {
          try {
            reportes = FXCollections.observableArrayList();
            ArrayList<ReporteMensual> reportesDAO = ReporteMensualDAO.obtenerReportesMensualesNoValidados();
            reportes.addAll(reportesDAO);
            tvReportesMensuales.setItems(reportes);
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tvReportesMensuales).close();
        }
    }
    private void configurarTabla() {
        colNombres.setCellValueFactory(new PropertyValueFactory("nombreArchivo"));
    }
    
    private void irPantallaSiguiente (ReporteMensual reporteMensual) throws IOException {
        Stage escenarioBase = (Stage) tvReportesMensuales.getScene().getWindow();
        FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource("vista/FXMLCU06_2_ValidarReportes.fxml"));
        Parent vista = cargador.load();

        FXMLCU06_2_ValidarReportesController controlador = cargador.getController();
        controlador.inicializarInformacion(reporteMensual);

        Scene escenaPrincipal = new Scene(vista);
        escenarioBase.setScene(escenaPrincipal);
        escenarioBase.setTitle("Validar reporte");
        escenarioBase.show();
    }
}
