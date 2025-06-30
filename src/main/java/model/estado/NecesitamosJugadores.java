package model.estado;

import model.partido.Partido;

import java.io.Serializable;

public class NecesitamosJugadores implements IEstadoPartido, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public void iniciar(Partido partido) {
        System.out.println("Aún faltan jugadores. No se puede iniciar.");
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("No se puede finalizar un partido sin haber comenzado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("Partido cancelado desde estado NecesitamosJugadores.");
        partido.cambiarEstado(new Cancelado());
    }

    @Override
    public void confirmar(Partido partido) {
        if (partido.getJugadores().size() == partido.getCupoMaximo()) {
            partido.cambiarEstado(new Confirmado());
        } else {
            System.out.println("No se puede confirmar el partido: Se necesitan más jugadores");
        }
    }

    @Override
    public void jugar(Partido partido) {
        System.out.println("No se puede jugar un partido que necesita jugadores");
    }


}