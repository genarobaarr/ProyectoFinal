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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;

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
    private TextArea taDescripcion;
    @FXML
    private TextField tfPeriodoReporte;
    @FXML
    private ComboBox<Proyecto> cbProyectos;
    @FXML
    private ComboBox<ResponsableDeProyecto> cbResponsables;
    @FXML
    private ComboBox<OrganizacionVinculada> cbOrganizaciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void tfNumeroReportePresionaEnter(KeyEvent event) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
    }

    @FXML
    private void tfNumeroHorasPresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfNombreEstudiantePresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfMatriculaPresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfPeriodoReportePresionaEnter(KeyEvent event) {
    }
    
}
