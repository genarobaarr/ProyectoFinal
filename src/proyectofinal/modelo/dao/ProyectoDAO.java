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
import proyectofinal.modelo.pojo.Proyecto;
import java.util.List;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class ProyectoDAO {
    
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
    
    public static ArrayList<Proyecto> obtenerProyectosPorNombre(String nombreProyecto) throws SQLException{
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "SELECT idProyecto, descripcion, fechaInicio, fechaFin, nombre, idResponsableDeProyecto, idCoordinador, objetivos FROM proyecto WHERE LOWER(nombre) LIKE LOWER(?)";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, "%" + nombreProyecto + "%");
            
            ResultSet resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                proyectos.add(convertirRegistroProyecto(resultado));
            }
            
            sentencia.close();
            resultado.close();
            conexionBD.close();
        } else {
            throw  new SQLException("Error de conexión con base de datos, intentalo más tarde");
        }
        return proyectos;
    }
    
    public static ResultadoOperacion registrarProyecto(Proyecto proyecto) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "INSERT INTO proyecto (descripcion, fechaInicio, fechaFin, nombre, idResponsableDeProyecto, idCoordinador, objetivos) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, proyecto.getDescripcion());
            sentencia.setString(2, proyecto.getFechaInicio());
            sentencia.setString(3, proyecto.getFechaFin());
            sentencia.setString(4, proyecto.getNombre());
            sentencia.setInt(5, proyecto.getIdResponsableDeProyecto());
            sentencia.setInt(6, proyecto.getIdCoordinador());
            sentencia.setString(7, proyecto.getObjetivos());
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Proyecto registrado correctamente");
            } else {
                resultado.setError(true);
                resultado.setMensaje("Lo sentimos :( por el momento no se puede "
                        + "registrar la información del proyecto, "
                        + "por favor inténtelo más tarde");
            }
            
            sentencia.close();
            conexionBD.close();
        } else {
            throw new SQLException("Error de conexión con base de datos, intentalo más tarde");
        }
        return resultado;
    }
    
    public static ResultadoOperacion modificarProyecto(Proyecto proyecto) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "UPDATE proyecto SET descripcion = ?, fechaInicio = ?, fechaFin = ?, nombre = ?, idResponsableDeProyecto = ?, idCoordinador = ?, objetivos = ? WHERE idProyecto = ?";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, proyecto.getDescripcion());
            sentencia.setString(2, proyecto.getFechaInicio());
            sentencia.setString(3, proyecto.getFechaFin());
            sentencia.setString(4, proyecto.getNombre());
            sentencia.setInt(5, proyecto.getIdResponsableDeProyecto());
            sentencia.setInt(6, proyecto.getIdCoordinador());
            sentencia.setString(7, proyecto.getObjetivos());
            sentencia.setInt(8, proyecto.getIdProyecto());
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Proyecto modificado correctamente");
            } else {
                resultado.setError(true);
                resultado.setMensaje("Lo sentimos :( por el momento no se puede "
                        + "modificar la información del proyecto, "
                        + "por favor inténtelo más tarde");
            }
            
            sentencia.close();
            conexionBD.close();
        } else {
            throw new SQLException("Error de conexión con base de datos, intentalo más tarde");
        }
        return resultado;
    }
    
    public static Proyecto obtenerProyectoPorEstudiante(int idEstudiante) throws SQLException{
        Proyecto proyecto = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null){
            String consulta = "SELECT p.idProyecto, p.nombre AS nombreProyecto, p.descripcion, "
                    + "p.objetivos, p.fechaInicio, p.fechaFin, p.idResponsableDeProyecto, "
                    + "p.idCoordinador, u.nombre AS nombreEstudiante, est.fechaNacimiento, "
                    + "est.matricula, est.idExperiencia, exp.idExpediente, exp.estatus "
                    + "AS estatusExpediente FROM proyecto p INNER JOIN expediente exp ON p.idProyecto = exp.idProyecto "
                    + "INNER JOIN estudiante est ON exp.idEstudiante = est.idUsuario INNER JOIN usuario u "
                    + "ON est.idUsuario = u.idUsuario WHERE exp.estatus = 'Activo' AND u.idUsuario = ?;";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idEstudiante);
             ResultSet resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                proyecto = convertirRegistroProyecto(resultado);
            }
            
            sentencia.close();
            resultado.close();
            conexionBD.close();
        } else {
            throw  new SQLException("Error de conexión con base de datos, intentalo más tarde");
        }
        return proyecto;
    }            
        
    
    private static Proyecto convertirRegistroProyecto(ResultSet resultado) throws SQLException {
        Proyecto proyecto = new Proyecto(
                resultado.getInt("idProyecto"), 
                resultado.getString("descripcion"), resultado.getString("fechaInicio"), 
                resultado.getString("fechaFin"), resultado.getString("nombreEstudiante"),
                resultado.getString("objetivos"), resultado.getInt("idResponsableDeProyecto"), 
                resultado.getInt("idCoordinador"));
        return proyecto;
    }
    
}