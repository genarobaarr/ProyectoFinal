/*
 * Alejandro Martínez Ramírez
 * 02-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    private static final List<String> NOMBRES_MESES = Arrays.asList(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", 
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar el registro del reporte? No se guardarán los cambios")) {
            Utilidad.getEscenario(taDescripcion).close();
        }
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
        if (event.getCode() == KeyCode.ENTER) {
            tfPeriodoReporte.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfPeriodoReportePresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            taDescripcion.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void taDescripcionPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            clicBotonRegistrar(new ActionEvent());
            event.consume();
        }
    }
    
    public void inicializarInformacion (Estudiante estudiante) {
        try {
            this.estudiante = estudiante;
            this.proyecto = obtenerProyectoDeEstudiante(estudiante.getIdUsuario());
            this.responsableProyecto = obtenerResponsableDeProyecto(proyecto.getIdResponsableDeProyecto());
            this.idExpediente = obtenerIdExpediente(estudiante.getIdUsuario());
            tfNumeroReporte.setText(String.valueOf(obtenerNumeroReporte(idExpediente)));
            tfNombreEstudiante.setText(estudiante.getNombre()+" "+estudiante.getApellidoPaterno()+" "+estudiante.getApellidoMaterno());
            tfMatricula.setText(estudiante.getMatricula());
            tfProyectoVinculado.setText(proyecto.getNombre());
            tfResponsableProyecto.setText(responsableProyecto.getNombre());
            tfOrganizacionVinculada.setText(obtenerOrganizacionVinculada(responsableProyecto.getIdOrganizacionVinculada()).getNombre());
        } catch (SQLException ex) {
            ex.printStackTrace();
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la información", "No se pudo cargar la inforamción");
            Utilidad.getEscenario(tfNumeroReporte).close();
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
    
    private int obtenerNumeroReporte(int idExpediente) throws SQLException {
        int numeroReporte = ReporteMensualDAO.obtenerSiguienteNumeroReporte(idExpediente);
        return numeroReporte;
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String numeroHoras = tfNumeroHoras.getText().trim();
        String periodoReporte = tfPeriodoReporte.getText().trim();
        String descripcion = taDescripcion.getText().trim();
        
        if (numeroHoras.isEmpty()) {
            camposValidos = false;
        } else {
            try {
                int numeroHorasParseado = Integer.parseInt(numeroHoras);
                if (numeroHorasParseado <= 0) {
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
        } else if (!validarFormatoPeriodo(periodoReporte)) {
            camposValidos = false;
            tfPeriodoReporte.setText("");
        }
        if (descripcion.isEmpty()) {
            camposValidos = false;
        }
        return camposValidos;
    }
    
    private boolean validarFormatoPeriodo(String periodo) {
        if (!periodo.contains("-")) {
            return false;
        }
        String[] partes = periodo.split("-");
        if (partes.length != 2) {
            return false;
        }
        
        String mes1 = partes[0].trim();
        String mes2 = partes[1].trim();
        
        if (mes1.isEmpty() || mes2.isEmpty()) {
            return false;
        }
        
        int indiceMes1 = NOMBRES_MESES.indexOf(mes1);
        int indiceMes2 = NOMBRES_MESES.indexOf(mes2);
        
        if (indiceMes1 == -1) {
            return false;
        }
        if (indiceMes2 == -1) {
            return false;
        }
        if (indiceMes2 != (indiceMes1 + 1)) {
            if (indiceMes1 == NOMBRES_MESES.size() - 1 && indiceMes2 == 0) {
                return true;
            }
            return false;
        }
        return true;
    }
    
    public ReporteMensual obtenerNuevoReporteMensual () {
        ReporteMensual reporteMensual = new ReporteMensual();
        reporteMensual.setNumeroReporte(Integer.parseInt(tfNumeroReporte.getText()));
        reporteMensual.setNumeroHoras(Integer.parseInt(tfNumeroHoras.getText()));
        reporteMensual.setObservaciones(taDescripcion.getText());
        reporteMensual.setExtensionArchivo("pdf");
        reporteMensual.setIdExpediente(idExpediente);
        reporteMensual.setNombreArchivo(estudiante.getNombre() + estudiante.getApellidoPaterno() + estudiante.getApellidoMaterno() 
                + "_ReporteMensual_" + Integer.parseInt(tfNumeroReporte.getText()) + "_" + tfPeriodoReporte.getText());
        return reporteMensual;
    }
    
    public void guardarReporteMensual (ReporteMensual reporteMensual) {
        try {
            ResultadoOperacion resultadoReporte = ReporteMensualDAO.registrarReporteMensual(reporteMensual);
            ResultadoOperacion resultadoHoras = ExpedienteDAO.actualizarNumeroHorasAcumuladas(reporteMensual.getNumeroHoras(), idExpediente);
            if (!resultadoReporte.isError() && !resultadoHoras.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "Reporte mensual registrado");
                Utilidad.getEscenario(tfNumeroReporte).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al regitrar", resultadoReporte.getMensaje());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de conexión", "Por el momento no hay conexión.");
        }
    }
}
