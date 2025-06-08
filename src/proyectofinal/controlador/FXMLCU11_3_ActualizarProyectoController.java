/*
 * Alejandro Martínez Ramírez
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

public class FXMLCU11_3_ActualizarProyectoController implements Initializable {

    @FXML
    private TextField tfNombreProyecto;
    @FXML
    private TextArea taBreveDescripcion;
    @FXML
    private Label lblOrganizacionVinculada;
    @FXML
    private TextArea taObjetivosGenerales;
    @FXML
    private Label lblResponsableProyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void btnCancelar(ActionEvent event) {
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
    }
    
}
