/*
 * Genaro Alejandro Barradas Sánchez
 * 07-06-2025
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.pojo.Academico;
import proyectofinal.modelo.pojo.AcademicoEvaluador;
import proyectofinal.modelo.pojo.Coordinador;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLPrincipalController implements Initializable {
    
    @FXML
    private Label lbNombre;
    @FXML
    private Button btnProyectos;
    @FXML
    private Button btnOrganizaciones;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnAvance;
    @FXML
    private Button btnEvaluaciones;
    @FXML
    private Label lbReloj;
    
    private Usuario usuarioSesion;
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarHora();
    }
    
    @FXML
    private void clicBotonCerrarSesion(ActionEvent event) {
        try {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Cerraste sesión", 
                        usuarioSesion.toString() + " cerraste sesión exitosamente");
            Stage escenarioBase = (Stage) lbNombre.getScene().getWindow();
            escenarioBase.setScene(new Scene(FXMLLoader.load(ProyectoFinal.class.getResource("vista/FXMLInicioSesion.fxml"))));
            escenarioBase.setTitle("Inicio de sesión");
            escenarioBase.showAndWait();
            usuarioSesion = null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clicBotonProyectos(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLProyectos.fxml", "Proyectos");
    }

    @FXML
    private void clicBotonOrganizaciones(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLOrganizaciones.fxml", "Organizaciones Vinculadas");
    }

    @FXML
    private void clicBotonReportes(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLReportes.fxml", "Reportes");
    }

    @FXML
    private void clicBotonAvance(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLAvance.fxml", "Avance");
    }

    @FXML
    private void clicBotonEvaluaciones(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLEvaluaciones.fxml", "Evaluaciones");
    }
    
    public void inicializarInformacion(Usuario usuario) {
        if (usuario != null) {
            this.usuarioSesion = usuario;
            lbNombre.setText("Bienvenido(a), " + usuarioSesion.toString());
            if (usuarioSesion instanceof Estudiante) {
                btnProyectos.setDisable(true);
                btnOrganizaciones.setDisable(true);
                btnReportes.setDisable(false);
                btnAvance.setDisable(false);
                btnEvaluaciones.setDisable(false);
            } else if (usuarioSesion instanceof AcademicoEvaluador) {
                btnProyectos.setDisable(true);
                btnOrganizaciones.setDisable(true);
                btnReportes.setDisable(true);
                btnAvance.setDisable(true);
                btnEvaluaciones.setDisable(false);
            } else if (usuarioSesion instanceof Academico) {
                btnProyectos.setDisable(true);
                btnOrganizaciones.setDisable(true);
                btnReportes.setDisable(false);
                btnAvance.setDisable(false);
                btnEvaluaciones.setDisable(true);
            } else if (usuarioSesion instanceof Coordinador) {
                btnProyectos.setDisable(false);
                btnOrganizaciones.setDisable(false);
                btnReportes.setDisable(false);
                btnAvance.setDisable(true);
                btnEvaluaciones.setDisable(true);
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la pantalla principal");
        }
    }
    
    public void irPantallaSecundaria(Usuario usuario, String fxmlPath, String titulo) {
        try {
            Stage escenarioNuevo = new Stage();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
            Parent vista = cargador.load();
            
            switch (titulo) {
                case "Reportes":
                    FXMLReportesController controladorReportes = cargador.getController();
                    controladorReportes.inicializarInformacion(usuarioSesion);
                    break;
                case "Avance":
                    FXMLAvanceController controladorAvance = cargador.getController();
                    controladorAvance.inicializarInformacion(usuarioSesion);
                    break;
                case "Evaluaciones":
                    FXMLEvaluacionesController controladorEvaluaciones = cargador.getController();
                    controladorEvaluaciones.inicializarInformacion(usuarioSesion);
                    break;
                default:
                    break;
            }
            
            Scene escena = new Scene(vista);
            escenarioNuevo.setScene(escena);
            escenarioNuevo.setTitle(titulo);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void mostrarHora() {
        Timeline reloj = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
                LocalDateTime ahora = LocalDateTime.now();
                lbReloj.setText(ahora.format(formato));
            }),
            new KeyFrame(Duration.minutes(1))
        );
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }
}
