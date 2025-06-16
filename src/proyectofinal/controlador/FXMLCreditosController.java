/*
 * Genaro Alejandro Barradas Sánchez
 * 15-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import proyectofinal.utilidades.Utilidad;

public class FXMLCreditosController implements Initializable {

    @FXML
    private ImageView ivGenaroABS;
    @FXML
    private ImageView ivAlejandroMR;
    @FXML
    private ImageView ivOmarMG;
    @FXML
    private Label lbCreditos;
    
    private double rotacion1 = 0;
    private double rotacion2 = 45;
    private double rotacion3 = 90;
    private Timeline timelineRotacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonRegresar(ActionEvent event) {
        Utilidad.getEscenario(lbCreditos).close();
    }
    
    public void inicializarInformacion() {
        iniciarRotacionImagenes();
    }
    
    private void iniciarRotacionImagenes() {
        timelineRotacion = new Timeline(
            new KeyFrame(Duration.ZERO, event -> {
            }), 
            new KeyFrame(Duration.seconds(1), event -> {
                rotacion1 = (rotacion1 + 45) % 360;
                rotacion2 = (rotacion2 + 45) % 360;
                rotacion3 = (rotacion3 + 45) % 360;
                ivGenaroABS.setRotate(rotacion1);
                ivAlejandroMR.setRotate(rotacion2);
                ivOmarMG.setRotate(rotacion3);
            })
        );
        timelineRotacion.setCycleCount(Timeline.INDEFINITE);
        timelineRotacion.play();
    }
}