/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea_to_do_control_usuario.negocio;

import java.util.ArrayList;
import java.util.List;
import tarea_to_do_convertidores.convertidores.Convertidor;
import tarea_to_do_dto.dto.Estado_DTO;
import tarea_to_do_dto.dto.Tarea_DTO;
import tarea_to_do_dto.dto.Usuario_DTO;
import tareas_to_do_persistencia.daos.Usuario_dao;
import tareas_to_do_persistencia.entity_class.Tarea;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
public class Control_Usuario {
    Usuario_dao usu;
    Convertidor conv;
    
    public Control_Usuario(){
        usu=new Usuario_dao();
        conv=new Convertidor();
    }
    
    public Usuario_DTO crearUsuario(Usuario_DTO usuario){
        Usuario_DTO usua= conv.convUsuarioDTO(this.usu.crearUsuario(conv.convUsuario(usuario)));
        return usua;
    }
    
    public Usuario_DTO iniciarSesion(Usuario_DTO usuario){
        Usuario_DTO usua = conv.convUsuarioDTO(this.usu.IniciarSesión(conv.convUsuario(usuario)));
        return usua;
    }
    
    public List<Tarea_DTO> listaTareaUsuario(Usuario_DTO usuario){
        List<Tarea> listaTareas = usu.consultarLista(conv.convUsuario(usuario));
        List<Tarea_DTO> listaTareaDTO = new ArrayList<>();
        
        for (Tarea tarea : listaTareas) {
            listaTareaDTO.add(conv.convTareaDTO(tarea));
        }

        return listaTareaDTO;
    }
    
    public List<Tarea_DTO> listaTareaEstado(Usuario_DTO usuario,Estado_DTO estado){
        List<Tarea> listaTareas = usu.consultarListaEstadoCompletado(conv.convUsuario(usuario), conv.convertiEstado(estado));
        List<Tarea_DTO> listaTareaDTO = new ArrayList<>();
        
        for (Tarea tarea : listaTareas) {
            listaTareaDTO.add(conv.convTareaDTO(tarea));
        }

        return listaTareaDTO;
    }
}
