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
    
    public static boolean mostrarAlertaConfirmacion(String titulo, String mensaje) {
        Alert alertaConfirmacion = new Alert(AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setHeaderText(mensaje);
        
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        alertaConfirmacion.getButtonTypes().setAll(botonSi, botonNo);
        
        return alertaConfirmacion.showAndWait().get() == botonSi;
    }
    
    public static Stage getEscenario(Control componente) {
        return (Stage) componente.getScene().getWindow();
    }
}