package model.notificacion;

import java.io.Serializable;

public class PushNotification implements IEstrategiaNotificacion, Serializable {
    private static final long serialVersionUID = 1L;
    private IAdapterPushNotification adapter;

    public PushNotification(IAdapterPushNotification adapter) {
        setAdapter(adapter);
    }

    @Override
    public void EnviarNotificacion(Notification notificacion) {
        adapter.EnviarPushNotification(notificacion);
    }

    public IAdapterPushNotification getAdapter() {
        return adapter;
    }

    public void setAdapter(IAdapterPushNotification adapter) {
        this.adapter = adapter;
    }
}