/*
 * Alejandro Martínez Ramírez
 * 14-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.*;
import java.util.ArrayList;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.DocumentoFinal;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class DocumentoFinalDAO {

    public static ArrayList<DocumentoFinal> obtenerDocumentosFinalesPorEstudiante(int idEstudiante) throws SQLException {
        ArrayList<DocumentoFinal> documentos = new ArrayList<>();
        try {
            Connection conexionBD = ConexionBD.abrirConexion();

            if (conexionBD != null) {
                String consulta = "SELECT df.idDocumentoFinal, df.fechaEntregado, df.nombreArchivo, " +
                                  "df.extensionArchivo, df.archivo, df.idExpediente " +
                                  "FROM documento_final df " +
                                  "JOIN expediente e ON df.idExpediente = e.idExpediente " +
                                  "WHERE e.idEstudiante = ?";

                PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setInt(1, idEstudiante);
                ResultSet resultados = sentencia.executeQuery();

                while (resultados.next()) {
                    DocumentoFinal documento = new DocumentoFinal();
                    documento.setIdDocumentoFinal(resultados.getInt("idDocumentoFinal"));
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
            }
        } catch (SQLException e) {
            throw new SQLException("Error: Sin conexión a la base de datos.");
        }
        return documentos;
    }

    public static ResultadoOperacion registrarDocumentoFinal(DocumentoFinal documento) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            String consulta = "INSERT INTO documento_final (fechaEntregado, nombreArchivo, extensionArchivo, archivo, idExpediente) " +
                              "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, documento.getFechaEntregado());
            sentencia.setString(2, documento.getNombreArchivo());
            sentencia.setString(3, documento.getExtensionArchivo());
            sentencia.setBytes(4, documento.getArchivo());
            sentencia.setInt(5, documento.getIdExpediente());

            int filasAfectadas = sentencia.executeUpdate();

            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Documento final guardado correctamente.");
            } else {
                resultado.setError(true);
                resultado.setMensaje("No se pudo guardar el documento final. Intente más tarde.");
            }

            sentencia.close();
            conexionBD.close();
        } else {
            throw new SQLException("Error: Sin conexión a la base de datos.");
        }
        return resultado;
    }
    
    public static int contarDocumentosFinalPorEstudiante(int idEstudiante) throws SQLException {
        int cantidadDocumentos = 0;
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            int idExpediente = ExpedienteDAO.obtenerIdExpedientePorIdEstudiante(idEstudiante);

            if (idExpediente == 0) {
                return 0;
            }
            
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            String consulta = "SELECT COUNT(*) AS totalDocumentos FROM documento_final WHERE idExpediente = ?";
            sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, idExpediente);
            resultado = sentencia.executeQuery();

            if (resultado.next()) {
                cantidadDocumentos = resultado.getInt("totalDocumentos");
            }
            
            resultado.close();
            sentencia.close();
            conexionBD.close();
        } catch (SQLException e) {
            throw new SQLException("Error: Sin conexión a la base de datos.");
        }
        return cantidadDocumentos;
    }
}