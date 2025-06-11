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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;

public class FXMLCU06_2_ValidarReportesController implements Initializable {

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
    private ComboBox<OrganizacionVinculada> cbOrganizaciones;
    @FXML
    private ComboBox<ResponsableDeProyecto> cbResponsables;
    @FXML
    private ComboBox<Proyecto> cbProyectos;
    @FXML
    private TextField tfPeriodoReporte;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBotonValidarReporte(ActionEvent event) {

    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }
    
}
