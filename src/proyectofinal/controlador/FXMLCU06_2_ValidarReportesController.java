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
import proyectofinal.modelo.pojo.OrganizacionVinculada;
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
            this.expediente = obtenerExpediente(reporte.getIdExpediente());
            this.estudiante = obtenerEstudiante(expediente.getIdEstudiante());
            this.proyecto = obtenerProyectoDeEstudiante(estudiante.getIdUsuario());
            this.responsableProyecto = obtenerResponsableDeProyecto(proyecto.getIdResponsableDeProyecto());
            tfNombreEstudiante.setText(estudiante.getNombre()+" "+estudiante.getApellidoPaterno()+" "+estudiante.getApellidoMaterno());
            tfMatricula.setText(estudiante.getMatricula());
            tfProyectoVinculado.setText(proyecto.getNombre());
            tfResponsableProyecto.setText(responsableProyecto.getNombre());
            tfOrganizacionVinculada.setText(obtenerOrganizacionVinculada(responsableProyecto.getIdOrganizacionVinculada()).getNombre());
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
    
    private Proyecto obtenerProyectoDeEstudiante(int idEstudiante) throws SQLException {
        Proyecto proyecto = ProyectoDAO.obtenerProyectoPorEstudiante(idEstudiante);
        return proyecto;
    }
    
    private Estudiante obtenerEstudiante(int idEstudiante) throws SQLException {
        Estudiante estudiante = EstudianteDAO.obtenerEstudiantePorIdUsuario(idEstudiante);
        return estudiante;
    }
    
    private ResponsableDeProyecto obtenerResponsableDeProyecto(int idResponsableProyecto) throws SQLException {
        ResponsableDeProyecto responsableProyecto = ResponsableDeProyectoDAO.obtenerResponsableDeProyectoPorId(idResponsableProyecto);
        return responsableProyecto;
    }
    
    private OrganizacionVinculada obtenerOrganizacionVinculada(int idOrganizacionVinculada) throws SQLException {
        OrganizacionVinculada organizacionVinculada = OrganizacionVinculadaDAO.obtenerOrganizacionVinculadaPorId(idOrganizacionVinculada);
        return organizacionVinculada;
    }
    
    private Expediente obtenerExpediente(int idExpediente) throws SQLException {
        Expediente expediente = ExpedienteDAO.obtenerExpedientePorId(idExpediente);
        return expediente;
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
