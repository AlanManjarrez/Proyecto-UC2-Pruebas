/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tareas_to_do_persistencia.daos;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
public class Tarea_dao {
    
    private EntityManagerFactory entityManagerFactory;
    
    public Tarea_dao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("conexionPU");
    }

    // Constructor para pruebas (permite inyectar mocks)
    public Tarea_dao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    public Tarea crearTarea(Tarea tarea) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Usuario usuarioExistente = entityManager.find(Usuario.class, tarea.getUsuario().getId());
            if (usuarioExistente == null) {
                throw new Exception("Usuario no encontrado");
            }

            tarea.setUsuario(usuarioExistente); 

            entityManager.getTransaction().begin();
            entityManager.persist(tarea);
            entityManager.getTransaction().commit();
            return tarea;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        finally{
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }
    
    public Tarea editarTarea(Tarea tarea) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tarea tareaEncontrada = entityManager.find(Tarea.class, tarea.getId());
            
            if (tareaEncontrada != null) {
                tareaEncontrada.setDescripcion(tarea.getDescripcion());
                tareaEncontrada.setEstado(tarea.getEstado());
                tareaEncontrada.setNombre(tarea.getNombre());
            }
            
            entityManager.merge(tareaEncontrada);
            entityManager.getTransaction().commit();
            
            return tareaEncontrada;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        finally{
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }
    
    public Boolean eliminarrTarea(Tarea tarea) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tarea tareaEncontrada = entityManager.find(Tarea.class, tarea.getId());
            if (tareaEncontrada != null) {
                entityManager.remove(tareaEncontrada);
                entityManager.getTransaction().commit();
                return true;
            } else {
                
                entityManager.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return false;
    }
    
    public Tarea cambiarEstado(Tarea tarea, Estado tipoEstado) {
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Tarea tareaEncontrada = entityManager.find(Tarea.class, tarea.getId());
            if (tareaEncontrada == null) {
                entityManager.getTransaction().rollback();
                return null;
            }

            tareaEncontrada.setEstado(tipoEstado);
            Tarea tareaActualizada = entityManager.merge(tareaEncontrada);

            entityManager.getTransaction().commit();
            return tareaActualizada;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error al cambiar el estado de la tarea", e);
        } finally {
            entityManager.close();
        }
       
    }
}
