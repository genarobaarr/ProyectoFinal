/*
 * Genaro Alejandro Barradas Sánchez
 * 07-06-2025
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU11_1_ActualizarProyectoController implements Initializable {

    @FXML
    private TextField tfNombreProyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void tfNombreProyectoPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            clicBotonBuscar(new ActionEvent());
            event.consume();
        }
    } 

    @FXML
    private void clicBotonBuscar(ActionEvent event) {
        if (validarCampos()) {
            try {
                irPantallaSiguiente(tfNombreProyecto.getText(), "/proyectofinal/vista/FXMLCU11_2_ActualizarProyecto.fxml", "Seleccionar proyecto");
            } catch (IOException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
                Utilidad.getEscenario(tfNombreProyecto).close();
            }
        }
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String busqueda = tfNombreProyecto.getText().trim();
        
        if (busqueda.isEmpty()) {
            camposValidos = false;
        }
        return camposValidos;
    }
    
    private void irPantallaSiguiente(String nombreProyecto, String fxmlPath, String titulo) throws IOException{
        Stage escenarioBase = (Stage) tfNombreProyecto.getScene().getWindow();
        FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
        Parent vista = cargador.load();

        FXMLCU11_2_ActualizarProyectoController controlador = cargador.getController();
        controlador.inicializarInformacion(nombreProyecto);

        Scene escenaPrincipal = new Scene(vista);
        escenarioBase.setScene(escenaPrincipal);
        escenarioBase.setTitle(titulo);
        escenarioBase.show();
    }
}