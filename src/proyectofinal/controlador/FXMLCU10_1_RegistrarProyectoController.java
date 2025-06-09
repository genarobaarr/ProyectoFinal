/*
 * Genaro Alejandro Barradas Sánchez
 * 07-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU10_1_RegistrarProyectoController implements Initializable {

    @FXML
    private TableView<OrganizacionVinculada> tvOrganizacionesVinculadas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnCancelar(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText("¿Deseas salir?");
        
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        confirmacion.getButtonTypes().setAll(botonSi, botonNo);
        
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == botonSi) {
            cerrarVentana();
        }
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        
    }
    
    private void cerrarVentana(){
        ((Stage) tvOrganizacionesVinculadas.getScene().getWindow()).close();
    }
}
