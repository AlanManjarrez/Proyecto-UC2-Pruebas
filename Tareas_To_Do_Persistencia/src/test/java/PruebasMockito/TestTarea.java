/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebasMockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tareas_to_do_persistencia.daos.Tarea_dao;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author JESUS
 */
public class TestTarea {
    
    public TestTarea() {
    }
    
    private Tarea_dao tareaDao;

    @Mock
    private EntityManagerFactory mockEntityManagerFactory;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityTransaction mockTransaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockEntityManagerFactory.createEntityManager()).thenReturn(mockEntityManager);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

        tareaDao = new Tarea_dao(mockEntityManagerFactory);
    }
    
    @Test
    public void testCrearTareaConValoresCorrectos() {
        Tarea tarea = new Tarea();
        Usuario mockUsuario = new Usuario();
        mockUsuario.setId(1L);
        tarea.setUsuario(mockUsuario);

        when(mockEntityManager.find(Usuario.class, 1L)).thenReturn(mockUsuario);

        Tarea result = tareaDao.crearTarea(tarea);

        verify(mockEntityManager).persist(tarea);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockUsuario, result.getUsuario());
    }

    @Test
    public void testEditarTarea(){
         
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setDescripcion("Nueva descripción");
        tarea.setEstado(Estado.PENDIENTES);
        tarea.setNombre("Tarea editada");

        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setDescripcion("Descripción antigua");
        tareaExistente.setEstado(Estado.PENDIENTES);
        tareaExistente.setNombre("Tarea antigua");

        
        when(mockEntityManager.find(Tarea.class, 1L)).thenReturn(tareaExistente);

        
        Tarea resultado = tareaDao.editarTarea(tarea);

        verify(mockTransaction).begin();
        verify(mockEntityManager).merge(tareaExistente);
        verify(mockTransaction).commit();

        assertNotNull(resultado);
        assertEquals("Nueva descripción", resultado.getDescripcion());
        assertEquals(Estado.PENDIENTES, resultado.getEstado());
        assertEquals("Tarea editada", resultado.getNombre());
    }
    
    @Test
    public void testEliminarrTarea() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);

        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);

        when(mockEntityManager.find(Tarea.class, 1L)).thenReturn(tareaExistente);

        Boolean resultado = tareaDao.eliminarrTarea(tarea);

        verify(mockTransaction).begin();
        verify(mockEntityManager).remove(tareaExistente);
        verify(mockTransaction).commit();

        assertTrue(resultado);
    }
    
    @Test
    public void testEliminarrTareaNoEncontrada() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);

        when(mockEntityManager.find(Tarea.class, 1L)).thenReturn(null);

        Boolean resultado = tareaDao.eliminarrTarea(tarea);

        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
        verify(mockEntityManager, never()).remove(any(Tarea.class));
        assertFalse(resultado);
    }
    
    /*
    @Test
    public void testCambiarEstado() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);

        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(1L);
        tareaExistente.setEstado(Estado.PENDIENTES);

        when(mockEntityManager.find(Tarea.class, 1L)).thenReturn(tareaExistente);

        Tarea resultado = tareaDao.cambiarEstado(tarea, Estado.COMPLETADAS);

        verify(mockTransaction).begin();
        verify(mockEntityManager).merge(tareaExistente);
        verify(mockTransaction).commit();

        assertNotNull(resultado);
        assertEquals(Estado.COMPLETADAS, resultado.getEstado());
    }
    
    /*
    @Test
    public void testCambiarEstadoTareaNoEncontrada() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);

        Estado nuevoEstado = Estado.COMPLETADAS;

        when(mockEntityManager.find(Tarea.class, 1L)).thenReturn(null);

        Tarea resultado = tareaDao.cambiarEstado(tarea, nuevoEstado);

        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
        assertNull(resultado);
    }*/
}
