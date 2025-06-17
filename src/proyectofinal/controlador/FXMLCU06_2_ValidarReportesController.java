/*
 * Alejandro Martínez Ramírez
 * 07-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.modelo.dao.ExpedienteDAO;
import proyectofinal.modelo.dao.OrganizacionVinculadaDAO;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.dao.ReporteMensualDAO;
import proyectofinal.modelo.dao.ResponsableDeProyectoDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Expediente;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU06_2_ValidarReportesController implements Initializable {

    @FXML
    private TextField tfNumeroReporte;
    @FXML
    private TextField tfNumeroHoras;
    @FXML
    private TextField tfNombreEstudiante;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextField tfPeriodoReporte;
    @FXML
    private TextField tfProyectoVinculado;
    @FXML
    private TextField tfResponsableProyecto;
    @FXML
    private TextField tfOrganizacionVinculada;
    @FXML
    private Label lbNombreReporte;

    private Estudiante estudiante;
    private Proyecto proyecto;
    private ResponsableDeProyecto responsableProyecto;
    private Expediente expediente;
    private ReporteMensual reporte;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonValidarReporte(ActionEvent event) {
        validarReporte(reporte);
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", 
                "¿Desea cancelar la validación del reporte?")) {
            Utilidad.getEscenario(tfNumeroReporte).close();
        }
    }

    public void inicializarInformacion (ReporteMensual reporte) {
        try {
            this.reporte = reporte;
            this.expediente = ExpedienteDAO.obtenerExpedientePorId(reporte.getIdExpediente());
            this.estudiante = EstudianteDAO.obtenerEstudiantePorIdUsuario(expediente.getIdEstudiante());
            this.proyecto = ProyectoDAO.obtenerProyectoPorEstudiante(estudiante.getIdUsuario());
            this.responsableProyecto = ResponsableDeProyectoDAO.obtenerResponsableDeProyectoPorId(proyecto.getIdResponsableDeProyecto());
            tfNombreEstudiante.setText(estudiante.getNombre() + " " + estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno());
            tfMatricula.setText(estudiante.getMatricula());
            tfProyectoVinculado.setText(proyecto.getNombre());
            tfResponsableProyecto.setText(responsableProyecto.getNombre());
            tfOrganizacionVinculada.setText(OrganizacionVinculadaDAO.obtenerOrganizacionVinculadaPorId(responsableProyecto.getIdOrganizacionVinculada()).getNombre());
            tfPeriodoReporte.setText(obtenerPeriodoDeArchivo(reporte.getNombreArchivo()));
            tfNumeroHoras.setText(String.valueOf(reporte.getNumeroHoras()));
            tfNumeroReporte.setText(String.valueOf(reporte.getNumeroReporte()));
            taDescripcion.setText(reporte.getObservaciones());
            lbNombreReporte.setText(reporte.getNombreArchivo());
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfNumeroReporte).close();
        }
    }
    
    private void validarReporte (ReporteMensual reporte) {
        try {
            ResultadoOperacion resultadoValidar = ReporteMensualDAO.validarReporteMensual(reporte.getIdReporteMensual());
            if(!resultadoValidar.isError()){
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "El reporte mensual " + reporte.getNombreArchivo()+ " ha sido validado exitosamente.");
                Utilidad.getEscenario(tfNumeroReporte).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al validar", 
                    resultadoValidar.getMensaje());
                Utilidad.getEscenario(tfNumeroReporte).close();
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfNumeroReporte).close();
        }
    }

    public static String obtenerPeriodoDeArchivo(String nombreArchivo) {
        String periodo = null;
        int ultimoGuionBajo = nombreArchivo.lastIndexOf('_');

        if (ultimoGuionBajo != -1 && ultimoGuionBajo < nombreArchivo.length() - 1) {
            periodo = nombreArchivo.substring(ultimoGuionBajo + 1);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al obtener periodo ", 
                    "Error, no se pudo obtener el periodo del arhivo");
        }
        return periodo;
    }
}
