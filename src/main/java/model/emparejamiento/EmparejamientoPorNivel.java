package model.emparejamiento;

import model.partido.Jugador;
import model.partido.Partido;

public class EmparejamientoPorNivel implements IEstrategiaEmparejamiento {
    private Jugador.NivelJugador nivelRequerido;

    public EmparejamientoPorNivel(Jugador.NivelJugador nivelJugadores) {
        setNivelJugadores(nivelJugadores);
    }

    public Jugador.NivelJugador getNivelJugadores() {
        return nivelRequerido;
    }

    public void setNivelJugadores(Jugador.NivelJugador nivelJugadores) {
        this.nivelRequerido = nivelJugadores;
    }

    @Override
    public boolean esApto(Jugador jugador, Partido partido) {
        if (jugador.getNivel() == nivelRequerido) {
            return true;
        }
        return false;
    }
}
