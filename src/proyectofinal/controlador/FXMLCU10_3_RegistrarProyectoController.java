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
import javafx.stage.Stage;
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
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorDescripcion;
    @FXML
    private Label lbErrorObjetivos;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private Label lbErrorFechaInicio;
    @FXML
    private Label lbErrorFechaFin;
    @FXML
    private DatePicker dpFechaFin;
    
    private OrganizacionVinculada organizacionVinculada;
    private ResponsableDeProyecto responsableDeProyecto;
    private Usuario coordinador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }    

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
        if (validarCampos()) {
            Proyecto proyecto = obtenerProyectoNuevo();
            guardarProyecto(proyecto);
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Desea cancelar el registro del proyecto?")) {
            cerrarVentana();
        }
    }
    
    public void inicializarInformacion (OrganizacionVinculada organizacionVinculada, ResponsableDeProyecto responsableDeProyecto, Usuario usuario) {
        this.coordinador = (Coordinador)usuario;
        this.organizacionVinculada = organizacionVinculada;
        lbOrganizacionVinculada.setText(organizacionVinculada.getNombre());
        this.responsableDeProyecto = responsableDeProyecto;
        lbResponsableProyecto.setText(responsableDeProyecto.getNombre());
    }
    
    private void cerrarVentana(){
        ((Stage) tfNombreProyecto.getScene().getWindow()).close();
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String nombreProyecto = tfNombreProyecto.getText().trim();
        String descripcion = taBreveDescripcion.getText().trim();
        String objetivos = taObjetivosGenerales.getText().trim();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        
        lbErrorDescripcion.setText("");
        lbErrorNombre.setText("");
        lbErrorObjetivos.setText("");
        lbErrorFechaInicio.setText("");
        lbErrorFechaFin.setText("");
        
        if (nombreProyecto.isEmpty()) {
            lbErrorNombre.setText("Nombre de proyecto requerido");
            camposValidos = false;
        }
        if (descripcion.isEmpty()) {
            lbErrorDescripcion.setText("Descripción requerida");
            camposValidos = false;
        }
        if (objetivos.isEmpty()) {
            lbErrorObjetivos.setText("Objetivos generales requeridos");
            camposValidos = false;
        }
        if (fechaInicio == null) {
            lbErrorFechaInicio.setText("Fecha de inicio requerida");
        }
        if (fechaFin == null) {
            lbErrorFechaFin.setText("Fecha de fin requerida");
        }
        if (fechaInicio != null || fechaFin != null) {
            if (fechaInicio.isAfter(fechaFin)) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error de fechas", "La fecha de inicio no puede ser posterior a la fecha de fin.");
                lbErrorFechaInicio.setText("Fecha de inicio inválida");
                lbErrorFechaFin.setText("Fecha de fin inválida");
                camposValidos = false;
            }
        }
        return camposValidos;
    }
    
    
    private Proyecto obtenerProyectoNuevo() {
        Proyecto proyecto = new Proyecto();
        proyecto.setDescripcion(taBreveDescripcion.getText());
        proyecto.setNombre(tfNombreProyecto.getText());
        proyecto.setFechaInicio(dpFechaInicio.getValue().toString());
        proyecto.setFechaFin(dpFechaFin.getValue().toString());
        proyecto.setIdResponsableDeProyecto(responsableDeProyecto.getIdResponsableDeProyecto());
        proyecto.setIdCoordinador(coordinador.getIdUsuario());
        System.out.println(coordinador.toString());
        return proyecto;
    }
    
    private void guardarProyecto (Proyecto proyecto) {
        /*try {
            ResultadoOperacion resultadoInsertar = ProyectoDAO.registrarProyecto
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de conexión", "Por el momento no hay conexión.");
        }*/
    }
}
