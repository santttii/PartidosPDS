package model.emparejamiento;

import model.partido.Jugador;
import model.partido.Partido;

import java.io.Serializable;

public class EmparejamientoPorNivel implements IEstrategiaEmparejamiento, Serializable {
    private static final long serialVersionUID = 1L;
    private Jugador.NivelJugador nivelMinimo;
    private Jugador.NivelJugador nivelMaximo;
    private boolean cualquierNivel;

    // Constructor para cualquier nivel
    public EmparejamientoPorNivel() {
        this.cualquierNivel = true;
        // Inicializamos con valores por defecto aunque no se usen
        this.nivelMinimo = Jugador.NivelJugador.PRINCIPIANTE;
        this.nivelMaximo = Jugador.NivelJugador.AVANZADO;
    }

    // Constructor para un nivel específico
    public EmparejamientoPorNivel(Jugador.NivelJugador nivelRequerido) {
        if (nivelRequerido == null) {
            throw new IllegalArgumentException("El nivel requerido no puede ser nulo");
        }
        this.nivelMinimo = nivelRequerido;
        this.nivelMaximo = nivelRequerido;
        this.cualquierNivel = false;
    }

    // Constructor para rango de niveles
    public EmparejamientoPorNivel(Jugador.NivelJugador nivelMinimo, Jugador.NivelJugador nivelMaximo) {
        if (nivelMinimo == null || nivelMaximo == null) {
            throw new IllegalArgumentException("Los niveles no pueden ser nulos");
        }
        if (nivelMinimo.ordinal() > nivelMaximo.ordinal()) {
            throw new IllegalArgumentException("El nivel mínimo no puede ser mayor que el máximo");
        }
        this.nivelMinimo = nivelMinimo;
        this.nivelMaximo = nivelMaximo;
        this.cualquierNivel = false;
    }

    @Override
    public boolean esApto(Jugador jugador, Partido partido) {
        if (jugador == null) {
            return false;
        }

        Jugador.NivelJugador nivelJugador = jugador.getNivel();

        // Si el jugador no tiene nivel, solo puede unirse a partidos con cualquierNivel
        if (nivelJugador == null) {
            return cualquierNivel;
        }

        if (cualquierNivel) {
            return true;
        }

        return nivelJugador.ordinal() >= nivelMinimo.ordinal() &&
            nivelJugador.ordinal() <= nivelMaximo.ordinal();
    }

    public Jugador.NivelJugador getNivelMinimo() {
        return nivelMinimo;
    }

    public Jugador.NivelJugador getNivelMaximo() {
        return nivelMaximo;
    }

    public boolean isCualquierNivel() {
        return cualquierNivel;
    }

    @Override
    public String toString() {
        if (cualquierNivel) {
            return "Cualquier nivel permitido";
        }
        if (nivelMinimo == nivelMaximo) {
            return "Nivel requerido: " + nivelMinimo;
        }
        return "Nivel mínimo: " + nivelMinimo + ", Nivel máximo: " + nivelMaximo;
    }
}