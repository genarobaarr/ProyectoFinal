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
import proyectofinal.modelo.pojo.Coordinador;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLProyectosController implements Initializable {

    @FXML
    private Label lbReloj;
    
    private Usuario coordinador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilidad.mostrarHora(lbReloj);
    }

    @FXML
    private void clicBotonRegresar(ActionEvent event) {
        Utilidad.getEscenario(lbReloj).close();
    }

    @FXML
    private void clicBotonRegistrarProyecto(ActionEvent event) {
        irPantalla("vista/FXMLCU10_1_RegistrarProyecto.fxml", "Selección de organización vinculada");
    }

    @FXML
    private void clicBotonModificarProyecto(ActionEvent event) {
        irPantalla("vista/FXMLCU11_1_ActualizarProyecto.fxml", "Buscar proyecto");
    }

    @FXML
    private void clicBotonAsignarProyecto(ActionEvent event) {
        irPantalla("vista/FXMLCU01_AsignarProyecto.fxml", "Asignación de proyecto");
    }
    
    public void inicializarInformacion (Usuario usuario) {
        this.coordinador = (Coordinador)usuario;
    }
    
    public void irPantalla(String fxmlPath, String titulo) {
        try {
            Stage escenarioNuevo = new Stage();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
            Parent vista = cargador.load();
            
            if  (fxmlPath.equals("vista/FXMLCU10_1_RegistrarProyecto.fxml")) {
                FXMLCU10_1_RegistrarProyectoController controlador = cargador.getController();
                controlador.inicializarInformacion(coordinador);
            }
            
            Scene escena = new Scene(vista);
            escenarioNuevo.setScene(escena);
            escenarioNuevo.setTitle(titulo);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
            Utilidad.getEscenario(lbReloj).close();
        }
    }
}
