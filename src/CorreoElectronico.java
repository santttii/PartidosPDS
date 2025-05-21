public class CorreoElectronico implements IEstrategiaNotificacion {
    private IAdapterPushNotification adapter;

    public CorreoElectronico(IAdapterPushNotification adapter) {
        setAdapter(adapter);
    }

    public void EnviarNotificacion(Notification notificacion) {
        // codear estrategia con CORREO
    }

    public IAdapterPushNotification getAdapter() {
        return adapter;
    }

    public void setAdapter(IAdapterPushNotification adapter) {
        this.adapter = adapter;
    }
}