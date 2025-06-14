/*
 * Omar Morales García
 * 10-06-2025
 */
package proyectofinal.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import proyectofinal.modelo.dao.EvaluacionOVDAO;
import proyectofinal.modelo.pojo.EvaluacionOV;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.dao.EstudianteDAO;
import proyectofinal.utilidades.Utilidad;
import proyectofinal.utilidades.SessionManager;
import proyectofinal.modelo.pojo.EvaluacionOVCategoria;
import proyectofinal.modelo.pojo.EvaluacionOVAfirmacion;
import proyectofinal.modelo.pojo.EvaluacionOVCriterio;
import proyectofinal.modelo.pojo.EvaluacionOVResultado;
import proyectofinal.modelo.dao.EvaluacionOVCategoriaDAO;
import proyectofinal.modelo.dao.EvaluacionOVAfirmacionDAO;
import proyectofinal.modelo.dao.EvaluacionOVCriterioDAO;
import proyectofinal.modelo.dao.EvaluacionOVResultadoDAO;
import proyectofinal.modelo.pojo.Expediente;
import proyectofinal.modelo.dao.ExpedienteDAO;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.dao.ProyectoDAO;
import java.sql.SQLException;

public class FXMLCU16_EvaluarOrganizacionVinculadaController implements Initializable {

    @FXML
    private TextField tfProyectoNombre;
    @FXML
    private TextField tfHorasCubiertas;
    @FXML
    private TextField tfOrganizacionVinculada;
    @FXML
    private TextField tfResponsableNombre;
    @FXML
    private TextField tfResponsableDepartamento;
    @FXML
    private TextField tfResponsableCargo;
    @FXML
    private TextField tfEstudianteNombre;
    @FXML
    private TextField tfEstudianteMatricula;
    @FXML
    private TextField tfEstudianteCorreo;
    @FXML
    private VBox vboxRubricaContenido;
    @FXML
    private TextArea taComentariosGenerales;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;

