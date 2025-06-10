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


public class EstudianteDAO {
    public List<Estudiante> obtenerEstudiantesSinProyectoAsignado() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String query = "SELECT u.idUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "e.fechaNacimiento, e.matricula, e.idExperiencia " + 
                       "FROM usuario u " +
                       "INNER JOIN estudiante e ON u.idUsuario = e.idUsuario " +
                       "LEFT JOIN expediente exp ON e.idUsuario = exp.idEstudiante AND exp.estatus = 'Activo' " +
                       "WHERE exp.idEstudiante is NULL; ";
        try (Connection conn = ConexionBD.abrirConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellidoPaterno = rs.getString("apellidoPaterno");
                String apellidoMaterno = rs.getString("apellidoMaterno");
                String email = rs.getString("email");                     
                String username = rs.getString("username");              

                Date fechaNacimientoSql = rs.getDate("fechaNacimiento");
                String fechaNacimiento = (fechaNacimientoSql != null) ? fechaNacimientoSql.toString() : null;
                String matricula = rs.getString("matricula");
                int idExperiencia = rs.getInt("idExperiencia");

                Estudiante estudiante = new Estudiante(fechaNacimiento, matricula, idExperiencia, idUsuario, nombre, apellidoPaterno, apellidoMaterno, email, username);
                estudiantes.add(estudiante);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estudiantes sin proyecto: " + e.getMessage());
            e.printStackTrace(); 
            throw new RuntimeException("Error al cargar estudiantes desde la base de datos.", e);
        }
        return estudiantes;
    }

    public void crearExpedienteEstudianteProyecto(int idEstudiante, int idProyecto, int idPeriodo) {
        String query = "INSERT INTO expediente (estatus, horasAcumuladas, idProyecto, idPeriodo, idEstudiante) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "Activo");
            pstmt.setInt(2, 0);
            pstmt.setInt(3, idProyecto);
            pstmt.setInt(4, idPeriodo);
            pstmt.setInt(5, idEstudiante);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo crear el expediente, ninguna fila afectada.");
            }
            System.out.println("DEBUG: Expediente creado para Estudiante " + idEstudiante + " y Proyecto " + idProyecto + " en Periodo " + idPeriodo);

        } catch (SQLException e) {
            System.err.println("Error al crear expediente: " + e.getMessage());
            e.printStackTrace(); 
            throw new RuntimeException("Error al registrar la asignación del proyecto en la base de datos.", e);
        }
    }
}