/*
 * Omar Morales García
 * 10-06-2025
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
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.ProyectoConEstudiante;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class ProyectoDAO {

    public static List<Proyecto> obtenerProyectosSinAsignar() throws SQLException{
        List<Proyecto> proyectos = new ArrayList<>();
        String consulta = "SELECT p.idProyecto, p.nombre, p.descripcion, p.objetivos, " +
                       "p.fechaInicio, p.fechaFin, p.idResponsableDeProyecto, p.idCoordinador " +
                       "FROM proyecto p " +
                       "LEFT JOIN expediente exp ON p.idProyecto = exp.idProyecto AND exp.estatus = 'Activo' " +
                       "WHERE exp.idProyecto IS NULL";

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setIdProyecto(resultado.getInt("idProyecto"));
                proyecto.setNombre(resultado.getString("nombre"));
                proyecto.setDescripcion(resultado.getString("descripcion"));
                proyecto.setObjetivos(resultado.getString("objetivos"));
                proyecto.setFechaInicio(resultado.getString("fechaInicio"));
                proyecto.setFechaFin(resultado.getString("fechaFin"));
                proyecto.setIdResponsableDeProyecto(resultado.getInt("idResponsableDeProyecto"));
                proyecto.setIdCoordinador(resultado.getInt("idCoordinador"));
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener proyectos sin asignar: " + e.getMessage());
        }
        return proyectos;
    }

    public static Proyecto obtenerProyectoPorId(int idProyecto) throws SQLException {
        Proyecto proyecto = null;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD != null) {
                String consulta = "SELECT p.idProyecto, p.nombre, p.descripcion, p.objetivos, p.fechaInicio, p.fechaFin, " +
                            "p.idCoordinador, p.IdResponsableDeProyecto, e.horasAcumuladas, ov.nombre AS nombreOrganizacion, " +
                            "rp.nombre AS nombreResponsable, rp.departamento AS departamentoResponsable, " +
                            "rp.puesto AS cargoResponsable " +
                            "FROM proyecto p " +
                            "INNER JOIN expediente e ON p.idProyecto = e.idProyecto " +
                            "LEFT JOIN responsable_de_proyecto rp ON p.idResponsableDeProyecto = rp.idResponsableDeProyecto " +
                            "LEFT JOIN organizacion_vinculada ov ON rp.idOrganizacionVinculada = ov.idOrganizacionVinculada " +
                            "WHERE p.idProyecto = ?;";
                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idProyecto);
                resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    proyecto = new Proyecto();
                    proyecto.setIdProyecto(resultado.getInt("idProyecto"));
                    proyecto.setNombre(resultado.getString("nombre"));
                    proyecto.setDescripcion(resultado.getString("descripcion"));
                    proyecto.setObjetivos(resultado.getString("objetivos"));
                    proyecto.setHorasTotales(resultado.getInt("horasAcumuladas"));
                    Date fechaInicioSql = resultado.getDate("fechaInicio");
                    proyecto.setFechaInicio((fechaInicioSql != null) ? fechaInicioSql.toString() : null);
                    Date fechaFinSql = resultado.getDate("fechaFin");
                    proyecto.setFechaFin((fechaFinSql != null) ? fechaFinSql.toString() : null);
                    proyecto.setIdCoordinador(resultado.getInt("idCoordinador"));
                    proyecto.setIdResponsableDeProyecto(resultado.getInt("idResponsableDeProyecto"));

                    proyecto.setNombreOrganizacion(resultado.getString("nombreOrganizacion"));
                    proyecto.setNombreResponsable(resultado.getString("nombreResponsable"));
                    proyecto.setDepartamentoResponsable(resultado.getString("departamentoResponsable"));
                    proyecto.setCargoResponsable(resultado.getString("cargoResponsable"));
                }
            } else {
                throw new SQLException("Error de conexión con base de datos, inténtalo más tarde");
            }
        } catch (SQLException e) {
            throw  new SQLException("Error al obtener proyecto por ID: " + e.getMessage());
        } finally {
            if (resultado != null) {
                resultado.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return proyecto;
    }
    
    public static ArrayList<Proyecto> obtenerProyectosPorNombre(String nombreProyecto) throws SQLException {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();

            if (conexionBD != null) {
                String consulta = "SELECT idProyecto, nombre, descripcion, objetivos, " +
                                  "fechaInicio, fechaFin, idResponsableDeProyecto, idCoordinador " +
                                  "FROM proyecto WHERE LOWER(nombre) LIKE LOWER(?)";

                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, "%" + nombreProyecto + "%");

                resultado = sentencia.executeQuery();

                while (resultado.next()) {
                    proyectos.add(convertirRegistroProyecto(resultado));
                }
            } else {
                throw new SQLException("Error de conexión con base de datos, intentalo más tarde");
            }
        } finally {
            if (resultado != null) {
                resultado.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return proyectos;
    }
    
    public static ResultadoOperacion registrarProyecto(Proyecto proyecto) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;

        try {
            conexionBD = ConexionBD.abrirConexion();

            if (conexionBD != null) {
                String consulta = "INSERT INTO proyecto (nombre, descripcion, objetivos, " +
                                  "fechaInicio, fechaFin, idResponsableDeProyecto, idCoordinador) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?)"; 

                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, proyecto.getNombre());
                sentencia.setString(2, proyecto.getDescripcion());
                sentencia.setString(3, proyecto.getObjetivos()); 

                sentencia.setString(4, proyecto.getFechaInicio());
                sentencia.setString(5, proyecto.getFechaFin());
                sentencia.setInt(6, proyecto.getIdResponsableDeProyecto());
                sentencia.setInt(7, proyecto.getIdCoordinador()); 

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
            } else {
                throw new SQLException("Error de conexión con base de datos, intentalo más tarde");
            }
        } finally {
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
        return resultado;
    }
    
    public static ResultadoOperacion modificarProyecto(Proyecto proyecto) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = null;
        PreparedStatement sentencia = null;

        try {
            conexionBD = ConexionBD.abrirConexion();

            if (conexionBD != null) {
                String consulta = "UPDATE proyecto SET nombre = ?, descripcion = ?, objetivos = ?, " +
                                  "fechaInicio = ?, fechaFin = ?, idResponsableDeProyecto = ?, idCoordinador = ? " +
                                  "WHERE idProyecto = ?";

                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, proyecto.getNombre());
                sentencia.setString(2, proyecto.getDescripcion());
                sentencia.setString(3, proyecto.getObjetivos()); 

                sentencia.setString(4, proyecto.getFechaInicio());
                sentencia.setString(5, proyecto.getFechaFin());
                sentencia.setInt(6, proyecto.getIdResponsableDeProyecto());
                sentencia.setInt(7, proyecto.getIdCoordinador()); 
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
            } else {
                throw new SQLException("Error de conexión con base de datos, intentalo más tarde");
            }
        } finally {
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexionBD != null) {
                conexionBD.close();
            }
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
                proyecto = convertirRegistroProyectoSimple(resultado);
            }
            
            sentencia.close();
            resultado.close();
            conexionBD.close();
        } else {
            throw  new SQLException("Error de conexión con base de datos, intentalo más tarde");
        }
        return proyecto;
    }            
        
    
   private static Proyecto convertirRegistroProyectoSimple(ResultSet resultado) throws SQLException {
        Proyecto proyecto = new Proyecto();
        proyecto.setIdProyecto(resultado.getInt("idProyecto"));
        proyecto.setDescripcion(resultado.getString("descripcion"));
        proyecto.setFechaFin(resultado.getString("fechaInicio")); 
        proyecto.setFechaFin(resultado.getString("fechaFin"));
        proyecto.setNombre(resultado.getString("nombreProyecto"));
        proyecto.setObjetivos(resultado.getString("objetivos")); 
        proyecto.setIdResponsableDeProyecto(resultado.getInt("idResponsableDeProyecto"));
        proyecto.setIdCoordinador(resultado.getInt("idCoordinador"));
        return proyecto;
    }
    
    private static Proyecto convertirRegistroProyecto(ResultSet resultado) throws SQLException {
        Proyecto proyecto = new Proyecto();
        proyecto.setIdProyecto(resultado.getInt("idProyecto"));
        proyecto.setNombre(resultado.getString("nombre"));
        proyecto.setDescripcion(resultado.getString("descripcion"));
        proyecto.setObjetivos(resultado.getString("objetivos")); 
    
        proyecto.setFechaInicio(resultado.getString("fechaInicio"));
        proyecto.setFechaFin(resultado.getString("fechaFin"));
        
        try {
            proyecto.setIdResponsableDeProyecto(resultado.getInt("idResponsableDeProyecto"));
        } catch (SQLException e) { 
            throw e;
        }
        try {
            proyecto.setIdCoordinador(resultado.getInt("idCoordinador"));
        } catch (SQLException e) { 
            throw e;
        }
        return proyecto;
    }
    
    public static List<ProyectoConEstudiante> obtenerProyectosConEstudiantesActivos() throws SQLException {
        List<ProyectoConEstudiante> lista = new ArrayList<>();
        
        String consulta = "SELECT " +
                       "p.idProyecto, p.nombre AS nombreProyecto, p.descripcion, p.objetivos, p.fechaInicio, p.fechaFin, " +
                       "p.idResponsableDeProyecto, p.idCoordinador, " +
                       "u.idUsuario, u.nombre AS nombreEstudiante, u.apellidoPaterno, u.apellidoMaterno, u.email, u.username, " +
                       "est.fechaNacimiento, est.matricula, est.idExperiencia, " + 
                       "exp.idExpediente, exp.estatus AS estatusExpediente " +
                       "FROM proyecto p " +
                       "INNER JOIN expediente exp ON p.idProyecto = exp.idProyecto " +
                       "INNER JOIN estudiante est ON exp.idEstudiante = est.idUsuario " +
                       "INNER JOIN usuario u ON est.idUsuario = u.idUsuario " +
                       "WHERE exp.estatus = 'Activo'"; 

        try (Connection conexionBD = ConexionBD.abrirConexion();
             PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setIdProyecto(resultado.getInt("idProyecto"));
                proyecto.setNombre(resultado.getString("nombreProyecto"));
                proyecto.setDescripcion(resultado.getString("descripcion"));
                proyecto.setObjetivos(resultado.getString("objetivos")); 

                Date fechaInicioSql = resultado.getDate("fechaInicio");
                proyecto.setFechaInicio((fechaInicioSql != null) ? fechaInicioSql.toString() : null);
                Date fechaFinSql = resultado.getDate("fechaFin");
                proyecto.setFechaFin((fechaFinSql != null) ? fechaFinSql.toString() : null);
                
                proyecto.setIdResponsableDeProyecto(resultado.getInt("idResponsableDeProyecto"));
                proyecto.setIdCoordinador(resultado.getInt("idCoordinador"));

                Date fechaNacimientoEstudianteSql = resultado.getDate("fechaNacimiento");
                String fechaNacimientoEstudiante = (fechaNacimientoEstudianteSql != null) ? fechaNacimientoEstudianteSql.toString() : null;

                Estudiante estudiante = new Estudiante(
                    fechaNacimientoEstudiante,
                    resultado.getString("matricula"), 
                    resultado.getInt("idExperiencia"), 
                    resultado.getInt("idUsuario"),
                    resultado.getString("nombreEstudiante"), 
                    resultado.getString("apellidoPaterno"), 
                    resultado.getString("apellidoMaterno"), 
                    resultado.getString("email"), 
                    resultado.getString("username") 
                );

                int idExpediente = resultado.getInt("idExpediente");

                ProyectoConEstudiante pce = new ProyectoConEstudiante(proyecto, estudiante, idExpediente);
                lista.add(pce);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al cargar proyectos con estudiantes activos.");
            
        } catch (Exception e) {
            throw new SQLException("Error inesperado al cargar proyectos con estudiantes activos.");
        }
        return lista;
    }
}