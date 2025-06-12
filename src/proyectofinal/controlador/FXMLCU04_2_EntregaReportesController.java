/*
 * Alejandro Martínez Ramírez
 * 02-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import proyectofinal.modelo.dao.ExpedienteDAO;
import proyectofinal.modelo.dao.OrganizacionVinculadaDAO;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.dao.ReporteMensualDAO;
import proyectofinal.modelo.dao.ResponsableDeProyectoDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU04_2_EntregaReportesController implements Initializable {

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
    
    private Estudiante estudiante;
    private Proyecto proyecto;
    private ResponsableDeProyecto responsableProyecto;
    private int idExpediente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void tfNumeroReportePresionaEnter(KeyEvent event) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        Utilidad.getEscenario(tfNumeroReporte).close();
    }

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
    if (validarCampos()) {
        ReporteMensual reporteMensual = obtenerNuevoReporteMensual();
        guardarReporteMensual(reporteMensual);
    }else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "Datos inválidos y/o campos vacíos");
        }
    }

    @FXML
    private void tfNumeroHorasPresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfNombreEstudiantePresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfMatriculaPresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfPeriodoReportePresionaEnter(KeyEvent event) {
    }
    
    public void inicializarInformacion (Estudiante estudiante) {
        try {
            this.estudiante = estudiante;
            this.proyecto = obtenerProyectoDeEstudiante(estudiante.getIdUsuario());
            this.responsableProyecto = obtenerResponsableDeProyecto(proyecto.getIdResponsableDeProyecto());
            this.idExpediente = obtenerIdExpediente(estudiante.getIdUsuario());
            tfNombreEstudiante.setText(estudiante.getNombre()+" "+estudiante.getApellidoPaterno()+" "+estudiante.getApellidoMaterno());
            tfMatricula.setText(estudiante.getMatricula());
            tfProyectoVinculado.setText(proyecto.getNombre());
            tfResponsableProyecto.setText(responsableProyecto.getNombre());
            tfOrganizacionVinculada.setText(obtenerOrganizacionVinculada(responsableProyecto.getIdOrganizacionVinculada()).getNombre());
        } catch (SQLException ex) {
             ex.printStackTrace();
        }
    }
    
    private Proyecto obtenerProyectoDeEstudiante(int idEstudiante) throws SQLException {
        Proyecto proyecto = ProyectoDAO.obtenerProyectoPorEstudiante(idEstudiante);
        return proyecto;
    }
    
    private ResponsableDeProyecto obtenerResponsableDeProyecto(int idResponsableProyecto) throws SQLException {
        ResponsableDeProyecto responsableProyecto = ResponsableDeProyectoDAO.obtenerResponsableDeProyectoPorId(idResponsableProyecto);
        return responsableProyecto;
    }
    
    private OrganizacionVinculada obtenerOrganizacionVinculada(int idOrganizacionVinculada) throws SQLException {
        OrganizacionVinculada organizacionVinculada = OrganizacionVinculadaDAO.obtenerOrganizacionVinculadaPorId(idOrganizacionVinculada);
        return organizacionVinculada;
    }
    
    private int obtenerIdExpediente(int idEstudiante) throws SQLException {
        int idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(idEstudiante);
        return idExpediente;
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String numeroReporte = tfNumeroReporte.getText();
        String numeroHoras = tfNumeroHoras.getText();
        String periodoReporte = tfPeriodoReporte.getText();
        String descripcion = taDescripcion.getText();
        
        if (numeroReporte.isEmpty() ) {
            camposValidos = false;
        } else {
            try {
                int numeroReporteParseado = Integer.parseInt(numeroReporte);
                if (numeroReporteParseado < 0 || numeroReporteParseado > 6) {
                    camposValidos = false;
                    tfNumeroReporte.setText("");
                }
            } catch (NumberFormatException e) {
                camposValidos = false;
                tfNumeroReporte.setText("");
            }
        }
        if (numeroHoras.isEmpty()) {
            camposValidos = false;
        } else {
            try {
                int numeroHorasParseado = Integer.parseInt(numeroHoras);
                if (numeroHorasParseado < 0) {
                    camposValidos = false;
                    tfNumeroHoras.setText("");
                }
            } catch (NumberFormatException e) {
                camposValidos = false;
                tfNumeroHoras.setText("");
            }
        }
        if (periodoReporte.isEmpty()) {
            camposValidos = false;
        }
        if (descripcion == null) {
            camposValidos = false;
        }
        return camposValidos;
        }
    
    public ReporteMensual obtenerNuevoReporteMensual () {
        ReporteMensual reporteMensual = new ReporteMensual();
        reporteMensual.setNumeroReporte(Integer.parseInt(tfNumeroReporte.getText()));
        reporteMensual.setNumeroHoras(Integer.parseInt(tfNumeroHoras.getText()));
        reporteMensual.setObservaciones(taDescripcion.getText());
        reporteMensual.setExtensionArchivo("pdf");
        reporteMensual.setIdExpediente(idExpediente);
        reporteMensual.setNombreArchivo(estudiante.getNombre() + estudiante.getApellidoPaterno() + estudiante.getApellidoMaterno() 
                + "_Reporte_Mensual_" + Integer.parseInt(tfNumeroReporte.getText()) + "_" + tfPeriodoReporte.getText());
        return reporteMensual;
    }
    
    public void guardarReporteMensual (ReporteMensual reporteMensual) {
        try {
            ResultadoOperacion resultado = ReporteMensualDAO.registrarReporteMensual(reporteMensual);
            if (!resultado.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "Reporte mensual registrado");
                Utilidad.getEscenario(tfNumeroReporte).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al regitrar", resultado.getMensaje());
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de conexión", "Por el momento no hay conexión.");
            ex.printStackTrace();
        }
        
    }
    }
