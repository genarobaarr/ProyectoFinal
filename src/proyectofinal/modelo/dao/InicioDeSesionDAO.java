/*
 * Genaro Alejandro Barradas Sánchez
 * 28-05-2025
 */
package proyectofinal.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyectofinal.modelo.ConexionBD;
import proyectofinal.modelo.pojo.Academico;
import proyectofinal.modelo.pojo.AcademicoEvaluador;
import proyectofinal.modelo.pojo.Coordinador;
import proyectofinal.modelo.pojo.Estudiante;
import proyectofinal.modelo.pojo.Usuario;

public class InicioDeSesionDAO {
    
    public static Usuario verificarCredenciales(String username, String password) throws SQLException {
        Connection conexionBD = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Usuario usuarioSesion = null;
        
        try {
            conexionBD = ConexionBD.abrirConexion();

            if(conexionBD != null) {
                String consulta = "SELECT idUsuario, nombre, apellidoPaterno, "
                        + "apellidoMaterno, email, username FROM usuario WHERE "
                        + "username = ? AND password = ?";

                sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, username);
                sentencia.setString(2, password);
                resultado = sentencia.executeQuery();
                
                if (resultado.next()) {
                    Usuario usuarioBase = convertirRegistroUsuario(resultado);
                    usuarioSesion = determinarRolEspecifico(usuarioBase, conexionBD);
                }     
            } else {
                throw new SQLException("Error: Sin conexión a la base de datos.");
            }
        } finally {
            sentencia.close();
            resultado.close();
            conexionBD.close();
        }
        return usuarioSesion;
    }
    
    private static Usuario convertirRegistroUsuario(ResultSet resultado) throws SQLException{
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultado.getInt("idUsuario"));
        usuario.setNombre(resultado.getString("nombre"));
        usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
        usuario.setApellidoMaterno(resultado.getString("apellidoMaterno") != null ? resultado.getString("apellidoMaterno") : "");
        usuario.setEmail(resultado.getString("email"));
        usuario.setUsername(resultado.getString("username"));
        
        return usuario;
    }
    
    private static Usuario determinarRolEspecifico(Usuario usuarioBase, Connection conexionBD) throws SQLException {
        
        String consultaEstudiante = "SELECT fechaNacimiento, matricula, idExperiencia FROM estudiante WHERE idUsuario = ?";
        try (PreparedStatement sentencia = conexionBD.prepareStatement(consultaEstudiante)) {
            sentencia.setInt(1, usuarioBase.getIdUsuario());
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    Estudiante estudiante = new Estudiante();
                    
                    copiarAtributosUsuario(usuarioBase, estudiante);
                    estudiante.setFechaNacimiento(resultado.getString("fechaNacimiento"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setIdExperienciaEducativa(resultado.getInt("idExperiencia"));
                    
                    return estudiante;
                }
            }
        }

        String consultaCoordinador = "SELECT telefono FROM coordinador WHERE idUsuario = ?";
        try (PreparedStatement sentencia = conexionBD.prepareStatement(consultaCoordinador)) {
            sentencia.setInt(1, usuarioBase.getIdUsuario());
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    Coordinador coordinador = new Coordinador();
                    
                    copiarAtributosUsuario(usuarioBase, coordinador);
                    coordinador.setTelefono(resultado.getString("telefono"));
                    
                    return coordinador;
                }
            }
        }

        String consultaAcademico = "SELECT noPersonal FROM academico WHERE idUsuario = ?";
        try (PreparedStatement sentencia = conexionBD.prepareStatement(consultaAcademico)) {
            sentencia.setInt(1, usuarioBase.getIdUsuario());
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    Academico academico = new Academico();
                    
                    copiarAtributosUsuario(usuarioBase, academico);
                    academico.setNoPersonal(resultado.getInt("noPersonal"));
                    
                    return academico;
                }
            }
        }
        
        String consultaAcademicoEvaluador = "SELECT noPersonal FROM academico_evaluador WHERE idUsuario = ?";
        try (PreparedStatement sentencia = conexionBD.prepareStatement(consultaAcademicoEvaluador)) {
            sentencia.setInt(1, usuarioBase.getIdUsuario());
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    AcademicoEvaluador academicoEvaluador = new AcademicoEvaluador();
                    
                    copiarAtributosUsuario(usuarioBase, academicoEvaluador);
                    academicoEvaluador.setNoPersonal(resultado.getInt("noPersonal"));
                    
                    return academicoEvaluador;
                }
            }
        }

        return null;
    }

    private static void copiarAtributosUsuario(Usuario origen, Usuario destino) {
        destino.setIdUsuario(origen.getIdUsuario());
        destino.setNombre(origen.getNombre());
        destino.setApellidoPaterno(origen.getApellidoPaterno());
        destino.setApellidoMaterno(origen.getApellidoMaterno());
        destino.setEmail(origen.getEmail());
        destino.setUsername(origen.getUsername());
    }
}