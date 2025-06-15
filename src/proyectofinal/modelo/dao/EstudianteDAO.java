/*
 * Omar Morales García
 * 09-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import proyectofinal.modelo.pojo.Estudiante;
import java.util.List;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.EvaluacionOV;
import proyectofinal.modelo.pojo.EvaluacionExposicion;
import java.math.BigDecimal;

public class EstudianteDAO {

    public static List<Estudiante> obtenerEstudiantesSinProyectoAsignado() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String consulta = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia " +
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "LEFT JOIN expediente exp ON e.idUsuario = exp.idEstudiante AND exp.estatus = 'Activo' " +
                       "WHERE exp.idEstudiante is NULL";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                int idUsuario = resultado.getInt("idUsuario");
                String nombre = resultado.getString("nombre");
                String apellidoPaterno = resultado.getString("apellidoPaterno");
                String apellidoMaterno = resultado.getString("apellidoMaterno");
                String email = resultado.getString("email");
                String username = resultado.getString("username");

                Date fechaNacimientoSql = resultado.getDate("fechaNacimiento");
                String fechaNacimiento = (fechaNacimientoSql != null) ? fechaNacimientoSql.toString() : null;
                String matricula = resultado.getString("matricula");
                int idExperiencia = resultado.getInt("idExperiencia");

                Estudiante estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia,
                                                       idUsuario, nombre, apellidoPaterno,
                                                       apellidoMaterno, email, username);
                estudiantes.add(estudiante);
            }
        }
        return estudiantes;
    }

    public static void crearExpedienteEstudianteProyecto(int idEstudiante, int idProyecto, int idPeriodo) throws SQLException {
        String consulta = "INSERT INTO expediente (estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            sentencia.setString(1, "Activo");
            sentencia.setInt(2, 0);
            sentencia.setInt(3, idProyecto);
            sentencia.setInt(4, idPeriodo);
            sentencia.setInt(5, idEstudiante);

            int filasAfectadas = sentencia.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo crear el expediente, ninguna fila afectada.");
            }
        }
    }

    public static List<Estudiante> obtenerEstudiantesConReporteMensual() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String consulta = "SELECT DISTINCT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia, exp.idExpediente, exp.horasAcumuladas " + 
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "INNER JOIN expediente exp ON e.idUsuario = exp.idEstudiante " +
                       "INNER JOIN reporte_mensual rm ON exp.idExpediente = rm.idExpediente " +
                       "WHERE exp.estatus = 'Activo'";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                int idUsuario = resultado.getInt("idUsuario");
                String nombre = resultado.getString("nombre");
                String apellidoPaterno = resultado.getString("apellidoPaterno");
                String apellidoMaterno = resultado.getString("apellidoMaterno");
                String email = resultado.getString("email");
                String username = resultado.getString("username");

                Date fechaNacimientoSql = resultado.getDate("fechaNacimiento");
                String fechaNacimiento = (fechaNacimientoSql != null) ? fechaNacimientoSql.toString() : null;
                String matricula = resultado.getString("matricula");
                int idExperiencia = resultado.getInt("idExperiencia");
                int idExpediente = resultado.getInt("idExpediente");
                int horasAcumuladas = resultado.getInt("horasAcumuladas");

                Estudiante estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia,
                                                       idUsuario, nombre, apellidoPaterno,
                                                       apellidoMaterno, email, username,
                                                       idExpediente, horasAcumuladas);
                estudiantes.add(estudiante);
            }
        }
        return estudiantes;
    }

    public static List<EvaluacionOV> obtenerEvaluacionesOVEstudiante(int idEstudiante) throws SQLException {
        List<EvaluacionOV> evaluaciones = new ArrayList<>();
        String consulta = "SELECT eo.idEvaluacionOV, eo.comentarios, eo.fecha, eo.puntaje_final " +
                       "FROM evaluacion_ov eo " +
                       "INNER JOIN expediente exp ON eo.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ? ORDER BY eo.fecha ASC";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    EvaluacionOV evaluacion = new EvaluacionOV();
                    evaluacion.setIdEvaluacionOV(resultado.getInt("idEvaluacionOV"));
                    evaluacion.setComentarios(resultado.getString("comentarios"));

                    Date fechaSql = resultado.getDate("fecha");
                    evaluacion.setFecha(fechaSql != null ? fechaSql.toString() : null);

                    BigDecimal puntaje = resultado.getBigDecimal("puntaje_final");
                    evaluacion.setPuntajeFinal(puntaje != null ? puntaje.doubleValue() : 0.0);

                    evaluaciones.add(evaluacion);
                }
            }
        }
        return evaluaciones;
    }

    public static List<EvaluacionExposicion> obtenerEvaluacionesExposicionEstudiante(int idEstudiante) throws SQLException {
        List<EvaluacionExposicion> evaluaciones = new ArrayList<>();
        String consulta = "SELECT ee.idEvaluacionExposicion, ee.comentarios, ee.puntajeFinal " +
                       "FROM evaluacion_exposicion ee " +
                       "INNER JOIN expediente exp ON ee.idExpediente = exp.idExpediente " +
                       "WHERE exp.idEstudiante = ?";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            sentencia.setInt(1, idEstudiante);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    EvaluacionExposicion evaluacion = new EvaluacionExposicion();
                    evaluacion.setIdEvaluacionExposicion(resultado.getInt("idEvaluacionExposicion"));
                    evaluacion.setComentarios(resultado.getString("comentarios"));

                    BigDecimal puntaje = resultado.getBigDecimal("puntajeFinal");
                    evaluacion.setPuntajeFinal(puntaje != null ? puntaje.doubleValue() : 0.0);

                    evaluaciones.add(evaluacion);
                }
            }
        }
        return evaluaciones;
    }

    public static Estudiante obtenerEstudiantePorIdUsuario(int idUsuario) throws SQLException {
        Estudiante estudiante = null;
        String consulta = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia, exp.idExpediente, exp.horasAcumuladas " +
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "LEFT JOIN expediente exp ON e.idUsuario = exp.idEstudiante " + 
                       "WHERE u.idUsuario = ?";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta)) {

            sentencia.setInt(1, idUsuario);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    String nombre = resultado.getString("nombre");
                    String apellidoPaterno = resultado.getString("apellidoPaterno");
                    String apellidoMaterno = resultado.getString("apellidoMaterno");
                    String email = resultado.getString("email");
                    String username = resultado.getString("username");

                    Date fechaNacimientoSql = resultado.getDate("fechaNacimiento");
                    String fechaNacimiento = (fechaNacimientoSql != null) ? fechaNacimientoSql.toString() : null;
                    String matricula = resultado.getString("matricula");
                    int idExperiencia = resultado.getInt("idExperiencia");
                    int idExpediente = resultado.getInt("idExpediente");
                    int horasAcumuladas = resultado.getInt("horasAcumuladas");

                    estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia,
                                                idUsuario, nombre, apellidoPaterno,
                                                apellidoMaterno, email, username,
                                                idExpediente, horasAcumuladas);
                }
            }
        }
        return estudiante;
    }
}