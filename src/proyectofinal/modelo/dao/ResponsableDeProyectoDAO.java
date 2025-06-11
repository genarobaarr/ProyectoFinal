/*
 * Genaro Alejandro Barradas Sánchez
 * 09-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.ResponsableDeProyecto;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class ResponsableDeProyectoDAO {
    
    public static ArrayList<ResponsableDeProyecto> obtenerResponsablesDeProyecto() throws SQLException{
        ArrayList<ResponsableDeProyecto> responsables = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "SELECT idResponsableDeProyecto, nombre, telefono, email, departamento, puesto, idOrganizacionVinculada FROM responsable_de_proyecto";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                responsables.add(convertirRegistroResponsableDeProyecto(resultado));
            }
            
            sentencia.close();
            resultado.close();
            conexionBD.close();
        } else {
            throw  new SQLException("Error: Sin conexión a la base de datos.");
        }
        return responsables;
    }
    
    public static ArrayList<ResponsableDeProyecto> obtenerResponsablesDeProyectoPorOrganizacion(int idOrganizacionVinculada) throws SQLException{
        ArrayList<ResponsableDeProyecto> responsables = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "SELECT idResponsableDeProyecto, nombre, telefono, email, departamento, puesto, idOrganizacionVinculada FROM responsable_de_proyecto WHERE idOrganizacionVinculada = ?";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idOrganizacionVinculada);
            
            ResultSet resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                responsables.add(convertirRegistroResponsableDeProyecto(resultado));
            }
            
            sentencia.close();
            resultado.close();
            conexionBD.close();
        } else {
            throw  new SQLException("Error: Sin conexión a la base de datos.");
        }
        return responsables;
    }
    
    public static ResponsableDeProyecto obtenerResponsableDeProyectoPorId(int idResponsableDeProyecto) throws SQLException{
        ResponsableDeProyecto responsable = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "SELECT idResponsableDeProyecto, nombre, telefono, email, departamento, puesto, idOrganizacionVinculada FROM responsable_de_proyecto WHERE idResponsableDeProyecto = ?";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idResponsableDeProyecto);
            
            ResultSet resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                responsable = convertirRegistroResponsableDeProyecto(resultado);
            }
            
            sentencia.close();
            resultado.close();
            conexionBD.close();
        } else {
            throw  new SQLException("Error: Sin conexión a la base de datos.");
        }
        return responsable;
    }
    
    public static ResultadoOperacion registrarResponsableDeProyecto(ResponsableDeProyecto responsableDeProyecto) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "INSERT INTO responsable_de_proyecto (nombre, telefono, email, departamento, puesto, idOrganizacionVinculada) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, responsableDeProyecto.getNombre());
            sentencia.setString(2, responsableDeProyecto.getTelefono());
            sentencia.setString(3, responsableDeProyecto.getEmail());
            sentencia.setString(4, responsableDeProyecto.getDepartamento());
            sentencia.setString(5, responsableDeProyecto.getPuesto());
            sentencia.setInt(6, responsableDeProyecto.getIdOrganizacionVinculada());
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Responsable de proyecto registrado correctamente");
            } else {
                resultado.setError(true);
                resultado.setMensaje("Lo sentimos :( por el momento no se puede "
                        + "registrar la información del responsable de proyecto, "
                        + "por favor inténtelo más tarde");
            }
            
            sentencia.close();
            conexionBD.close();
        } else {
            throw new SQLException("Error: Sin conexión a la base de datos.");
        }
        return resultado;
    }
    
    private  static ResponsableDeProyecto convertirRegistroResponsableDeProyecto(ResultSet resultado) throws SQLException {
        ResponsableDeProyecto responsableDeProyecto = new ResponsableDeProyecto(
                resultado.getInt("idResponsableDeProyecto"), 
                resultado.getString("email"), resultado.getString("nombre"), 
                resultado.getString("telefono"), resultado.getInt("idOrganizacionVinculada"),
                resultado.getString("departamento"), resultado.getString("puesto"));
        return responsableDeProyecto;
    }
}
