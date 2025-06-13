/*
 * Genaro Alejandro Barradas Sánchez
 * 08-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.pojo.AcademicoEvaluador;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLEvaluacionesController implements Initializable {

    @FXML
    private Label lbReloj;
    @FXML
    private Button btnEvaluarOrganizacion;
    @FXML
    private Button btnRegistrarEvaluacion;
    @FXML
    private ImageView ivEvaluarOrganizacion;
    @FXML
    private ImageView ivRegistrarEvaluacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilidad.mostrarHora(lbReloj);
    }

    @FXML
    private void clicBotonEvaluarOrganizacion(ActionEvent event) {
        irPantalla("vista/FXMLCU16_EvaluarOrganizacionVinculada.fxml", "Evaluación de organización");
    }

    @FXML
    private void clicBotonRegistrarEvaluacion(ActionEvent event) {
        irPantalla("vista/FXMLCU09_1_ProyectosRegistradosActivos.fxml", "Proyectos registrados activos");
    }

    @FXML
    private void clicBotonRegresar(ActionEvent event) {
        Utilidad.getEscenario(lbReloj).close();
        
    }
    
    public void inicializarInformacion(Usuario usuario) {
        if (usuario instanceof AcademicoEvaluador) {
            btnEvaluarOrganizacion.setDisable(true);
            btnEvaluarOrganizacion.setText("No disponible");
            ivEvaluarOrganizacion.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnRegistrarEvaluacion.setDisable(false);
        } else if (usuario instanceof Estudiante) {
            btnEvaluarOrganizacion.setDisable(false);
            
            btnRegistrarEvaluacion.setDisable(true);
            btnRegistrarEvaluacion.setText("No disponible");
            ivRegistrarEvaluacion.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
        }
    }
    
    public void irPantalla(String fxmlPath, String titulo) {
        try {
            Stage escenarioNuevo = new Stage();
            Parent vista = FXMLLoader.load(ProyectoFinal.class.getResource(fxmlPath));
            Scene escena = new Scene(vista);
            escenarioNuevo.setScene(escena);
            escenarioNuevo.setTitle(titulo);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la pantalla siguiente");
        }
    }
}