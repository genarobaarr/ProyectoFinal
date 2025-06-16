/*
 * Genaro Alejandro Barradas Sánchez
 * 07-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.pojo.Academico;
import proyectofinal.modelo.pojo.AcademicoEvaluador;
import proyectofinal.modelo.pojo.Coordinador;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLPrincipalController implements Initializable {
    
    @FXML
    private Label lbNombre;
    @FXML
    private Button btnProyectos;
    @FXML
    private Button btnOrganizaciones;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnAvance;
    @FXML
    private Button btnEvaluaciones;
    @FXML
    private Label lbReloj;
    @FXML
    private ImageView ivProyectos;
    @FXML
    private ImageView ivOrganizaciones;
    @FXML
    private ImageView ivReportes;
    @FXML
    private ImageView ivAvance;
    @FXML
    private ImageView ivEvaluaciones;
    @FXML
    private Button btnConfiguracion;
    
    private Usuario usuarioSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utilidad.mostrarHora(lbReloj);
    }
    
    @FXML
    private void clicBotonCerrarSesion(ActionEvent event) {
        try {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Cerraste sesión", 
                        usuarioSesion.toString() + " cerraste sesión exitosamente");
            Stage escenarioBase = (Stage) lbNombre.getScene().getWindow();
            escenarioBase.setScene(new Scene(FXMLLoader.load(ProyectoFinal.class.getResource("vista/FXMLInicioSesion.fxml"))));
            escenarioBase.setTitle("Inicio de sesión");
            escenarioBase.show();
            usuarioSesion = null;
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al cerrar sesión");
            Utilidad.getEscenario(lbNombre).close();
        }
    }
    
    @FXML
    private void clicBotonProyectos(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLProyectos.fxml", "Proyectos");
    }

    @FXML
    private void clicBotonOrganizaciones(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLOrganizaciones.fxml", "Organizaciones Vinculadas");
    }

    @FXML
    private void clicBotonReportes(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLReportes.fxml", "Reportes");
    }

    @FXML
    private void clicBotonAvance(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLAvance.fxml", "Avance");
    }

    @FXML
    private void clicBotonEvaluaciones(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLEvaluaciones.fxml", "Evaluaciones");
    }

    @FXML
    private void clicBotonCreditos(MouseEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLCreditos.fxml", "Créditos");
    }

    @FXML
    private void clicBotonConfiguracion(ActionEvent event) {
        irPantallaSecundaria(usuarioSesion, "vista/FXMLAgregarUsuarioNuevo.fxml", "Agregar usuario nuevo");
    }
    
    public void inicializarInformacion(Usuario usuario) {
        if (usuario != null) {
            this.usuarioSesion = usuario;
            lbNombre.setText("Bienvenido(a), " + usuarioSesion.toString());
            
            if (usuarioSesion instanceof Estudiante) {
                btnProyectos.setDisable(true);
                btnProyectos.setText("No disponible");
                ivProyectos.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnOrganizaciones.setDisable(true);
                btnOrganizaciones.setText("No disponible");
                ivOrganizaciones.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnReportes.setDisable(false);
                btnAvance.setDisable(false);
                btnEvaluaciones.setDisable(false);
                
                btnConfiguracion.setDisable(true);
            } else if (usuarioSesion instanceof AcademicoEvaluador) {
                btnProyectos.setDisable(true);
                btnProyectos.setText("No disponible");
                ivProyectos.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnOrganizaciones.setDisable(true);
                btnOrganizaciones.setText("No disponible");
                ivOrganizaciones.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnReportes.setDisable(true);
                btnReportes.setText("No disponible");
                ivReportes.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnAvance.setDisable(true);
                btnAvance.setText("No disponible");
                ivAvance.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnEvaluaciones.setDisable(false);
                
                btnConfiguracion.setDisable(true);
            } else if (usuarioSesion instanceof Academico) {
                btnProyectos.setDisable(true);
                btnProyectos.setText("No disponible");
                ivProyectos.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnOrganizaciones.setDisable(true);
                btnOrganizaciones.setText("No disponible");
                ivOrganizaciones.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnReportes.setDisable(false);
                btnAvance.setDisable(false);
                
                btnEvaluaciones.setDisable(true);
                btnEvaluaciones.setText("No disponible");
                ivEvaluaciones.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnConfiguracion.setDisable(true);
            } else if (usuarioSesion instanceof Coordinador) {
                btnProyectos.setDisable(false);
                btnOrganizaciones.setDisable(false);
                btnReportes.setDisable(false);
                
                btnAvance.setDisable(true);
                btnAvance.setText("No disponible");
                ivAvance.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnEvaluaciones.setDisable(true);
                btnEvaluaciones.setText("No disponible");
                ivEvaluaciones.setImage(new Image("/proyectofinal/recursos/iconoTriste.png"));
                
                btnConfiguracion.setDisable(false);
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la pantalla");
            Utilidad.getEscenario(lbNombre).close();
        }
    }
    
    public void irPantallaSecundaria(Usuario usuario, String fxmlPath, String titulo) {
        try {
            Stage escenarioNuevo = new Stage();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
            Parent vista = cargador.load();
            
            switch (titulo) {
                case "Proyectos": {
                    FXMLProyectosController controladorProyectos = cargador.getController();
                    controladorProyectos.inicializarInformacion(usuarioSesion);
                    break;
                }
                case "Reportes": {
                    FXMLReportesController controladorReportes = cargador.getController();
                    controladorReportes.inicializarInformacion(usuarioSesion);
                    break;
                }
                case "Avance": {
                    FXMLAvanceController controladorAvance = cargador.getController();
                    controladorAvance.inicializarInformacion(usuarioSesion);
                    break;
                }
                case "Evaluaciones": {
                    FXMLEvaluacionesController controladorEvaluaciones = cargador.getController();
                    controladorEvaluaciones.inicializarInformacion(usuarioSesion);
                    break;
                }
                case "Créditos": {
                    FXMLCreditosController controladorCreditos = cargador.getController();
                    controladorCreditos.inicializarInformacion();
                    break;
                }
                case "Agregar usuario nuevo": {
                    FXMLAgregarUsuarioNuevoController controladorCreditos = cargador.getController();
                    controladorCreditos.inicializarInformacion();
                    break;
                }
                default:
                    break;
            }
            
            Scene escena = new Scene(vista);
            escenarioNuevo.setScene(escena);
            escenarioNuevo.setTitle(titulo);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
            Utilidad.getEscenario(lbNombre).close();
        }
    }
}
