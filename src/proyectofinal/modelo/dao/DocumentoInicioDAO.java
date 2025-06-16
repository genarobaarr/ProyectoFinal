/*
 * Alejandro Martínez Ramírez
 * 14-06-2025
 */
package proyectofinal.modelo.dao;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.DocumentoInicio;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.ResultadoOperacion;
import proyectofinal.utilidades.Utilidad;

public class DocumentoInicioDAO {
    
    
    public static ArrayList<DocumentoInicio> obtenerDocumentoIniciosPorEstudiante(int idEstudiante) throws SQLException {
    ArrayList<DocumentoInicio> documentos = new ArrayList<>();
    Connection conexionBD = ConexionBD.abrirConexion();

    if (conexionBD != null) {
        String consulta = "SELECT di.idDocumentoInicio, di.fechaEntregado, di.nombreArchivo, " +
                          "di.extensionArchivo, di.archivo, di.idExpediente " +
                          "FROM documento_inicio di " +
                          "JOIN expediente e ON di.idExpediente = e.idExpediente " +
                          "WHERE e.idEstudiante = ?";

        try {
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idEstudiante);
            ResultSet resultados = sentencia.executeQuery();

            while (resultados.next()) {
                DocumentoInicio documento = new DocumentoInicio();
                documento.setIdDocumentoInicio(resultados.getInt("idDocumentoInicio"));
                documento.setFechaEntregado(resultados.getString("fechaEntregado"));
                documento.setNombreArchivo(resultados.getString("nombreArchivo"));
                documento.setExtensionArchivo(resultados.getString("extensionArchivo"));
                documento.setArchivo(resultados.getBytes("archivo"));
                documento.setIdExpediente(resultados.getInt("idExpediente"));

                documentos.add(documento);
            }

            resultados.close();
            sentencia.close();
            conexionBD.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return documentos;
}


    
    public static ResultadoOperacion registrarDocumentoInicio(DocumentoInicio documento) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            String consulta= "INSERT INTO documento_inicio (idDocumentoInicio, fechaEntregado, nombreArchivo, extensionArchivo, archivo, idExpediente) " +
                             "VALUES (?, ?, ?, ?, ?, ?);";
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, documento.getIdDocumentoInicio());
            sentencia.setString(2, documento.getFechaEntregado());
            sentencia.setString(3, documento.getNombreArchivo());
            sentencia.setString(4, documento.getExtensionArchivo());
            sentencia.setBytes(5, documento.getArchivo());
            sentencia.setInt(6, documento.getIdExpediente());
            
           int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Documento guardado correctamente.");
            } else {
                resultado.setError(true);
                resultado.setMensaje("Lo sentimos no se pudo guardar el documento. Intente más tarde.");
            }

            sentencia.close();
            conexionBD.close();
        } else {
            throw new SQLException("Error: Sin conexión a la base de datos.");
        }

        return resultado;
    }
    
    public static int contarDocumentosInicioPorEstudiante(int idEstudiante) throws SQLException {
        int cantidadDocumentos = 0;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            int idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(idEstudiante);

            if (idExpediente == 0) {
                System.out.println("DEBUG: No se encontró expediente para el estudiante con ID: " + idEstudiante + ". Cantidad de documentos: 0.");
                return 0;
            }
            
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            String consulta = "SELECT COUNT(*) AS totalDocumentos FROM documento_inicio WHERE idExpediente = ?";
            sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idExpediente);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                cantidadDocumentos = resultado.getInt("totalDocumentos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            resultado.close();
            sentencia.close();
            conexionBD.close(); 
        }
        return cantidadDocumentos;
    }
}