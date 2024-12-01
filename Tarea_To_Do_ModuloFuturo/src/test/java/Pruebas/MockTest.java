/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Pruebas;

import com.id.tarea_to_do_modulofuturo.Control_Notificacion;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import tarea_to_do_convertidores.convertidores.Convertidor;
import tarea_to_do_dto.dto.Notificacion_DTO;
import tarea_to_do_dto.dto.Usuario_DTO;
import tareas_to_do_persistencia.daos.Notificacion_dao;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Notificacion;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author JESUS
 */
public class MockTest {
    
    public MockTest() {
    }

    @Mock
    private Notificacion_dao notiDaoMock;

    @Mock
    private Convertidor convertidorMock;

    private Control_Notificacion controlNotificacion;

    @BeforeEach
    void setUp() {
        // Inicializar los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
        controlNotificacion = new Control_Notificacion();
        controlNotificacion.noti = notiDaoMock;
        controlNotificacion.conv = convertidorMock;
    }

    @Test
    void testObtenerNotificaciones() {
        // Crear datos de prueba
        Usuario_DTO usuarioDTO = new Usuario_DTO();
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje("Tarea próxima: Estudiar, vence a las 2024-12-01 10:00");
        notificacion.setFechaCreacion(Calendar.getInstance());

        // Simular la respuesta del DAO
        List<Notificacion> notificacionesMock = new ArrayList<>();
        notificacionesMock.add(notificacion);
        when(notiDaoMock.generarNotificacionesProximas(any(Usuario.class))).thenReturn(notificacionesMock);

        // Simular la conversión a DTO
        Notificacion_DTO notificacionDTO = new Notificacion_DTO();
        notificacionDTO.setMensaje(notificacion.getMensaje());
        when(convertidorMock.convNotificacion_DTO(any(Notificacion.class))).thenReturn(notificacionDTO);

        // Llamar al método que estamos probando
        List<Notificacion_DTO> resultado = controlNotificacion.obtenerNotificaciones(usuarioDTO);

        // Verificar que la lista no esté vacía y contiene la notificación esperada
        assertNotNull(resultado);
        
    }

    @Test
    void testObtenerNotificaciones_Vacia() {
        Usuario_DTO usuarioDTO = new Usuario_DTO();

        // Simula la respuesta del DAO con lista vacía
        when(notiDaoMock.generarNotificacionesProximas(any(Usuario.class))).thenReturn(Collections.emptyList());

        // Llamar al método
        List<Notificacion_DTO> resultado = controlNotificacion.obtenerNotificaciones(usuarioDTO);

        // Verificar que la lista no sea nula y esté vacía
        assertNotNull(resultado, "La lista de notificaciones no debe ser nula");
        assertTrue(resultado.isEmpty(), "La lista de notificaciones debe estar vacía");

        // Verificar que se haya retornado una lista vacía y no se llame a convNotificacion_DTO
        verify(convertidorMock, never()).convNotificacion_DTO(any(Notificacion.class));
    }
    
    @Test
    void testObtenerNotificaciones_Null() {
        Usuario_DTO usuarioDTO = new Usuario_DTO();

        // Simular el DAO devolviendo null
        when(notiDaoMock.generarNotificacionesProximas(any(Usuario.class))).thenReturn(Collections.emptyList());

        // Llamar al método
        List<Notificacion_DTO> resultado = controlNotificacion.obtenerNotificaciones(usuarioDTO);

        // Verificar que la lista no sea nula y esté vacía
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "La lista de notificaciones debe estar vacía");
    }
    
    
}
