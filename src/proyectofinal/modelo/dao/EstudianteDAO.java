/*
 * Omar Morales García
 * 09-06-2025
 */

package proyectofinal.modelo.dao;

import proyectofinal.modelo.pojo.Estudiante;
import java.util.List;


public interface EstudianteDAO {
    List<Estudiante> obtenerEstudiantesSinProyectoAsignado();
    void crearExpedienteEstudianteProyecto(int idEstudiante, int idProyecto, int idPeriodo);
}