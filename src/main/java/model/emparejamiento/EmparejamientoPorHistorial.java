package main.java.model.emparejamiento;

import main.java.model.partido.Jugador;
import main.java.model.partido.Partido;

public class EmparejamientoPorHistorial implements IEstrategiaEmparejamiento {

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
