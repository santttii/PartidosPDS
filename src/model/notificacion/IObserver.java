package model.notificacion;

import model.partido.Partido;

public interface IObserver {
    void serNotificado(Partido partido, Notification noti);
}