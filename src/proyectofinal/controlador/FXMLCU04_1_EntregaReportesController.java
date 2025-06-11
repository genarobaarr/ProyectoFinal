/*
 * Alejandro Martínez Ramírez
 * 02-06-2025
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
import javafx.stage.Stage;
import proyectofinal.ProyectoFinal;
import proyectofinal.modelo.dao.OrganizacionVinculadaDAO;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.pojo.ReporteMensual;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;


public class FXMLCU04_1_EntregaReportesController implements Initializable {

    @FXML
    private TableView<?> tvReportesMensuales;
    @FXML
    private TableColumn colNombres;
    
    private ObservableList ReporteMensual;
    private Estudiante estudiante;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void clicBotonNuevoReporte(ActionEvent event) {
         try {
            Stage escenarioBase = (Stage) tvReportesMensuales.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(ProyectoFinal.class.getResource("vista/FXMLCU04_2_EntregaReportes.fxml"));
            Parent vista = cargador.load();

            FXMLCU04_2_EntregaReportesController controlador = cargador.getController();
            controlador.inicializarInformacion(estudiante);

            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Entregar reporte");
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        
    }
    
    public void inicializarInformacion (Estudiante estudiante) {
        this.estudiante = estudiante;
        
    }
}
    
  