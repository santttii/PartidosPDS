package main.java.model.notificacion;

import main.java.model.partido.Partido;

public interface IObserver {
    String getEmail();

    void serNotificado(Partido partido, Notification noti);
}