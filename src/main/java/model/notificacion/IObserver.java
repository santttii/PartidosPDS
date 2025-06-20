package model.notificacion;

import model.partido.Partido;

public interface IObserver {
    String getEmail();
    String getTokenFCM();
    void serNotificado(Partido partido, Notification noti);
}