/*
 * Omar Morales García
 * 09-06-2025
 */

package proyectofinal.modelo.dao;

import proyectofinal.modelo.pojo.Proyecto;
import java.util.List;

public interface ProyectoDAO {
    List<Proyecto> obtenerProyectosSinAsignar();
}