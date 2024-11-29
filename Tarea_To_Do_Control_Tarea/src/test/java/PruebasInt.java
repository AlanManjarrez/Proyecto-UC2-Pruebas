/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.util.Calendar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tarea_to_do_control_tarea.negocio.Control_Tarea;
import tarea_to_do_dto.dto.Estado_DTO;
import tarea_to_do_dto.dto.Tarea_DTO;
import tarea_to_do_dto.dto.Usuario_DTO;

/**
 *
 * @author joarv
 */
public class PruebasInt {
    
    public PruebasInt() {
    }
    
     private Control_Tarea controlTarea;

    @BeforeEach
    void setUp() {
        controlTarea = new Control_Tarea();
    }
    
    /*
    @Test
    void testCrearTarea() {
        Tarea_DTO nuevaTarea = new Tarea_DTO();
        nuevaTarea.setNombre("Tarea 1");
        nuevaTarea.setDescripcion("Descripción de la tarea 1");
        nuevaTarea.setEstado(Estado_DTO.PENDIENTES);
        nuevaTarea.setFecha(Calendar.getInstance());
        Usuario_DTO usuario = new Usuario_DTO();
        usuario.setId(6L); 
        nuevaTarea.setUsuario(usuario);

        Tarea_DTO tareaCreada = controlTarea.crearTarea(nuevaTarea);
        assertNotNull(tareaCreada);
        assertEquals("Tarea 1", tareaCreada.getNombre());

    }

    
    @Test
    void testEditarTarea() {
        Tarea_DTO tareaExistente = new Tarea_DTO();
        tareaExistente.setId(17L); 
        tareaExistente.setNombre("Tarea Modificada");
        tareaExistente.setEstado(Estado_DTO.COMPLETADAS);
        tareaExistente.setFecha(Calendar.getInstance());
        tareaExistente.setDescripcion("Nueva descripción");

        Tarea_DTO tareaEditada = controlTarea.editarTarea(tareaExistente);
        assertNotNull(tareaEditada);
        assertEquals("Tarea Modificada", tareaEditada.getNombre());
    }

    @Test
    void testEliminarTarea() {
        Tarea_DTO tareaEliminar = new Tarea_DTO();
        tareaEliminar.setId(4L); 
        tareaEliminar.setDescripcion("SA");
        tareaEliminar.setEstado(Estado_DTO.COMPLETADAS);
        tareaEliminar.setNombre("assd");
        tareaEliminar.setFecha(Calendar.getInstance());

        boolean eliminada = controlTarea.eliminarTarea(tareaEliminar);
        assertTrue(eliminada);
    }

    @Test
    void testCambiarEstado() {
        Tarea_DTO tarea = new Tarea_DTO();
        tarea.setId(18L); 
        tarea.setDescripcion("dsf");
        tarea.setNombre("prueba");
        tarea.setEstado(Estado_DTO.PENDIENTES);
        tarea.setFecha(Calendar.getInstance());
        Estado_DTO nuevoEstado = Estado_DTO.COMPLETADAS;
        

        Tarea_DTO tareaActualizada = controlTarea.cambiarEstado(tarea, nuevoEstado);
        assertNotNull(tareaActualizada);
    }
*/
}
