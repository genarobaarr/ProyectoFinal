/*
 * Genaro Alejandro Barradas Sánchez
 * 08-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.pojo.Academico;
import proyectofinal.modelo.pojo.Coordinador;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLReportesController implements Initializable {

    @FXML
    private Label lbReloj;
    @FXML
    private Button btnValidarReportes;
    @FXML
    private Button btnNuevoReporte;
    @FXML
    private Button btnHabilitarEntrega;
    @FXML
    private ImageView ivValidarReportes;
    @FXML
    private ImageView ivNuevoReporte;
    @FXML
    private ImageView ivHabilitarEntrega;
    
    private Estudiante estudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilidad.mostrarHora(lbReloj);
    }

    @FXML
    private void clicBotonValidarReportes(ActionEvent event) {
        irPantalla("vista/FXMLCU06_1_ValidarReportes.fxml", "Reportes entregados");
    }

    @FXML
    private void clicBotonNuevoReporte(ActionEvent event) {
        irPantalla("vista/FXMLCU04_1_EntregaReportes.fxml", "Reportes mensuales");
    }

    @FXML
    private void clicBotonHabilitarEntrega(ActionEvent event) {
        irPantalla("vista/FXMLCU05_HabilitarEntregas.fxml", "Habilitar entre de reportes");
    }

    @FXML
    private void clicBotonRegresar(ActionEvent event) {
        Utilidad.getEscenario(lbReloj).close();
    }
    
    public void inicializarInformacion(Usuario usuario) {
        if (usuario instanceof Academico) {
            btnValidarReportes.setDisable(false);
            
            btnNuevoReporte.setDisable(true);
            btnNuevoReporte.setText("No disponible");
            ivNuevoReporte.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnHabilitarEntrega.setDisable(true);
            btnHabilitarEntrega.setText("No disponible");
            ivHabilitarEntrega.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
        } else if (usuario instanceof Estudiante) {
            this.estudiante = (Estudiante)usuario;
            btnValidarReportes.setDisable(true);
            btnValidarReportes.setText("No disponible");
            ivValidarReportes.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnNuevoReporte.setDisable(false);
            
            btnHabilitarEntrega.setDisable(true);
            btnHabilitarEntrega.setText("No disponible");
            ivHabilitarEntrega.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
        } else if (usuario instanceof Coordinador) {
            btnValidarReportes.setDisable(true);
            btnValidarReportes.setText("No disponible");
            ivValidarReportes.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnNuevoReporte.setDisable(true);
            btnNuevoReporte.setText("No disponible");
            ivNuevoReporte.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnHabilitarEntrega.setDisable(false);
        }
    }
    
    public void irPantalla(String fxmlPath, String titulo) {
        try {
            Stage escenarioNuevo = new Stage();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
            Parent vista = cargador.load();
            
            switch (fxmlPath) {
                case "vista/FXMLCU04_1_EntregaReportes.fxml": {
                    FXMLCU04_1_EntregaReportesController controladorEntregaReportes = cargador.getController();
                    controladorEntregaReportes.inicializarInformacion(estudiante);
                    break;
                }
                case "vista/FXMLCU06_1_ValidarReportes.fxml": {
                    FXMLCU06_1_ValidarReportesController controladorValidarReportes = cargador.getController();
                    controladorValidarReportes.inicializarInformacion();
                    break;
                }
                case "vista/FXMLCU05_HabilitarEntregas.fxml": {
                    FXMLCU05_HabilitarEntregasController controladorHabilitarEntregas = cargador.getController();
                    controladorHabilitarEntregas.incializarInformacion();
                    break;
                }
            }            
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