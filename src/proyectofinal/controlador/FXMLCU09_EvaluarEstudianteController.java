/*
 * Alejandro Martínez Ramírez
 * 02-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class FXMLCU09_EvaluarEstudianteController implements Initializable {

    @FXML
    private RadioButton rbDominioSatisfactorio;
    @FXML
    private ToggleGroup tgDominioTema;
    @FXML
    private RadioButton rbDominioExcelente;
    @FXML
    private RadioButton rbDominioPuedeMejorar;
    @FXML
    private RadioButton rbDominioNoCumple;
    @FXML
    private RadioButton rbFormalidadExcelente;
    @FXML
    private ToggleGroup tgFormalidadPresentacion;
    @FXML
    private RadioButton rbFormalidadSatisfactorio;
    @FXML
    private RadioButton rbFormalidadPuedeMejorar;
    @FXML
    private RadioButton rbFormalidadNoCumple;
    @FXML
    private RadioButton rbOrganizacionExcelente;
    @FXML
    private ToggleGroup tgOrganizacionEquipo;
    @FXML
    private RadioButton rbOrganizacionSatisfactorio;
    @FXML
    private RadioButton rbOrganizacionPuedeMejorar;
    @FXML
    private RadioButton rbOrganizacionNoCumple;
    @FXML
    private TextArea taComentarios;

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
