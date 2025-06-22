package controller;

import model.notificacion.Notification;
import model.partido.Jugador;
import data.NotificacionDAO; // Importar el nuevo DAO

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotificacionController implements Serializable {
    private static final long serialVersionUID = 1L;
    private final NotificacionDAO gestorNotificaciones; // Cambiar a NotificacionDAO

    public NotificacionController() {
        this.gestorNotificaciones = NotificacionDAO.getInstancia(); // Obtener la instancia del DAO
        gestorNotificaciones.cargar(); // Cargar las notificaciones al inicializar
    }

    // Constructor para inyección de dependencia (útil para tests)
    public NotificacionController(NotificacionDAO gestorNotificaciones) {
        this.gestorNotificaciones = gestorNotificaciones;
        this.gestorNotificaciones.cargar(); // Cargar las notificaciones al inicializar
    }

    public void agregarNotificacion(Notification notificacion) {
        gestorNotificaciones.agregarNotificacion(notificacion);
        gestorNotificaciones.guardar(); // Guardar después de agregar
    }

    public void eliminarNotificacion(Notification notificacion) {
        gestorNotificaciones.eliminarNotificacion(notificacion);
        gestorNotificaciones.guardar(); // Guardar después de eliminar
    }

    public List<Notification> getNotificacionesPorJugador(Jugador jugador) {
        return gestorNotificaciones.obtenerTodas().stream() // Obtener todas del DAO
                .filter(n -> n.getCorreoDestinatario().equals(jugador.getEmail()))
                .collect(Collectors.toList());
    }

    public ArrayList<Notification> getNotificacionesPendientes() {
        // Asumiendo que "pendientes" son todas las que están en el DAO.
        // Si necesitas una lógica de estado de notificación (leída/no leída),
        // esa lógica debería estar en la clase Notification o filtrarse aquí.
        return new ArrayList<>(gestorNotificaciones.obtenerTodas());
    }

    // Metodo adicional para obtener el DAO (útil para tests o gestión externa si es necesario)
    public NotificacionDAO getGestorNotificaciones() {
        return gestorNotificaciones;
    }

}