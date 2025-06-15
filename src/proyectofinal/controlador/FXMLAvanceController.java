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
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLAvanceController implements Initializable {

    @FXML
    private Button btnConsultarAvance;
    @FXML
    private Button btnSubirDocumento;
    @FXML
    private Label lbReloj;
    @FXML
    private ImageView ivConsultarAvance;
    @FXML
    private ImageView ivSubirDocumentos;
    
    private Usuario estudiante;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilidad.mostrarHora(lbReloj);
    }

    @FXML
    private void clicBotonRegresar(ActionEvent event) {
        Utilidad.getEscenario(lbReloj).close();
    }

    @FXML
    private void clicBotonConsultarAvance(ActionEvent event) {
        irPantalla("vista/FXMLCU07_1_ConsultarAvance.fxml", "Avances de estudiantes");
    }

    @FXML
    private void clicBotonSubirDocumento(ActionEvent event) {
        irPantalla("vista/FXMLCU03_ActualizarExpediente.fxml", "Actualizar expediente");
    }
    
    public void inicializarInformacion(Usuario usuario) {
        if (usuario instanceof Academico) {
            btnConsultarAvance.setDisable(false);
            btnSubirDocumento.setDisable(true);
            btnSubirDocumento.setText("No disponible");
            ivSubirDocumentos.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
        } else if (usuario instanceof Estudiante) {
            btnConsultarAvance.setDisable(true);
            btnConsultarAvance.setText("No disponible");
            ivConsultarAvance.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));   
            btnSubirDocumento.setDisable(false);
            this.estudiante = usuario;
        }
    }
    
    public void irPantalla(String fxmlPath, String titulo) {
        try {
            Stage escenarioNuevo = new Stage();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
            Parent vista = cargador.load();
            
            if (fxmlPath.equals("vista/FXMLCU03_ActualizarExpediente.fxml")){
                FXMLCU03_ActualizarExpedienteController controlador = cargador.getController();
                controlador.inicializarInformacion(estudiante);
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