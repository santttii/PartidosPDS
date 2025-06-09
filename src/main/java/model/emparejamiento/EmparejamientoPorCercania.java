package main.java.model.emparejamiento;

import main.java.model.partido.Jugador;
import main.java.model.partido.Partido;

public class EmparejamientoPorCercania implements IEstrategiaEmparejamiento {
    private String zonaRequerida;

    public EmparejamientoPorCercania(String zonaRequerida) {
        setZonaRequerida(zonaRequerida);
    }

    public String getZonaRequerida() {
        return zonaRequerida;
    }

    public void setZonaRequerida(String zonaRequerida) {
        this.zonaRequerida = zonaRequerida;
    }    

    @Override
    public boolean esApto(Jugador jugador, Partido partido) {
        if (jugador.getUbicacion().equals(partido.getUbicacion())) {
            return true;
        }
        return false;
    }
}
