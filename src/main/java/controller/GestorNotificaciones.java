package main.java.controller;

import main.java.model.notificacion.Notification;
import main.java.model.partido.Jugador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorNotificaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Notification> notificaciones;

    public GestorNotificaciones() {
        notificaciones = new ArrayList<>();
    }
    
    public void agregarNotificacion(Notification notificacion) {
        notificaciones.add(notificacion);
    }

    public void eliminarNotificacion(Notification notificacion) {
        notificaciones.remove(notificacion);
    }

    public List<Notification> getNotificacionesPorJugador(Jugador jugador) {
        return notificaciones.stream()
                .filter(n -> n.getCorreoDestinatario().equals(jugador.getEmail()))
                .collect(Collectors.toList());
    }

    public ArrayList<Notification> getNotificacionesPendientes() {
        return notificaciones;
    }
    
}