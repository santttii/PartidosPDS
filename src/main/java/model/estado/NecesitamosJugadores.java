package main.java.model.estado;

import main.java.model.partido.Partido;

public class NecesitamosJugadores implements IEstadoPartido {
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
}
