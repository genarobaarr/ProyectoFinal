/*
 * Omar Morales García
 * 10-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.sql.Date; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import proyectofinal.modelo.dao.EvaluacionExposicionDAO;
import proyectofinal.modelo.pojo.EvaluacionExposicion;
import proyectofinal.modelo.pojo.EvaluacionExposicionCriterio;
import proyectofinal.utilidades.Utilidad; 

public class FXMLCU09_2_EvaluarEstudianteController implements Initializable {

    @FXML
    private ToggleGroup tgDominioTema;
    @FXML
    private ToggleGroup tgFormalidadPresentacion;
    @FXML
    private ToggleGroup tgOrganizacionEquipo;
    @FXML
    private TextArea taComentarios;

    private int idExpediente;
    private int idAcademico;
    private static final double EXCELENTE = 1.0;
    private static final double SATISFACTORIO = 0.85;
    private static final double PUEDE_MEJORAR = 0.7;
    private static final double NO_CUMPLE = 0.50;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarInformacion(int idExpediente, int idAcademico) {
        this.idExpediente = idExpediente;
        this.idAcademico = idAcademico; 
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
        if (tgDominioTema.getSelectedToggle() == null ||
            tgFormalidadPresentacion.getSelectedToggle() == null ||
            tgOrganizacionEquipo.getSelectedToggle() == null) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos Incompletos", 
                    "Por favor, seleccione una calificación para todas las categorías de la rúbrica.");
            return;
        }

        List<EvaluacionExposicionCriterio> criterios = new ArrayList<>();
        double puntajeDominioTema = getPuntajeYCriterio(tgDominioTema, "Dominio del tema", criterios);
        double puntajeFormalidad = getPuntajeYCriterio(tgFormalidadPresentacion, "Formalidad de la presentación", criterios);
        double puntajeOrganizacion = getPuntajeYCriterio(tgOrganizacionEquipo, "Organización del equipo", criterios);

        double puntajeTotal = (puntajeDominioTema + puntajeFormalidad + puntajeOrganizacion);

        String comentarios = taComentarios.getText().trim();

        EvaluacionExposicion evaluacionPrincipal = new EvaluacionExposicion();
        evaluacionPrincipal.setFechaEvaluacion(Date.valueOf(LocalDate.now())); 
        evaluacionPrincipal.setComentarios(comentarios);
        evaluacionPrincipal.setPuntajeFinal(puntajeTotal);
        evaluacionPrincipal.setIdExpediente(this.idExpediente);
        evaluacionPrincipal.setIdAcademicoEvaluador(this.idAcademico); 

        try {
            int idGenerado = EvaluacionExposicionDAO.guardarEvaluacionExposicion(evaluacionPrincipal, criterios);
            if (idGenerado != -1) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", "Evaluación de exposición registrada con éxito.");
                Utilidad.getEscenario(taComentarios).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al registrar", "No se pudo registrar la evaluación de exposición. Inténtalo más tarde.");
            }
        } catch (SQLException e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Error en la base de datos", "Error al guardar la evaluación de exposición. Detalles: " + e.getMessage());
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Utilidad.getEscenario(taComentarios).close();
    }

    private double getPuntajeYCriterio(ToggleGroup group, String nombreCriterio, List<EvaluacionExposicionCriterio> criteriosLista) {
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
        if (selectedRadioButton != null) {
            String id = selectedRadioButton.getId(); 
            String nivel = "";
            double valor = 0.0;

            if (id.contains("Excelente")) {
                nivel = "Excelente";
                valor = EXCELENTE;
            } else if (id.contains("Satisfactorio")) {
                nivel = "Satisfactorio";
                valor = SATISFACTORIO;
            } else if (id.contains("PuedeMejorar")) {
                nivel = "Puede mejorar";
                valor = PUEDE_MEJORAR;
            } else if (id.contains("NoCumple")) {
                nivel = "No cumple con lo requerido";
                valor = NO_CUMPLE;
            }
            
            EvaluacionExposicionCriterio criterio = new EvaluacionExposicionCriterio();
            criterio.setCriterio(nombreCriterio);
            criterio.setNivel(nivel);
            criterio.setValor(valor);
            criteriosLista.add(criterio);

            return valor;
        }
        return 0.0; 
    }
}