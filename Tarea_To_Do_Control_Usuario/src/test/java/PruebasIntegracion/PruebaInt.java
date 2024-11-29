/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebasIntegracion;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tarea_to_do_control_usuario.negocio.Control_Usuario;
import tarea_to_do_dto.dto.Estado_DTO;
import tarea_to_do_dto.dto.Tarea_DTO;
import tarea_to_do_dto.dto.Usuario_DTO;

/**
 *
 * @author JESUS
 */
public class PruebaInt {
    
    public PruebaInt() {
    }
    
    private Control_Usuario controlUsuario;

    @BeforeEach
    void setUp() {
        controlUsuario = new Control_Usuario();
    }

    /*
    @Test
    void testCrearUsuario() {
        Usuario_DTO nuevoUsuario = new Usuario_DTO();
        nuevoUsuario.setUser("usuario_prueba8");
        nuevoUsuario.setContra("123456");

        Usuario_DTO usuarioCreado = controlUsuario.crearUsuario(nuevoUsuario);
        assertNotNull(usuarioCreado);
        assertEquals("usuario_prueba8", usuarioCreado.getUser());
    }*/

    
    @Test
    void testIniciarSesion() {
        Usuario_DTO usuario = new Usuario_DTO();
        usuario.setUser("JOSH");
        usuario.setContra("123"); // Contraseña correcta

        Usuario_DTO usuarioLogueado = controlUsuario.iniciarSesion(usuario);
        assertNotNull(usuarioLogueado);
        assertEquals("JOSH", usuarioLogueado.getUser());
    }

    
    @Test
    void testListaTareaUsuario() {
        Usuario_DTO usuario = new Usuario_DTO();
        usuario.setId(2L); // ID de un usuario existente

        List<Tarea_DTO> tareas = controlUsuario.listaTareaUsuario(usuario);
        assertNotNull(tareas);
        assertFalse(tareas.isEmpty());
    }
    
    
    @Test
    void testListaTareaEstado() {
        Usuario_DTO usuario = new Usuario_DTO();
        usuario.setId(2L); // ID de un usuario existente
        

        List<Tarea_DTO> tareas = controlUsuario.listaTareaEstado(usuario, Estado_DTO.COMPLETADAS);
        assertNotNull(tareas);
        assertFalse(tareas.isEmpty());
    }
}
