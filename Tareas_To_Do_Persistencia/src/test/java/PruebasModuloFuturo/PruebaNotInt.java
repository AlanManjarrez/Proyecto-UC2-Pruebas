/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebasModuloFuturo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tareas_to_do_persistencia.daos.Notificacion_dao;
import tareas_to_do_persistencia.daos.Tarea_dao;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Notificacion;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author JESUS
 */
public class PruebaNotInt {
    
    public PruebaNotInt() {
    }
    
    @Test
    public void testGenerarNotificacionesProximasConUsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setUser("JOSH");
        usuario.setContra("123");

        Calendar ahora = Calendar.getInstance();
        Calendar fechaLimiteTarea = (Calendar) ahora.clone();
        fechaLimiteTarea.add(Calendar.HOUR, +1); // 1 hora 

        // Crear una tarea
        Tarea tare = new Tarea();
        tare.setEstado(Estado.PENDIENTES);
        tare.setNombre("Entrega de proyecto");
        tare.setDescripcion("Descripción de la tarea");
        tare.setUsuario(usuario);
        tare.setFecha(fechaLimiteTarea);

        // Guardar la tarea en la base de datos (simulando la persistencia)
        Tarea_dao tareaDao = new Tarea_dao();
        tareaDao.crearTarea(tare);

        // Crear las notificaciones correspondientes
        Notificacion_dao notificacionDao = new Notificacion_dao();
        List<Notificacion> notificaciones = notificacionDao.generarNotificacionesProximas(usuario);

        // Verificar que la lista de notificaciones no sea nula ni vacía
        assertNotNull(notificaciones, "La lista de notificaciones no debe ser nula.");
        assertFalse(notificaciones.isEmpty(), "Se esperaban notificaciones.");

    }
    
    @Test
    public void testGenerarNotificacionesProximasConUsuarioSinTareasCercanas() {
        // Crear un usuario
        Usuario usuario = new Usuario();
        usuario.setId(121L);
        usuario.setUser("MARTA");
        usuario.setContra("456");

        // Crear un calendario para el "ahora"
        Calendar ahora = Calendar.getInstance();
        Calendar fechaLimiteTarea = (Calendar) ahora.clone();
        fechaLimiteTarea.add(Calendar.HOUR, -5); // 5 horas atrás, sin tareas cercanas

        // Crear una tarea, pero no la asignamos al usuario
        Tarea tare = new Tarea();
        tare.setEstado(Estado.PENDIENTES);
        tare.setNombre("Entrega de informe");
        tare.setDescripcion("Descripción de la tarea");
        tare.setFecha(fechaLimiteTarea);
        tare.setUsuario(usuario);

        // Guardar la tarea en la base de datos (simulando la persistencia)
        Tarea_dao tareaDao = new Tarea_dao();
        tareaDao.crearTarea(tare);

        // Crear las notificaciones correspondientes
        Notificacion_dao notificacionDao = new Notificacion_dao();
        List<Notificacion> notificaciones = notificacionDao.generarNotificacionesProximas(usuario);

        // Verificar que la lista de notificaciones esté vacía o nula
        assertNotNull(notificaciones, "La lista de notificaciones no debe ser nula.");
        assertTrue(notificaciones.isEmpty(), "No se esperaban notificaciones.");
    }
    
}
