/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea_to_do_dto.dto;

import java.util.Calendar;

/**
 *
 * @author JESUS
 */
public class Notificacion_DTO {
    private Long id;
    private Usuario_DTO usuario;
    private String mensaje;
    private Calendar fecCalendar;

    public Notificacion_DTO() {
    }

    public Notificacion_DTO(Usuario_DTO usuario, String mensaje, Calendar fecCalendar) {
        this.usuario = usuario;
        this.mensaje = mensaje;
        this.fecCalendar = fecCalendar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario_DTO getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_DTO usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Calendar getFecCalendar() {
        return fecCalendar;
    }

    public void setFecCalendar(Calendar fecCalendar) {
        this.fecCalendar = fecCalendar;
    }
    
    
    
}
