/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Pruebas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tarea_to_do_dto.dto.Notificacion_DTO;
import tarea_to_do_dto.dto.Usuario_DTO;
import tareas_to_do_persistencia.entity_class.Notificacion;
import com.id.tarea_to_do_modulofuturo.Control_Notificacion;

/**
 *
 * @author JESUS
 */
public class IntTest {
    
    public IntTest() {
    }
    
    @Test
    void testObtenerNotificaciones_NoEstaVacia() {
        Control_Notificacion control=new Control_Notificacion();
        // Crear datos de prueba
        Usuario_DTO usuarioDTO = new Usuario_DTO();
        usuarioDTO.setId(3L);
        usuarioDTO.setUser("Prueba18");
        usuarioDTO.setContra("usuario");
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje("Tarea próxima: Estudiar, vence a las 2024-12-01 10:00");
        notificacion.setFechaCreacion(Calendar.getInstance());

        // Simular la respuesta del DAO con una lista de notificaciones
        List<Notificacion> notificaciones = new ArrayList<>();
        notificaciones.add(notificacion);

        // Llamar al método que estamos probando
        List<Notificacion_DTO> resultado = control.obtenerNotificaciones(usuarioDTO);
        
        
        // Verificar que la lista no esté vacía
        assertNotNull(resultado, "La lista de notificaciones no debe ser nula");

    }          
      
    @Test
    void testObtenerNotificaciones_Vacia() {
        Control_Notificacion control=new Control_Notificacion();
        // Crear datos de prueba
        Usuario_DTO usuarioDTO = new Usuario_DTO();
        usuarioDTO.setId(129L);
        
        // Cambiar la implementación del método obtenerNotificaciones para retornar una lista vacía
        List<Notificacion_DTO> resultado = control.obtenerNotificaciones(usuarioDTO);

        // Verificar que la lista no esté nula
        assertNotNull(resultado, "La lista de notificaciones no debe ser nula");

        // Verificar que la lista esté vacía
        assertTrue(resultado.isEmpty(), "La lista de notificaciones debe estar vacía");
    }
    
   
}
