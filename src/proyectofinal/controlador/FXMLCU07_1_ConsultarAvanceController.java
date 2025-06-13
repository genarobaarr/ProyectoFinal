/*
 * Omar Morales García
 * 10-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU07_1_ConsultarAvanceController implements Initializable {

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> tcNombre;
    @FXML
    private TableColumn<Estudiante, String> tcApellidoPaterno;
    @FXML
    private TableColumn<Estudiante, String> tcMatricula;

    private ObservableList<Estudiante> listaEstudiantes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaEstudiantes();
        cargarEstudiantesConReporte();
    }

    @FXML
    private void clicBotonConsultarAvance(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado != null) {
            mostrarVentanaAvanceEstudiante(estudianteSeleccionado);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Selección de estudiante", 
                    "Por favor, selecciona un estudiante de la lista para consultar su avance.");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir?")) {
            Utilidad.getEscenario(tvEstudiantes).close();
        } 
    }

    private void configurarTablaEstudiantes() {
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
    }
     
    private void cargarEstudiantesConReporte() {
        try {
            List<Estudiante> estudiantesBD = EstudianteDAO.obtenerEstudiantesConReporteMensual();
            listaEstudiantes = FXCollections.observableArrayList(estudiantesBD);
            tvEstudiantes.setItems(listaEstudiantes);
        } catch (RuntimeException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Error", "Sin conexión a la base de datos");
        }
    }
    
    private void mostrarVentanaAvanceEstudiante(Estudiante estudiante) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyectofinal/vista/FXMLCU07_2_AvanceEstudiante.fxml"));
            Parent root = loader.load();

            FXMLCU07_2_AvanceEstudianteController avanceController = loader.getController();
            avanceController.inicializarInformacion(estudiante);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Avance de estudiante");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la pantalla principal");
        }
    }
}