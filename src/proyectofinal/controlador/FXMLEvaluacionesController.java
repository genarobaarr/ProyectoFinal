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
    
    private Usuario usuario;

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
            this.usuario = usuario;
            btnEvaluarOrganizacion.setDisable(true);
            btnEvaluarOrganizacion.setText("No disponible");
            ivEvaluarOrganizacion.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
            
            btnRegistrarEvaluacion.setDisable(false);
        } else if (usuario instanceof Estudiante) {
            this.usuario = usuario;
            btnEvaluarOrganizacion.setDisable(false);
            
            btnRegistrarEvaluacion.setDisable(true);
            btnRegistrarEvaluacion.setText("No disponible");
            ivRegistrarEvaluacion.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
        }
    }
    
    public void irPantalla(String fxmlPath, String titulo) {
        try {
            Stage escenarioNuevo = new Stage();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
            Parent vista = cargador.load();
            
            switch (fxmlPath) {
                case "vista/FXMLCU09_1_ProyectosRegistradosActivos.fxml": {
                    FXMLCU09_1_ProyectosRegistradosActivosController controladorProyectosRegistrados = cargador.getController();
                    controladorProyectosRegistrados.inicializarInformacion(usuario);
                    break;
                }
                case "vista/FXMLCU16_EvaluarOrganizacionVinculada.fxml": {
                    FXMLCU16_EvaluarOrganizacionVinculadaController controladorEvaluarOrganizacion = cargador.getController();
                    controladorEvaluarOrganizacion.inicializarInformacion(usuario);
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