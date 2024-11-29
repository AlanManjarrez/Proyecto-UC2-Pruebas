package tarea_to_do_control_tarea.negocio;

import tarea_to_do_convertidores.convertidores.Convertidor;
import tarea_to_do_dto.dto.Estado_DTO;
import tarea_to_do_dto.dto.Tarea_DTO;
import tareas_to_do_persistencia.daos.Tarea_dao;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
public class Control_Tarea {

    public Tarea_dao tarea;
    public Convertidor conv;

    public Control_Tarea() {
        tarea = new Tarea_dao();
        conv = new Convertidor();
    }

    public Tarea_DTO crearTarea(Tarea_DTO tarea) {
        Tarea_DTO tar = conv.convTareaDTO(this.tarea.crearTarea(conv.convTarea(tarea)));
        return tar;
    }

    public Tarea_DTO editarTarea(Tarea_DTO tarea) {
        Tarea_DTO tar = conv.convTareaDTO(this.tarea.editarTarea(conv.convTarea(tarea)));
        return tar;
    }

    public Boolean eliminarTarea(Tarea_DTO tarea) {
        boolean bandera = this.tarea.eliminarrTarea(conv.convTarea(tarea));
        return bandera;
    }

    public Tarea_DTO cambiarEstado(Tarea_DTO tarea, Estado_DTO estado) {

        Tarea_DTO tar = conv.convTareaDTO(this.tarea.cambiarEstado(conv.convTarea(tarea), conv.convertiEstado(estado)));
        return tar;
    }
}
