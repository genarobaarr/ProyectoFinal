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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class FXMLCU04_1_EntregaReportesController implements Initializable {

    @FXML
    private TableView<?> tvReportesMensuales;
    @FXML
    private TableColumn colNombres;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBotonNuevoReporte(ActionEvent event) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }
    
}
