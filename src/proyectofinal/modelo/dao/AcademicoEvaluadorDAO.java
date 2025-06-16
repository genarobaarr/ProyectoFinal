/*
 * Genaro Alejandro Barradas Sánchez
 * 16-06-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.AcademicoEvaluador;
import proyectofinal.modelo.pojo.ResultadoOperacion;

public class AcademicoEvaluadorDAO {
    
    public static ResultadoOperacion registrarAcademicoEvaluador(AcademicoEvaluador academicoEvaluador) throws SQLException {
        ResultadoOperacion resultado = new ResultadoOperacion();
        String consulta = "INSERT INTO academico_evaluador (idUsuario, noPersonal) VALUES (?, ?)";
        
        Connection conexionBD = null;
        PreparedStatement sentencia = null;

        try {
            conexionBD = ConexionBD.abrirConexion();
            if (conexionBD == null) {
                throw new SQLException("No hay conexión con la base de datos.");
            }

            sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setInt(1, academicoEvaluador.getIdUsuario());
            sentencia.setInt(2, academicoEvaluador.getNoPersonal());
            
            int filasAfectadas = sentencia.executeUpdate();
            
            if (filasAfectadas == 1) {
                resultado.setError(false);
                resultado.setMensaje("Académico evaluador registrado correctamente");
            } else {
                resultado.setError(true);
                resultado.setMensaje("Lo sentimos :( por el momento no se puede "
                        + "registrar la información del académico evaluador, "
                        + "por favor inténtelo más tarde");
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
}