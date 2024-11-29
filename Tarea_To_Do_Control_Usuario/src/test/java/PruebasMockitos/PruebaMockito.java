/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebasMockitos;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static net.bytebuddy.matcher.ElementMatchers.any;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import tarea_to_do_control_usuario.negocio.Control_Usuario;
import tarea_to_do_convertidores.convertidores.Convertidor;
import tarea_to_do_dto.dto.Estado_DTO;
import tarea_to_do_dto.dto.Tarea_DTO;
import tarea_to_do_dto.dto.Usuario_DTO;
import tareas_to_do_persistencia.daos.Usuario_dao;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author JESUS
 */
public class PruebaMockito {
    
    public PruebaMockito() {
    }
    
    private Control_Usuario controlUsuario;

    @Mock
    private Usuario_dao usuarioDaoMock;

    @Mock
    private Convertidor convertidorMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controlUsuario = new Control_Usuario();
        controlUsuario.usu = usuarioDaoMock;
        controlUsuario.conv = convertidorMock;
    }
    
    @Test
    void testCrearUsuario() {
        Usuario_DTO inputUsuarioDTO = new Usuario_DTO();
        Usuario_DTO expectedUsuarioDTO = new Usuario_DTO();

        // Crear una instancia real de Usuario para la simulación
        Usuario usuarioSimulado = new Usuario();  // Instancia simulada de la clase Usuario

        // Configurar el comportamiento de los mocks
        when(convertidorMock.convUsuario(inputUsuarioDTO)).thenReturn(usuarioSimulado); // Simula la conversión de Usuario_DTO a Usuario
        when(usuarioDaoMock.crearUsuario(usuarioSimulado)).thenReturn(usuarioSimulado); // Simula la creación del usuario en el DAO
        when(convertidorMock.convUsuarioDTO(usuarioSimulado)).thenReturn(expectedUsuarioDTO); // Simula la conversión de Usuario a Usuario_DTO

        // Ejecutar el método a probar
        Usuario_DTO result = controlUsuario.crearUsuario(inputUsuarioDTO);

        // Verificar resultados
        assertEquals(expectedUsuarioDTO, result);
        verify(convertidorMock).convUsuario(inputUsuarioDTO); // Verifica que se llamó a convUsuario
        verify(usuarioDaoMock).crearUsuario(usuarioSimulado); // Verifica que se llamó a crearUsuario
        verify(convertidorMock).convUsuarioDTO(usuarioSimulado);
    }
    
    @Test
    void testIniciarSesion() {
        Usuario_DTO inputUsuarioDTO = new Usuario_DTO();
        Usuario_DTO expectedUsuarioDTO = new Usuario_DTO();

        // Crear una instancia real de Usuario para la simulación
        Usuario usuarioSimulado = new Usuario();  // Instancia simulada de la clase Usuario

        // Configurar el comportamiento de los mocks
        when(convertidorMock.convUsuario(inputUsuarioDTO)).thenReturn(usuarioSimulado); // Simula la conversión de Usuario_DTO a Usuario
        when(usuarioDaoMock.IniciarSesión(usuarioSimulado)).thenReturn(usuarioSimulado); // Simula el inicio de sesión en el DAO
        when(convertidorMock.convUsuarioDTO(usuarioSimulado)).thenReturn(expectedUsuarioDTO); // Simula la conversión de Usuario a Usuario_DTO

        // Ejecutar el método a probar
        Usuario_DTO result = controlUsuario.iniciarSesion(inputUsuarioDTO);

        // Verificar resultados
        assertEquals(expectedUsuarioDTO, result);
        verify(convertidorMock).convUsuario(inputUsuarioDTO); // Verifica que se llamó a convUsuario
        verify(usuarioDaoMock).IniciarSesión(usuarioSimulado); // Verifica que se llamó a IniciarSesión
        verify(convertidorMock).convUsuarioDTO(usuarioSimulado); // Verifica que se llamó a convUsuarioDTO
    }
    
    
    @Test
    void testListaTareaUsuario() {
         Usuario_DTO inputUsuarioDTO = new Usuario_DTO();
        Tarea_DTO expectedTareaDTO = new Tarea_DTO();
        List<Tarea> listaTareasSimulada = new ArrayList<>();
        listaTareasSimulada.add(new Tarea()); // Simula una tarea en la lista

        // Configurar el comportamiento de los mocks
        when(convertidorMock.convUsuario(inputUsuarioDTO)).thenReturn(new Usuario()); // Simula la conversión de Usuario_DTO a Usuario
        when(usuarioDaoMock.consultarLista(any(Usuario.class))).thenReturn(listaTareasSimulada); // Simula la consulta de tareas en el DAO
        when(convertidorMock.convTareaDTO(any(Tarea.class))).thenReturn(expectedTareaDTO); // Simula la conversión de Tarea a Tarea_DTO

        // Ejecutar el método a probar
        List<Tarea_DTO> result = controlUsuario.listaTareaUsuario(inputUsuarioDTO);

        // Verificar resultados
        assertNotNull(result); // Verifica que el resultado no sea null
        assertEquals(1, result.size()); // Verifica que haya una tarea en el resultado
        verify(convertidorMock).convUsuario(inputUsuarioDTO); // Verifica que se llamó a convUsuario
        verify(usuarioDaoMock).consultarLista(any(Usuario.class)); // Verifica que se llamó a consultarLista
        verify(convertidorMock).convTareaDTO(any(Tarea.class)); // Verifica que se llamó a convTareaDTO
    }
    
    @Test
    void testListaTareaEstado() {
        // Crear instancias de los objetos
        Usuario_DTO inputUsuarioDTO = new Usuario_DTO();
        Estado_DTO inputEstadoDTO = Estado_DTO.COMPLETADAS; // Enum con un valor simulado
        Estado expectedEstado = Estado.COMPLETADAS; // Valor esperado del enum de Estado
        Tarea_DTO expectedTareaDTO = new Tarea_DTO();
        List<Tarea> listaTareasSimulada = new ArrayList<>();
        listaTareasSimulada.add(new Tarea()); // Simula una tarea en la lista

        // Configurar el comportamiento de los mocks
        when(convertidorMock.convUsuario(inputUsuarioDTO)).thenReturn(new Usuario()); // Simula la conversión de Usuario_DTO a Usuario
        when(convertidorMock.convertiEstado(inputEstadoDTO)).thenReturn(expectedEstado); // Simula la conversión de Estado_DTO a Estado
        when(usuarioDaoMock.consultarListaEstadoCompletado(any(Usuario.class), eq(expectedEstado)))
            .thenReturn(listaTareasSimulada); // Simula la consulta de tareas en el DAO por estado
        when(convertidorMock.convTareaDTO(any(Tarea.class))).thenReturn(expectedTareaDTO); // Simula la conversión de Tarea a Tarea_DTO

        // Ejecutar el método a probar
        List<Tarea_DTO> result = controlUsuario.listaTareaEstado(inputUsuarioDTO, inputEstadoDTO);

        // Verificar resultados
        assertNotNull(result);
        assertEquals(1, result.size()); // Verifica que hay una tarea en el resultado
        verify(convertidorMock).convUsuario(inputUsuarioDTO); // Verifica que se llamó a convUsuario
        verify(convertidorMock).convertiEstado(inputEstadoDTO); // Verifica que se llamó a convertiEstado
        verify(usuarioDaoMock).consultarListaEstadoCompletado(any(Usuario.class), eq(expectedEstado)); // Verifica que se llamó a consultarListaEstadoCompletado
        verify(convertidorMock).convTareaDTO(any(Tarea.class)); // Verifica que se llamó a convTareaDTO
    }
    
}
