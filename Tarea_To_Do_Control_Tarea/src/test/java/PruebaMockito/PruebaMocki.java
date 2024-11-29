/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebaMockito;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import tarea_to_do_control_tarea.negocio.Control_Tarea;
import tarea_to_do_convertidores.convertidores.Convertidor;
import tarea_to_do_dto.dto.Estado_DTO;
import tarea_to_do_dto.dto.Tarea_DTO;
import tareas_to_do_persistencia.daos.Tarea_dao;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Tarea;

/**
 *
 * @author JESUS
 */
public class PruebaMocki {
    
    public PruebaMocki() {
    }
    
    @Mock
    private Tarea_dao tareaDaoMock;

    @Mock
    private Convertidor convertidorMock;

    private Control_Tarea controlTarea;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controlTarea = new Control_Tarea();
        controlTarea.tarea = tareaDaoMock;
        controlTarea.conv = convertidorMock;
    }
    
    @Test
    public void testCrearTarea() {
        // Configurar los mocks
        Tarea_DTO tareaDTO = new Tarea_DTO();
        Tarea tareaEntity = new Tarea();  // Suponiendo que Tarea es la entidad correspondiente
        when(convertidorMock.convTarea(tareaDTO)).thenReturn(tareaEntity);
        when(tareaDaoMock.crearTarea(tareaEntity)).thenReturn(tareaEntity);
        when(convertidorMock.convTareaDTO(tareaEntity)).thenReturn(tareaDTO);

        // Llamar al método
        Tarea_DTO result = controlTarea.crearTarea(tareaDTO);

        // Verificar resultados
        assertNotNull(result);
        verify(convertidorMock).convTarea(tareaDTO);
        verify(tareaDaoMock).crearTarea(tareaEntity);
        verify(convertidorMock).convTareaDTO(tareaEntity);
    }
    
    @Test
    public void testEditarTarea() {
        // Configurar los mocks
        Tarea_DTO tareaDTO = new Tarea_DTO();
        Tarea tareaEntity = new Tarea();
        when(convertidorMock.convTarea(tareaDTO)).thenReturn(tareaEntity);
        when(tareaDaoMock.editarTarea(tareaEntity)).thenReturn(tareaEntity);
        when(convertidorMock.convTareaDTO(tareaEntity)).thenReturn(tareaDTO);

        // Llamar al método
        Tarea_DTO result = controlTarea.editarTarea(tareaDTO);

        // Verificar resultados
        assertNotNull(result);
        verify(convertidorMock).convTarea(tareaDTO);
        verify(tareaDaoMock).editarTarea(tareaEntity);
        verify(convertidorMock).convTareaDTO(tareaEntity);
    }
    
    @Test
    public void testEliminarTarea() {
        // Configurar los mocks
        Tarea_DTO tareaDTO = new Tarea_DTO();
        Tarea tareaEntity = new Tarea();
        when(convertidorMock.convTarea(tareaDTO)).thenReturn(tareaEntity);
        when(tareaDaoMock.eliminarrTarea(tareaEntity)).thenReturn(true);

        // Llamar al método
        Boolean result = controlTarea.eliminarTarea(tareaDTO);

        // Verificar resultados
        assertTrue(result);
        verify(convertidorMock).convTarea(tareaDTO);
        verify(tareaDaoMock).eliminarrTarea(tareaEntity);
    }
    
    @Test
    public void testCambiarEstado() {
        // Configurar los mocks
        Tarea_DTO tareaDTO = new Tarea_DTO();
        Tarea tareaEntity = new Tarea();
        Estado_DTO estadoDTO = Estado_DTO.PENDIENTES;  // Enum en el DTO
        Estado estado = Estado.PENDIENTES;  // Enum en la persistencia

        // Simulamos la conversión de Estado_DTO a Estado y viceversa
        when(convertidorMock.convertiEstado(estadoDTO)).thenReturn(estado);
        when(convertidorMock.convTarea(tareaDTO)).thenReturn(tareaEntity);
        when(tareaDaoMock.cambiarEstado(tareaEntity, estado)).thenReturn(tareaEntity);
        when(convertidorMock.convTareaDTO(tareaEntity)).thenReturn(tareaDTO);

        // Llamar al método
        Tarea_DTO result = controlTarea.cambiarEstado(tareaDTO, estadoDTO);

        // Verificar resultados
        assertNotNull(result);
        verify(convertidorMock).convertiEstado(estadoDTO);  // Verificamos la conversión del DTO al estado
        verify(convertidorMock).convTarea(tareaDTO);
        verify(tareaDaoMock).cambiarEstado(tareaEntity, estado);
        verify(convertidorMock).convTareaDTO(tareaEntity);
    }
    
}
