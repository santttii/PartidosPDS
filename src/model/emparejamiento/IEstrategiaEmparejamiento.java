package model.emparejamiento;

import model.partido.Jugador;
import model.partido.Partido;

public interface IEstrategiaEmparejamiento {
    boolean esApto(Jugador jugador, Partido partido);

}