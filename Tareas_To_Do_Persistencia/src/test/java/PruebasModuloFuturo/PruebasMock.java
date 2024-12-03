/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebasModuloFuturo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import tareas_to_do_persistencia.daos.Notificacion_dao;
import tareas_to_do_persistencia.daos.Tarea_dao;
import tareas_to_do_persistencia.daos.Usuario_dao;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Notificacion;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author JESUS
 */
public class PruebasMock {
    
    public PruebasMock() {
    }
    
    @Mock
    private EntityManagerFactory mockEntityManagerFactory;  

    @Mock
    private EntityManager mockEntityManager;  

    @Mock
    private EntityTransaction mockTransaction;  

    @InjectMocks
    private Notificacion_dao notificacionDao;  

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        
        
        when(mockEntityManagerFactory.createEntityManager()).thenReturn(mockEntityManager);

        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
    }
    
    @Test
    void testGenerarNotificacionesProximas() {
        // Crear usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("Jesús");

        // Crear tarea de prueba
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Entrega de proyecto");
        Calendar fechaLimite = Calendar.getInstance();
        tarea.setFecha(fechaLimite); 
        tarea.setEstado(Estado.PENDIENTES);
        tarea.setUsuario(usuario);

        // Lista de tareas que se retornará por la consulta
        List<Tarea> tareasProximas = List.of(tarea);

        // Crear un mock para la consulta
        TypedQuery<Tarea> mockQueryTareas = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(
                "SELECT t FROM tarea t WHERE t.usuario = :usuario AND t.estado = :estado AND t.fecha BETWEEN :dosHorasAntes AND :horaActual",
                Tarea.class)
        ).thenReturn(mockQueryTareas);

        when(mockQueryTareas.setParameter("usuario", usuario)).thenReturn(mockQueryTareas);
        when(mockQueryTareas.setParameter("estado", Estado.PENDIENTES)).thenReturn(mockQueryTareas);
        when(mockQueryTareas.setParameter(eq("horaActual"), any())).thenReturn(mockQueryTareas);
        when(mockQueryTareas.setParameter(eq("dosHorasAntes"), any())).thenReturn(mockQueryTareas);
        when(mockQueryTareas.getResultList()).thenReturn(tareasProximas);

        // Llamada al método de la clase Notificacion_dao
        List<Notificacion> notificaciones = notificacionDao.generarNotificacionesProximas(usuario);

        assertNotNull(notificaciones);
    }
    
    
    @Test
    void testNoHayTareasCercanas() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("Jesús");

        List<Tarea> tareasProximas = new ArrayList<>();

        TypedQuery<Tarea> mockQueryTareas = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(
                "SELECT t FROM tarea t WHERE t.usuario = :usuario AND t.estado = :estado AND t.fecha BETWEEN :dosHorasAntes AND :horaActual",
                Tarea.class)
        ).thenReturn(mockQueryTareas);

        when(mockQueryTareas.setParameter("usuario", usuario)).thenReturn(mockQueryTareas);
        when(mockQueryTareas.setParameter("estado", Estado.PENDIENTES)).thenReturn(mockQueryTareas);
        when(mockQueryTareas.setParameter(eq("horaActual"), any())).thenReturn(mockQueryTareas);
        when(mockQueryTareas.setParameter(eq("dosHorasAntes"), any())).thenReturn(mockQueryTareas);
        when(mockQueryTareas.getResultList()).thenReturn(tareasProximas);

        List<Notificacion> notificaciones = notificacionDao.generarNotificacionesProximas(usuario);

        assertNotNull(notificaciones);
        assertTrue(notificaciones.isEmpty(), "Se esperaba una lista vacía de notificaciones");

        // Verificar que no se haya realizado ninguna operación en la transacción ni persistido ninguna notificación
        verify(mockTransaction, times(0)).begin();
        verify(mockTransaction, times(0)).commit();
        verify(mockEntityManager, times(0)).persist(any(Notificacion.class));
    }
    
    
}
