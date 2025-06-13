/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.utilidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Utilidad {
    
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
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
    
    public static void mostrarHora(Label lbReloj) {
        Timeline reloj = new Timeline(
            new KeyFrame(Duration.ZERO, e -> {
                LocalDateTime ahora = LocalDateTime.now();
                lbReloj.setText(ahora.format(FORMATO_HORA));
            }),
            new KeyFrame(Duration.minutes(1))
        );
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }
}