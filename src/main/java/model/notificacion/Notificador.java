package model.notificacion;

import java.io.Serializable;

public class Notificador implements Serializable {
    private static final long serialVersionUID = 1L;

    private IEstrategiaNotificacion estrategia;

    public Notificador(IEstrategiaNotificacion estrategia) {
        setEstrategia(estrategia);
    }

    public IEstrategiaNotificacion getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(IEstrategiaNotificacion estrategia) {
        this.estrategia = estrategia;
    }

    public void CambiarEstrategia(IEstrategiaNotificacion nuevaEstrategia) {
        setEstrategia(nuevaEstrategia);
    }

    public void EnviarNotificacion(Notification notificacion) {
        estrategia.EnviarNotificacion(notificacion);
    }
}