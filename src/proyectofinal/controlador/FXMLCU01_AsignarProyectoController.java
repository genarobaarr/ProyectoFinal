/*
 * Omar Morales García
 * 09-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.dao.PeriodoDAO;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.modelo.pojo.Periodo;
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
        configurarTabla();
        cargarDatos();
    }

    @FXML
    private void clicBotonAceptar(ActionEvent event) {
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        Proyecto proyectoSeleccionado = tvProyectos.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null || proyectoSeleccionado == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Selección inválida", 
                    "Por favor, seleccione un estudiante y un proyecto.");
            return;
        }

        String mensajeConfirmacion = "¿Está seguro que desea asignar el proyecto " + 
                proyectoSeleccionado.getNombre() + " al estudiante " + 
                estudianteSeleccionado.toString() + "?";
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", mensajeConfirmacion)) {
            try {
                Periodo periodoActual = PeriodoDAO.obtenerPeriodoActual();
                if (periodoActual == null) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Período", 
                            "No se pudo determinar el período actual. No se puede asignar el proyecto.");
                    return;
                }
                EstudianteDAO.crearExpedienteEstudianteProyecto(estudianteSeleccionado.getIdUsuario(), proyectoSeleccionado.getIdProyecto(), periodoActual.getIdPeriodo());
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Operación exitosa", 
                        "El proyecto ha sido asignado exitosamente");
                cargarDatos();
            } catch (SQLException e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
                Utilidad.getEscenario(tvProyectos).close();
            }
        } else {
            if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar el proceso?")) {
                Utilidad.getEscenario(tvProyectos).close();
            }
        }
    }
    
    @FXML
    private void clicBotonSalir(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", 
                "¿Deseas salir? No se guardarán los cambios")) {
            Utilidad.getEscenario(tvProyectos).close();
        }
    }
    
    public void configurarTabla(){
        tcEstudianteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcProyectoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    private void cargarDatos() {
        try {
            listaEstudiantesSinProyecto = FXCollections.observableArrayList(EstudianteDAO.obtenerEstudiantesSinProyectoAsignado());
            tvEstudiantes.setItems(listaEstudiantesSinProyecto);

            listaProyectosSinAsignar = FXCollections.observableArrayList(ProyectoDAO.obtenerProyectosSinAsignar());
            tvProyectos.setItems(listaProyectosSinAsignar);
        } catch (RuntimeException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar datos",
                    "No se pudieron cargar los datos. Inténtalo más tarde.");
            Utilidad.getEscenario(tvProyectos).close();
        } catch (SQLException ex){
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tvProyectos).close();
        }
    }
}