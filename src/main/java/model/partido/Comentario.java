package model.partido;

import model.partido.Jugador;
import java.io.Serializable;
import java.util.Date;

public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;
    private Jugador jugador;
    private String mensaje;
    private Date fecha;

    public Comentario(Jugador jugador, String mensaje) {
        this.jugador = jugador;
        this.mensaje = mensaje;
        this.fecha = new Date();
    }

    public Jugador getJugador() {
        return jugador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Date getFecha() {
        return fecha;
    }
}


