/*
 * Omar Morales García
 * 09-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.dao.PeriodoDAO;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.utilidades.Utilidad;


public class FXMLCU01_AsignarProyectoController implements Initializable {

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableView<Proyecto> tvProyectos;
    @FXML
    private TableColumn<Estudiante, String> tcEstudianteNombre;
    @FXML
    private TableColumn<Proyecto, String> tcProyectoNombre;

    private ObservableList<Estudiante> listaEstudiantesSinProyecto;
    private ObservableList<Proyecto> listaProyectosSinAsignar;

    private EstudianteDAO estudianteDAO;
    private ProyectoDAO proyectoDAO;
    private PeriodoDAO periodoDAO;

    public FXMLCU01_AsignarProyectoController() {
        this.estudianteDAO = new EstudianteDAO();
        this.proyectoDAO = new ProyectoDAO();
        this.periodoDAO = new PeriodoDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcEstudianteNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));

        tcProyectoNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        cargardatos();
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        Proyecto proyectoSeleccionado = tvProyectos.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null || proyectoSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Selección inválida", "Por favor, seleccione un estudiante y un proyecto.");
            return;
        }

        // Aqui podría ir la verificación de avance crediticio y promedio

        String mensajeConfirmacion = "¿Está seguro que desea asignar el proyecto " + proyectoSeleccionado.getNombre() +
                                     " al estudiante " + estudianteSeleccionado.getNombre() + " " +
                                     estudianteSeleccionado.getApellidoPaterno() + " " +
                                     estudianteSeleccionado.getApellidoMaterno() + "?";

        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", mensajeConfirmacion)) {
            try {
                int idPeriodoActual = periodoDAO.obtenerIdPeriodoActual();
                if (idPeriodoActual == -1) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Período", "No se pudo determinar el período actual. No se puede asignar el proyecto.");
                    return;
                }
                estudianteDAO.crearExpedienteEstudianteProyecto(estudianteSeleccionado.getIdUsuario(), proyectoSeleccionado.getIdProyecto(), idPeriodoActual);

                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Operación exitosa", "El proyecto ha sido asignado exitosamente");

                cargardatos();

            } catch (RuntimeException e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la operación",
                                             "Hubo un problema al asignar el proyecto: " + e.getMessage());
            }
        } else {
            if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar el proceso?")) {
                cerrarVentana();
            }
        }
    }

    @FXML
    private void btnSalir(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir? No se guardarán los cambios")) {
            cerrarVentana();
        }
    }

    private void cargardatos() {
        try {
            listaEstudiantesSinProyecto = FXCollections.observableArrayList(estudianteDAO.obtenerEstudiantesSinProyectoAsignado());
            tvEstudiantes.setItems(listaEstudiantesSinProyecto);

            listaProyectosSinAsignar = FXCollections.observableArrayList(proyectoDAO.obtenerProyectosSinAsignar());
            tvProyectos.setItems(listaProyectosSinAsignar);
        } catch (RuntimeException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar datos",
                    "No se pudieron cargar los datos. Inténtalo más tarde. Detalles: " + e.getMessage());
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tvEstudiantes.getScene().getWindow();
        stage.close();
    }

}