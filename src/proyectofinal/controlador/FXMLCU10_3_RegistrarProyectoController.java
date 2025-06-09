/*
 * Genaro Alejandro Barradas Sánchez
 * 07-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLCU10_3_RegistrarProyectoController implements Initializable {

    @FXML
    private Label lblOrganizacionVinculada;
    @FXML
    private TextField tfNombreProyecto;
    @FXML
    private TextArea taBreveDescripcion;
    @FXML
    private TextArea taObjetivosGenerales;
    @FXML
    private Label lblResponsableProyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnRegistrar(ActionEvent event) {
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
    }
    
}
