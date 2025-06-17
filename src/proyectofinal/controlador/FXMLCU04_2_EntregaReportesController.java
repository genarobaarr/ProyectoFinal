/*
 * Alejandro Martínez Ramírez
 * 02-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import proyectofinal.modelo.dao.PeriodoDAO;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.dao.ReporteMensualDAO;
import proyectofinal.modelo.dao.ResponsableDeProyectoDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Periodo;
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
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", 
                "¿Deseas cancelar el registro del reporte? No se guardarán los cambios")) {
            Utilidad.getEscenario(taDescripcion).close();
        }
    }

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
        if (validarCampos()) {
            ReporteMensual reporteMensual = obtenerNuevoReporteMensual();
            guardarReporteMensual(reporteMensual);
        }else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", 
                    "Datos inválidos y/o campos vacíos");
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
            this.proyecto = ProyectoDAO.obtenerProyectoPorEstudiante(estudiante.getIdUsuario());
            this.responsableProyecto = ResponsableDeProyectoDAO.obtenerResponsableDeProyectoPorId(proyecto.getIdResponsableDeProyecto());
            this.idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(estudiante.getIdUsuario());
            tfNumeroReporte.setText(String.valueOf(ReporteMensualDAO.obtenerSiguienteNumeroReporte(idExpediente)));
            tfNombreEstudiante.setText(estudiante.getNombre() + " " + 
                    estudiante.getApellidoPaterno() + " " + estudiante.getApellidoMaterno());
            tfMatricula.setText(estudiante.getMatricula());
            tfProyectoVinculado.setText(proyecto.getNombre());
            tfResponsableProyecto.setText(responsableProyecto.getNombre());
            tfOrganizacionVinculada.setText(OrganizacionVinculadaDAO.obtenerOrganizacionVinculadaPorId(responsableProyecto.getIdOrganizacionVinculada()).getNombre());
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfNumeroReporte).close();
        }
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String numeroHoras = tfNumeroHoras.getText().trim();
        String periodoReporte = tfPeriodoReporte.getText().trim();
        String descripcion = taDescripcion.getText().trim();
        
        if (numeroHoras.isEmpty() || numeroHoras.length() > 3) {
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
        } else if (!validarFormatoPeriodoReporte(periodoReporte)) {
            camposValidos = false;
            tfPeriodoReporte.setText("");
        }
        if (descripcion.isEmpty() || descripcion.length() > 200) {
            camposValidos = false;
        }
        return camposValidos;
    }
    
    private int obtenerNumeroMes(String nombreMes) {
        int indice = NOMBRES_MESES.indexOf(nombreMes);
        return (indice != -1) ? (indice + 1) : -1;
    }
    
    private boolean validarFormatoPeriodoReporte(String periodoReporte) {
        if (!periodoReporte.contains("-")) {
            return false;
        }
        String[] partes = periodoReporte.split("-");
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
        try {
            Periodo periodoSemestral = PeriodoDAO.obtenerPeriodoActual();
            
            if (periodoSemestral == null) {
                return false;
            }

            LocalDate fechaInicioSemestre = LocalDate.parse(periodoSemestral.getFechaInicio(), FORMATO_FECHA);
            LocalDate fechaFinSemestre = LocalDate.parse(periodoSemestral.getFechaFin(), FORMATO_FECHA);
            int semestreAnio = fechaInicioSemestre.getYear();

            LocalDate periodoReporteFechaInicio = LocalDate.of(semestreAnio, obtenerNumeroMes(mes1), 1);
            LocalDate periodoReporteFechaFin;

            if (indiceMes1 == 11 && indiceMes2 == 0) { 
                periodoReporteFechaFin = LocalDate.of(semestreAnio + 1, obtenerNumeroMes(mes2), Month.of(obtenerNumeroMes(mes2)).length(periodoReporteFechaInicio.isLeapYear()));
            } else {
                periodoReporteFechaFin = LocalDate.of(semestreAnio, obtenerNumeroMes(mes2), Month.of(obtenerNumeroMes(mes2)).length(periodoReporteFechaInicio.isLeapYear()));
            }
            
            if (periodoReporteFechaInicio.isBefore(fechaInicioSemestre)) {
                return false;
            }

            if (periodoReporteFechaFin.isAfter(fechaFinSemestre)) {
                return false;
            }

        } catch (SQLException ex) {
            return false;
        } catch (DateTimeParseException ex) {
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
        reporteMensual.setNombreArchivo(estudiante.getNombre() + 
                estudiante.getApellidoPaterno() + estudiante.getApellidoMaterno() 
                + "_ReporteMensual_" + Integer.parseInt(tfNumeroReporte.getText()) 
                + "_" + tfPeriodoReporte.getText());
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
                Utilidad.getEscenario(taDescripcion).close();
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(taDescripcion).close();
        }
    }
}