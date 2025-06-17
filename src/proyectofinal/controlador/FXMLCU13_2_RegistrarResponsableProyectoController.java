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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import proyectofinal.modelo.dao.ResponsableDeProyectoDAO;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU13_2_RegistrarResponsableProyectoController implements Initializable {

    @FXML
    private TextField tfDepartamento;
    @FXML
    private TextField tfCorreoElectronico;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfNombreResponsable;
    @FXML
    private TextField tfNombrePuesto;
    @FXML
    private Label lbOrganizacionVinculada;
    
    private OrganizacionVinculada organizacionVinculada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
        if (validarCampos()) {
            ResponsableDeProyecto responsableDeProyecto = obtenerResponsableDeProyectoNuevo();
            guardarResponsableDeProyecto(responsableDeProyecto);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "Datos inválidos y/o campos vacíos");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar?")) {
            Utilidad.getEscenario(tfDepartamento).close();
        }
    }

    @FXML
    private void tfNombreResponsablePresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfNombrePuesto.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfNombrePuestoPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfDepartamento.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfDepartamentoPresionaEnter(KeyEvent event) {
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
    
    public void inicializarInformacion (OrganizacionVinculada organizacionVinculada) {
        this.organizacionVinculada = organizacionVinculada;
        lbOrganizacionVinculada.setText(organizacionVinculada.getNombre());
    }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String nombreResponsable = tfNombreResponsable.getText().trim();
        String email = tfCorreoElectronico.getText().trim();
        String departamento = tfDepartamento.getText().trim();
        String nombrePuesto = tfNombrePuesto.getText().trim();
        String telefono = tfTelefono.getText().trim();
        
        if (nombreResponsable.isEmpty() || nombreResponsable.length() > 100) {
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
        if (departamento.isEmpty() || departamento.length() > 50) {
            camposValidos = false;
        }
        if (nombrePuesto.isEmpty() || nombrePuesto.length() > 50) {
            camposValidos = false;
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
    
    private ResponsableDeProyecto obtenerResponsableDeProyectoNuevo() {
        ResponsableDeProyecto responsable = new ResponsableDeProyecto();
        responsable.setDepartamento(tfDepartamento.getText());
        responsable.setEmail(tfCorreoElectronico.getText());
        responsable.setIdOrganizacionVinculada(organizacionVinculada.getIdOrganizacionVinculada());
        responsable.setNombre(tfNombreResponsable.getText());
        responsable.setPuesto(tfNombrePuesto.getText());
        responsable.setTelefono(tfTelefono.getText().replaceAll("[^0-9]", ""));
        return responsable;
    }
    
    private void guardarResponsableDeProyecto (ResponsableDeProyecto responsableDeProyecto) {
        try {
            ResultadoOperacion resultadoInsertar = ResponsableDeProyectoDAO.registrarResponsableDeProyecto(responsableDeProyecto);
            if (!resultadoInsertar.isError()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", 
                        "El responsable de proyecto ha sido registrado exitosamente.");
                Utilidad.getEscenario(tfDepartamento).close();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error al regitrar responsable", resultadoInsertar.getMensaje());
                Utilidad.getEscenario(tfDepartamento).close();
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfDepartamento).close();
        }
    }
}