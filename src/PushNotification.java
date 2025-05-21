public class PushNotification implements IEstrategiaNotificacion {
    private IAdapterPushNotification adapter;

    public PushNotification(IAdapterPushNotification adapter) {
        setAdapter(adapter);
    }

    public void EnviarNotificacion(Notificacion notificacion) {
        // codear estrategia con CORREO
    }

    public IAdapterPushNotification getAdapter() {
        return adapter;
    }

    public void setAdapter(IAdapterPushNotification adapter) {
        this.adapter = adapter;
    }
}