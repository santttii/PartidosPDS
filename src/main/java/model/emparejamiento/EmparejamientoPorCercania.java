package model.emparejamiento;

import model.partido.Jugador;
import model.partido.Partido;

import java.io.Serializable;

public class EmparejamientoPorCercania implements IEstrategiaEmparejamiento, Serializable {
    private static final long serialVersionUID = 1L;
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