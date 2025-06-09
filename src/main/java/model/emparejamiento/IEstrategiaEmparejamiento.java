package main.java.model.emparejamiento;

import main.java.model.partido.Jugador;
import main.java.model.partido.Partido;

public interface IEstrategiaEmparejamiento {
    boolean esApto(Jugador jugador, Partido partido);

}