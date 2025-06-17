/*
 * Genaro Alejandro Barradas Sánchez
 * 16-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import proyectofinal.modelo.dao.AcademicoDAO;
import proyectofinal.modelo.dao.AcademicoEvaluadorDAO;
import proyectofinal.modelo.dao.CoordinadorDAO;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.modelo.dao.UsuarioDAO;
import proyectofinal.modelo.pojo.Academico;
import proyectofinal.modelo.pojo.AcademicoEvaluador;
import proyectofinal.modelo.pojo.Coordinador;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLAgregarUsuarioNuevoController implements Initializable {

    @FXML
    private ComboBox<String> cbTipoUsuario;
    @FXML
    private TextField tfPorDefinir;
    @FXML
    private Label lbPorDefinir;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreoElectronico;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfUsuario;
    @FXML
    private DatePicker dpFechaNacimiento;
    
    ObservableList<String> opcionesRoles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir? No se guardarán los cambios")) {
            Utilidad.getEscenario(tfPorDefinir).close();
        }
    }

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
        if (validarCampos()) {
            String tipoUsuario = cbTipoUsuario.getSelectionModel().getSelectedItem();
            Usuario usuarioNuevo = obtenerUsuarioNuevo(tipoUsuario);
            guardarUsuario(usuarioNuevo);
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Error", "Datos inválidos y/o campos vacíos");
        }
    }

    @FXML
    private void tfNombrePresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfApellidoPaterno.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfApellidoPaternoPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfApellidoMaterno.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfApellidoMaternoPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfCorreoElectronico.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfCorreoElectronicoPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfUsuario.requestFocus();
            event.consume();
        }
    }

    @FXML
    private void tfUsuarioPresionaEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            tfPassword.requestFocus();
            event.consume();
        }
    }
    
    private void seleccionarTipoUsuario() {
       cbTipoUsuario.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lbPorDefinir.setText("");
                tfPorDefinir.setPromptText("");
                dpFechaNacimiento.setValue(null);
                switch (newValue) {
                    case "Académico": {
                        lbPorDefinir.setText("Número de personal");
                        tfPorDefinir.setPromptText("Ej: 12345");
                        tfPorDefinir.setDisable(false);
                        dpFechaNacimiento.setDisable(true);
                        break;
                    }
                    case "Académico Evaluador": {
                        lbPorDefinir.setText("Número de personal");
                        tfPorDefinir.setPromptText("Ej: 12345");
                        tfPorDefinir.setDisable(false);
                        dpFechaNacimiento.setDisable(true);
                        break;
                    }
                    case "Coordinador": {
                        lbPorDefinir.setText("Teléfono");
                        tfPorDefinir.setPromptText("Ej: 2281234567");
                        tfPorDefinir.setDisable(false);
                        dpFechaNacimiento.setDisable(true);
                        break;
                    }
                    case "Estudiante": {
                        lbPorDefinir.setText("Matrícula");
                        tfPorDefinir.setPromptText("Ej:S23012345");
                        tfPorDefinir.setDisable(false);
                        dpFechaNacimiento.setDisable(false);
                        break;
                    }
                    default:
                        lbPorDefinir.setText("");
                        tfPorDefinir.setPromptText("");
                        tfPorDefinir.setDisable(true);
                        dpFechaNacimiento.setDisable(true);
                        break;
                }
            }
        });
   }
    
    public void inicializarInformacion() {
        tfPorDefinir.setDisable(true);
        dpFechaNacimiento.setDisable(true);
        cargarRoles();
        seleccionarTipoUsuario();
    }
    
   private void cargarRoles() {
       this.opcionesRoles = FXCollections.observableArrayList("Académico", "Académico Evaluador", "Coordinador", "Estudiante");
       cbTipoUsuario.setItems(opcionesRoles);
   }
    
    private boolean validarCampos() {
        boolean camposValidos = true;
        String nombre = tfNombre.getText().trim();
        String apellidoPaterno = tfApellidoPaterno.getText().trim();
        String apellidoMaterno = tfApellidoMaterno.getText().trim();
        String correoElectronico = tfCorreoElectronico.getText().trim();
        String usuario = tfUsuario.getText().trim();
        String password = tfPassword.getText().trim();
        String tipoUsuario = cbTipoUsuario.getSelectionModel().getSelectedItem();
        String textFieldVariable = tfPorDefinir.getText().trim();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        
        if (nombre.isEmpty() || nombre.length() > 50 || nombre.length() < 2) {
            camposValidos = false;
        }
        if (apellidoPaterno.isEmpty() || apellidoPaterno.length() > 50 || apellidoPaterno.length() < 3) {
            camposValidos = false;
        }
        if (apellidoMaterno.isEmpty() || apellidoMaterno.length() > 50 || apellidoMaterno.length() < 3) {
            camposValidos = false;
        }
        if (correoElectronico.isEmpty() || correoElectronico.length() > 50  || correoElectronico.length() < 7) {
            camposValidos = false;
        } else {
            int indiceArroba = correoElectronico.indexOf('@');
            int indicePunto = correoElectronico.lastIndexOf('.');

            if (indiceArroba == -1 || indicePunto == -1) {
                tfCorreoElectronico.setText("");
                camposValidos = false;
            } else if (indiceArroba > indicePunto || indiceArroba == 0 || indicePunto == correoElectronico.length() - 1 || (indicePunto - indiceArroba) < 2) {
                tfCorreoElectronico.setText("");
                camposValidos = false;
            } else if (correoElectronico.contains("..") || correoElectronico.contains("@.")) { 
                tfCorreoElectronico.setText("");
                camposValidos = false;
            }
        }
        if (usuario.isEmpty() || usuario.length() > 25  || usuario.length() < 5) {
            camposValidos = false;
        } else {
            try {
                if (UsuarioDAO.existeUsuario(usuario)) {
                    camposValidos = false;
                    tfUsuario.setText("");
                }
            } catch (SQLException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                        "Error de conexión con base de datos, inténtalo más tarde");
                Utilidad.getEscenario(tfNombre).close();
            }
        }
        if (password.isEmpty() || password.length() > 25 || password.length() < 8) {
            camposValidos = false;
        }
        
        if (!camposValidos) {
            return false;
        }
        if (tipoUsuario.isEmpty()) {
            camposValidos = false;
        } else {
            if (textFieldVariable.isEmpty()) {
                camposValidos = false;
            } else {
                switch (tipoUsuario) {
                    case "Académico":
                    case "Académico Evaluador": {
                        try {
                            int numeroPersonalParseado = Integer.parseInt(textFieldVariable);
                            if (textFieldVariable.length() != 5) {
                                camposValidos = false;
                                tfPorDefinir.setText("");
                            }
                        } catch (NumberFormatException e) {
                            camposValidos = false;
                            tfPorDefinir.setText("");
                        }
                        break;
                    }
                    case "Coordinador": {
                        String telefonoSoloDigitos = textFieldVariable.replaceAll("[^0-9]", "");
                        if (telefonoSoloDigitos.length() < 8 || telefonoSoloDigitos.length() > 15) {
                            camposValidos =  false;
                            tfPorDefinir.setText("");
                        }
                        break;
                    }
                case "Estudiante": {
                        String regex = "^S\\d{8}$";
                        if (!textFieldVariable.matches(regex)) {
                            camposValidos = false;
                            tfPorDefinir.setText("");
                        } else {
                            try {
                                if (EstudianteDAO.existeMatricula(textFieldVariable)) {
                                    camposValidos = false;
                                    tfPorDefinir.setText("");
                                }
                            } catch (SQLException ex) {
                                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                                        "Error de conexión con base de datos, inténtalo más tarde");
                                Utilidad.getEscenario(tfNombre).close();
                            }
                        }
                        if (fechaNacimiento == null) {
                            camposValidos = false;
                        } else {
                            LocalDate fechaActual = LocalDate.now();
                            int edad = Period.between(fechaNacimiento, fechaActual).getYears();
                            if (edad < 18 || edad > 100) {
                                camposValidos = false;
                                dpFechaNacimiento.setValue(null);
                            } 
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        return camposValidos;
    }
    
    private Usuario obtenerUsuarioNuevo(String tipoUsuario) {
        Usuario usuarioNuevo = null;
        switch (tipoUsuario) {
            case "Académico": {
                Academico academico = new Academico();
                academico.setNombre(tfNombre.getText());
                academico.setApellidoPaterno(tfApellidoPaterno.getText());
                academico.setApellidoMaterno(tfApellidoMaterno.getText());
                academico.setEmail(tfCorreoElectronico.getText());
                academico.setUsername(tfUsuario.getText());
                academico.setPassword(tfPassword.getText());
                academico.setNoPersonal(Integer.parseInt(tfPorDefinir.getText()));
                usuarioNuevo = academico;
                break;
            }
            case "Académico Evaluador": {
                AcademicoEvaluador academicoEvaluador = new AcademicoEvaluador();
                academicoEvaluador.setNombre(tfNombre.getText());
                academicoEvaluador.setApellidoPaterno(tfApellidoPaterno.getText());
                academicoEvaluador.setApellidoMaterno(tfApellidoMaterno.getText());
                academicoEvaluador.setEmail(tfCorreoElectronico.getText());
                academicoEvaluador.setUsername(tfUsuario.getText());
                academicoEvaluador.setPassword(tfPassword.getText());
                academicoEvaluador.setNoPersonal(Integer.parseInt(tfPorDefinir.getText()));
                usuarioNuevo = academicoEvaluador;
                break;
            }
            case "Coordinador": {
                Coordinador coordinador = new Coordinador();
                coordinador.setNombre(tfNombre.getText());
                coordinador.setApellidoPaterno(tfApellidoPaterno.getText());
                coordinador.setApellidoMaterno(tfApellidoMaterno.getText());
                coordinador.setEmail(tfCorreoElectronico.getText());
                coordinador.setUsername(tfUsuario.getText());
                coordinador.setPassword(tfPassword.getText());
                coordinador.setTelefono(tfPorDefinir.getText().replaceAll("[^0-9]", ""));
                usuarioNuevo = coordinador;
                break;
            }
            case "Estudiante": {
                Estudiante estudiante = new Estudiante();
                estudiante.setNombre(tfNombre.getText());
                estudiante.setApellidoPaterno(tfApellidoPaterno.getText());
                estudiante.setApellidoMaterno(tfApellidoMaterno.getText());
                estudiante.setEmail(tfCorreoElectronico.getText());
                estudiante.setUsername(tfUsuario.getText());
                estudiante.setPassword(tfPassword.getText());
                estudiante.setMatricula(tfPorDefinir.getText());
                estudiante.setFechaNacimiento(dpFechaNacimiento.getValue().toString());
                estudiante.setIdExperienciaEducativa(6);
                usuarioNuevo = estudiante;
                break;
            }
        }
        return usuarioNuevo;
    }
    
    private void guardarUsuario (Usuario usuario) {
        try {
            ResultadoOperacion resultadoInsertarUsuario = UsuarioDAO.registrarUsuario(usuario);
            
            if (!resultadoInsertarUsuario.isError()) {
                ResultadoOperacion resultadoInsertar = new ResultadoOperacion();
                
                int idUsuario = UsuarioDAO.obtenerIdUsuarioPorCredenciales(usuario.getUsername(), usuario.getPassword());
                usuario.setIdUsuario(idUsuario);
                
                if (usuario instanceof Estudiante) {
                    resultadoInsertar = EstudianteDAO.registrarEstudiante((Estudiante) usuario);
                } else if (usuario instanceof Academico) {
                    resultadoInsertar = AcademicoDAO.registrarAcademico((Academico) usuario);
                } else if (usuario instanceof AcademicoEvaluador) {
                    resultadoInsertar = AcademicoEvaluadorDAO.registrarAcademicoEvaluador((AcademicoEvaluador) usuario);
                } else if (usuario instanceof Coordinador) {
                    resultadoInsertar = CoordinadorDAO.registrarCoordinador((Coordinador) usuario);
                }
                if (!resultadoInsertar.isError()) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                            "Operación exitosa", 
                            "El nuevo usuario ha sido registrado exitosamente.");
                    Utilidad.getEscenario(tfNombre).close();
                } else {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                            "Error al regitrar usuario", resultadoInsertar.getMensaje());
                    Utilidad.getEscenario(tfNombre).close();
                }
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                            "Error al regitrar usuario", resultadoInsertarUsuario.getMensaje());
                Utilidad.getEscenario(tfNombre).close();
            }
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tfNombre).close();
        }
    }
}