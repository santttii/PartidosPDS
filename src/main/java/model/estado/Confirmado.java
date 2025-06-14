package main.java.model.estado;

import main.java.model.partido.Partido;

public class Confirmado implements IEstadoPartido {
    @Override
    public void iniciar(Partido partido) {
        System.out.println("Partido iniciado.");
        partido.cambiarEstado(new EnJuego());
    }

    @Override
    public void finalizar(Partido partido) {
        System.out.println("No se puede finalizar un partido que no ha comenzado.");
    }

    @Override
    public void cancelar(Partido partido) {
        System.out.println("Partido cancelado.");
        partido.cambiarEstado(new Cancelado());
    }
}
