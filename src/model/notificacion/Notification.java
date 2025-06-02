package model.notificacion;

import model.estado.IEstadoPartido;
import model.partido.Deporte;

public class Notification {

    private IEstadoPartido estado;
    private Deporte deporte;

    public Notification(IEstadoPartido estado, Deporte deporte) {
        setEstado(estado);
        setDeporte(deporte);
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }


    public String TextoNotificacion() {
        return "El partido cambio su estado a " + estado.getClass().getSimpleName() + ".";
    }

    public void setEstado(IEstadoPartido estado) {
        this.estado = estado;
    }
}