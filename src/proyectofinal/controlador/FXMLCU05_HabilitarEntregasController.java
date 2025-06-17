/*
 * Alejandro Martínez Ramírez
 * 11-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import proyectofinal.modelo.dao.AsignacionReporteDAO;
import proyectofinal.modelo.dao.PeriodoDAO;
import proyectofinal.modelo.pojo.AsignacionReporte;
import proyectofinal.modelo.pojo.Periodo;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU05_HabilitarEntregasController implements Initializable {

    @FXML
    private TextArea taDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextField tfTituloAsignacion;

    private AsignacionReporte asignacionActual;
    private boolean esEdicion;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", 
                "¿Deseas salir? El avance no se guardará")) {
            Utilidad.getEscenario(dpFechaFin).close();
        }
    }

    @FXML
    private void clicBotonHabilitar(ActionEvent event) {
        if (validarCampos()) {
            AsignacionReporte asignacion = obtenerAsignacion();
            guardarAsignacionReporte(asignacion);
        } else {
            Utilidad.mostrarAlertaSimple(AlertType.WARNING, "Error", 
                    "Datos inválidos y/o campos vacíos");
        }
        
    }

    @FXML
    private void tfTituloAsignacionPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            taDescripcion.requestFocus();
            event.consume();
        }
    }
    
    public void incializarInformacion () {
        cargarAsignacion();
    }

    private void cargarAsignacion() {
        try {
            this.asignacionActual = AsignacionReporteDAO.obtenerAsignacionActual();
            if (asignacionActual != null) {
                this.esEdicion = true;
                tfTituloAsignacion.setText(asignacionActual.getTitulo());
                taDescripcion.setText(asignacionActual.getDescripcion());
                dpFechaInicio.setValue(LocalDate.parse(asignacionActual.getFechaInicio()));
                dpFechaFin.setValue(LocalDate.parse(asignacionActual.getFechaFin()));
            } else {
                this.esEdicion = false;
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(dpFechaFin).close();
        }
    }
    
    private boolean validarCampos () {
        boolean camposValidos = true;
        String titulo = tfTituloAsignacion.getText().trim();
        String descripcion = taDescripcion.getText().trim();
        LocalDate fechaInicioAsignacion = dpFechaInicio.getValue();
        LocalDate fechaFinAsignacion = dpFechaFin.getValue();
        
        if (titulo.isEmpty() || titulo.length() > 100) {
            camposValidos = false;
        }
        if (descripcion.isEmpty() || descripcion.length() > 100) {
            camposValidos = false;
        } else {
            if (descripcion.length() < 20) {
                camposValidos = false;
                taDescripcion.setText("");
            }
        }
        if (fechaInicioAsignacion == null) {
            camposValidos = false;
        }
        if (fechaFinAsignacion == null) {
            camposValidos = false;
        }
        if (fechaInicioAsignacion != null && fechaFinAsignacion != null) {
            if (fechaInicioAsignacion.isAfter(fechaFinAsignacion)) {
                dpFechaInicio.setValue(null);
                dpFechaFin.setValue(null);
                camposValidos = false;
            }
            if (fechaInicioAsignacion.isEqual(fechaFinAsignacion)) {
                dpFechaInicio.setValue(null);
                dpFechaFin.setValue(null);
                camposValidos = false;
            }
            
            try {
                Periodo periodoActual = PeriodoDAO.obtenerPeriodoActual();

                if (periodoActual == null) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Período", "No se encontró un período actual o reciente en la base de datos. No se puede crear la asignación.");
                    return false;
                }

                LocalDate fechaInicioPeriodo = LocalDate.parse(periodoActual.getFechaInicio(), FORMATO_FECHA);
                LocalDate fechaFinPeriodo = LocalDate.parse(periodoActual.getFechaFin(), FORMATO_FECHA);

                if (fechaInicioAsignacion.isBefore(fechaInicioPeriodo)) {
                    dpFechaInicio.setValue(null);
                    return false;
                }

                if (fechaFinAsignacion.isAfter(fechaFinPeriodo)) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Fecha de Fin Inválida",
                            "La fecha de fin de la asignación (" + fechaFinAsignacion.format(FORMATO_FECHA) +
                            ") no puede ser posterior a la fecha de fin del período actual (" + fechaFinPeriodo.format(FORMATO_FECHA) + ").");
                    dpFechaFin.setValue(null);
                    return false;
                }
            } catch (SQLException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
                Utilidad.getEscenario(dpFechaFin).close();
            }
        }
        return camposValidos;
    }
    
    private AsignacionReporte obtenerAsignacion() {
        AsignacionReporte asignacion = new AsignacionReporte();
        
        if (esEdicion) {
            asignacion.setIdAsignacion(asignacionActual.getIdAsignacion());
        }
        asignacion.setTitulo(tfTituloAsignacion.getText());
        asignacion.setDescripcion(taDescripcion.getText());
        asignacion.setFechaInicio(dpFechaInicio.getValue().toString());
        asignacion.setFechaFin(dpFechaFin.getValue().toString());
        asignacion.setEstatus("Habilitado");
        
        return asignacion;
    }
    
    private void guardarAsignacionReporte (AsignacionReporte asignacionNueva) {
        try {
            ResultadoOperacion resultado;
            if (esEdicion) {
                resultado = AsignacionReporteDAO.actualizarAsignacion(asignacionNueva);
            } else {
                resultado = AsignacionReporteDAO.registrarAsignacion(asignacionNueva);
            }
            if (!resultado.isError()) {
                Utilidad.mostrarAlertaSimple(AlertType.INFORMATION, "Operación exitosa", resultado.getMensaje());
                Utilidad.getEscenario(dpFechaFin).close();
            } else {
                Utilidad.mostrarAlertaSimple(AlertType.ERROR, "Error", resultado.getMensaje());
                Utilidad.getEscenario(dpFechaFin).close();
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(dpFechaFin).close();
        }
    }
}
