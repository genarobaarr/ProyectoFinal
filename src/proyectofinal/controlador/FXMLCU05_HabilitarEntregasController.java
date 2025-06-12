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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarAsignacion();
    }

    private void cargarAsignacion() {
        try {
            asignacionActual = AsignacionReporteDAO.obtenerAsignacionActual();
            if (asignacionActual != null) {
                tfTituloAsignacion.setText(asignacionActual.getTitulo());
                taDescripcion.setText(asignacionActual.getDescripcion());
                dpFechaInicio.setValue(LocalDate.parse(asignacionActual.getFechaInicio()));
                dpFechaFin.setValue(LocalDate.parse(asignacionActual.getFechaFin()));
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(AlertType.ERROR, "Error", "No se pudo cargar la asignación actual");
        }
    }
    
    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        cargarAsignacion();
    }

    @FXML
    private void clicBotonHabilitar(ActionEvent event) {
        if (asignacionActual == null) {
            Utilidad.mostrarAlertaSimple(AlertType.ERROR, "Error", "No se encontró una asignación para actualizar.");
            return;
        }

        String titulo = tfTituloAsignacion.getText().trim();
        String descripcion = taDescripcion.getText().trim();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();

        if (titulo.isEmpty() || descripcion.isEmpty() || fechaInicio == null || fechaFin == null) {
            Utilidad.mostrarAlertaSimple(AlertType.WARNING, "Campos incompletos", "Por favor, llena todos los campos.");
            return;
        }

        asignacionActual.setTitulo(titulo);
        asignacionActual.setDescripcion(descripcion);
        asignacionActual.setFechaInicio(fechaInicio.toString());
        asignacionActual.setFechaFin(fechaFin.toString());
        asignacionActual.setEstatus("Habilitado");

        try {
            ResultadoOperacion resultado = AsignacionReporteDAO.actualizarAsignacion(asignacionActual);
            Utilidad.mostrarAlertaSimple(
                resultado.isError() ? AlertType.ERROR : AlertType.INFORMATION,
                "Resultado",
                resultado.getMensaje()
            );
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(AlertType.ERROR, "Error", "Error al actualizar la asignación: " + e.getMessage());
        }
    }
}
