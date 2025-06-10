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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import proyectofinal.modelo.pojo.ReporteMensual;

public class FXMLCU06_1_ValidarReportesController implements Initializable {

    @FXML
    private TableView<ReporteMensual> tvReportesMensuales;
    @FXML
    private TableColumn colNombres;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBotonValidar(ActionEvent event) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }
    
}
