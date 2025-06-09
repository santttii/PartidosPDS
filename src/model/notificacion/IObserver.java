package model.notificacion;

import model.partido.Partido;

public interface IObserver {
    String getEmail();

    void serNotificado(Partido partido, Notification noti);
}