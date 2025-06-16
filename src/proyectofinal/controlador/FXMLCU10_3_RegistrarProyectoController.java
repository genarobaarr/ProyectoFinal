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
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.pojo.Coordinador;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU10_3_RegistrarProyectoController implements Initializable {

    @FXML
    private TextField tfNombreProyecto;
    @FXML
    private TextArea taBreveDescripcion;
    @FXML
    private TextArea taObjetivosGenerales;
    @FXML
    private Label lbOrganizacionVinculada;
    @FXML
    private Label lbResponsableProyecto;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    
    private OrganizacionVinculada organizacionVinculada;
    private ResponsableDeProyecto responsableDeProyecto;
    private Usuario coordinador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
        if (validarCampos()) {
            Proyecto proyecto = obtenerProyectoNuevo();
            guardarProyecto(proyecto);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "Datos inválidos y/o campos vacíos");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar el registro del proyecto?")) {
            Utilidad.getEscenario(tfNombreProyecto).close();
        }
    }
    
    public void inicializarInformacion (OrganizacionVinculada organizacionVinculada, ResponsableDeProyecto responsableDeProyecto, Usuario usuario) {
        this.coordinador = (Coordinador)usuario;
        this.organizacionVinculada = organizacionVinculada;
        lbOrganizacionVinculada.setText(organizacionVinculada.getNombre());
        this.responsableDeProyecto = responsableDeProyecto;
        lbResponsableProyecto.setText(responsableDeProyecto.getNombre());
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String nombreProyecto = tfNombreProyecto.getText().trim();
        String descripcion = taBreveDescripcion.getText().trim();
        String objetivos = taObjetivosGenerales.getText().trim();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        
        if (nombreProyecto.isEmpty()) {
            camposValidos = false;
        }
        if (descripcion.isEmpty()) {
            camposValidos = false;
        }
        if (objetivos.isEmpty()) {
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
        proyecto.setIdCoordinador(coordinador.getIdUsuario());
        return proyecto;
    }
    
    private void guardarProyecto (Proyecto proyecto) {
        try {
            ResultadoOperacion resultadoInsertar = ProyectoDAO.registrarProyecto(proyecto);
            if (!resultadoInsertar.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "El proyecto " + proyecto.getNombre() + " ha sido registrado exitosamente.");
                Utilidad.getEscenario(tfNombreProyecto).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al regitrar proyecto", resultadoInsertar.getMensaje());
                Utilidad.getEscenario(tfNombreProyecto).close();
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfNombreProyecto).close();
        }
    }
}