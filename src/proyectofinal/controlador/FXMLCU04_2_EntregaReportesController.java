/*
 * Alejandro Martínez Ramírez
 * 02-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import proyectofinal.modelo.dao.OrganizacionVinculadaDAO;
import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.dao.ResponsableDeProyectoDAO;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.utilidades.Utilidad;

public class FXMLCU04_2_EntregaReportesController implements Initializable {

    @FXML
    private TextField tfNumeroReporte;
    @FXML
    private TextField tfNumeroHoras;
    @FXML
    private TextField tfNombreEstudiante;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextField tfPeriodoReporte;
    @FXML
    private TextField tfProyectoVinculado;
    @FXML
    private TextField tfResponsableProyecto;
    @FXML
    private TextField tfOrganizacionVinculada;
    
    private Estudiante estudiante;
    private Proyecto proyecto;
    private ResponsableDeProyecto responsableProyecto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void tfNumeroReportePresionaEnter(KeyEvent event) {
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
    }

    @FXML
    private void clicBotonRegistrar(ActionEvent event) {
    
    }

    @FXML
    private void tfNumeroHorasPresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfNombreEstudiantePresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfMatriculaPresionaEnter(KeyEvent event) {
    }

    @FXML
    private void tfPeriodoReportePresionaEnter(KeyEvent event) {
    }
    
    public void inicializarInformacion (Estudiante estudiante) {
        try {
            this.estudiante = estudiante;
            this.proyecto = obtenerProyectoDeEstudiante(estudiante.getIdUsuario());
            this.responsableProyecto = obtenerResponsableDeProyecto(proyecto.getIdResponsableDeProyecto());
            tfNombreEstudiante.setText(estudiante.getNombre()+" "+estudiante.getApellidoPaterno()+" "+estudiante.getApellidoMaterno());
            tfMatricula.setText(estudiante.getMatricula());
            tfProyectoVinculado.setText(proyecto.getNombre());
            tfResponsableProyecto.setText(responsableProyecto.getNombre());
            tfOrganizacionVinculada.setText(obtenerOrganizacionVinculada(responsableProyecto.getIdOrganizacionVinculada()).getNombre());
        } catch (SQLException ex) {
             ex.printStackTrace();
        }
    }
    
    private Proyecto obtenerProyectoDeEstudiante(int idEstudiante) throws SQLException {
        Proyecto proyecto = ProyectoDAO.obtenerProyectoPorEstudiante(idEstudiante);
        return proyecto;
    }
    
    private ResponsableDeProyecto obtenerResponsableDeProyecto(int idResponsableProyecto) throws SQLException {
        ResponsableDeProyecto responsableProyecto = ResponsableDeProyectoDAO.obtenerResponsableDeProyectoPorId(idResponsableProyecto);
        return responsableProyecto;
    }
    
    private OrganizacionVinculada obtenerOrganizacionVinculada(int idOrganizacionVinculada) throws SQLException {
        OrganizacionVinculada organizacionVinculada = OrganizacionVinculadaDAO.obtenerOrganizacionVinculadaPorId(idOrganizacionVinculada);
        return organizacionVinculada;
    }
    
}
