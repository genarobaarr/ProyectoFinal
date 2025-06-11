/*
 * Omar Morales García
 * 10-06-2025
 */
package proyectofinal.utilidades;

import proyectofinal.modelo.pojo.AcademicoEvaluador;
import proyectofinal.modelo.pojo.Usuario;
import proyectofinal.modelo.pojo.Estudiante; 

public class SessionManager {
    private static Usuario loggedInUser; 

    public static void setLoggedInUser(AcademicoEvaluador user) {
        loggedInUser = user;
    }

    public static void setLoggedInUser(Estudiante user) {
        loggedInUser = user;
    }

    public static void setLoggedInUser(Usuario user) { 
        loggedInUser = user;
    }

    public static Usuario getLoggedInUser() {
        return loggedInUser;
    }

    public static void clearSession() {
        loggedInUser = null;
    }

    public static boolean isUserLoggedIn() {
        return loggedInUser != null;
    }
}