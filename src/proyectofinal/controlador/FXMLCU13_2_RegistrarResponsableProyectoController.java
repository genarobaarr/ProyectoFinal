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
import javafx.scene.control.TextField;

public class FXMLCU13_2_RegistrarResponsableProyectoController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfPuesto;
    @FXML
    private TextField tfDepartamento;
    @FXML
    private TextField tfCorreoElectronico;
    @FXML
    private TextField tfTelefono;

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
