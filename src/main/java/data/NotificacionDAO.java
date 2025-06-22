package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.notificacion.Notification;
import util.Serializador;

public class NotificacionDAO implements Serializable {
    private static final String ARCHIVO_NOTIFICACIONES = "notificaciones";
    private static NotificacionDAO instancia; // ← Singleton
    private ArrayList<Notification> notificaciones;

    public NotificacionDAO() {
        this.notificaciones = new ArrayList<>();
    }

    public static NotificacionDAO getInstancia() {
        if (instancia == null) {
            instancia = new NotificacionDAO();
        }
        return instancia;
    }

    public void guardar() {
        Serializador.guardarDatos(notificaciones, ARCHIVO_NOTIFICACIONES);
    }

    @SuppressWarnings("unchecked")
    public void cargar() {
        ArrayList<Notification> cargados = Serializador.cargarDatos(ARCHIVO_NOTIFICACIONES, ArrayList.class);
        if (cargados != null) {
            this.notificaciones = cargados;
        }
    }

    /**
     * Agrega una nueva notificación a la lista
     */
    public boolean agregarNotificacion(Notification notificacion) {
        if (notificacion == null) {
            return false;
        }
        return notificaciones.add(notificacion);
    }

    /**
     * Elimina una notificación de la lista
     */
    public boolean eliminarNotificacion(Notification notificacion) {
        return notificaciones.remove(notificacion);
    }

    /**
     * Obtiene todas las notificaciones
     */
    public List<Notification> obtenerTodas() {
        return new ArrayList<>(notificaciones);
    }
}
