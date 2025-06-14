/*
 * Alejandro Martínez Ramírez
 * 14-06-2025
 */
package proyectofinal.modelo.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.DocumentoInicio;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class DocumentoInicioDAO {
    
    
    public static ResultadoOperacion registrarDocumentoInicio(DocumentoInicio documento) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            String consulta= "INSERT INTO documento_inicio (idDocumentoInicio, fechaEntregado, nombreArchivo, extensionArchivo, archivo, idExpediente) " +
                             "VALUES (?, ?, ?, ?, ?, ?);";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, documento.getIdDocumentoInicio());
            sentencia.setString(2, documento.getFechaEntregado());
            sentencia.setString(3, documento.getExtensionArchivo());
            sentencia.setBytes (4, documento.getArchivo());
                  
        }
            return resultado;    
    }
    
}