    private EvaluacionOV evaluacionOVActual;
    private Estudiante estudianteLoggeado;
    private List<EvaluacionOVCategoria> categorias;
    private List<EvaluacionOVCriterio> criterios;
    private Map<Integer, ToggleGroup> afirmacionToggleGroups;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        evaluacionOVActual = new EvaluacionOV();
        cargarInformacionDelEstudiante();
        cargarInformacionProyectoYOrganizacion();
        cargarInformacionRubricaYConstruirIU();
    }

    @FXML
    private void clicBotonAceptar(ActionEvent event) {
        manejarAceptar();
    }

    @FXML
    private void clicBotonCancelar(ActionEvent event) {
        if (Utilidad.mostrarAlertaConfirmacion("Confirmación", "¿Deseas salir?")) {
            Utilidad.getEscenario(btnAceptar).close();
        }
    }

    private void cargarInformacionDelEstudiante() {
        if (SessionManager.isUserLoggedIn()) {
            if (SessionManager.getLoggedInUser() instanceof Estudiante) {
                int idUsuario = SessionManager.getLoggedInUser().getIdUsuario();
                try {
                    estudianteLoggeado = EstudianteDAO.obtenerEstudiantePorIdUsuario(idUsuario);
                    if (estudianteLoggeado != null) {
                        tfEstudianteNombre.setText(estudianteLoggeado.getNombre() + " " + estudianteLoggeado.getApellidoPaterno() + " " + estudianteLoggeado.getApellidoMaterno());
                        tfEstudianteMatricula.setText(estudianteLoggeado.getMatricula());
                        tfEstudianteCorreo.setText(estudianteLoggeado.getEmail());

                        evaluacionOVActual.setIdExpediente(estudianteLoggeado.getIdExpediente());
                    } else {
                        Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                                "Error al cargar estudiante", "No se encontraron los datos del estudiante loggeado para el ID de usuario: " + idUsuario);
                        btnAceptar.disableProperty().set(true);
                    }
                } catch (Exception e) {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                            "Error de conexión", "Error al cargar datos del estudiante desde la base de datos: " + e.getMessage());
                    e.printStackTrace();
                    btnAceptar.disableProperty().set(true);
                }
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                        "Acceso no autorizado", "El usuario loggeado no es un estudiante y no tiene permiso para realizar esta evaluación.");
                btnAceptar.disableProperty().set(true);
                btnCancelar.fire();
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                    "Sesión no iniciada", "No hay un usuario loggeado. Los datos del estudiante no se cargarán.");
            btnAceptar.disableProperty().set(true);
            btnCancelar.fire();
        }
    }

    private void cargarInformacionProyectoYOrganizacion() {
        if (evaluacionOVActual.getIdExpediente() > 0) {
            try {
                Expediente expediente = ExpedienteDAO.obtenerExpedientePorId(evaluacionOVActual.getIdExpediente());

                if (expediente != null) {
                   Proyecto proyecto = ProyectoDAO.obtenerProyectoPorId(expediente.getIdProyecto());
                    if (proyecto != null) {
                        tfProyectoNombre.setText(proyecto.getNombre());
                        tfHorasCubiertas.setText(String.valueOf(expediente.getHorasAcumuladas()));

                       String nombreOrganizacion = proyecto.getNombreOrganizacion();
                        if (nombreOrganizacion != null && !nombreOrganizacion.isEmpty()) {
                            tfOrganizacionVinculada.setText(nombreOrganizacion);
                        } else {
                            tfOrganizacionVinculada.setText("N/A");
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                                    "Organización no encontrada", "No se encontró la organización vinculada asociada al proyecto.");
                        }

                        String nombreResponsable = proyecto.getNombreResponsable();
                        String departamentoResponsable = proyecto.getDepartamentoResponsable();
                        String cargoResponsable = proyecto.getCargoResponsable();

                        if (nombreResponsable != null && !nombreResponsable.isEmpty()) {
                            tfResponsableNombre.setText(nombreResponsable);
                            tfResponsableDepartamento.setText(departamentoResponsable != null ? departamentoResponsable : "N/A");
                            tfResponsableCargo.setText(cargoResponsable != null ? cargoResponsable : "N/A");
                        } else {
                            tfResponsableNombre.setText("N/A");
                            tfResponsableDepartamento.setText("N/A");
                            tfResponsableCargo.setText("N/A");
                            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                                    "Responsable no encontrado", "No se encontró el responsable de proyecto asociado al proyecto.");
                        }

                    } else {
                        tfProyectoNombre.setText("N/A");
                        tfHorasCubiertas.setText("N/A");
                        tfOrganizacionVinculada.setText("N/A"); 
                        tfResponsableNombre.setText("N/A");     
                        tfResponsableDepartamento.setText("N/A"); 
                        tfResponsableCargo.setText("N/A");        
                        Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                                "Proyecto no encontrado", "No se encontró el proyecto asociado al expediente.");
                    }
                } else {
                    Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                            "Expediente no encontrado", "No se encontró el expediente completo para el estudiante.");
                    btnAceptar.disableProperty().set(true);
                }
            } catch (SQLException e) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error de conexión", "Error al cargar datos del proyecto/organización desde la base de datos: " + e.getMessage());
                btnAceptar.disableProperty().set(true);
            }
        } else {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                    "Expediente no disponible", "No se pudo cargar el ID del expediente para obtener los datos del proyecto/organización.");
            btnAceptar.disableProperty().set(true);
        }
    }

    private void cargarInformacionRubricaYConstruirIU() {
        afirmacionToggleGroups = new HashMap<>();
        vboxRubricaContenido.getChildren().clear();
        try {
            categorias = EvaluacionOVCategoriaDAO.obtenerCategorias();
            criterios = EvaluacionOVCriterioDAO.obtenerCriterios();

            if (categorias.isEmpty() || criterios.isEmpty()) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Datos de rúbrica incompletos", "No se pudieron cargar las categorías o criterios de la rúbrica.");
                btnAceptar.disableProperty().set(true);
                return;
            }

            for (EvaluacionOVCategoria categoria : categorias) {
                Label categoriaLabel = new Label(categoria.getNombre());
                categoriaLabel.setFont(Font.font("Gill Sans MT", FontWeight.BOLD, 15));
                categoriaLabel.setTextFill(Color.BLACK);
                vboxRubricaContenido.getChildren().add(categoriaLabel);

                List<EvaluacionOVAfirmacion> afirmaciones = EvaluacionOVAfirmacionDAO.obtenerAfirmacionesPorCategoria(categoria.getIdCategoriaEvaluacionOV());

                if (afirmaciones.isEmpty()) {
                    Label noAfirmacionesLabel = new Label("No hay afirmaciones para esta categoría.");
                    noAfirmacionesLabel.setFont(Font.font("Gill Sans MT", FontWeight.NORMAL, 14));
                    noAfirmacionesLabel.setTextFill(Color.BLACK);
                    vboxRubricaContenido.getChildren().add(noAfirmacionesLabel);
                    continue;
                }

                for (EvaluacionOVAfirmacion afirmacion : afirmaciones) {
                    Label afirmacionLabel = new Label("  - " + afirmacion.getDescripcion());
                    afirmacionLabel.setFont(Font.font("Gill Sans MT", FontWeight.NORMAL, 14));
                    vboxRubricaContenido.getChildren().add(afirmacionLabel);

                    HBox criteriosHBox = new HBox(10);
                    ToggleGroup toggleGroup = new ToggleGroup();
                    afirmacionToggleGroups.put(afirmacion.getIdAfirmacionEvaluacionOV(), toggleGroup);

                    for (EvaluacionOVCriterio criterio : criterios) {
                        RadioButton rb = new RadioButton(criterio.getNombre());
                        rb.setToggleGroup(toggleGroup);
                        rb.setUserData(criterio);
                        criteriosHBox.getChildren().add(rb);
                    }
                    vboxRubricaContenido.getChildren().add(criteriosHBox);
                }
                vboxRubricaContenido.getChildren().add(new Label(""));
            }

        } catch (Exception e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar rúbrica", "No se pudieron cargar los datos de la rúbrica: " + e.getMessage());
            btnAceptar.disableProperty().set(true);
        }
    }

    private boolean validarSeleccionesRubrica() {
        for (Map.Entry<Integer, ToggleGroup> entry : afirmacionToggleGroups.entrySet()) {
            Integer idAfirmacion = entry.getKey();
            ToggleGroup toggleGroup = entry.getValue();
            if (toggleGroup.getSelectedToggle() == null) {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, "Rúbrica incompleta", "Debes seleccionar una opción para cada afirmación de la rúbrica.");
                return false;
            }
        }
        return true;
    }

    private void manejarAceptar() {
        if (estudianteLoggeado == null || evaluacionOVActual.getIdExpediente() == 0) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Error de datos", "No se pudo obtener el expediente del estudiante. Asegúrate de que los datos del estudiante se cargaron correctamente.");
            return;
        }
        if (!validarSeleccionesRubrica()) {
            return;
        }

        List<EvaluacionOVResultado> resultadosRubrica = new ArrayList<>();
        double puntajeTotal = 0.0;
        int afirmacionesContadas = 0;

        for (Map.Entry<Integer, ToggleGroup> entry : afirmacionToggleGroups.entrySet()) {
            Integer idAfirmacion = entry.getKey();
            RadioButton selectedRadioButton = (RadioButton) entry.getValue().getSelectedToggle();

            if (selectedRadioButton != null) {
                EvaluacionOVCriterio criterioSeleccionado = (EvaluacionOVCriterio) selectedRadioButton.getUserData();

                EvaluacionOVResultado resultado = new EvaluacionOVResultado();
                resultado.setIdAfirmacionEvaluacionOV(idAfirmacion);
                resultado.setIdCriterioEvaluacionOV(criterioSeleccionado.getIdCriterioEvaluacionOV());
                resultadosRubrica.add(resultado);

                puntajeTotal += criterioSeleccionado.getValor();
                afirmacionesContadas++;
            }
        }

        evaluacionOVActual.setPuntajeFinal(puntajeTotal);

        evaluacionOVActual.setComentarios(taComentariosGenerales.getText().trim());
        if (evaluacionOVActual.getComentarios().isEmpty()) {
             Utilidad.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                     "Campos vacíos", "Debes añadir comentarios generales de la evaluación.");
             return;
        }

        evaluacionOVActual.setFecha(java.time.LocalDate.now().toString());

        try {
            int idEvaluacionGenerada = EvaluacionOVDAO.guardarEvaluacionOV(evaluacionOVActual);

            if (idEvaluacionGenerada > 0) {
                for (EvaluacionOVResultado resultado : resultadosRubrica) {
                    resultado.setIdEvaluacionOV(idEvaluacionGenerada);
                }
                EvaluacionOVResultadoDAO.guardarResultadosEvaluacionOV(resultadosRubrica);

                Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Operación exitosa", "Evaluación a organización vinculada registrada y rúbrica guardada.");
                btnAceptar.getScene().getWindow().hide();
            } else {
                Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                        "Error", "No se pudo registrar la evaluación principal.");
            }
        } catch (Exception e) {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Error en la base de datos", "Error de conexión con base de datos, intentalo más tarde: " + e.getMessage());
        }
    }
}