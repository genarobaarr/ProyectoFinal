/*
 * Omar Morales García
 * 10-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU07_ConsultarAvanceController implements Initializable {

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> tcNombre;
    @FXML
    private TableColumn<Estudiante, String> tcApellidoPaterno;
    @FXML
    private TableColumn<Estudiante, String> tcMatricula;

    private ObservableList<Estudiante> listaEstudiantes;
    private EstudianteDAO estudianteDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estudianteDAO = new EstudianteDAO();
        configurarTablaEstudiantes();
        cargarEstudiantesConReporte();
    }    

    private void configurarTablaEstudiantes() {
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
    }
     
    private void cargarEstudiantesConReporte() {
        try {
            List<Estudiante> estudiantesBD = estudianteDAO.obtenerEstudiantesConReporteMensual();
            listaEstudiantes = FXCollections.observableArrayList(estudiantesBD);
            tvEstudiantes.setItems(listaEstudiantes);
        } catch (RuntimeException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Sin conexión a la base de datos");
            e.printStackTrace();
        }
    }
     
    @FXML
    private void btnConsultarAvance(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado != null) {
            mostrarVentanaAvanceEstudiante(estudianteSeleccionado);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Selección de estudiante", 
                    "Por favor, selecciona un estudiante de la lista para consultar su avance.");
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir?")) {
            cerrarVentana();
        } else {
        }
    }
    
    private void mostrarVentanaAvanceEstudiante(Estudiante estudiante) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyectofinal/vista/FXMLCU07_AvanceEstudiante.fxml"));
            Parent root = loader.load();

            FXMLCU07_AvanceEstudianteController avanceController = loader.getController();
            avanceController.inicializarInformacion(estudiante, estudianteDAO); // Pasar también el DAO

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Avance de Estudiante: " + estudiante.getNombre() + " " + estudiante.getApellidoPaterno());
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta que esta se cierre
            stage.showAndWait(); // Espera a que la ventana se cierre
        } catch (IOException e) {
            System.err.println("Error al cargar la ventana de avance de estudiante: " + e.getMessage());
            e.printStackTrace();
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Interfaz", "No se pudo cargar la ventana de avance del estudiante.");
        }
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) tvEstudiantes.getScene().getWindow();
        stage.close();
    }
    
}
