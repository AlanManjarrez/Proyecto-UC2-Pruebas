
package tareas_to_do_persistencia.entity_class;



import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jose Alan Manjarrez Ontiveros 228982
 * @author Jesus Eduardo Villanueva Godoy 235078
 */
@Entity(name = "tarea")
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "estado", nullable = false)
    private Estado estado;
    
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar fecha;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
   
    public Tarea() {
    }

    public Tarea(String nombre, String descripcion, Estado estado, Calendar fecha, Usuario usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.usuario = usuario;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
