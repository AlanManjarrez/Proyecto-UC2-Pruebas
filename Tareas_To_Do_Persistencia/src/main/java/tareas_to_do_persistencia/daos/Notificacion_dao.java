/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tareas_to_do_persistencia.daos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Usuario;
import tareas_to_do_persistencia.entity_class.Notificacion;
import tareas_to_do_persistencia.entity_class.Tarea;

/**
 *
 * @author JESUS
 */
public class Notificacion_dao {
    
    private EntityManagerFactory entityManagerFactory;

    public Notificacion_dao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("conexionPU");
    }

    public Notificacion_dao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Notificacion> generarNotificacionesProximas(Usuario usuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Notificacion> notificaciones = new ArrayList<>();
        try {
            Calendar ahora = Calendar.getInstance();

            // Obtener las tareas próximas
            List<Tarea> tareasProximas = entityManager.createQuery(
                    "SELECT t FROM tarea t WHERE t.usuario = :usuario AND t.estado = :estado AND t.fecha BETWEEN :horaActual AND :dosHorasDespues",
                    Tarea.class)
                    .setParameter("usuario", usuario)
                    .setParameter("estado", Estado.PENDIENTES)
                    .setParameter("horaActual", ahora.getTime())
                    .setParameter("dosHorasDespues", obtenerDosHorasDespues(ahora).getTime())
                    .getResultList();

            
            if (!tareasProximas.isEmpty()) {
                for (Tarea tarea : tareasProximas) {
                    Notificacion notificacion = new Notificacion();
                    notificacion.setUsuario(usuario);

                    // Formatear la fecha de vencimiento de la tarea
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String formattedDate = sdf.format(tarea.getFecha().getTime());

                    notificacion.setMensaje("Tarea próxima: " + tarea.getNombre() + " vence a las " + formattedDate);
                    notificacion.setFechaCreacion(Calendar.getInstance());

                    // Agregar la notificación a la lista (sin persistirla)
                    notificaciones.add(notificacion);
                }
            } else {
                System.out.println("No hay tareas próximas para generar notificaciones.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();  // Asegurarse de cerrar el EntityManager
        }
        return notificaciones;    
    }

    public Calendar obtenerDosHorasDespues(Calendar ahora) {
        Calendar dosHorasAntes = (Calendar) ahora.clone();
        dosHorasAntes.add(Calendar.HOUR_OF_DAY, +2); // Restar dos horas
        return dosHorasAntes;
    }
    
}
