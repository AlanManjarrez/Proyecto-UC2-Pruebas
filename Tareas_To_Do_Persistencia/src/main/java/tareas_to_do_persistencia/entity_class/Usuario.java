
package tareas_to_do_persistencia.entity_class;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
@Entity(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user", nullable = false, length = 100)
    private String user;

    @Column(name = "contraseña", nullable = false, length = 100)
    private String contra;
    
    @OneToMany(mappedBy = "usuario")
    private List<Tarea> tareas;

    public Usuario() {
    }

    public Usuario(String user, String contra, List<Tarea> tareas) {
        this.user = user;
        this.contra = contra;
        this.tareas = tareas;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Usuario)) return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(user, usuario.user) &&
               Objects.equals(contra, usuario.contra);
    }
    
}
