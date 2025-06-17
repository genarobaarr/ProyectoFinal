/*
 * Genaro Alejandro Barradas Sánchez
 * 08-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.dao.AsignacionReporteDAO;
import proyectofinal.modelo.pojo.Academico;
import proyectofinal.modelo.pojo.AsignacionReporte;
import proyectofinal.modelo.pojo.Coordinador;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLReportesController implements Initializable {

    @FXML
    private Label lbReloj;
    @FXML
    private Button btnValidarReportes;
    @FXML
    private Button btnNuevoReporte;
    @FXML
    private Button btnHabilitarEntrega;
    @FXML
    private ImageView ivValidarReportes;
    @FXML
    private ImageView ivNuevoReporte;
    @FXML
    private ImageView ivHabilitarEntrega;
    
    private Estudiante estudiante;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilidad.mostrarHora(lbReloj);
        validarFechaAsignacionReporte();
    }

    @FXML
    private void clicBotonValidarReportes(ActionEvent event) {
        irPantalla("vista/FXMLCU06_1_ValidarReportes.fxml", "Reportes entregados");
    }

    @FXML
    private void clicBotonNuevoReporte(ActionEvent event) {
        AsignacionReporte asignacionActual = obtenerAsignacionReporteActual();
        if (asignacionActual != null && "Inhabilitado".equals(asignacionActual.getEstatus())) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                    "Asignación Inactiva",
                    "No se pueden entregar nuevos reportes. La asignación actual está inactiva.");  
            return; 
        }
        irPantalla("vista/FXMLCU04_1_EntregaReportes.fxml", "Reportes mensuales");
    }

    @FXML
    private void clicBotonHabilitarEntrega(ActionEvent event) {
        irPantalla("vista/FXMLCU05_HabilitarEntregas.fxml", "Habilitar entre de reportes");
    }

    @FXML
    private void clicBotonRegresar(ActionEvent event) {
        Utilidad.getEscenario(lbReloj).close();
    }
    
    public void inicializarInformacion(Usuario usuario) {
        if (usuario instanceof Academico) {
            btnValidarReportes.setDisable(false);
            
            btnNuevoReporte.setDisable(true);
            btnNuevoReporte.setText("No disponible");
            ivNuevoReporte.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnHabilitarEntrega.setDisable(true);
            btnHabilitarEntrega.setText("No disponible");
            ivHabilitarEntrega.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
        } else if (usuario instanceof Estudiante) {
            this.estudiante = (Estudiante)usuario;
            btnValidarReportes.setDisable(true);
            btnValidarReportes.setText("No disponible");
            ivValidarReportes.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnNuevoReporte.setDisable(false);
            
            btnHabilitarEntrega.setDisable(true);
            btnHabilitarEntrega.setText("No disponible");
            ivHabilitarEntrega.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
        } else if (usuario instanceof Coordinador) {
            btnValidarReportes.setDisable(true);
            btnValidarReportes.setText("No disponible");
            ivValidarReportes.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnNuevoReporte.setDisable(true);
            btnNuevoReporte.setText("No disponible");
            ivNuevoReporte.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnHabilitarEntrega.setDisable(false);
        }
    }
    
    public void irPantalla(String fxmlPath, String titulo) {
        try {
            Stage escenarioNuevo = new Stage();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
            Parent vista = cargador.load();
            
            switch (fxmlPath) {
                case "vista/FXMLCU04_1_EntregaReportes.fxml": {
                    FXMLCU04_1_EntregaReportesController controladorEntregaReportes = cargador.getController();
                    controladorEntregaReportes.inicializarInformacion(estudiante);
                    break;
                }
                case "vista/FXMLCU06_1_ValidarReportes.fxml": {
                    FXMLCU06_1_ValidarReportesController controladorValidarReportes = cargador.getController();
                    controladorValidarReportes.inicializarInformacion();
                    break;
                }
                case "vista/FXMLCU05_HabilitarEntregas.fxml": {
                    FXMLCU05_HabilitarEntregasController controladorHabilitarEntregas = cargador.getController();
                    controladorHabilitarEntregas.incializarInformacion();
                    break;
                }
            }            
            Scene escena = new Scene(vista);
            escenarioNuevo.setScene(escena);
            escenarioNuevo.setTitle(titulo);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
            Utilidad.getEscenario(lbReloj).close();
        }
    }
    
    public void validarFechaAsignacionReporte() {
        AsignacionReporte resultadoAsignacionReporte = obtenerAsignacionReporteActual();
        
        if (resultadoAsignacionReporte == null) {
            return;
        }
        String fechaInicioActual = resultadoAsignacionReporte.getFechaInicio();
        String fechaFinActual = resultadoAsignacionReporte.getFechaFin();
        LocalDate fechaActual = LocalDate.now();
        String estatusActual = resultadoAsignacionReporte.getEstatus();
        String valorDeEstatusCalculado = "Inhabilitado";

        try {
            if (fechaInicioActual != null && !fechaInicioActual.trim().isEmpty() &&
                fechaFinActual != null && !fechaFinActual.trim().isEmpty()) {
                LocalDate fechaInicioAsignacion = LocalDate.parse(fechaInicioActual, FORMATO_FECHA);
                LocalDate fechaFinAsignacion = LocalDate.parse(fechaFinActual, FORMATO_FECHA);

                if (!fechaActual.isBefore(fechaInicioAsignacion) 
                        && !fechaActual.isAfter(fechaFinAsignacion)) {
                    valorDeEstatusCalculado = "Habilitado";
                } else {
                    valorDeEstatusCalculado = "Inhabilitado";
                }
            } else {
                valorDeEstatusCalculado = "Inhabilitado";
            }

            if (!estatusActual.equals(valorDeEstatusCalculado)) {
                resultadoAsignacionReporte.setEstatus(valorDeEstatusCalculado);
                actualizarEstatusAsignacionReporte(resultadoAsignacionReporte);
            }
        } catch (DateTimeParseException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                "Error al cargar la pantalla", "No se pudo cargar la pantalla siguiente.");
        }
        
    }
    
    public AsignacionReporte obtenerAsignacionReporteActual() {
        AsignacionReporte asignacionReporte = null;
        try {
            asignacionReporte = AsignacionReporteDAO.obtenerAsignacionActual();
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de conexión", "Por el momento no hay conexión.");
            Utilidad.getEscenario(lbReloj).close();
        }
        return asignacionReporte;
    }
    
    public void actualizarEstatusAsignacionReporte(AsignacionReporte asignacionReporte) {
        try {
            ResultadoOperacion resultadoInsertar = AsignacionReporteDAO.actualizarAsignacion(asignacionReporte);
            if (resultadoInsertar.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
                Utilidad.getEscenario(lbReloj).close();
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(lbReloj).close();
        }
    }
}