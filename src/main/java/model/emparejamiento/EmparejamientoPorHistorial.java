package model.emparejamiento;

import model.partido.Jugador;
import model.partido.Partido;

import java.io.Serializable;

public class EmparejamientoPorHistorial implements IEstrategiaEmparejamiento, Serializable {
    private static final long serialVersionUID = 1L;

    private int cantidadPartidosJugadosRequeridos;

    public EmparejamientoPorHistorial(int cantidadPartidosJugadosRequeridos) {
        setCantidadPartidosJugadosRequeridos(cantidadPartidosJugadosRequeridos);
    }

    public int getCantidadPartidosJugadosRequeridos() {
        return cantidadPartidosJugadosRequeridos;
    }

    public void setCantidadPartidosJugadosRequeridos(int cantidadPartidosJugadosRequeridos) {
        this.cantidadPartidosJugadosRequeridos = cantidadPartidosJugadosRequeridos;
    }

    @Override
    public boolean esApto(Jugador jugador, Partido partido) {
        if (jugador.getCantidadPartidosJugados() >= cantidadPartidosJugadosRequeridos) {
            return true;
        }
        return false;
    }
}