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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
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
}
