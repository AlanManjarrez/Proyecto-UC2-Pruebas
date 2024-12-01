/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package PruebaIntegracion;

import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tareas_to_do_persistencia.daos.Usuario_dao;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author JESUS
 */
public class TestUsuarioInt {
    
    public TestUsuarioInt() {
    }
    
    Usuario_dao usuarioDAO;
    
    @BeforeEach
    public void setup(){
        usuarioDAO = new Usuario_dao();
    }
    
    @Test
    public void testCrearUsuarioDatosValidos(){
        //Configurar datos para crear un usuario
        Usuario usuario= new Usuario("Prueba40", "usuario", null);
        
        //Crear el usuario
        Usuario respuesta= usuarioDAO.crearUsuario(usuario);
        
        //Verificar que el usuario se creo
        assertNotNull(respuesta,"El usuario debe haberse creado");
        assertNotNull(respuesta.getId(),"La tarea debe tener un id");
        assertEquals(usuario, respuesta);
    }
    
    @Test
    public void testCrearUsuarioConUsuarioYaExistente(){
        //Configurar datos para crear un usuario
        Usuario usuario= new Usuario("Prueba", "usuario", null);
        
        //Intentar crear el usuario
        Usuario respuesta = usuarioDAO.crearUsuario(usuario);
        
        //Verificar que el usuario no se crea
        assertNull(respuesta,"Deberia ser nulo");
    }
    
    @Test
    public void testIniciarSesionDatosValidos(){
        //Configurar un usuario existente
        Usuario usuario=new Usuario();
        usuario.setUser("Prueba");
        usuario.setContra("usuario");
        
        //Iniciar sesion
        Usuario respuesta = usuarioDAO.IniciarSesión(usuario);
        
        assertNotNull(respuesta, "El usuario debe haber iniciado sesión");
        assertNotNull(respuesta.getId(), "El usuario debe tener un id");

        // Comparar explícitamente con el método equals
        assertTrue(usuario.equals(respuesta), "Los usuarios deberían ser iguales");

        // Asegurarse de que el nombre de usuario y la contraseña coincidan
        assertEquals("Prueba", respuesta.getUser(), "El nombre de usuario debe ser 'JOSH'");
        assertEquals("usuario", respuesta.getContra(), "La contraseña debe ser '123'");
        
    }
    
    @Test
    public void testIniciarSesionDatosInvalidos(){
        //Configurar un usuario existente
        Usuario usuario=new Usuario();
        usuario.setUser("JOSHas");
        usuario.setContra("123");
        
        //Iniciar sesion
        Usuario respuesta = usuarioDAO.IniciarSesión(usuario);
        
        //Verificar que el usuario no hay iniciado sesion
        assertNull(respuesta,"Deberia ser nulo");
    }
    
    
    @Test
    public void testConsultarListaConTareas(){
        //Configurar un usuario existente
        Usuario usuario=new Usuario();
        usuario.setId(1L);
        usuario.setUser("jesus");
        usuario.setContra("123");
        
        //Obtener la lista de las tarea del usuario
        List<Tarea> tareas= usuarioDAO.consultarLista(usuario);
        
        //Verficar que la lista trae las tareas del usuario
        assertNotNull(tareas);
        assertFalse(tareas.isEmpty(),"Deberia de tener al menos una tarea");
        assertTrue(tareas.size() > 0, "La lista de tareas debe tener al menos una tarea");
    }
    
    @Test
    public void testConsultarListaSinTareas(){
        //Configurar un usuario existen sin tareas
        Usuario usuario=new Usuario();
        usuario.setId(111L);
        usuario.setUser("Pruebas");
        usuario.setContra("usuario");
        
        //Obtener la lista de tareas
        List<Tarea> tareas=usuarioDAO.consultarLista(usuario);
        
        //Verificar que la lista no trae ninguna tarea
        assertTrue(tareas.isEmpty(),"La lista deberia estar vacia");
        assertEquals(0, tareas.size(), "La lista de tareas debe tener tamaño 0");
    }
    
    @Test
    public void testConsultarListaConTareasPorUnEstado(){
        //Configurar un usuario existente con tareas
        Usuario usuario=new Usuario();
        usuario.setId(1L);
        usuario.setUser("jesus");
        usuario.setContra("123");
        
        //Obtener la lista
        List<Tarea> tareas= usuarioDAO.consultarListaEstadoCompletado(usuario, Estado.COMPLETADAS);
        
        //Verificar que la lista tenga la tarea
        assertNotNull(tareas,"La lista deberia tener tareas");
        assertFalse(tareas.isEmpty(), "La lista de tareas no debe estar vacía");
    }
    
    @Test
    public void testConsultarListaDeTareasPorUnEstadoQueNotieneEsasTareas(){
        //Configurar un usuario existen con tareas
        Usuario usuario=new Usuario();
        usuario.setId(1L);
        usuario.setUser("jesus");
        usuario.setContra("123");
        
        //Obtener la lista
        List<Tarea> tareas= usuarioDAO.consultarListaEstadoCompletado(usuario, Estado.PENDIENTES);
        
        //Verificar que la lista no tenga esas tareas
        assertTrue(tareas.isEmpty(),"La lista deberia estar vacia");
        assertEquals(0, tareas.size(), "La lista de tareas debe tener tamaño 0");
    }
    
    @Test
    void testEquals_SameObject() {
        Usuario usuario = new Usuario("user1", "password1",null);
        assertTrue(usuario.equals(usuario)); // Mismo objeto
    }

    @Test
    void testEquals_NullObject() {
        Usuario usuario = new Usuario("user1", "password1",null);
        assertFalse(usuario.equals(null)); // Comparación con null
    }

    @Test
    void testEquals_DifferentClass() {
        Usuario usuario = new Usuario("user1", "password1",null);
        String otherObject = "Not a Usuario";
        assertFalse(usuario.equals(otherObject)); // Diferente clase
    }

    @Test
    void testEquals_EqualObjects() {
        Usuario usuario1 = new Usuario("user1", "password1",null);
        Usuario usuario2 = new Usuario("user1", "password1",null);
        assertTrue(usuario1.equals(usuario2)); // Atributos iguales
    }

    @Test
    void testEquals_DifferentUser() {
        Usuario usuario1 = new Usuario("user1", "password1",null);
        Usuario usuario2 = new Usuario("user2", "password1",null);
        assertFalse(usuario1.equals(usuario2)); // Diferente user
    }

    @Test
    void testEquals_DifferentPassword() {
        Usuario usuario1 = new Usuario("user1", "password1",null);
        Usuario usuario2 = new Usuario("user1", "password2",null);
        assertFalse(usuario1.equals(usuario2)); // Diferente contra
    }
    
}
