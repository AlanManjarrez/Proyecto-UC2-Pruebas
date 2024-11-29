/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebaIntegracion;

import java.util.Calendar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
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
public class TestTareaInt {
    
    public TestTareaInt() {
    }
    
    private Tarea_dao tareaDao;
   
    
   @BeforeEach
    public void setup() {
        tareaDao = new Tarea_dao(); 
    }
    
    
    @Test
    public void testCrearTarea() {
        // Configurar los datos para crear una tarea
        Usuario usuario = new Usuario();
        usuario.setId(1L); // ID de usuario existente en tu base de datos

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setNombre("Tarea de prueba");
        nuevaTarea.setDescripcion("Descripción inicial de prueba");
        nuevaTarea.setEstado(Estado.PENDIENTES);
        nuevaTarea.setFecha(Calendar.getInstance());
        nuevaTarea.setUsuario(usuario);

        // Crear la tarea
        Tarea tareaCreada = tareaDao.crearTarea(nuevaTarea);

        // Verificar que la tarea fue creada correctamente
        assertNotNull(tareaCreada, "La tarea debe haberse creado correctamente");
        assertNotNull(tareaCreada.getId(), "La tarea debe tener un ID asignado");
        assertEquals("Tarea de prueba", tareaCreada.getNombre());


    }
    
   
    @Test
    public void testEditarTarea() {
        
        Tarea tareaCompartida= new Tarea();
        tareaCompartida.setId(21L);
        tareaCompartida.setNombre("Tarea modificada");
        tareaCompartida.setDescripcion("Descripción modificada");
        tareaCompartida.setEstado(Estado.COMPLETADAS);
        
        assertNotNull(tareaCompartida.getId(),"No debe ser nulo");

        // Editar la tarea
        Tarea tareaEditada = tareaDao.editarTarea(tareaCompartida);

        // Verificar que los cambios se hayan aplicado
        assertNotNull(tareaEditada);
        assertEquals("Tarea modificada", tareaEditada.getNombre());
        assertEquals("Descripción modificada", tareaEditada.getDescripcion());
        assertEquals(Estado.COMPLETADAS, tareaEditada.getEstado());
    }
    
    
    @Test
    public void testCambiarEstado(){
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(21L);

        // Cambiar el estado de la tarea
        Estado nuevoEstado = Estado.PENDIENTES;
        Tarea tareaActualizada = tareaDao.cambiarEstado(tareaExistente, nuevoEstado);

        // Verificar que el estado se ha cambiado correctamente
        assertNotNull(tareaActualizada, "La tarea debe haber sido actualizada");
        assertEquals(nuevoEstado, tareaActualizada.getEstado(), "El estado de la tarea debe ser el esperado");
    }
    
    
    @Test
    public void testEliminarTarea(){
        Tarea tareaExistente = new Tarea();
        tareaExistente.setId(72L);
        
        boolean resultado = tareaDao.eliminarrTarea(tareaExistente);
        
        assertTrue(resultado,"No se ha eliminado");
    }
    
    
}
