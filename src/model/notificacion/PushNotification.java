package model.notificacion;

public class PushNotification implements IEstrategiaNotificacion {
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