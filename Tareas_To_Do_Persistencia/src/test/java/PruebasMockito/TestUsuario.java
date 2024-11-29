/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PruebasMockito;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import tareas_to_do_persistencia.daos.Usuario_dao;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author uirtis
 */
public class TestUsuario {

    public TestUsuario() {
    }

    private Usuario_dao usuarioDAO;

    @Mock
    private EntityManagerFactory mockEntityManagerFactory;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityTransaction mockEntityTransaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockEntityManagerFactory.createEntityManager()).thenReturn(mockEntityManager);
        when(mockEntityManager.getTransaction()).thenReturn(mockEntityTransaction);

        usuarioDAO = new Usuario_dao(mockEntityManagerFactory);

    }

    @Test
    public void testRegistrarUsuarioCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);

        when(mockEntityManager.createQuery(anyString(), eq(Usuario.class)))
                .thenThrow(NoResultException.class);

        Usuario usuarioResultado = usuarioDAO.crearUsuario(usuario);

        verify(mockEntityTransaction).begin();
        verify(mockEntityManager).persist(usuario);
        verify(mockEntityTransaction).commit();

        Assertions.assertNotNull(usuarioResultado);
        Assertions.assertEquals(usuario.getId(), usuarioResultado.getId());
    }
    
    @Test
    public void testCrearUsuarioConContrasenaVacia() {
        Usuario usuario = new Usuario();
        usuario.setUser("Juan Creado");
        usuario.setContra("");  // Contraseña vacía

        usuarioDAO.crearUsuario(usuario);

        // Verificamos que no se ha persistido el usuario
        verify(mockEntityManager, never()).persist(usuario);
    }
    
    @Test
    public void testCrearUsuarioConDatosEnBlanco() {
        Usuario usuario = new Usuario();
        usuario.setUser("");  // Nombre de usuario en blanco
        usuario.setContra("");  // Contraseña en blanco

        usuarioDAO.crearUsuario(usuario);

        // Verificamos que no se ha persistido el usuario
        verify(mockEntityManager, never()).persist(usuario);
    }
    
    @Test
    public void testCrearUsuarioNull() {
        Usuario usuario = null;

        usuarioDAO.crearUsuario(usuario);

        verify(mockEntityManager, never()).persist(usuario);
    }
    
    @Test
    public void testCrearUsuarioConCampoObligatorioNull() {
        Usuario usuario = new Usuario(null, "usuarioTest", null); // Nombre es null

        // Ejecutar la creación del usuario
        usuarioDAO.crearUsuario(usuario);

        // Verificar que el EntityManager no persiste el usuario
        verify(mockEntityManager, never()).persist(usuario);
    }

    @Test
    public void testIniciarSesionCorrectamente() {
        Usuario usuarioCreado = new Usuario();
        usuarioCreado.setId(10L);
        usuarioCreado.setUser("Juan Creado");
        usuarioCreado.setContra("1234");

        TypedQuery<Usuario> mockTypedQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery(anyString(), eq(Usuario.class))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setParameter(eq("user"), eq("Juan Creado"))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setParameter(eq("contra"), eq("1234"))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getSingleResult()).thenReturn(usuarioCreado);

        Usuario usuarioResultado = usuarioDAO.IniciarSesión(usuarioCreado);

        verify(mockEntityTransaction).begin();
        verify(mockEntityTransaction).commit();

        Assertions.assertNotNull(usuarioResultado);
        Assertions.assertEquals("Juan Creado", usuarioResultado.getUser());
        Assertions.assertEquals("1234", usuarioResultado.getContra());
    }
    
    
    
    @Test
    public void testIniciarSesionConCredencialesIncorrectas() {
        Usuario usuario = new Usuario();
        usuario.setUser("usuarioInexistente");
        usuario.setContra("contraseñaIncorrecta");

        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("user"), eq("usuarioInexistente"))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("contra"), eq("contraseñaIncorrecta"))).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException("No result"));


        Usuario result = usuarioDAO.IniciarSesión(usuario);

        assertNull(result, "El usuario no debería existir con esas credenciales");
    }

    @Test
    public void testConsultarListaCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);
        Tarea tarea1 = new Tarea();

        tarea1.setId(10L);
        tarea1.setNombre("Juan Tarea");
        tarea1.setDescripcion("Tarea");
        tarea1.setEstado(Estado.PENDIENTES);
        tarea1.setUsuario(usuario);

        Tarea tarea2 = new Tarea();
        tarea2.setId(11L);
        tarea2.setNombre("Juan Tarea 2");
        tarea2.setDescripcion("Tarea 2");
        tarea2.setEstado(Estado.PENDIENTES);
        tarea2.setUsuario(usuario);

        List<Tarea> tareasSimuladas = new ArrayList<>();
        tareasSimuladas.add(tarea1);
        tareasSimuladas.add(tarea2);

        TypedQuery<Tarea> mockTypedQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery(anyString(), eq(Tarea.class))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setParameter("usuarioId", usuario)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(tareasSimuladas);

        List<Tarea> tareasEncontradas = usuarioDAO.consultarLista(usuario);

        verify(mockEntityTransaction).begin();
        verify(mockEntityTransaction).commit();

        Assertions.assertNotNull(tareasEncontradas);
        Assertions.assertEquals(tareasSimuladas, tareasEncontradas);
    }
    
    @Test
    public void testConsultarListaTareasConUsuarioInexistente() {
        Usuario usuario = new Usuario();
        usuario.setId(999L); // ID inexistente

        // Simular la consulta con un ID inexistente
        List<Tarea> tareasSimuladas = new ArrayList<>();
        TypedQuery<Tarea> queryMock = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(anyString(), eq(Tarea.class))).thenReturn(queryMock);
        when(queryMock.setParameter("usuarioId", usuario)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(tareasSimuladas);

        List<Tarea> tareasEncontradas = usuarioDAO.consultarLista(usuario);

        // Verificar que la lista de tareas está vacía
        assertNotNull(tareasEncontradas);
        assertTrue(tareasEncontradas.isEmpty(), "La lista de tareas debe estar vacía");
    }

    @Test
    public void testConsultarEstadoCompletadoCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);

        Tarea tarea1 = new Tarea();
        tarea1.setId(10L);
        tarea1.setEstado(Estado.COMPLETADAS);
        tarea1.setUsuario(usuario);

        Tarea tarea2 = new Tarea();
        tarea2.setId(10L);
        tarea2.setEstado(Estado.COMPLETADAS);
        tarea2.setUsuario(usuario);

        List<Tarea> tareasSimuladas = new ArrayList<>();
        tareasSimuladas.add(tarea1);
        tareasSimuladas.add(tarea2);

        TypedQuery<Tarea> mockTypedQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery(anyString(), eq(Tarea.class))).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setParameter("usuario", usuario)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setParameter("estado", Estado.COMPLETADAS)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(tareasSimuladas);

        List<Tarea> tareasEncontradas = usuarioDAO.consultarListaEstadoCompletado(usuario, Estado.COMPLETADAS);

        verify(mockEntityTransaction).begin();
        verify(mockEntityTransaction).commit();

        Assertions.assertNotNull(tareasEncontradas);
        Assertions.assertEquals(tareasSimuladas, tareasEncontradas);
    }
    
    @Test
    public void testConsultarListaSinTareas() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);

        // Simula la consulta de tareas para un usuario que no tiene tareas asignadas
        List<Tarea> tareasSimuladas = new ArrayList<>();
        TypedQuery<Tarea> queryMock = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(anyString(), eq(Tarea.class))).thenReturn(queryMock);
        when(queryMock.setParameter("usuarioId", usuario)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(tareasSimuladas);

        List<Tarea> tareasEncontradas = usuarioDAO.consultarLista(usuario);

        assertNotNull(tareasEncontradas);
        assertTrue(tareasEncontradas.isEmpty(), "La lista de tareas debe estar vacía");
    }
    
    @Test
    public void testIniciarSesion_NoResultException() {
        Usuario usuarioMock = new Usuario("testUser", "testPassword",null);
        TypedQuery<Usuario> queryMock = Mockito.mock(TypedQuery.class);

        when(mockEntityManager.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(NoResultException.class);

        Usuario result = usuarioDAO.IniciarSesión(usuarioMock);

        assertNull(result, "Debe devolver null si no se encuentra el usuario.");

        // Verificar que la transacción comenzó
        verify(mockEntityTransaction).begin();
        // Verificar que commit no se llama
        verify(mockEntityTransaction, never()).commit();
        // Verificar que el EntityManager fue cerrado
        verify(mockEntityManager).close();
    }
    
    @Test
    public void testConsultarLista_GenericException() {
        Usuario usuarioMock = new Usuario("testUser", "testPassword",null);
        TypedQuery<Tarea> queryMock = Mockito.mock(TypedQuery.class);

        when(mockEntityManager.createQuery(anyString(), eq(Tarea.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);

        // Simula que la transacción está activa
        when(mockEntityTransaction.isActive()).thenReturn(true);

        // Simula una excepción al obtener resultados
        when(queryMock.getResultList()).thenThrow(RuntimeException.class);

        List<Tarea> tareas = usuarioDAO.consultarLista(usuarioMock);

        assertNull(tareas, "Debe devolver null si ocurre una excepción genérica.");

        // Verificar que se comenzó la transacción
        verify(mockEntityTransaction).begin();
        // Verificar que se llama a rollback debido a la excepción
        verify(mockEntityTransaction).rollback();
        // Verificar que el EntityManager fue cerrado
        verify(mockEntityManager).close();
    }
    
    @Test
    public void testConsultarListaEstadoCompletado_GenericException() {
        Usuario usuarioMock = new Usuario("testUser", "testPassword",null);
        Estado estadoMock= Estado.COMPLETADAS;
        TypedQuery<Tarea> queryMock = Mockito.mock(TypedQuery.class);

        when(mockEntityManager.createQuery(anyString(), eq(Tarea.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);

        // Simula que la transacción está activa
        when(mockEntityTransaction.isActive()).thenReturn(true);

        // Simula una excepción genérica
        when(queryMock.getResultList()).thenThrow(RuntimeException.class);

        List<Tarea> tareas = usuarioDAO.consultarListaEstadoCompletado(usuarioMock, estadoMock);

        assertNull(tareas, "Debe devolver null si ocurre una excepción genérica.");

        // Verificar que se comenzó la transacción
        verify(mockEntityTransaction).begin();
        // Verificar que se llama a rollback debido a la excepción
        verify(mockEntityTransaction).rollback();
        // Verificar que el EntityManager fue cerrado
        verify(mockEntityManager).close();
    }
    
    @Test
    public void testIniciarSesion_GenericException() {
        Usuario usuarioMock = new Usuario("testUser", "testPassword",null);
        TypedQuery<Usuario> queryMock = Mockito.mock(TypedQuery.class);

        when(mockEntityManager.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);

        // Simula que la transacción está activa
        when(mockEntityTransaction.isActive()).thenReturn(true);

        // Simula una excepción genérica durante la ejecución de la consulta
        when(queryMock.getSingleResult()).thenThrow(RuntimeException.class);

        Usuario resultado = usuarioDAO.IniciarSesión(usuarioMock);

        assertNull(resultado, "Debe devolver null si ocurre una excepción genérica.");

        // Verificar que se inició la transacción
        verify(mockEntityTransaction).begin();
        // Verificar que se llama a rollback debido a la excepción
        verify(mockEntityTransaction).rollback();
        // Verificar que el EntityManager fue cerrado
        verify(mockEntityManager).close();
    }
    
}
