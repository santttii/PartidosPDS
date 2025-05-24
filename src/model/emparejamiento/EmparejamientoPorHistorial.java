package model.emparejamiento;

import model.partido.Jugador;
import model.partido.Partido;

public class EmparejamientoPorHistorial implements IEstrategiaEmparejamiento {

    @Override
    public boolean esApto(Jugador jugador, Partido partido) {
        return false;
    }
}
