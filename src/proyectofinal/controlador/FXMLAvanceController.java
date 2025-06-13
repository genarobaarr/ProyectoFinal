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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.pojo.Academico;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Usuario;

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
    
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarHora();
    }

    @FXML
    private void clicBotonRegresar(ActionEvent event) {
        cerrarVentana();
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
            ex.printStackTrace();
        }
    }
    
    private void cerrarVentana(){
        ((Stage) lbReloj.getScene().getWindow()).close();
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