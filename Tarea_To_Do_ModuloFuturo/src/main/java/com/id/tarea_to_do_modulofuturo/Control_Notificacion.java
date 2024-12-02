/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.id.tarea_to_do_modulofuturo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import tareas_to_do_persistencia.daos.Notificacion_dao;
import tareas_to_do_persistencia.entity_class.Notificacion;
import tarea_to_do_dto.dto.Notificacion_DTO;
import tarea_to_do_convertidores.convertidores.Convertidor;
import tarea_to_do_dto.dto.Usuario_DTO;

/**
 *
 * @author JESUS
 */
public class Control_Notificacion {
    public Convertidor conv;
    public Notificacion_dao noti;
    
    public Control_Notificacion(){
        noti= new Notificacion_dao();
        conv=new Convertidor();
    }
    
    public List<Notificacion_DTO> obtenerNotificaciones(Usuario_DTO usuario){
        List<Notificacion> notificaciones = noti.generarNotificacionesProximas(conv.convUsuario(usuario));
        List<Notificacion_DTO> notificacionesDTO = new ArrayList<>();

        for (Notificacion notificacion : notificaciones) {
            Notificacion_DTO dto = conv.convNotificacion_DTO(notificacion);
            notificacionesDTO.add(dto);
        }

        return notificacionesDTO;
    }
    
    public String generarMensajeNotificacion(Usuario_DTO usuario) {
        List<Notificacion_DTO> notificaciones = obtenerNotificaciones(usuario);

        if (notificaciones.isEmpty()) {
            return ""; 
        } else if (notificaciones.size() == 1) {
            String mensajeOriginal = notificaciones.get(0).getMensaje();
            String horaVencimiento = extraerHora(mensajeOriginal); 
            return "Tiene una tarea pendiente: Vence a las " + horaVencimiento;
        } else {
            return "Tiene " + notificaciones.size() + " tareas pendientes.";
        }
    }

    private String extraerHora(String mensaje) {
        try {
      
            int inicioFecha = mensaje.indexOf("vence a las") + 12; 
            String fechaCompleta = mensaje.substring(inicioFecha).trim();

            // Convertir la fecha completa a un formato más legible
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            SimpleDateFormat formatoSalida = new SimpleDateFormat("HH:mm:ss", Locale.US); 
            Date fecha = formatoEntrada.parse(fechaCompleta); 
            return formatoSalida.format(fecha); 
        } catch (Exception e) {
            e.printStackTrace(); 
            return "Hora no disponible"; 
        }
    }
    
}
