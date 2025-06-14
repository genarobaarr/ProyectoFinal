/*
 * Genaro Alejandro Barradas Sánchez
 * 30-05-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.dao.InicioDeSesionDAO;
import proyectofinal.modelo.pojo.*;
import proyectofinal.utilidades.Utilidad;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void tfUsuarioPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfPassword.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfPasswordPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnClicVerificarSesion(new ActionEvent());
            event.consume();
        }
    }

    @FXML
    private void btnClicVerificarSesion(ActionEvent event) {
        String username = tfUsuario.getText();
        String password = tfPassword.getText();

        if (validarCampos(username, password)) {
            validarCredenciales(username, password);
        }
    }

    private boolean validarCampos(String username, String password) {
        lbErrorPassword.setText("");
        lbErrorUsuario.setText("");
        boolean camposValidos = true;

        if (username.isEmpty()) {
            lbErrorUsuario.setText("Usuario requerido");
            camposValidos = false;
        }

        if (password.isEmpty()) {
            lbErrorPassword.setText("Contraseña requerida");
            camposValidos = false;
        }

        return camposValidos;
    }

    private void validarCredenciales(String username, String password) {
        try {
            Usuario usuarioSesion = InicioDeSesionDAO.verificarCredenciales(username, password);

            if (usuarioSesion != null) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION,
                        "Inicio de sesión exitoso", usuarioSesion.toString() +
                        ", bienvenido(a) al sistema.");

                if (usuarioSesion instanceof Estudiante) {
                    irPantallaPrincipal(usuarioSesion, "/proyectofinal/vista/FXMLPrincipal.fxml", "Home Estudiante");
                } else if (usuarioSesion instanceof AcademicoEvaluador) {
                    irPantallaPrincipal(usuarioSesion, "/proyectofinal/vista/FXMLPrincipal.fxml", "Home Académico Evaluador");
                } else if (usuarioSesion instanceof Academico) {
                    irPantallaPrincipal(usuarioSesion, "/proyectofinal/vista/FXMLPrincipal.fxml", "Home Académico");
                } else if (usuarioSesion instanceof Coordinador) {
                    irPantallaPrincipal(usuarioSesion, "/proyectofinal/vista/FXMLPrincipal.fxml", "Home Coordinador");
                }
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING,
                        "Credenciales incorrectas", "Usuario y/o contraseña incorrectos, por favor verifica tu información.");
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en base de datos", ex.getMessage());
        } catch (IOException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar pantalla principal", "No se pudo cargar la pantalla: " + ex.getMessage());
        }
    }

    private void irPantallaPrincipal(Usuario usuarioSesion, String fxmlPath, String titulo) throws IOException {
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
        Parent vista = cargador.load();

        FXMLPrincipalController controlador = cargador.getController();
        controlador.inicializarInformacion(usuarioSesion);

        Scene escenaPrincipal = new Scene(vista);
        escenarioBase.setScene(escenaPrincipal);
        escenarioBase.setTitle(titulo);
        escenarioBase.show();
    }
}