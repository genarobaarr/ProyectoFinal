/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class Utilidad {
    
    public static void mostrarAlertaSimple(AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    
    public static boolean mostrarAlertaConfirmacion(String titulo, String contenido) {
        Alert alertaConfirmacion = new Alert(AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText(contenido);
        alertaConfirmacion.showAndWait();   
        
        return alertaConfirmacion.showAndWait().get() == ButtonType.OK;
    }
    
    public static Stage getEscenario(Control componente) {
        return (Stage) componente.getScene().getWindow();
    }
}