/*
 * Alejandro Martínez Ramírez
 * 30-05-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class FXMLCU01_AsignarProyectoController implements Initializable {

    @FXML
    private TableView<?> tvEstudiantes;
    @FXML
    private TableView<?> tvProyectos;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAceptar(ActionEvent event) {
    }

    @FXML
    private void btnSalir(ActionEvent event) {
    }
    
}
