
package tarea_to_do_dto.dto;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
public class Usuario_DTO {
    private Long id;
    private String user;
    private String contra;
    private List<Tarea_DTO> tareas;

    public Usuario_DTO() {
    }

    public Usuario_DTO(Long id, String user, String contra, List<Tarea_DTO> tareas) {
        this.id = id;
        this.user = user;
        this.contra = contra;
        this.tareas = tareas;
    }

    public Usuario_DTO(String user, String contra, List<Tarea_DTO> tareas) {
        this.user = user;
        this.contra = contra;
        this.tareas = tareas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Tarea_DTO> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea_DTO> tareas) {
        this.tareas = tareas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user,contra);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) return false; 
        Usuario_DTO usuario=(Usuario_DTO) obj;
        return Objects.equals(user, usuario.user) &&
                Objects.equals(contra, usuario.contra);
    }
    
    

    @Override
    public String toString() {
        return "UsuarioDTO{" + "id=" + id + ", user=" + user + ", contra=" + contra + ", tareas=" + tareas + '}';
    }
}
