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
import javafx.scene.control.TextField;

public class FXMLCU12_RegistrarOrganizacionVinculadaController implements Initializable {

    @FXML
    private TextField tfNombreOrganizacionVinculada;
    @FXML
    private TextField tfDireccion;
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
