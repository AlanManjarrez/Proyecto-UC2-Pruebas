
package tarea_to_do_dto.dto;

import java.util.Objects;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
public class Tarea_DTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Estado_DTO estado;
    private Usuario_DTO usuario;
    
    public Tarea_DTO() {
    }

    public Tarea_DTO(Long id, String nombre, String descripcion, Estado_DTO estado,Usuario_DTO usuario) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuario=usuario;
    }

    public Tarea_DTO(String nombre, String descripcion, Estado_DTO estado,Usuario_DTO usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado_DTO getEstado() {
        return estado;
    }

    public void setEstado(Estado_DTO estado) {
        this.estado = estado;
    }

    public Usuario_DTO getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_DTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "TareaDTO{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", estado=" + estado + '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Tarea_DTO tareaDTO = (Tarea_DTO) obj;
        return Objects.equals(nombre, tareaDTO.nombre) &&
               Objects.equals(descripcion, tareaDTO.descripcion) &&
               estado == tareaDTO.estado &&
               Objects.equals(usuario, tareaDTO.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, descripcion, estado, usuario);
    }
}
