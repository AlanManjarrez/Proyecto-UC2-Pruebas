
package tarea_to_do_convertidores.convertidores;

import tarea_to_do_dto.dto.Estado_DTO;
import tarea_to_do_dto.dto.Tarea_DTO;
import tarea_to_do_dto.dto.Usuario_DTO;
import tareas_to_do_persistencia.entity_class.Estado;
import tareas_to_do_persistencia.entity_class.Tarea;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
public class Convertidor {
    
    public Convertidor(){
    }
    
    public Usuario_DTO convUsuarioDTO(Usuario usuario){
        if (usuario == null) {
            return null;
        }

        Usuario_DTO usuarioDTO = new Usuario_DTO(
            usuario.getId(),
            usuario.getUser(),
            usuario.getContra(),
            null 
        );
        
        return usuarioDTO;
    }

    public Usuario convUsuario(Usuario_DTO usuarioDTO){
        if (usuarioDTO == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setUser(usuarioDTO.getUser());
        usuario.setContra(usuarioDTO.getContra());

        return usuario;
    }
    
    public Tarea_DTO convTareaDTO(Tarea tarea){
        if (tarea == null) {
            return null;
        }
        
        Usuario_DTO usuarioDTO = convUsuarioDTO(tarea.getUsuario());
        Estado_DTO estadoDTO = Estado_DTO.valueOf(tarea.getEstado().name());
        
        
        return new Tarea_DTO(
            tarea.getId(),
            tarea.getNombre(),
            tarea.getDescripcion(),
            estadoDTO,
            usuarioDTO
        );
    }
    
    public Tarea convTarea(Tarea_DTO tareaDTO){
        if (tareaDTO == null) {
            return null;
        }
        
        Estado estado = Estado.valueOf(tareaDTO.getEstado().name());
        
        Tarea tarea = new Tarea();
        tarea.setId(tareaDTO.getId());
        tarea.setNombre(tareaDTO.getNombre());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setEstado(estado);
        
        Usuario usuario = convUsuario(tareaDTO.getUsuario());
        tarea.setUsuario(usuario);
        
        return tarea;
    }
    
    public Estado convertiEstado(Estado_DTO estado){
        return Estado.valueOf(estado.name());
    }
}