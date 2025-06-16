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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.dao.OrganizacionVinculadaDAO;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU13_1_RegistrarResponsableProyectoController implements Initializable {

    @FXML
    private TableView<OrganizacionVinculada> tvOrganizacionesVinculadas;
    @FXML
    private TableColumn colNombre;
    
    private ObservableList<OrganizacionVinculada> organizaciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    

    @FXML
    private void clicBotonAceptar(ActionEvent event) {
        OrganizacionVinculada organizacionVinculada = tvOrganizacionesVinculadas.getSelectionModel().getSelectedItem();
        if (organizacionVinculada != null) {
            try {
                irPantallaSiguiente(organizacionVinculada, "/proyectofinal/vista/FXMLCU13_2_RegistrarResponsableProyecto.fxml", "Registrar responsable de proyecto");
            } catch (IOException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la pantalla siguiente");
                Utilidad.getEscenario(tvOrganizacionesVinculadas).close();
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                    "Selecciona una organización vinculada", 
                    "Para continuar, por favor selecciona alguna organización vinculada de la tabla.");
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas cancelar?")) {
            Utilidad.getEscenario(tvOrganizacionesVinculadas).close();
        }
    }
    
    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
    }
    
    private void cargarInformacionTabla() {
        try {
            organizaciones = FXCollections.observableArrayList();
            ArrayList<OrganizacionVinculada> organizacionesDAO = OrganizacionVinculadaDAO.obtenerOrganizacionesVinculadas();
            organizaciones.addAll(organizacionesDAO);
            tvOrganizacionesVinculadas.setItems(organizaciones);
            
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(tvOrganizacionesVinculadas).close();
        }
    }
    
    private void irPantallaSiguiente(OrganizacionVinculada organizacionVinculada, String fxmlPath, String titulo) throws IOException {
        Stage escenarioBase = (Stage) tvOrganizacionesVinculadas.getScene().getWindow();
        FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
        Parent vista = cargador.load();

        FXMLCU13_2_RegistrarResponsableProyectoController controlador = cargador.getController();
        controlador.inicializarInformacion(organizacionVinculada);

        Scene escenaPrincipal = new Scene(vista);
        escenarioBase.setScene(escenaPrincipal);
        escenarioBase.setTitle(titulo);
        escenarioBase.show();
    }
}