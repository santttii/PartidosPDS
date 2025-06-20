package model.notificacion;

import model.estado.IEstadoPartido;
import model.partido.Deporte;

public class Notification {

    private IEstadoPartido estado;
    private Deporte deporte;
    private String correoDestinatario;
    private String tokenFCM;

    public Notification(IEstadoPartido estado, Deporte deporte, String correoDestinatario,  String tokenFCM) {
        setEstado(estado);
        setDeporte(deporte);
        setCorreoDestinatario(correoDestinatario);
        setTokenFCM(tokenFCM);
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

    public String getCorreoDestinatario() {
        return correoDestinatario;
    }

    public void setCorreoDestinatario(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }

    public String getTokenFCM() {
        return tokenFCM;
    }

    public void setTokenFCM(String tokenFCM) {
        this.tokenFCM = tokenFCM;
    }
}