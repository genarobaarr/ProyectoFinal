/*
 * Genaro Alejandro Barradas Sánchez
 * 07-06-2025
 */
package proyectofinal.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.dao.ResponsableDeProyectoDAO;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU10_2_RegistrarProyectoController implements Initializable {

    @FXML
    private TableColumn colNombre;
    @FXML
    private Label lbOrganizacionVinculada;
    @FXML
    private TableView<ResponsableDeProyecto> tvResponsablesDeProyecto;
    
    private OrganizacionVinculada organizacionVinculada;
    private ObservableList<ResponsableDeProyecto> responsables;
    private Usuario coordinador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicBotonAceptar(ActionEvent event) {
        ResponsableDeProyecto responsableDeProyecto = tvResponsablesDeProyecto.getSelectionModel().getSelectedItem();
        if (responsableDeProyecto != null) {
            try {
                irPantallaSiguiente(organizacionVinculada, responsableDeProyecto, 
                        "/proyectofinal/vista/FXMLCU10_3_RegistrarProyecto.fxml", "Registro de proyecto");
            } catch (IOException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                        "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
                Utilidad.getEscenario(lbOrganizacionVinculada).close();
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                    "Selecciona un responsable de proyecto", 
                    "Para continuar, por favor selecciona a algún responsable de proyecto de la tabla.");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir?")) {
            Utilidad.getEscenario(lbOrganizacionVinculada).close();
        }
    }
    
    public void inicializarInformacion (OrganizacionVinculada organizacionVinculada, Usuario usuario) {
        this.coordinador = usuario;
        this.organizacionVinculada = organizacionVinculada;
        lbOrganizacionVinculada.setText(organizacionVinculada.getNombre());
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
    }
    
    private void cargarInformacionTabla() {
        try {
            responsables = FXCollections.observableArrayList();
            ArrayList<ResponsableDeProyecto> responsablesDAO = ResponsableDeProyectoDAO.obtenerResponsablesDeProyectoPorOrganizacion(organizacionVinculada.getIdOrganizacionVinculada());
            responsables.addAll(responsablesDAO);
            tvResponsablesDeProyecto.setItems(responsables);
            
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(lbOrganizacionVinculada).close();
        }
    }
    
    private void irPantallaSiguiente(OrganizacionVinculada organizacionVinculada, ResponsableDeProyecto resposanDeProyecto, String fxmlPath, String titulo) throws IOException {
            Stage escenarioBase = (Stage) tvResponsablesDeProyecto.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
            Parent vista = cargador.load();

            FXMLCU10_3_RegistrarProyectoController controlador = cargador.getController();
            controlador.inicializarInformacion(organizacionVinculada, resposanDeProyecto, coordinador);

            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle(titulo);
            escenarioBase.show();
    }
}