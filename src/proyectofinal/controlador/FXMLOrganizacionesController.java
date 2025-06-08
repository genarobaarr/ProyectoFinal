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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import proyectofinal.ProyectoFinal;

public class FXMLOrganizacionesController implements Initializable {

    @FXML
    private Label lbReloj;
    
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarHora();
    }

    @FXML
    private void clicBotonRegistrarResponsable(ActionEvent event) {
        irPantalla("vista/FXMLCU13_1_RegistrarResponsableProyecto.fxml", "Selección de organización vinculada");
    }

    @FXML
    private void clicBotonRegistrarOrganizacion(ActionEvent event) {
        irPantalla("vista/FXMLCU12_RegistrarOrganizacionVinculada.fxml", "Selección de organización vinculada");
    }

    @FXML
    private void clicBotonRegresar(ActionEvent event) {
        cerrarVentana();
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
