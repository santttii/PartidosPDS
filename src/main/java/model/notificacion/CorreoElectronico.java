package model.notificacion;
import model.notificacion.Notification;
import model.notificacion.IAdapterEmailNotification;
import model.notificacion.IEstrategiaNotificacion;

import java.io.Serializable;

public class CorreoElectronico implements IEstrategiaNotificacion, Serializable {
    private static final long serialVersionUID = 1L;
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