/*
 * Genaro Alejandro Barradas Sánchez
 * 09/06/2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.OrganizacionVinculada;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class OrganizacionVinculadaDAO {
    
    public static ArrayList<OrganizacionVinculada> obtenerOrganizacionesVinculadas() throws SQLException{
        ArrayList<OrganizacionVinculada> organizaciones = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "SELECT idOrganizacionVinculada, nombre, telefono, direccion, email FROM organizacion_vinculada";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                organizaciones.add(convertirRegistroOrganizacionVinculada(resultado));
            }
            
            sentencia.close();
            resultado.close();
            conexionBD.close();
        } else {
            throw  new SQLException("Error: Sin conexión a la base de datos.");
        }
        return organizaciones;
    }
    
    public static OrganizacionVinculada obtenerOrganizacionVinculadaPorId(int idOrganizacionVinculada) throws SQLException{
        OrganizacionVinculada organizacion = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "SELECT idOrganizacionVinculada, nombre, telefono, direccion, email FROM organizacion_vinculada WHERE idOrganizacionVinculada = ?";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idOrganizacionVinculada);
            
            ResultSet resultado = sentencia.executeQuery();
            
            while (resultado.next()) {
                organizacion = convertirRegistroOrganizacionVinculada(resultado);
            }
            
            sentencia.close();
            resultado.close();
            conexionBD.close();
        } else {
            throw  new SQLException("Error: Sin conexión a la base de datos.");
        }
        return organizacion;
    }
    
    public static boolean existeOrganizacionVinculada(String nombre) throws SQLException {
        boolean existe = false;
        String consulta = "SELECT COUNT(*) FROM organizacion_vinculada WHERE nombre = ?";
        
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, nombre);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                if (resultado.getInt(1) > 0) {
                    existe = true;
                }
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
        return existe;
    }
    
    public static ResultadoOperacion registrarOrganizacionVinculada(OrganizacionVinculada organizacion) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            String consulta = "INSERT INTO organizacion_vinculada (nombre, telefono, direccion, email) VALUES (?, ?, ?, ?)";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, organizacion.getNombre());
            sentencia.setString(2, organizacion.getTelefono());
            sentencia.setString(3, organizacion.getDireccion());
            sentencia.setString(4, organizacion.getEmail());
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Organización vinculada registrada correctamente");
            } else {
                resultado.setError(true);
                resultado.setMensaje("Lo sentimos :( por el momento no se puede "
                        + "registrar la información de la organización vinculada, "
                        + "por favor inténtelo más tarde");
            }
            
            sentencia.close();
            conexionBD.close();
        } else {
            throw new SQLException("Error: Sin conexión a la base de datos.");
        }
        return resultado;
    }
    
    private  static OrganizacionVinculada convertirRegistroOrganizacionVinculada(ResultSet resultado) throws SQLException {
        OrganizacionVinculada organizacion = new OrganizacionVinculada(
                resultado.getInt("idOrganizacionVinculada"), 
                resultado.getString("nombre"), resultado.getString("telefono"), 
                resultado.getString("direccion"), resultado.getString("email"));
        return organizacion;
    }
}