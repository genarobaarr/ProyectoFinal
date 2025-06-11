/*
 * Omar Morales García
 * 10-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.pojo.AcademicoEvaluador;
import proyectofinal.modelo.pojo.ProyectoConEstudiante;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.SessionManager;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU09_ProyectosRegistradosActivosController implements Initializable {

    @FXML
    private TableView<ProyectoConEstudiante> tvProyectoYEstudiante; 
    @FXML
    private TableColumn<ProyectoConEstudiante, String> tcProyecto;
    @FXML
    private TableColumn<ProyectoConEstudiante, String> tcEstudianteVinculado;

    private ObservableList<ProyectoConEstudiante> listaProyectosConEstudiantes;
    private ProyectoDAO proyectoDAO;

    public FXMLCU09_ProyectosRegistradosActivosController() {
        proyectoDAO = new ProyectoDAO(); 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla(); 
        cargarProyectosConEstudiantes(); 
    }

    private void configurarTabla() {
        tcProyecto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreProyecto()));
        tcEstudianteVinculado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreCompletoEstudiante()));
    }

    private void cargarProyectosConEstudiantes() {
        try {
            List<ProyectoConEstudiante> proyectos = proyectoDAO.obtenerProyectosConEstudiantesActivos();
            listaProyectosConEstudiantes = FXCollections.observableArrayList(proyectos);
            tvProyectoYEstudiante.setItems(listaProyectosConEstudiantes);

            if (proyectos.isEmpty()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Sin Datos", "No hay proyectos con estudiantes activos registrados en el sistema.");
            }
        } catch (RuntimeException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", "Error de conexión con base de datos, inténtalo más tarde. Detalles: " + e.getMessage());
        }
    }

    private AcademicoEvaluador obtenerAcademicoEvaluadorSesion() {
        Usuario loggedInUser = SessionManager.getLoggedInUser();
        if (loggedInUser == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Sesión", "No se encontró información del evaluador loggeado. Por favor, reinicie la aplicación y vuelva a iniciar sesión.");
            return null;
        }
        if (!(loggedInUser instanceof AcademicoEvaluador)) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Permiso Denegado", "El usuario loggeado no tiene permisos para realizar esta evaluación.");
            return null;
        }
        return (AcademicoEvaluador) loggedInUser;
    }

    @FXML
    private void clicBotonEvaluar(ActionEvent event) {
        ProyectoConEstudiante seleccion = tvProyectoYEstudiante.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Selección Requerida", "Por favor, seleccione un proyecto con estudiante para evaluar.");
            return;
        }

        AcademicoEvaluador academicoLoggeado = obtenerAcademicoEvaluadorSesion();
        if (academicoLoggeado == null) {
            return;
        }

        int idAcademicoLoggeado = academicoLoggeado.getIdUsuario();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proyectofinal/vista/FXMLCU09_EvaluarEstudiante.fxml"));
            Parent vista = loader.load();

            FXMLCU09_EvaluarEstudianteController controller = loader.getController();
            controller.inicializarInformacion(seleccion.getIdExpediente(), idAcademicoLoggeado);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle("Evaluación de Exposición");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarProyectosConEstudiantes();

        } catch (IOException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Interfaz", "No se pudo cargar la ventana de evaluación. Inténtalo más tarde.");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir?")) {
            cerrarVentana();
        }
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) tvProyectoYEstudiante.getScene().getWindow();
        stage.close();
    }
}
