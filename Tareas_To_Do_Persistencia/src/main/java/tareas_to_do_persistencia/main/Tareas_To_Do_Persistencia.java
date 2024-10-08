/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package tareas_to_do_persistencia.main;

import tareas_to_do_persistencia.daos.Tarea_dao;
import tareas_to_do_persistencia.daos.Usuario_dao;
import tareas_to_do_persistencia.entity_class.Usuario;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
public class Tareas_To_Do_Persistencia {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Usuario_dao usu=new Usuario_dao();
        Tarea_dao tare=new Tarea_dao();
        
        Usuario usuario=new Usuario("JOSH", "123", null);
        
        usu.crearUsuario(usuario);
    }
}
