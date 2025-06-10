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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FXMLCU05_HabilitarEntregasController implements Initializable {

    @FXML
    private TextArea taDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextField tfTituloAsignacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void tfTituloAsignacionPresionaEnter(KeyEvent event) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }

    @FXML
    private void clicBotonHabilitar(ActionEvent event) {
    }
    
}
