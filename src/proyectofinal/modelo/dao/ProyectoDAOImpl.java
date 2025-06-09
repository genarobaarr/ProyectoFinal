/*
 * Omar Morales García
 * 09-06-2025
 */

package proyectofinal.modelo.dao;

import proyectofinal.modelo.dao.ProyectoDAO;
import proyectofinal.modelo.pojo.Proyecto;
import proyectofinal.modelo.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

public class ProyectoDAOImpl implements ProyectoDAO {

    @Override
    public List<Proyecto> obtenerProyectosSinAsignar() {
        List<Proyecto> proyectos = new ArrayList<>();
        String query = "SELECT p.idProyecto, p.descripcion, p.fechaInicio, p.fechaFin, p.nombre, p.objetivos, p.idResponsableDeProyecto, p.idCoordinador " +
                       "FROM proyecto p " +
                       "LEFT JOIN expediente exp ON p.idProyecto = exp.idProyecto AND exp.estatus = 'Activo' " +
                       "WHERE exp.idProyecto IS NULL " +
                       "AND p.fechaFin >= CURDATE(); "; 

        try (Connection conn = ConexionBD.abrirConexion();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int idProyecto = rs.getInt("idProyecto");
                String descripcion = rs.getString("descripcion");
                Date fechaInicioSql = rs.getDate("fechaInicio");
                String fechaInicio = (fechaInicioSql != null) ? fechaInicioSql.toString() : null;
                Date fechaFinSql = rs.getDate("fechaFin");
                String fechaFin = (fechaFinSql != null) ? fechaFinSql.toString() : null; 
                String nombre = rs.getString("nombre");
                String objetivos = rs.getString("objetivos");
                int idResponsableDeProyecto = rs.getInt("idResponsableDeProyecto");
                int idCoordinador = rs.getInt("idCoordinador");

                Proyecto proyecto = new Proyecto(idProyecto, descripcion, fechaInicio, fechaFin, nombre, objetivos, idResponsableDeProyecto, idCoordinador);
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proyectos sin asignar: " + e.getMessage());
            throw new RuntimeException("Error al cargar proyectos desde la base de datos.", e);
        }
        return proyectos;
    }
}