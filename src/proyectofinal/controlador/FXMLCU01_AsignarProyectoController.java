/*
 * Omar Morales García
 * 09-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcEstudianteNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().toString()));
        tcProyectoNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        cargardatos();
    }

    @FXML
    private void clicBotonAceptar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        Proyecto proyectoSeleccionado = tvProyectos.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null || proyectoSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Selección inválida", "Por favor, seleccione un estudiante y un proyecto.");
            return;
        }

        String mensajeConfirmacion = "¿Está seguro que desea asignar el proyecto " + proyectoSeleccionado.getNombre() +
                                     " al estudiante " + estudianteSeleccionado.toString() + "?";

        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", mensajeConfirmacion)) {
            try {
                int idPeriodoActual = PeriodoDAO.obtenerIdPeriodoActual();
                if (idPeriodoActual == -1) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Período", "No se pudo determinar el período actual. No se puede asignar el proyecto.");
                    return;
                }
                EstudianteDAO.crearExpedienteEstudianteProyecto(estudianteSeleccionado.getIdUsuario(), proyectoSeleccionado.getIdProyecto(), idPeriodoActual);

                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Operación exitosa", "El proyecto ha sido asignado exitosamente");

                cargardatos();

            } catch (SQLException e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la operación",
                                             "Hubo un problema al asignar el proyecto: " + e.getMessage());
            }
        } else {
            if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar el proceso?")) {
                Utilidad.getEscenario(tvProyectos).close();
            }
        }
    }

    @FXML
    private void clicBotonSalir(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir? No se guardarán los cambios")) {
            Utilidad.getEscenario(tvProyectos).close();
        }
    }

    private void cargardatos() {
        try {
            listaEstudiantesSinProyecto = FXCollections.observableArrayList(EstudianteDAO.obtenerEstudiantesSinProyectoAsignado());
            tvEstudiantes.setItems(listaEstudiantesSinProyecto);

            listaProyectosSinAsignar = FXCollections.observableArrayList(ProyectoDAO.obtenerProyectosSinAsignar());
            tvProyectos.setItems(listaProyectosSinAsignar);
        } catch (RuntimeException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar datos",
                    "No se pudieron cargar los datos. Inténtalo más tarde. Detalles: " + e.getMessage());
        }
    }
}