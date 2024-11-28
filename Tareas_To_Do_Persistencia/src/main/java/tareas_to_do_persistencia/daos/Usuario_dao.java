package tareas_to_do_persistencia.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
public class Usuario_dao {

    private EntityManagerFactory entityManagerFactory;

    public Usuario_dao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("conexionPU");
    }

    public Usuario_dao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Usuario crearUsuario(Usuario usuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            String user = usuario.getUser();
            Usuario usuarioExistente = null;
            try {
                usuarioExistente = entityManager.createQuery("SELECT U FROM usuario U WHERE U.user =:user", Usuario.class)
                        .setParameter("user", user)
                        .getSingleResult();
            } catch (NoResultException e) {

            }

            if (usuarioExistente == null) {
                entityManager.persist(usuario);
                entityManager.getTransaction().commit();
                return usuario;
            } else {
                return usuarioExistente;
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
        return null;
    }

    public Usuario IniciarSesión(Usuario usuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String contra = usuario.getContra();
            String user = usuario.getUser();
            Usuario usuarioEncontrado = entityManager.createQuery("SELECT U FROM usuario U WHERE U.user =:user AND U.contra =:contra", Usuario.class)
                    .setParameter("user", user)
                    .setParameter("contra", contra)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return usuarioEncontrado;
        } catch (NoResultException e) {
            // Manejo de caso cuando no se encuentra el usuario
            return null;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }

    public List<Tarea> consultarLista(Usuario usuario) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tarea> tareas = null;
        try {
            entityManager.getTransaction().begin();

            tareas = entityManager.createQuery("SELECT t FROM tarea t WHERE t.usuario =:usuarioId", Tarea.class)
                    .setParameter("usuarioId", usuario)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return tareas;
    }

    public List<Tarea> consultarListaEstadoCompletado(Usuario usuario, Estado estado) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            List<Tarea> tareas = entityManager.createQuery(
                    "SELECT T FROM tarea T WHERE T.usuario = :usuario AND T.estado = :estado", Tarea.class)
                    .setParameter("usuario", usuario)
                    .setParameter("estado", estado)
                    .getResultList();

            entityManager.getTransaction().commit();
            return tareas;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return null;
    }
}
