/*
 * Alejandro Martínez Ramírez
 * 11-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import proyectofinal.modelo.dao.AsignacionReporteDAO;
import proyectofinal.modelo.pojo.AsignacionReporte;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir? El avance no se guardará")) {
            Utilidad.getEscenario(dpFechaFin).close();
        }
    }

    @FXML
    private void clicBotonHabilitar(ActionEvent event) {
        if (validarCampos()) {
            AsignacionReporte asignacion = obtenerAsignacion();
            guardarAsignacionReporte(asignacion);
        } else {
            Utilidad.mostrarAlertaSimple(AlertType.WARNING, "Error", "Datos inválidos y/o campos vacíos");
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
            Utilidad.mostrarAlertaSimple(AlertType.ERROR, "Error", "No se pudo cargar la asignación actual");
            Utilidad.getEscenario(dpFechaFin).close();
        }
    }
    
    private boolean validarCampos () {
        boolean camposValidos = true;
        String titulo = tfTituloAsignacion.getText().trim();
        String descripcion = taDescripcion.getText().trim();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        
        if (titulo.isEmpty()) {
            camposValidos = false;
        }
        if (descripcion.isEmpty()) {
            camposValidos = false;
        } else {
            if (descripcion.length() < 20) {
                camposValidos = false;
                taDescripcion.setText("");
            }
        }
        if (fechaInicio == null) {
            camposValidos = false;
        }
        if (fechaFin == null) {
            camposValidos = false;
        }
        if (fechaInicio != null || fechaFin != null) {
            if (fechaInicio.isAfter(fechaFin)) {
                dpFechaInicio.setValue(null);
                dpFechaFin.setValue(null);
                camposValidos = false;
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
            Utilidad.mostrarAlertaSimple(AlertType.ERROR, "Error", "Error al actualizar la asignación");
            Utilidad.getEscenario(dpFechaFin).close();
        }
    }
}
