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
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU11_2_ActualizarProyectoController implements Initializable {

    @FXML
    private TableView<Proyecto> tvProyectosEncontrados;
    @FXML
    private Label lbProyectoFiltro;
    @FXML
    private TableColumn colNombre;
    
    private ObservableList<Proyecto> proyectos;
    private String nombreProyectoABuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void clicBotonActualizarProyecto(ActionEvent event) {
        Proyecto proyectoSeleccionado = tvProyectosEncontrados.getSelectionModel().getSelectedItem();
        if (proyectoSeleccionado != null) {
            try {
                irPantallaSiguiente(proyectoSeleccionado, 
                        "/proyectofinal/vista/FXMLCU11_3_ActualizarProyecto.fxml", "Actualizar proyecto");
            } catch (IOException ex) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR,
                    "Error al cargar la pantalla", "No se pudo cargar la siguiente pantalla");
                Utilidad.getEscenario(lbProyectoFiltro).close();
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                    "Selecciona un proyecto", 
                    "Para continuar, por favor selecciona algún proyecto de la tabla.");
        }
    }
    
    public void inicializarInformacion (String nombreProyecto) {
        this.nombreProyectoABuscar = nombreProyecto;
        lbProyectoFiltro.setText(nombreProyecto);
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
    }
    
    private void cargarInformacionTabla() {
        try {
            proyectos = FXCollections.observableArrayList();
            ArrayList<Proyecto> proyectosDAO = ProyectoDAO.obtenerProyectosPorNombre(nombreProyectoABuscar);
            proyectos.addAll(proyectosDAO);
            tvProyectosEncontrados.setItems(proyectos);
            
        } catch (SQLException ex) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la base de datos", 
                    "Error de conexión con base de datos, inténtalo más tarde");
            Utilidad.getEscenario(lbProyectoFiltro).close();
        }
    }
    
    private void irPantallaSiguiente(Proyecto proyecto, String fxmlPath, String titulo) throws IOException {
        Stage escenarioBase = (Stage) tvProyectosEncontrados.getScene().getWindow();
        FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource(fxmlPath));
        Parent vista = cargador.load();

        FXMLCU11_3_ActualizarProyectoController controlador = cargador.getController();
        controlador.inicializarInformacion(proyecto);

        Scene escenaPrincipal = new Scene(vista);
        escenarioBase.setScene(escenaPrincipal);
        escenarioBase.setTitle(titulo);
        escenarioBase.show();
    }
}