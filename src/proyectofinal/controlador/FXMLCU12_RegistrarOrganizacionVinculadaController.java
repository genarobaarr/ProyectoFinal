/*
 * Genaro Alejandro Barradas Sánchez
 * 07-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import proyectofinal.modelo.dao.OrganizacionVinculadaDAO;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU12_RegistrarOrganizacionVinculadaController implements Initializable {

    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfCorreoElectronico;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfNombreOrganizacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
        if (validarCampos()) {
            OrganizacionVinculada organizacionVinculada = obtenerOrganizacionVinculadaNueva();
            guardarOrganizacion(organizacionVinculada);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "Datos inválidos y/o campos vacíos");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar el registro de la organización vinculada?")) {
            Utilidad.getEscenario(tfTelefono).close();
        }
    }

    @FXML
    private void tfNombreOrganizacionPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfDireccion.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfDireccionPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfCorreoElectronico.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfCorreoElectronicoPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfTelefono.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfTelefonoPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            clicBotonRegistrar(new ActionEvent());
            event.consume();
        }
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String nombreOrganizacion = tfNombreOrganizacion.getText().trim();
        String email = tfCorreoElectronico.getText().trim();
        String direccion = tfDireccion.getText().trim();
        String telefono = tfTelefono.getText().trim();
        
        if (nombreOrganizacion.isEmpty() || nombreOrganizacion.length() > 100) {
            camposValidos = false;
        }
        if (email.isEmpty() || email.length() > 50) {
            camposValidos = false;
        } else {
            int indiceArroba = email.indexOf('@');
            int indicePunto = email.lastIndexOf('.');

            if (indiceArroba == -1 || indicePunto == -1) {
                tfCorreoElectronico.setText("");
                camposValidos = false;
            } else if (indiceArroba > indicePunto || indiceArroba == 0 || indicePunto == email.length() - 1 || (indicePunto - indiceArroba) < 2) {
                tfCorreoElectronico.setText("");
                camposValidos = false;
            } else if (email.contains("..") || email.contains("@.")) { 
                tfCorreoElectronico.setText("");
                camposValidos = false;
            }
        }
        if (direccion.isEmpty() || direccion.length() > 200) {
            camposValidos = false;
        } else {
            if (direccion.length() < 25) {
                camposValidos = false;
                tfDireccion.setText("");
            }
        }
        if (telefono.isEmpty()) {
            camposValidos = false;
        } else {
            String telefonoSoloDigitos = telefono.replaceAll("[^0-9]", "");
            if (telefonoSoloDigitos.length() < 8 || telefonoSoloDigitos.length() > 15) {
                camposValidos =  false;
                tfTelefono.setText("");
            }
        }
        return camposValidos;
    }
    
    private OrganizacionVinculada obtenerOrganizacionVinculadaNueva() {
        OrganizacionVinculada organizacion = new OrganizacionVinculada();
        organizacion.setDireccion(tfDireccion.getText());
        organizacion.setEmail(tfCorreoElectronico.getText());
        organizacion.setNombre(tfNombreOrganizacion.getText());
        organizacion.setTelefono(tfTelefono.getText().replaceAll("[^0-9]", ""));
        return organizacion;
    }
    
    private void guardarOrganizacion(OrganizacionVinculada organizacionVinculada) {
        try {
            ResultadoOperacion resultadoInsertar = OrganizacionVinculadaDAO.registrarOrganizacionVinculada(organizacionVinculada);
            if (!resultadoInsertar.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "La organización " + organizacionVinculada.getNombre() + " ha sido registrada exitosamente");
                Utilidad.getEscenario(tfTelefono).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al registrar organización vinculada", resultadoInsertar.getMensaje());
                Utilidad.getEscenario(tfTelefono).close();
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfTelefono).close();
        }
    }
}