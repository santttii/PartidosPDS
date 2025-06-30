package model.notificacion;

import model.estado.IEstadoPartido;
import model.partido.Deporte;

public class Notification {

    private IEstadoPartido estado;
    private Deporte deporte;
    private String correoDestinatario;
    private String tokenFCM;
    private String mensajePersonalizado; // Nuevo campo

    // Constructor original
    public Notification(IEstadoPartido estado, Deporte deporte, String correoDestinatario, String tokenFCM) {
        setEstado(estado);
        setDeporte(deporte);
        setCorreoDestinatario(correoDestinatario);
        setTokenFCM(tokenFCM);
        this.mensajePersonalizado = null;
    }

    // Nuevo constructor para notificaciones generales
    public Notification(String mensajePersonalizado, Deporte deporte, String correoDestinatario, String tokenFCM) {
        this.estado = null;
        setDeporte(deporte);
        setCorreoDestinatario(correoDestinatario);
        setTokenFCM(tokenFCM);
        this.mensajePersonalizado = mensajePersonalizado;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    // Modificado para soportar mensaje personalizado
    public String TextoNotificacion() {
        if (mensajePersonalizado != null) {
            return mensajePersonalizado;
        }
        if (estado != null) {
            return "El partido cambio su estado a " + estado.getClass().getSimpleName() + ".";
        }
        return "Tienes una nueva notificación.";
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