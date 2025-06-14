package main.java.model.notificacion;
public class CorreoElectronico implements IEstrategiaNotificacion {
    private IAdapterEmailNotification adapter;

    public CorreoElectronico(IAdapterEmailNotification adapter) {
        setAdapter(adapter);
    }

    @Override
    public void EnviarNotificacion(Notification notificacion) {
        adapter.EnviarEmail(notificacion);
    }

    public IAdapterEmailNotification getAdapter() {
        return adapter;
    }

    public void setAdapter(IAdapterEmailNotification adapter) {
        this.adapter = adapter;
    }
}