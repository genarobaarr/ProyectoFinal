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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLCU04_2_EntregaReportesController implements Initializable {

    @FXML
    private TextField tfNumeroReporte;
    @FXML
    private TextField tfNumeroHoras;
    @FXML
    private TextField tfNombreEstudiante;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfPeriodo;
    @FXML
    private TextField tfProyectoVinculado;
    @FXML
    private TextField tfOrganizacionVinculada;
    @FXML
    private TextField tfResponsabkeProyecto;
    @FXML
    private TextArea taDescripcion;

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
