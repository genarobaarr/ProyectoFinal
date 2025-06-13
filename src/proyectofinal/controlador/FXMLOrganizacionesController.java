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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.utilidades.Utilidad;

public class FXMLOrganizacionesController implements Initializable {

    @FXML
    private Label lbReloj;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilidad.mostrarHora(lbReloj);
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
        Utilidad.getEscenario(lbReloj).close();
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
