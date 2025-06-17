/*
 * Genaro Alejandro Barradas Sánchez
 * 07-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import proyectofinal.modelo.dao.OrganizacionVinculadaDAO;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.dao.ResponsableDeProyectoDAO;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU11_3_ActualizarProyectoController implements Initializable {

    @FXML
    private TextField tfNombreProyecto;
    @FXML
    private TextArea taBreveDescripcion;
    @FXML
    private TextArea taObjetivosGenerales;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private Label lbResponsableProyecto;
    @FXML
    private Label lbOrganizacionVinculada;
    
    private Proyecto proyectoAntiguo;
    private OrganizacionVinculada organizacionVinculada;
    private ResponsableDeProyecto responsableDeProyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar el registro del proyecto?")) {
            Utilidad.getEscenario(tfNombreProyecto).close();
        }
    }

    @FXML
    private void clicBotonGuardar(ActionEvent event) {
        if (validarCampos()) {
            if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Está seguro de guardar los cambios?")) {
                Proyecto proyecto = obtenerProyectoNuevo();
                guardarProyecto(proyecto);
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "Datos inválidos y/o campos vacíos");
        }
    }
    
    public void inicializarInformacion (Proyecto proyecto) {
        try {
            this.proyectoAntiguo = proyecto;
            this.responsableDeProyecto = ResponsableDeProyectoDAO.obtenerResponsableDeProyectoPorId(proyecto.getIdResponsableDeProyecto());
            this.organizacionVinculada = OrganizacionVinculadaDAO.obtenerOrganizacionVinculadaPorId(responsableDeProyecto.getIdOrganizacionVinculada());
            cargarInformacionFormulario();
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfNombreProyecto).close();
        }
    }
    
    private void cargarInformacionFormulario () {
        lbResponsableProyecto.setText(responsableDeProyecto.getNombre());
        lbOrganizacionVinculada.setText(organizacionVinculada.getNombre());
        taBreveDescripcion.setText(proyectoAntiguo.getDescripcion());
        taObjetivosGenerales.setText(proyectoAntiguo.getObjetivos());
        tfNombreProyecto.setText(proyectoAntiguo.getNombre());
        dpFechaInicio.setValue(LocalDate.parse(proyectoAntiguo.getFechaInicio().toString()));
        dpFechaFin.setValue(LocalDate.parse(proyectoAntiguo.getFechaFin().toString()));
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String nombreProyecto = tfNombreProyecto.getText().trim();
        String descripcion = taBreveDescripcion.getText().trim();
        String objetivos = taObjetivosGenerales.getText().trim();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        
        if (nombreProyecto.isEmpty() || nombreProyecto.length() > 50 || nombreProyecto.length() < 5) {
            tfNombreProyecto.setText("");
            camposValidos = false;
        }
        if (descripcion.isEmpty() || descripcion.length() > 200 || descripcion.length() < 15) {
            taBreveDescripcion.setText("");
            camposValidos = false;
        }
        if (objetivos.isEmpty() || objetivos.length() > 255 || objetivos.length() < 15) {
            taObjetivosGenerales.setText("");
            camposValidos = false;
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
    
    private Proyecto obtenerProyectoNuevo() {
        Proyecto proyecto = new Proyecto();
        proyecto.setDescripcion(taBreveDescripcion.getText());
        proyecto.setObjetivos(taObjetivosGenerales.getText());
        proyecto.setNombre(tfNombreProyecto.getText());
        proyecto.setFechaInicio(dpFechaInicio.getValue().toString());
        proyecto.setFechaFin(dpFechaFin.getValue().toString());
        proyecto.setIdResponsableDeProyecto(responsableDeProyecto.getIdResponsableDeProyecto());
        proyecto.setIdCoordinador(proyectoAntiguo.getIdCoordinador());
        proyecto.setIdProyecto(proyectoAntiguo.getIdProyecto());
        return proyecto;
    }
    
    private void guardarProyecto (Proyecto proyecto) {
        try {
            ResultadoOperacion resultadoModificar = ProyectoDAO.modificarProyecto(proyecto);
            if (!resultadoModificar.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "Los cambios han sido guardados exitosamente.");
                Utilidad.getEscenario(tfNombreProyecto).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al modificar proyecto", resultadoModificar.getMensaje());
                Utilidad.getEscenario(tfNombreProyecto).close();
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfNombreProyecto).close();
        }
    }
}