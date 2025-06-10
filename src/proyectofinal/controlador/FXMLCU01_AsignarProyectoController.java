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
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.dao.PeriodoDAO;
import proyectofinal.modelo.dao.EstudianteDAOImpl;
import proyectofinal.modelo.dao.ProyectoDAOImpl;
import proyectofinal.modelo.dao.PeriodoDAOImpl;


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
        this.estudianteDAO = new EstudianteDAOImpl();
        this.proyectoDAO = new ProyectoDAOImpl();
        this.periodoDAO = new PeriodoDAOImpl();
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
            mostrarAlerta(Alert.AlertType.WARNING, "Selección inválida", "Por favor, seleccione un estudiante y un proyecto.");
            return;
        }

        //Aquí podría ir la verificación de avance crediticio y promedio
        
        Alert confirmacionAsignacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacionAsignacion.setTitle("Confirmación");
        confirmacionAsignacion.setHeaderText("¿Está seguro que desea asignar el proyecto " + proyectoSeleccionado.getNombre() + 
                                             " al estudiante " + estudianteSeleccionado.toString() + "?");

        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        confirmacionAsignacion.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> resultadoConfirmacion = confirmacionAsignacion.showAndWait();

        if (resultadoConfirmacion.isPresent() && resultadoConfirmacion.get() == botonSi) {
            
            try {
                int idPeriodoActual = periodoDAO.obtenerIdPeriodoActual();
                if (idPeriodoActual == -1) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error de Período", "No se pudo determinar el período actual. No se puede asignar el proyecto.");
                    return;
                }

                estudianteDAO.crearExpedienteEstudianteProyecto(estudianteSeleccionado.getIdUsuario(), proyectoSeleccionado.getIdProyecto(), idPeriodoActual);

                Alert operacionExitosa = new Alert(Alert.AlertType.INFORMATION);
                operacionExitosa.setTitle("Operación exitosa");
                operacionExitosa.setHeaderText("El proyecto ha sido asignado exitosamente");
                operacionExitosa.getButtonTypes().setAll(ButtonType.OK);
                operacionExitosa.showAndWait();

                cargardatos();

            } catch (RuntimeException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en la operación",
                        "Hubo un problema al asignar el proyecto: " + e.getMessage());
            }
        } else {
            Alert confirmacionCancelarProceso = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacionCancelarProceso.setTitle("Confirmación");
            confirmacionCancelarProceso.setHeaderText("¿Deseas cancelar el proceso?");
            ButtonType btnSiCancelar = new ButtonType("Sí");
            ButtonType btnNoCancelar = new ButtonType("No");
            confirmacionCancelarProceso.getButtonTypes().setAll(btnSiCancelar, btnNoCancelar);

            Optional<ButtonType> resultadoCancelarProceso = confirmacionCancelarProceso.showAndWait();

            if (resultadoCancelarProceso.isPresent() && resultadoCancelarProceso.get() == btnSiCancelar) {
                cerrarVentana();
            }
        }
    }

    @FXML
    private void btnSalir(ActionEvent event) {
        Alert confirmacionSalir = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacionSalir.setTitle("Confirmación");
        confirmacionSalir.setHeaderText("¿Deseas salir? No se guardarán los cambios");

        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        confirmacionSalir.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> resultado = confirmacionSalir.showAndWait();

        if (resultado.isPresent() && resultado.get() == botonSi) {
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
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar datos",
                    "No se pudieron cargar los datos. Inténtalo más tarde. Detalles: " + e.getMessage());
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tvEstudiantes.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}